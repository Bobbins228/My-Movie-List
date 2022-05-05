package com.my_movie_list.ui.rating

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log.v
import android.view.*
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.my_movie_list.R
import com.my_movie_list.databinding.CardRatingBinding
import com.my_movie_list.databinding.RatingFragmentBinding
import com.my_movie_list.main.MyMovieListApp
import com.my_movie_list.models.RatingModel
import java.util.*
import androidx.lifecycle.Observer
import com.android.volley.VolleyLog.v
import timber.log.Timber.Forest.v
import java.time.LocalDate
import java.time.LocalDate.now

class RatingFragment : Fragment() {
    var rating = RatingModel()
    lateinit var app: MyMovieListApp
    private var _fragBinding: RatingFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private var _fragCardBinding: CardRatingBinding? = null
    private val fragCardBinding get() = _fragCardBinding!!
    //Add view model
    private lateinit var ratingViewModel: RatingViewModel

    //lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = RatingFragmentBinding.inflate(inflater, container, false)
        _fragCardBinding = CardRatingBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        setButtonListener(fragBinding)

        ratingViewModel = ViewModelProvider(this).get(RatingViewModel::class.java)
        ratingViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })
        val numberPicker = fragBinding.numberPicker
        numberPicker.setMinValue(1)
        numberPicker.setMaxValue(10)
        return root;
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.ratingError), Toast.LENGTH_LONG).show()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            RatingFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: RatingFragmentBinding) {


        layout.addRatingButton.setOnClickListener {
            val msg = Toast.makeText(context,R.string.enter_rating, Toast.LENGTH_SHORT)
            val dateToday = LocalDate.now()
            val title = fragBinding.title.text.toString()
            val contentText = layout.ratingContent.text.toString()
            val ratingNumber = layout.numberPicker.value
            if (layout.title.text.isEmpty()) {
                val toast = Toast.makeText(context,R.string.enter_movie_title, Toast.LENGTH_SHORT)
                toast.show()
            }
            else if (layout.ratingContent.text.isEmpty()) {
                val toast = Toast.makeText(context,R.string.enter_rating_content, Toast.LENGTH_SHORT)
                toast.show()
            }
            else {
                ratingViewModel.addRating(RatingModel(title = title, content = contentText, rating = ratingNumber,  date = dateToday))
                val toast = Toast.makeText(context,layout.title.text.toString() + " added!", Toast.LENGTH_SHORT)
                toast.show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.rating_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        //val movieViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
    }
}