package com.mymovielist.main

import android.app.Application

import com.mymovielist.models.MovieListStore
import timber.log.Timber
import android.R
import androidx.lifecycle.Transformations.map

import com.google.android.gms.maps.SupportMapFragment
import com.mymovielist.models.MovieManager


class MyMovieListApp : Application() {
    lateinit var movies: MovieListStore

    override fun onCreate() {
        super.onCreate()

        //movies = MovieJSONStore(applicationContext)
        //movies = MovieManager()
        Timber.plant(Timber.DebugTree())
        Timber.i("My Movie List has started")

    }

    }