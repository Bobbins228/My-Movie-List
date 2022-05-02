package com.my_movie_list.ui.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_movie_list.models.Movie
import com.my_movie_list.models.MovieManager

class MovieListFragmentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is movieList Fragment"
    }

    private val movieList =
        MutableLiveData<List<Movie>>()

    val observableMovieList: LiveData<List<Movie>>
        get() = movieList

    init {
        load()
    }

    fun load(){
        movieList.value = MovieManager.findAll()
    }

    val text: LiveData<String> = _text
}