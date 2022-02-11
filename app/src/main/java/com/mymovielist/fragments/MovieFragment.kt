package com.mymovielist.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log.i
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.mymovielist.databinding.FragmentMovieBinding
import com.mymovielist.main.MyMovieListApp
import com.mymovielist.R
import com.mymovielist.databinding.CardMovieBinding
import com.mymovielist.databinding.FragmentMovieListBinding
import com.mymovielist.helpers.readImage
import com.mymovielist.helpers.readImageFromPath
import com.mymovielist.helpers.showImagePicker
import java.util.*
import com.mymovielist.models.MovieListModel
import timber.log.Timber
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResult
import org.jetbrains.anko.intentFor
import timber.log.Timber.i
import timber.log.Timber.log
import java.lang.Math.log

class MovieFragment : Fragment() {
    var imageMovie = ""
    val IMAGE_REQUEST = 1
    var movie = MovieListModel()
    lateinit var app: MyMovieListApp
    private var _fragBinding: FragmentMovieBinding? = null
    private val fragBinding get() = _fragBinding!!
    private var _fragCardBinding: CardMovieBinding? = null
    private val fragCardBinding get() = _fragCardBinding!!
    var edit = false
    //lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MyMovieListApp
        setHasOptionsMenu(true)
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentMovieBinding.inflate(inflater, container, false)
        _fragCardBinding = CardMovieBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.app_name)
        setButtonListener(fragBinding)
        onUpdateButton(fragCardBinding)
        return root;
    }

     fun onUpdateButton(layout: CardMovieBinding) {
        val datePicker = fragBinding.movieReleaseDate
        val today = Calendar.getInstance()
        datePicker.init(

            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month

        }

        layout.editButton.setOnClickListener {

            val title = fragBinding.title.text.toString()
            val genre = fragBinding.genre.text.toString()
            val director = fragBinding.director.text.toString()
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            app.movies.update(MovieListModel(title = title, genre = genre,director = director, day = day, month = month, year = year, image = imageMovie))
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            MovieFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener(layout: FragmentMovieBinding) {

        fragCardBinding.editButton.setOnClickListener {

        }


        fragBinding.chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        val datePicker = fragBinding.movieReleaseDate
        val today = Calendar.getInstance()
        datePicker.init(

            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month

        }

        layout.addMovieButton.setOnClickListener {

            val title = fragBinding.title.text.toString()
            val genre = fragBinding.genre.text.toString()
            val director = fragBinding.director.text.toString()
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            app.movies.create(MovieListModel(title = title, genre = genre,director = director, day = day, month = month, year = year, image = imageMovie))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    movie.image = data.getData().toString()
                    imageMovie = movie.image
                    fragBinding.movieImage.setImageBitmap(readImage(this, resultCode, data))
                    fragBinding.chooseImage.setText(R.string.change_movie_image)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }
}