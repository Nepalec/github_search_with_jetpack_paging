package com.example.githubsearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearchapp.MainActivity
import com.example.githubsearchapp.MyApp
import com.example.githubsearchapp.databinding.FragmentSearchBinding
import com.example.githubsearchapp.model.Repo
import com.example.githubsearchapp.ui.adapters.DefaultLoadStateAdapter
import com.example.githubsearchapp.ui.adapters.ReposAdapter
import com.example.githubsearchapp.vm.AppVMFactory
import com.example.githubsearchapp.vm.SearchViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchFragment : Fragment() {

    @Inject
    lateinit var factory: AppVMFactory
    lateinit var viewModel: SearchViewModel
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupReposList()
        setupSearchInput()
        setupSwipeToRefresh()

       // binding.searchEditText.setText("php")

    }
    private fun setupReposList() {
        val adapter = ReposAdapter{ r -> openDetailScreen(r)}

        // in case of loading errors this callback is called when you tap the 'Try Again' button
        val tryAgainAction = { adapter.retry() }

        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)

        // combined adapter which shows both the list of Repos + footer indicator when loading pages
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapterWithLoadState

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction
        )

        observeRepos(adapter)
        observeLoadState(adapter)
        handleListVisibility(adapter)

    }

    private fun openDetailScreen(r: Repo) {
        (activity as MainActivity).navController.navigate(SearchFragmentDirections.actionNavigationHomeToDetailFragment(r.owner.login))
    }


    private fun observeRepos(adapter: ReposAdapter) {
        lifecycleScope.launchWhenCreated {
            viewModel.reposFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener {
            viewModel.setSearchBy(it.toString())
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }



    private fun observeLoadState(adapter: ReposAdapter) {
        // you can also use adapter.addLoadStateListener
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                // main indicator in the center of the screen
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun handleListVisibility(adapter: ReposAdapter) = lifecycleScope.launch {
        getRefreshLoadStateFlow(adapter)
            .collectLatest {
                if(it is LoadState.Error) binding.loadStateView.messageTextView.text = it.error.message
            }
    }

    private fun getRefreshLoadStateFlow(adapter: ReposAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}