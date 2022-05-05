package com.my_movie_list.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.my_movie_list.R
import com.my_movie_list.databinding.RatingDetailFragmentBinding
import timber.log.Timber

class RatingDetail : Fragment() {

    companion object {
        fun newInstance() = RatingDetail()
    }

    private val args by navArgs<RatingDetailArgs>()

    private lateinit var ratingDetailViewModel: RatingDetailViewModel

    private lateinit var viewModel: RatingDetailViewModel

    private var _fragBinding: RatingDetailFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = RatingDetailFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        ratingDetailViewModel = ViewModelProvider(this)[RatingDetailViewModel::class.java]
        ratingDetailViewModel.observableRating.observe(viewLifecycleOwner, Observer { render() })

        //val view = inflater.inflate(R.layout.rating_detail_fragment, container, false)
        return root
    }

    private fun render() {
        fragBinding.ratingvm = ratingDetailViewModel
        Timber.i("Retrofit fragBinding.ratingvm == $fragBinding.ratingvm")
    }

    override fun onResume() {
        super.onResume()
        ratingDetailViewModel.getRating(args.ratingid)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RatingDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}