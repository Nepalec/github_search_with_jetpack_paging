package com.example.githubsearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.githubsearchapp.MainActivity
import com.example.githubsearchapp.MyApp
import com.example.githubsearchapp.R

import com.example.githubsearchapp.databinding.FragmentDetailBinding
import com.example.githubsearchapp.model.Profile
import com.example.githubsearchapp.network.result.Result
import com.example.githubsearchapp.vm.AppVMFactory
import com.example.githubsearchapp.vm.DetailViewModel
import com.example.githubsearchapp.vm.SearchViewModel
import javax.inject.Inject

class DetailFragment : Fragment() {

    @Inject
    lateinit var factory: AppVMFactory
    private lateinit var viewModel: DetailViewModel

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        viewModel.getProfile(args.id).observe(this){ result->
            when(result){
                is Result.Success       ->   binding.data = result.value.toProfile()
                is Result.Failure<*>    ->   binding.errMsg = result.error?.message ?: "Непредвиденная ошибка"
            }
            binding.bg.visibility=View.GONE
            binding.progressBar.visibility=View.GONE
            binding
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.toolbar){
            setNavigationIcon(R.drawable.ic_back)
            title = args.id
            setNavigationOnClickListener{(activity as MainActivity).navController.navigateUp()}  }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}