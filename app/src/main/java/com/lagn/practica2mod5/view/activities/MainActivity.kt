package com.lagn.practica2mod5.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.lagn.practica2mod5.R
import com.lagn.practica2mod5.databinding.ActivityMainBinding
import com.lagn.practica2mod5.db.DbHelper
import com.lagn.practica2mod5.db.DbMovies
import com.lagn.practica2mod5.model.Movie
import com.lagn.practica2mod5.view.adapters.MoviesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var listMovies: ArrayList<Movie>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dbMov = DbMovies(this)

        listMovies = dbMov.getMovies()

        val adapter = MoviesAdapter(this, listMovies)
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        binding.rvMovies.adapter = adapter

        if (listMovies.size == 0){
            binding.tvSin.visibility = View.VISIBLE
            binding.lyMa.setBackgroundResource(R.drawable.bc_bienvenida)

        }else binding.tvSin.visibility = View.INVISIBLE


    }

    fun click(view: View) {

        startActivity(Intent(this, insertActivity::class.java))
        finish()
    }

    fun selectedGame(movie: Movie){
        //manejasmo el click del elemento en el recycler view}
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("ID", movie.id)
        startActivity(intent)
        finish()


    }
}