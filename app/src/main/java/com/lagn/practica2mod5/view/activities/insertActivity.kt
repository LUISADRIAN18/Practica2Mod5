package com.lagn.practica2mod5.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import com.lagn.practica2mod5.R
import com.lagn.practica2mod5.databinding.ActivityInsertBinding
import com.lagn.practica2mod5.db.DbMovies

class insertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listGenres()




    }

    private fun listGenres() {

        val genres = resources.getStringArray(R.array.generos)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,genres)

        binding.spGenres.adapter = adapter


    }


    fun click(view: View) {

        val dbMovies = DbMovies(this)

   with(binding){

       when{
           tietTitle.text.toString().isEmpty()->{

               tietTitle.error = getString(R.string.errorVacio)
               Toast.makeText(this@insertActivity, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show()

           }


           spGenres.isEmpty()->{
               Toast.makeText(this@insertActivity, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show()

           }

           tietYear.text.toString().isEmpty()->{
               tietYear.error = getString(R.string.errorVacio)
               Toast.makeText(this@insertActivity, getString(R.string.camposVacios), Toast.LENGTH_SHORT).show()

           }

           else->{

               val id = dbMovies.insertMovie(tietTitle.text.toString(),spGenres.selectedItem.toString(),tietYear.text.toString().toInt())
               Toast.makeText(this@insertActivity,spGenres.selectedItem.toString(), Toast.LENGTH_SHORT).show()
               if (id>0){

                   Toast.makeText(this@insertActivity, getString(R.string.registroGuardarDb),Toast.LENGTH_SHORT).show()
                   tietTitle.text?.clear()
                   tietYear.text?.clear()
                   tietTitle.requestFocus()


               }else{
                   Toast.makeText(this@insertActivity, getString(R.string.errorGuardarDb),Toast.LENGTH_SHORT).show()
               }




           }
       }
   }


    }

    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this,MainActivity::class.java))

    }
}