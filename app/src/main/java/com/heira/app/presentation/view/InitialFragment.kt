package com.heira.app.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.heira.app.R
import com.heira.app.core.onTextChange
import com.heira.app.databinding.FragmentInitialBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class InitialFragment : Fragment(R.layout.fragment_initial) {

    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInitialBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tfUsername?.onTextChange { s ->
            if (s.length >= 5){
                binding?.btnProcessExist?.visibility = View.VISIBLE
            } else {
                binding?.btnProcessExist?.visibility = View.GONE
            }
        }

        binding?.btnProcessExist?.setOnClickListener {
            binding?.containerContent?.visibility = View.GONE
            binding?.initialLoad?.root?.visibility = View.VISIBLE

            lifecycleScope.launch {
                delay(3000)

                if (findNavController().currentDestination?.id == R.id.initialFragment){
                    val action = InitialFragmentDirections.actionInitialFragmentToMainFragment()
                    findNavController().navigate(action)
                }
            }
        }

    }

}