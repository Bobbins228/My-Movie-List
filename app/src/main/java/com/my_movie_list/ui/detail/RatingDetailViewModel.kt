package com.my_movie_list.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_movie_list.models.FirebaseDBManager
import com.my_movie_list.models.RatingModel
import timber.log.Timber
import java.lang.Exception

class RatingDetailViewModel : ViewModel() {
    private val rating = MutableLiveData<RatingModel>()

    var observableRating: LiveData<RatingModel>
        get() = rating
        set(value) {rating.value = value.value}

    fun getRating(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, rating)
            Timber.i("Detail getRating() Success : ${
                rating.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getRating() Error : $e.message")
        }
    }

    fun updateRating(userid:String, id: String,rating: RatingModel) {
        try {
            FirebaseDBManager.update(userid, id, rating)
            Timber.i("Detail update() Success : $rating")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}