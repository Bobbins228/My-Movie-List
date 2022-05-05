package com.my_movie_list.ui.ratingList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.my_movie_list.R
import com.my_movie_list.adapters.RatingAdapter
import com.my_movie_list.adapters.RatingClickListener
import com.my_movie_list.databinding.RatingListFragmentBinding
import com.my_movie_list.main.MyMovieListApp
import com.my_movie_list.models.RatingModel

class RatingListFragment : Fragment(), RatingClickListener {

    var rating = RatingModel()
    lateinit var app: MyMovieListApp
    private var _fragBinding: RatingListFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var ratingListViewModel: RatingListViewModel
    //lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = RatingListFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.app_name)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        ratingListViewModel = ViewModelProvider(this).get(RatingListViewModel::class.java)
        ratingListViewModel.observableRatingList.observe(viewLifecycleOwner, Observer {
                ratings ->
            ratings?.let { render(ratings) }
        })
        //fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        //fragBinding.recyclerView.adapter = MovieListAdapter(app.movies.findAll())
        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = RatingListFragmentDirections.actionRatingListFragmentToRatingFragment()
            findNavController().navigate(action)
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_ratinglist, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(ratingList: List<RatingModel>) {
        fragBinding.recyclerView.adapter = RatingAdapter(ratingList,this)
        if (ratingList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.ratingsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.ratingsNotFound.visibility = View.GONE
        }
    }

    override fun onRatingClick(rating: RatingModel) {
        val action = RatingListFragmentDirections.actionNavRatingListToRatingDetail(rating.id)
        findNavController().navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RatingListFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onResume() {
        super.onResume()
        ratingListViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}