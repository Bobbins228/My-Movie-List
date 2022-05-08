package com.my_movie_list.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.util.*

@IgnoreExtraProperties
@Parcelize
data class RatingModel (
        var uid: String? = "",
        var title: String = "",
        var content: String = "",
        var ratingNumber: Int = 0,
        var profilepic: String ="",
        var email: String? = "joe@bloggs.com")
        : Parcelable
{
        @Exclude
        fun toMap(): Map<String, Any?> {
                return mapOf(
                        "uid" to uid,
                        "title" to title,
                        "content" to content,
                        "ratingNumber" to ratingNumber,
                        "profilepic" to profilepic,
                        "email" to email
                )
        }
}