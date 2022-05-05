package com.my_movie_list.ui.ratingList

import android.media.Rating
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_movie_list.models.RatingManager
import com.my_movie_list.models.RatingModel

class RatingListViewModel : ViewModel() {
    private val ratingList = MutableLiveData<List<RatingModel>>()

    val observableRatingList: LiveData<List<RatingModel>>
        get() = ratingList

    init {
        load()
    }

    fun load() {
        ratingList.value = RatingManager.findAll()
    }
}