package com.my_movie_list.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_movie_list.models.Movie
import com.my_movie_list.models.MovieManager

class MovieDetailViewModel : ViewModel() {
    private val movie = MutableLiveData<Movie>()

    val observableMovie: LiveData<Movie>
        get() = movie


    fun getMovie(id: Long) {
        movie.value = MovieManager.findById(id)
    }
}