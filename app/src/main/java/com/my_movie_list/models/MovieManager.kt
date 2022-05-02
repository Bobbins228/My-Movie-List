package com.my_movie_list.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object MovieManager : MovieStore {

    private val movies = ArrayList<Movie>()

    override fun findAll(): List<Movie> {
        return movies
    }

    override fun findById(id:Long) : Movie? {
        val foundMovie: Movie? = movies.find { it.id == id }
        return foundMovie
    }



    fun logAll() {
        Timber.v("** Movies List **")
        movies.forEach { Timber.v("Movie ${it}") }
    }
}