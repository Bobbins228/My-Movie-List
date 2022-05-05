package com.my_movie_list.models

import androidx.lifecycle.MutableLiveData

interface RatingStore {
    fun findAll() : List<RatingModel>
    fun create(rating: RatingModel)
    fun update(rating: RatingModel)
    fun delete(rating: RatingModel)
    fun findById(id: Long) : RatingModel?
}