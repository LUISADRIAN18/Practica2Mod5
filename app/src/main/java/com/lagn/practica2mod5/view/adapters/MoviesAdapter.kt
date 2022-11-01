package com.lagn.practica2mod5.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lagn.practica2mod5.R
import com.lagn.practica2mod5.databinding.ListElementBinding
import com.lagn.practica2mod5.model.Movie
import com.lagn.practica2mod5.view.activities.MainActivity

class MoviesAdapter (private val context: Context, val movies: ArrayList<Movie>): RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: ListElementBinding): RecyclerView.ViewHolder(view.root){
        val tvTitle = view.tvTitle
        val tvGenre = view.tvGenre
        val tvYear = view.tvYear
        var imageGen = view.imageGenre
        var layout = view.lyHorizontal

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListElementBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvTitle.text = movies[position].title
        holder.tvGenre.text = movies[position].genre
        holder.tvYear.text = movies[position].año.toString()


        when{
            holder.tvGenre.text.equals("Comedia") -> {

                holder.imageGen.setImageResource(R.drawable.comedia)
                holder.layout.setBackgroundResource(R.drawable.bc_comedia)


            }

            holder.tvGenre.text.equals("Terror") -> {
                holder.imageGen.setImageResource(R.drawable.terror)
                holder.layout.setBackgroundResource(R.drawable.bc_terror)


            }

            holder.tvGenre.text.equals("Fantasía") -> {
                holder.imageGen.setImageResource(R.drawable.fantasia)
                holder.layout.setBackgroundResource(R.drawable.bc_fantasia)

            }
            holder.tvGenre.text.equals("Romance") -> {
                holder.imageGen.setImageResource(R.drawable.romance)
                holder.layout.setBackgroundResource(R.drawable.bc_romance)


            }

        }






        //Para los clicks de cada elemento viewholder

        holder.itemView.setOnClickListener {
            //Manejar el click
            if(context is MainActivity) context.selectedGame(movies[position])
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

}