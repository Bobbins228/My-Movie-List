package com.my_movie_list.ui.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.my_movie_list.firebase.FirebaseDBManager
import com.my_movie_list.firebase.FirebaseImageManager
import com.my_movie_list.models.RatingModel

class RatingViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRating(firebaseUser: MutableLiveData<FirebaseUser>,
                    rating: RatingModel) {
        status.value = try {
            rating.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser,rating)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}