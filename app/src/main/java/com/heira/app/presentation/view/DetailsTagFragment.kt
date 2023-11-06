package com.heira.app.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.heira.app.R
import com.heira.app.databinding.FragmentDetailsTagBinding
import com.heira.app.presentation.adapter.route.RouteAdapter
import com.heira.app.presentation.view_model.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsTagFragment : Fragment(R.layout.fragment_details_tag) {

    private var _binding: FragmentDetailsTagBinding? = null
    private val binding get() = _binding

    // injection - viewModel
    private val homeViewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsTagBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvListRoutes?.layoutManager = manager

        homeViewModel.resultRouteTag.observe(requireActivity()){ routes ->
            binding?.rvListRoutes?.adapter =  RouteAdapter(routeList = routes!!) {onSelectRoute(route = it) }
        }
    }

    private fun onSelectRoute(route: String) {
        homeViewModel.getRouteById(route)
        homeViewModel.resultRouteId.observe(requireActivity()) { route ->
            val startLocation = "${route!!.start.longitude},${route.start.latitude}"
            val endLocation = "${route.end.longitude},${route.end.latitude}"
            homeViewModel.coordinates("$startLocation|$endLocation")
        }
    }

}