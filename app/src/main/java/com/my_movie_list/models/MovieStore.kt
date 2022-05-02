package com.my_movie_list.models

interface MovieStore {
    fun findAll() : List<Movie>
    fun findById(id: Long) : Movie?
    //fun create(movie: Movie)
}