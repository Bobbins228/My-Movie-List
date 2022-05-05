package com.my_movie_list.ui.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_movie_list.models.RatingManager
import com.my_movie_list.models.RatingModel

class RatingViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRating(rating: RatingModel) {
        status.value = try {
            RatingManager.create(rating)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}