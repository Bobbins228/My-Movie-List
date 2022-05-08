package com.my_movie_list.ui.ratingList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.my_movie_list.firebase.FirebaseDBManager
import com.my_movie_list.models.RatingModel
import timber.log.Timber
import java.lang.Exception

class RatingListViewModel : ViewModel() {
    private val ratingList = MutableLiveData<List<RatingModel>>()

    val observableRatingList: LiveData<List<RatingModel>>
        get() = ratingList

    init {
        load()
    }

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)
    fun loadAll() {
       try {
           readOnly.value = true
           FirebaseDBManager.findAll(ratingList)
           Timber.i("Report LoadAll Success : ${ratingList.value.toString()}")
       }
       catch (e: Exception) {
           Timber.i("Report LoadAll Error : $e.message")
       }
   }


    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                ratingList)
            Timber.i("RatingList Load Success : ${ratingList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("RatingList Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}