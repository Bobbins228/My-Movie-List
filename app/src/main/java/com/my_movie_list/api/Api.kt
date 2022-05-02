package com.my_movie_list.api

import com.my_movie_list.models.GetMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "APIKEY",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "APIKEY",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "APIKEY",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}