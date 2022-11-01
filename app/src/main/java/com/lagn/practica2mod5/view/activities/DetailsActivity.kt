package com.lagn.practica2mod5.view.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.lagn.practica2mod5.R
import com.lagn.practica2mod5.databinding.ActivityDetailsBinding
import com.lagn.practica2mod5.databinding.ActivityMainBinding
import com.lagn.practica2mod5.db.DbMovies
import com.lagn.practica2mod5.model.Movie

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding
    private lateinit var dbMovies: DbMovies
    var movie : Movie? = null
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listGenres()

        val bundle = intent.extras

        if (bundle != null) {
            id = bundle.getInt("ID",0)
        }
        dbMovies = DbMovies(this)

        movie = dbMovies.getMovie(id)

        movie?.let { movie ->

            with(binding){

                val genres = resources.getStringArray(R.array.generos)


                tietTitle.setText(movie.title)
                tietYear.setText(movie.año.toString())


                for ((i ,value) in genres.withIndex() ){

                    if (movie.genre.equals(value)){

                        spGenres.setSelection(i)


                    }

                }

                // Dessactivando editex
                tietTitle.inputType = InputType.TYPE_NULL
                tietYear.inputType = InputType.TYPE_NULL
                spGenres.isEnabled = false






            }
        }

    }

    private fun listGenres() {

        val genres = resources.getStringArray(R.array.generos)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,genres)

        binding.spGenres.adapter = adapter


    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))

    }

    fun click(view: View) {

        when (view.id){

            R.id.btnEdit ->{
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("ID",id)

                startActivity(intent)
                finish()



            }

            R.id.btnDelete ->{

                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.titleAlertDialog))
                    .setMessage(getString(R.string.messAlert))
                    .setPositiveButton(getString(R.string.acepp), DialogInterface.OnClickListener { dialogInterface, i ->

                        if (dbMovies.deleteMovie(id)){
                            Toast.makeText(this@DetailsActivity, getString(R.string.deleteExito), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@DetailsActivity, MainActivity::class.java))
                            finish()

                        }else  Toast.makeText(this@DetailsActivity, getString(R.string.deleteNoExito), Toast.LENGTH_SHORT).show()

                    })
                    .setNegativeButton(getString(R.string.cancelDele),DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()

                    })
                    .show()


            }

        }




    }
}