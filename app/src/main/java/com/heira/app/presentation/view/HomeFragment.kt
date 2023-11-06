package com.heira.app.presentation.view

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.heira.app.R
import com.heira.app.data.remote.dto.LogLatDTO
import com.heira.app.data.remote.dto.RouteCreationDTO
import com.heira.app.databinding.BottomCreationRouteBinding
import com.heira.app.databinding.FragmentHomeBinding
import com.heira.app.presentation.view_model.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private var initialRoute: Boolean = false

    private lateinit var initialLog: String
    private lateinit var initialLat: String
    lateinit var poly: Polyline

    // injection - viewModel
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var map: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createFragmentMap()

        binding?.creationRoute?.setOnClickListener {
            showDialogCreationRoute()
        }

        val tabLayout = binding?.tabLayoutEventAndNotifications
        val viewPager = binding?.viewPagerEventAndNotifications

        tabLayout?.setBackgroundColor(111820)

        tabLayout?.addTab(tabLayout.newTab().setText("Forestacion"))
        tabLayout?.addTab(tabLayout.newTab().setText("Calidad de aire"))
        tabLayout?.addTab(tabLayout.newTab().setText("Ruidoso"))
        tabLayout?.addTab(tabLayout.newTab().setText("Quiet"))


        homeViewModel.getRouteByTag("FORESTATION")

        viewPager?.adapter = AdapterTags(requireContext(), childFragmentManager, tabLayout!!.tabCount)

        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position

                when(tab.position) {
                    0 -> {
                        homeViewModel.getRouteByTag("FORESTATION")
                    }
                    1 -> {
                        homeViewModel.getRouteByTag("QUALITY_AIR")
                    }
                    2 -> {
                        homeViewModel.getRouteByTag("NOISY")
                    }
                    3 -> {
                        homeViewModel.getRouteByTag("QUIET")
                    }
                }
                Toast.makeText(requireActivity(), "${tab.position}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        homeViewModel.resultCoordinates.observe(requireActivity()) {
            var startCoordinates = it.split("|")[0]
            var endCoordinates = it.split("|")[1]
            homeViewModel.getCoordinateForRoteOneToOne(start = startCoordinates, end = endCoordinates)
        }

        homeViewModel.resultCoordinatesRouteOneToOne.observe(requireActivity()) {resultCoordinatesRoutes ->
            val polyOptions = PolylineOptions()
                .width(15f)
                .color(ContextCompat.getColor(requireContext(), R.color.blue200))
            resultCoordinatesRoutes.forEach { coordinates ->
                polyOptions.add(LatLng(coordinates[1], coordinates[0]))
                val coordinates = LatLng(coordinates[1], coordinates[0])
                map.animateCamera( CameraUpdateFactory.newLatLngZoom(coordinates, 20f),
                    2000,
                    null
                )
            }
            requireActivity().runOnUiThread{
                poly = map.addPolyline(polyOptions)
            }
        }

    }

    // GOOGLE MAPS
    private fun createFragmentMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fcvMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val styleOptions =
            MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.google_map_styled)
        // MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_point))
        map.setMapStyle(styleOptions)
        enableLocation()
    }

    private fun isLocationPermissionGranted() = ActivityCompat.checkSelfPermission(
        requireActivity(),
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
        requireActivity(),
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) &&
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            Toast.makeText(requireActivity(), "Accept permission in config", Toast.LENGTH_LONG)
                .show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(requireActivity(), "Accept permission in config", Toast.LENGTH_LONG)
                    .show()
            }

            else -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        if (!::map.isInitialized) return
        if (!isLocationPermissionGranted()) {
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            map.isMyLocationEnabled = false
            Toast.makeText(requireActivity(), "Accept permission in config", Toast.LENGTH_LONG)
                .show()
        }
    }


    // Dialogs
    private fun showDialogCreationRoute() {
        val dialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.bottom_creation_route, null)

        val bindingDialog = BottomCreationRouteBinding.bind(view)

        bindingDialog.ivLocationUse.setBackgroundResource(R.drawable.ic_play)


        bindingDialog.location.setOnClickListener {
            initialLat = map.myLocation.latitude.toString()
            initialLog = map.myLocation.longitude.toString()

            initialRoute = !initialRoute
            if (initialRoute) {
                bindingDialog.ivLocationUse.setBackgroundResource(R.drawable.ic_pause)
            } else {
                bindingDialog.ivLocationUse.setBackgroundResource(R.drawable.ic_play)
                bindingDialog.btnCreateLocation.visibility = View.VISIBLE
            }
        }

        bindingDialog.btnCreateLocation.setOnClickListener {

            var noise = bindingDialog.tfNoise.text.toString().toDouble()
            var airquality = bindingDialog.tfAirquality.text.toString().toDouble()
            var forestation = bindingDialog.tfForestation.text.toString().toDouble()

            homeViewModel.postCreationRoute(
                RouteCreationDTO(
                    noise = noise,
                    airquality = airquality,
                    forestation = forestation,
                    start = LogLatDTO(id = 0, latitude = initialLat, longitude = initialLog),
                    end = LogLatDTO(
                        id = 0,
                        latitude = map.myLocation.latitude.toString(),
                        longitude = map.myLocation.longitude.toString()
                    )
                )
            )

            dialog.dismiss()

        }

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

}

internal class AdapterTags(var context: Context, fm: FragmentManager, var totalTabs: Int): FragmentPagerAdapter(fm){

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                DetailsTagFragment()
            }
            1 -> {
                DetailsTagFragment()
            }
            2 -> {
                DetailsTagFragment()
            }
            3 -> {
                DetailsTagFragment()
            }
            else -> getItem(position)
        }
    }

}