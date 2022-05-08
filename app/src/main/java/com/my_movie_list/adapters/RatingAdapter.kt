package com.my_movie_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.my_movie_list.databinding.CardRatingBinding
import com.my_movie_list.models.RatingModel
import com.my_movie_list.utils.customTransformation
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

interface RatingClickListener {
    fun onRatingClick(rating: RatingModel)
}
class RatingAdapter (
    private var ratings: ArrayList<RatingModel>,
    private val listener: RatingClickListener,
    private val readOnly: Boolean
)
    : RecyclerView.Adapter<RatingAdapter.MainHolder>(){
    //private lateinit var app: MyMovieListApp
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val rating = ratings[holder.adapterPosition]
        holder.bind(rating,listener)
    }

    fun removeAt(position: Int) {
        ratings.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = ratings.size

    inner class MainHolder( val binding : CardRatingBinding, private val readOnly: Boolean) : RecyclerView.ViewHolder(binding.root){

        val readOnlyRow = readOnly
        fun bind(rating: RatingModel, listener: RatingClickListener) {
            binding.root.tag = rating
            binding.rating = rating
            Picasso.get().load(rating.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(binding.imageIcon)
            binding.executePendingBindings()
            binding.root.setOnClickListener { listener.onRatingClick(rating) }
        }
    }
}