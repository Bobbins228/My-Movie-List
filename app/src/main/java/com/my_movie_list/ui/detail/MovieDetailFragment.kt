package com.my_movie_list.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.my_movie_list.R
import com.my_movie_list.databinding.MovieDetailFragmentBinding

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
class MovieDetailFragment : Fragment() {
     private lateinit var backdrop: ImageView
     private lateinit var poster: ImageView
     private lateinit var title: TextView
     private lateinit var rating: RatingBar
     private lateinit var releaseDate: TextView
     private lateinit var overview: TextView

    private lateinit var movieDetailsViewModel: MovieDetailViewModel

    private val args by navArgs<MovieDetailFragmentArgs>()
    companion object {
        fun newInstance() = MovieDetailFragment()
    }
    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        val root = binding.root
        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        movieDetailsViewModel.observableMovie.observe(viewLifecycleOwner, Observer{ render() })


        /*
        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

         */
        Toast.makeText(context,"Movie ID Selected : ${args.id}",Toast.LENGTH_LONG).show()
        //
        return root
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        movieDetailsViewModel.getMovie(args.id)
    }
    private fun render(){
        binding.movievm = movieDetailsViewModel
    }


}