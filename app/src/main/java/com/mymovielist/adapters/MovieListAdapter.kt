package com.mymovielist.adapters

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mymovielist.R
import com.mymovielist.databinding.CardMovieBinding
import com.mymovielist.helpers.readImageFromPath
import com.mymovielist.main.MyMovieListApp
import com.mymovielist.models.MovieJSONStore
import org.jetbrains.anko.toast

import com.mymovielist.models.MovieListModel
import com.mymovielist.models.MovieListStore
import com.mymovielist.ui.movieList.MovieListFragment
import timber.log.Timber

interface MovieClickListener {
    fun onMovieClick(movie: MovieListModel)
}
class MovieListAdapter(
    private var movies: List<MovieListModel>,
    private val listener: MovieClickListener
)
    : RecyclerView.Adapter<MovieListAdapter.MainHolder>(){
    //private lateinit var app: MyMovieListApp
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val movie = movies[holder.adapterPosition]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class MainHolder( val binding : CardMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieListModel) {
            val releaseDate = movie.day.toString() + "/" + movie.month.toString() + "/" + movie.year.toString()
            binding.title.text = movie.title
            binding.genre.text = movie.genre
            binding.director.text = movie.director
            binding.movieReleaseDate.text = releaseDate
            binding.imageIcon.setImageBitmap(readImageFromPath(itemView.context, movie.image))
            binding.root.setOnClickListener { listener.onMovieClick(movie) }
        // DELETE FUNCTION KILLS APP
            /*
            binding.itemDelete.setOnClickListener {
                Timber.plant(Timber.DebugTree())
                Timber.i("Deleting movie " + movie.title)
                app.movies.delete(movie)
            }
             */
        }
    }
}