package com.my_movie_list.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import timber.log.Timber

object FirebaseDBManager : RatingStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(ratingList: MutableLiveData<List<RatingModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, ratingList: MutableLiveData<List<RatingModel>>) {

        database.child("user-ratings").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Rating error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<RatingModel>()
                    val children = snapshot.children
                    children.forEach {
                        val rating = it.getValue(RatingModel::class.java)
                        localList.add(rating!!)
                    }
                    database.child("user-ratings").child(userid)
                        .removeEventListener(this)

                    ratingList.value = localList
                }
            })
    }

    override fun findById(userid: String, ratingid: String, rating: MutableLiveData<RatingModel>) {

        database.child("user-ratings").child(userid)
            .child(ratingid).get().addOnSuccessListener {
                rating.value = it.getValue(RatingModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, rating: RatingModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("ratings").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        rating.uid = key
        val ratingValues = rating.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/ratings/$key"] = ratingValues
        childAdd["/user-ratings/$uid/$key"] = ratingValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, ratingid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/ratings/$ratingid"] = null
        childDelete["/user-ratings/$userid/$ratingid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, ratingid: String, rating: RatingModel) {

        val ratingValues = rating.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["ratings/$ratingid"] = ratingValues
        childUpdate["user-ratings/$userid/$ratingid"] = ratingValues

        database.updateChildren(childUpdate)
    }
}