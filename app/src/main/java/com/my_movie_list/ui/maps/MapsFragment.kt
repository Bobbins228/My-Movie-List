package com.my_movie_list.ui.maps

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.my_movie_list.R
import com.my_movie_list.models.RatingModel
import com.my_movie_list.ui.auth.LoggedInViewModel
import com.my_movie_list.ui.ratingList.RatingListViewModel
import com.my_movie_list.utils.createLoader
import com.my_movie_list.utils.hideLoader
import com.my_movie_list.utils.showLoader

class MapsFragment : Fragment() {

    private val mapsViewModel: MapsViewModel by activityViewModels()
    private val ratingListViewModel: RatingListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    lateinit var loader : AlertDialog

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        mapsViewModel.map = googleMap
        mapsViewModel.map.isMyLocationEnabled = true
        mapsViewModel.currentLocation.observe(viewLifecycleOwner,{
            val loc = LatLng(mapsViewModel.currentLocation.value!!.latitude,
                mapsViewModel.currentLocation.value!!.longitude)

            mapsViewModel.map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 14f))
            mapsViewModel.map.uiSettings.isZoomControlsEnabled = true
            mapsViewModel.map.uiSettings.isMyLocationButtonEnabled = true

            ratingListViewModel.observableRatingList.observe(viewLifecycleOwner, Observer { ratings ->
                ratings?.let {
                    render(ratings as ArrayList<RatingModel>)
                    hideLoader(loader)
                }
            })
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loader = createLoader(requireActivity())

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun render(ratingList: ArrayList<RatingModel>) {
        var markerColour: Float
        if (!ratingList.isEmpty()) {
            mapsViewModel.map.clear()
            ratingList.forEach {
                if(it.email.equals(this.ratingListViewModel.liveFirebaseUser.value!!.email))
                    markerColour = BitmapDescriptorFactory.HUE_AZURE + 5
                else
                    markerColour = BitmapDescriptorFactory.HUE_RED

                mapsViewModel.map.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude))
                        .title("${it.title} ★${it.ratingNumber}")
                        .snippet(it.content)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColour ))
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map, menu)

        val item = menu.findItem(R.id.toggleRatings) as MenuItem
        item.setActionView(R.layout.togglebutton_layout)
        val toggleDonations: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleDonations.isChecked = false

        toggleDonations.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) ratingListViewModel.loadAll()
            else ratingListViewModel.load()
        }
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading Ratings")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                ratingListViewModel.liveFirebaseUser.value = firebaseUser
                ratingListViewModel.load()
            }
        })
    }
}