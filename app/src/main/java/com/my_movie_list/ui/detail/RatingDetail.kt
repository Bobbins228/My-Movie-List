package com.my_movie_list.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.my_movie_list.R
import com.my_movie_list.databinding.RatingDetailFragmentBinding
import com.my_movie_list.ui.auth.LoggedInViewModel
import com.my_movie_list.ui.rating.RatingViewModel
import com.my_movie_list.ui.ratingList.RatingListViewModel
import timber.log.Timber

class RatingDetail : Fragment() {

    companion object {
        fun newInstance() = RatingDetail()
    }

    private val args by navArgs<RatingDetailArgs>()

    private lateinit var ratingDetailViewModel: RatingDetailViewModel
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val ratingViewModel: RatingViewModel by activityViewModels()

    private lateinit var viewModel: RatingDetailViewModel

    private var _fragBinding: RatingDetailFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    private val ratingListViewModel : RatingListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = RatingDetailFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        ratingDetailViewModel = ViewModelProvider(this)[RatingDetailViewModel::class.java]
        ratingDetailViewModel.observableRating.observe(viewLifecycleOwner, Observer { render() })


        fragBinding.editRatingButton.setOnClickListener {
            ratingDetailViewModel.updateRating(loggedInViewModel.liveFirebaseUser.value?.uid!!, args.ratingid, fragBinding.ratingvm?.observableRating!!.value!!)
            ratingListViewModel.load()
            findNavController().navigateUp()
        }

        fragBinding.deleteRatingButton.setOnClickListener {
            ratingListViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                ratingDetailViewModel.observableRating.value?.uid!!)
            findNavController().navigateUp()
        }


        //val view = inflater.inflate(R.layout.rating_detail_fragment, container, false)
        return root
    }

    private fun render() {
        fragBinding.ratingvm = ratingDetailViewModel
        Timber.i("Retrofit fragBinding.ratingvm == $fragBinding.ratingvm")
    }

    override fun onResume() {
        super.onResume()
        ratingDetailViewModel.getRating(loggedInViewModel.liveFirebaseUser.value?.uid!!, args.ratingid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RatingDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}