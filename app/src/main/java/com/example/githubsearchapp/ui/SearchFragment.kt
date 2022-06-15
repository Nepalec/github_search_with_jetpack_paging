package com.example.githubsearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearchapp.MainActivity
import com.example.githubsearchapp.MyApp
import com.example.githubsearchapp.R
import com.example.githubsearchapp.databinding.FragmentSearchBinding
import com.example.githubsearchapp.vm.AppVMFactory
import com.example.githubsearchapp.vm.SearchViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var factory: AppVMFactory
    lateinit var vm: SearchViewModel

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApp).appComponent.inject(this)
        vm = ViewModelProvider(this, factory)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.textView.setOnClickListener { (requireActivity() as MainActivity).navController.navigate(
            R.id.action_navigation_home_to_detailFragment) }

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}