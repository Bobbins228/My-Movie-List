package com.mymovielist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mymovielist.R
import com.mymovielist.databinding.CardMovieBinding
import com.mymovielist.helpers.readImageFromPath

import com.mymovielist.models.MovieListModel

class MovieListAdapter constructor(private var movies: List<MovieListModel>)
    : RecyclerView.Adapter<MovieListAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val movie = movies[holder.adapterPosition]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class MainHolder(val binding : CardMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieListModel) {
            val releaseDate = movie.day.toString() + "/" + movie.month.toString() + "/" + movie.year.toString()
            binding.title.text = movie.title
            binding.genre.text = movie.genre
            binding.director.text = movie.director
            binding.movieReleaseDate.text = releaseDate
            binding.imageIcon.setImageBitmap(readImageFromPath(itemView.context, movie.image))


        }
    }
}