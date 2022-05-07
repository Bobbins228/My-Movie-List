package com.my_movie_list.ui.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.my_movie_list.models.FirebaseDBManager
import com.my_movie_list.models.RatingModel

class RatingViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRating(firebaseUser: MutableLiveData<FirebaseUser>,
                    rating: RatingModel) {
        status.value = try {
            FirebaseDBManager.create(firebaseUser,rating)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}