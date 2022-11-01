package com.lagn.practica2mod5.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isEmpty
import com.lagn.practica2mod5.R
import com.lagn.practica2mod5.databinding.ActivityEditBinding
import com.lagn.practica2mod5.databinding.ActivityMainBinding
import com.lagn.practica2mod5.db.DbMovies
import com.lagn.practica2mod5.model.Movie

class EditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditBinding

    private lateinit var dbMovies: DbMovies
    var movie : Movie? = null
    var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
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


                for ((i,value) in genres.withIndex() ){

                    if (movie.genre.equals(value)){

                        spGenres.setSelection(i)


                    }

                }

            }
        }




    }

    private fun listGenres() {

        val genres = resources.getStringArray(R.array.generos)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,genres)

        binding.spGenres.adapter = adapter


    }
    fun click(view: View) {


        with(binding){

            when{
                tietTitle.text.toString().isEmpty()->{

                    tietTitle.error = getString(R.string.errorVacio)
                    Toast.makeText(this@EditActivity, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show()

                }


                spGenres.isEmpty()->{
                    Toast.makeText(this@EditActivity ,getString(R.string.camposVacios), Toast.LENGTH_SHORT).show()

                }

                tietYear.text.toString().isEmpty()->{
                    tietYear.error = getString(R.string.errorVacio)
                    Toast.makeText(this@EditActivity, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show()

                }

                else->{

                    val movie = Movie(id,tietTitle.text.toString(),spGenres.selectedItem.toString(),tietYear.text.toString().toInt())

                    if (dbMovies.update(movie)){
                        Toast.makeText(this@EditActivity,getString(R.string.registroAc), Toast.LENGTH_SHORT).show()

                        val intetn = Intent(this@EditActivity, DetailsActivity::class.java)
                        intetn.putExtra("ID",id)
                        startActivity(intetn)
                        finish()
                    }else{
                        Toast.makeText(this@EditActivity,getString(R.string.registroNoAc), Toast.LENGTH_SHORT).show()
                    }






                }
            }
        }



    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DetailsActivity::class.java))

    }

}