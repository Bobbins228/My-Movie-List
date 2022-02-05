package com.mymovielist.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mymovielist.databinding.FragmentMovieBinding
import com.mymovielist.main.MyMovieListApp
import com.mymovielist.R

import com.mymovielist.models.MovieListModel
import java.util.*


class MovieFragment : Fragment() {

    lateinit var app: MyMovieListApp
    private var _fragBinding: FragmentMovieBinding? = null
    private val fragBinding get() = _fragBinding!!
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

        _fragBinding = FragmentMovieBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.app_name)
        setButtonListener(fragBinding)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MovieFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: FragmentMovieBinding) {
        val datePicker = fragBinding.movieReleaseDate
        val today = Calendar.getInstance()
        datePicker.init(

            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month

        }
        layout.addMovieButton.setOnClickListener {
            val title = fragBinding.title.toString()
            val genre = fragBinding.genre.toString()
            val director = fragBinding.director.toString()
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            app.movies.create(MovieListModel(title = title, genre = genre,director = director, day = day, month = month, year = year))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    //    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }
}