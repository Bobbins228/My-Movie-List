package com.mymovielist.main

import android.app.Application
import com.mymovielist.models.MovieMemStore
import com.mymovielist.models.MovieListStore
import timber.log.Timber

class MyMovieListApp : Application() {
    lateinit var movies: MovieListStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        movies = MovieMemStore()
        Timber.i("My Movie List has started")
    }
}