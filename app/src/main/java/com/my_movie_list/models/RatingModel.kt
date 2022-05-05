package com.my_movie_list.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
data class RatingModel (var id: Long = 0,
                        var title: String = "",
                        var content: String = "",
                        var rating: Int = 0,
                        var date: LocalDate = LocalDate.now()
                          ): Parcelable
