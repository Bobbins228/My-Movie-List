package com.my_movie_list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.my_movie_list.R
import com.my_movie_list.databinding.ItemMovieBinding
import com.my_movie_list.models.Movie

interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}

class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private val listener: MovieClickListener
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[holder.adapterPosition]
        holder.bind(movie)
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    inner class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            binding.root.tag = movie
            binding.movie = movie
            binding.executePendingBindings()
            binding.root.setOnClickListener { listener.onMovieClick(movie) }

        }
    }
}