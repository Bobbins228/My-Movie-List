package com.my_movie_list.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface RatingStore {
    fun findAll(ratingList:
                MutableLiveData<List<RatingModel>>)
    fun findAll(userid:String,
                ratingList:
                MutableLiveData<List<RatingModel>>)
    fun findById(userid:String, ratingid: String,
                 rating: MutableLiveData<RatingModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, rating: RatingModel)
    fun delete(userid:String, ratingid: String)
    fun update(userid:String, ratingid: String, rating: RatingModel)
}