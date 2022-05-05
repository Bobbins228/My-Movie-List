package com.my_movie_list.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}
object RatingManager : RatingStore {

    val ratings = ArrayList<RatingModel>()

    override fun findAll(): List<RatingModel> {
        return ratings
    }

    override fun create(rating: RatingModel) {
        rating.id = getId()
        ratings.add(rating)
        logAll()
    }

    override fun findById(id:Long) : RatingModel? {
        val foundRating: RatingModel? = ratings.find { it.id == id }
        return foundRating
    }

    override fun update(rating: RatingModel) {
        TODO("Not yet implemented")
    }

    override fun delete(rating: RatingModel) {
        TODO("Not yet implemented")
    }

    fun logAll() {
        Timber.v("** Ratings List **")
        ratings.forEach { Timber.v("Rating ${it}") }
    }
}