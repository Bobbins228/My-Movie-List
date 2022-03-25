package com.mymovielist.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mymovielist.models.MovieListModel
import com.mymovielist.models.MovieManager

class MovieViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addMovie(movie: MovieListModel) {
        status.value = try {
            MovieManager.create(movie)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}