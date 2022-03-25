package com.mymovielist.ui.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mymovielist.models.MovieListModel
import com.mymovielist.models.MovieManager

class MovieListViewModel : ViewModel() {

    private val movieList = MutableLiveData<List<MovieListModel>>()

    val observableMovieList: LiveData<List<MovieListModel>>
        get() = movieList

    init {
        load()
    }

    fun load() {
        movieList.value = MovieManager.findAll()
    }
}
