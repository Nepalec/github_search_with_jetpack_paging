package com.example.githubsearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.githubsearchapp.MyApp

import com.example.githubsearchapp.databinding.FragmentDetailBinding
import com.example.githubsearchapp.vm.AppVMFactory
import com.example.githubsearchapp.vm.DetailViewModel
import com.example.githubsearchapp.vm.SearchViewModel
import javax.inject.Inject

class DetailFragment : Fragment() {

    @Inject
    lateinit var factory: AppVMFactory
    private lateinit var viewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null

    val args: DetailFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        viewModel.getProfile(args.id).observe(this){
            binding.textView.text = it?.bio ?: "Err"
        }

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