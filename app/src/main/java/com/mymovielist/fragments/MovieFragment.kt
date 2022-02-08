package com.mymovielist.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mymovielist.databinding.FragmentMovieBinding
import com.mymovielist.main.MyMovieListApp
import com.mymovielist.R
import com.mymovielist.helpers.readImage
import com.mymovielist.helpers.showImagePicker


import com.mymovielist.models.MovieListModel
import java.io.IOException
import java.util.*


class MovieFragment : Fragment() {
    var imageMovie = ""
    val IMAGE_REQUEST = 1
    var movie = MovieListModel()
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

        fragBinding.chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        val datePicker = fragBinding.movieReleaseDate
        val today = Calendar.getInstance()
        datePicker.init(

            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month

        }
        layout.addMovieButton.setOnClickListener {

            val title = fragBinding.title.text.toString()
            val genre = fragBinding.genre.text.toString()
            val director = fragBinding.director.text.toString()
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            app.movies.create(MovieListModel(title = title, genre = genre,director = director, day = day, month = month, year = year, image = imageMovie))
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    movie.image = data.getData().toString()
                    imageMovie = movie.image
                    fragBinding.movieImage.setImageBitmap(readImage(this, resultCode, data))
                    fragBinding.chooseImage.setText(R.string.change_movie_image)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }
}