package com.heira.app.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heira.app.R
import com.heira.app.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding

    private lateinit var fragmentName: Fragment
    private var initial = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initial++
        if (initial == 1) {
            loadFragment(HomeFragment())
        } else {
            loadFragment(fragmentName)
        }

        binding?.bnHeira?.itemIconTintList = null

        binding?.bnHeira?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    fragmentName = HomeFragment()
                }
                R.id.navigation_channel -> {
                    loadFragment(LikedFragment())
                    fragmentName = LikedFragment()
                }
                else -> {
                    loadFragment(HomeFragment())
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.fragmentContainerView, fragment)
        fragmentTransition.commit()
    }

}