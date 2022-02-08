package com.mymovielist.main

import android.app.Application
import com.mymovielist.models.MovieJSONStore
import com.mymovielist.models.MovieListStore
import timber.log.Timber

class MyMovieListApp : Application() {
    lateinit var movies: MovieListStore

    override fun onCreate() {
        super.onCreate()

        movies = MovieJSONStore(applicationContext)

        Timber.plant(Timber.DebugTree())
        Timber.i("My Movie List has started")
    }
}