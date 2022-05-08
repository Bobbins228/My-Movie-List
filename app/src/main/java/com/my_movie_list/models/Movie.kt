package com.my_movie_list.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String
    //@SerializedName("isFavourite") val isFavourite: Boolean
    // WHEN LOOKING AT GALLERY OF MOVIES ADD A SWITCH TO FLICK AND IT WILL FILTER MOVIES BY FAVOURITE = TRUE
    // THEN IT WILL SHOW ONLY THE MOVIES THAT ARE THE USER'S FAVOURITES
): Parcelable
