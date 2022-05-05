package com.my_movie_list.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_movie_list.models.RatingManager
import com.my_movie_list.models.RatingModel

class RatingDetailViewModel : ViewModel() {
    private val rating = MutableLiveData<RatingModel>()

    val observableRating: LiveData<RatingModel>
        get() = rating


    fun getRating(id: Long) {
        rating.value = RatingManager.findById(id)
    }
}