package com.example.starbuckfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starbuckfinder.model.Starbuck
import com.example.starbuckfinder.databinding.AdapterStarbuckBinding

class StarbuckAdapter: RecyclerView.Adapter<MainViewHolder>() {

    var movies = mutableListOf<Starbuck>()

    fun setMovieList(movies: List<Starbuck>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterStarbuckBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.name.text = movie.name
       // Glide.with(holder.itemView.context).load(movie.imageUrl).into(holder.binding.imageview)

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MainViewHolder(val binding: AdapterStarbuckBinding) : RecyclerView.ViewHolder(binding.root) {

}