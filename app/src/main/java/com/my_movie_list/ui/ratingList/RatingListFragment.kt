package com.my_movie_list.ui.ratingList

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.my_movie_list.R
import com.my_movie_list.adapters.RatingAdapter
import com.my_movie_list.adapters.RatingClickListener
import com.my_movie_list.databinding.RatingListFragmentBinding
import com.my_movie_list.main.MyMovieListApp
import com.my_movie_list.models.RatingModel
import com.my_movie_list.ui.auth.LoggedInViewModel
import com.my_movie_list.utils.*

class RatingListFragment : Fragment(), RatingClickListener {

    var rating = RatingModel()
    lateinit var app: MyMovieListApp
    private var _fragBinding: RatingListFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    lateinit var loader : AlertDialog
    private val ratingListViewModel: RatingListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
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

        loader = createLoader(requireActivity())
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        //ratingListViewModel = ViewModelProvider(this).get(RatingListViewModel::class.java)
        ratingListViewModel.observableRatingList.observe(viewLifecycleOwner, Observer {
                ratings ->
            render(ratings as ArrayList<RatingModel>)
            hideLoader(loader)
            checkSwipeRefresh()
        })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Rating")
                val adapter = fragBinding.recyclerView.adapter as RatingAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                ratingListViewModel.delete(ratingListViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as RatingModel).uid!!)
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onRatingClick(viewHolder.itemView.tag as RatingModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)
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

    private fun render(ratingList: ArrayList<RatingModel>) {
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
        val action = RatingListFragmentDirections.actionNavRatingListToRatingDetail(rating.uid!!)
        findNavController().navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RatingListFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    private fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Donations")
            ratingListViewModel.load()
        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }


    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Ratings")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                ratingListViewModel.liveFirebaseUser.value = firebaseUser
                ratingListViewModel.load()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}