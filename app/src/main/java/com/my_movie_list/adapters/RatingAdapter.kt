package com.my_movie_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my_movie_list.databinding.CardRatingBinding
import com.my_movie_list.models.RatingModel

interface RatingClickListener {
    fun onRatingClick(rating: RatingModel)
}
class RatingAdapter (
    private var ratings: ArrayList<RatingModel>,
    private val listener: RatingClickListener
)
    : RecyclerView.Adapter<RatingAdapter.MainHolder>(){
    //private lateinit var app: MyMovieListApp
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val rating = ratings[holder.adapterPosition]
        holder.bind(rating)
    }

    fun removeAt(position: Int) {
        ratings.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = ratings.size

    inner class MainHolder( val binding : CardRatingBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(rating: RatingModel) {
            binding.root.tag = rating
            binding.rating = rating
            binding.executePendingBindings()
            binding.root.setOnClickListener { listener.onRatingClick(rating) }
        }
    }
}