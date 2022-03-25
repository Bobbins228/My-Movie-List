package com.mymovielist.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object MovieManager : MovieListStore {

    val movies = ArrayList<MovieListModel>()

    override fun findAll(): List<MovieListModel> {
        return movies
    }

    override fun create(movie: MovieListModel) {
        movie.id = getId()
        movies.add(movie)
        logAll()
    }

    override fun update(movie: MovieListModel) {
        TODO("Not yet implemented")
    }

    override fun delete(movie: MovieListModel) {
        TODO("Not yet implemented")
    }

    fun logAll() {
        Timber.v("** Movies List **")
        movies.forEach { Timber.v("Movie ${it}") }
    }
}