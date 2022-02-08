package com.mymovielist.models

interface MovieListStore {
    fun findAll() : List<MovieListModel>
    //fun findById(id: Long) : MovieListModel?
    fun create(movie: MovieListModel)
    fun update(movie: MovieListModel)
    fun delete(movie: MovieListModel)
}