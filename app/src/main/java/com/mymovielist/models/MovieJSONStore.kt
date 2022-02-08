package com.mymovielist.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mymovielist.helpers.exists
import com.mymovielist.helpers.read
import com.mymovielist.helpers.write
import org.jetbrains.anko.AnkoLogger

import java.util.*


val JSON_FILE = "movies.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<MovieListModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class MovieJSONStore : MovieListStore, AnkoLogger {

    val context: Context
    var movies = mutableListOf<MovieListModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<MovieListModel> {
        return movies
    }

    override fun create(movie: MovieListModel) {
        movie.id = generateRandomId()
        movies.add(movie)
        serialize()
    }


    override fun update(movie: MovieListModel) {
        val moviesList = findAll() as ArrayList<MovieListModel>
        var foundMovie: MovieListModel? = moviesList.find { m -> m.id == movie.id}

        if (foundMovie != null) {
            foundMovie.title = movie.title
            foundMovie.genre = movie.genre
            foundMovie.director = movie.director
            foundMovie.day = movie.day
            foundMovie.month = movie.month
            foundMovie.year = movie.year
            foundMovie.image = movie.image
        }
        serialize()
    }

    override fun delete(movie: MovieListModel) {
        movies.remove(movie)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(movies, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        movies = Gson().fromJson(jsonString, listType)
    }
}