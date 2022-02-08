package com.mymovielist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieListModel(var id: Long = 0,
                          var title: String = "",
                          var genre: String = "",
                          var director: String = "",
                          var image: String="",
                          var day: Int = 0,
                          var month: Int = 0,
                          var year: Int = 0,
                          ): Parcelable
