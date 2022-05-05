package com.my_movie_list.main

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.firebase.auth.FirebaseUser
import com.my_movie_list.R
import com.my_movie_list.databinding.ActivityMainBinding
import com.my_movie_list.databinding.NavHeaderMainBinding
import com.my_movie_list.models.MoviesRepository
import com.my_movie_list.models.RatingModel
import com.my_movie_list.ui.auth.LoggedInViewModel
import com.my_movie_list.ui.auth.Login

class MyMovieListApp : Application() {
    lateinit var ratings: RatingModel

    override fun onCreate() {
        super.onCreate()
    }


}