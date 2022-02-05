package com.mymovielist.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class MovieMemStore : MovieListStore {

    val movies = ArrayList<MovieListModel>()

    override fun findAll(): List<MovieListModel> {
        return movies
    }

    override fun findById(id:Long) : MovieListModel? {
        val foundMovie: MovieListModel? = movies.find { it.id == id }
        return foundMovie
    }

    override fun create(movie: MovieListModel) {
        movie.id = getId()
        movies.add(movie)
        logAll()
    }

    fun logAll() {
        Timber.v("** Movies List **")
        movies.forEach { Timber.v("Movie ${it}") }
    }
}