package com.example.starbuckfinder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starbuckfinder.databinding.AdapterStarbuckBinding
import com.example.starbuckfinder.model.Model

/*
Adapter used to display list of data in recyclerView
@param onItemClicked : to handle item click event of list
 */
class ListAdapter(
    private val onItemClicked: (Model) -> Unit
): RecyclerView.Adapter<MainViewHolder>() {
    val TAG: String = "ListAdapter"
    var movies = mutableListOf<Model>()

    fun setMovieList(movies: List<Model>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterStarbuckBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    /*
    Binding data set to view in layout
     */
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.name.text = movie.name
        holder.binding.tvDescription.text = movie.desc


         Glide.with(holder.itemView.context).load(movie.imageUrl).into(holder.binding.imageview)
        // Click listener to handle item click
        holder.itemView.setOnClickListener {
            Log.d(TAG,"indide onClickListener")
            onItemClicked(movie) }
    }




    override fun getItemCount(): Int {
        return movies.size
    }
}

/*
View Holder for every item in recyclerView
 */
class MainViewHolder(val binding: AdapterStarbuckBinding) : RecyclerView.ViewHolder(binding.root) {

}