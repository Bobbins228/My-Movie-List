package com.mymovielist.ui.movieList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mymovielist.databinding.FragmentMovieListBinding
import com.mymovielist.main.MyMovieListApp
import com.mymovielist.R
import com.mymovielist.adapters.MovieClickListener
import com.mymovielist.adapters.MovieListAdapter
import com.mymovielist.models.MovieListModel

class MovieListFragment : Fragment(), MovieClickListener {
    var movie = MovieListModel()
    lateinit var app: MyMovieListApp
    private var _fragBinding: FragmentMovieListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var movieListViewModel: MovieListViewModel
    //lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MyMovieListApp
        setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMovieListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.app_name)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        movieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        movieListViewModel.observableMovieList.observe(viewLifecycleOwner, Observer {
                movies ->
            movies?.let { render(movies) }
        })
        //fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        //fragBinding.recyclerView.adapter = MovieListAdapter(app.movies.findAll())
        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieFragment()
            findNavController().navigate(action)
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(movieList: List<MovieListModel>) {
        fragBinding.recyclerView.adapter = MovieListAdapter(movieList,this)
        if (movieList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.moviesNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.moviesNotFound.visibility = View.GONE
        }
    }

    override fun onMovieClick(movie: MovieListModel) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie.id)
        findNavController().navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MovieListFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onResume() {
        super.onResume()
        movieListViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}