package com.lagn.practica2mod5.db

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.widget.Toast
import com.lagn.practica2mod5.model.Movie

import kotlin.Exception

class DbMovies(private val contex: Context): DbHelper(contex) {

    //operaciones CRUD
    fun insertMovie(title: String, genre: String, year: Int ): Long{

        val dbHelper = DbHelper(contex)
        val db = dbHelper.writableDatabase

        var id: Long = 0

        try {
            val values = ContentValues()
            values.put("title",title)
            values.put("genre",genre)
            values.put("year",year)

            id = db.insert("movies", null, values)
        }catch (e: Exception){

           Toast.makeText(contex,"Error: $e", Toast.LENGTH_SHORT).show()
        }finally {
            db.close()
        }

        return id

    }

    fun getMovies(): ArrayList<Movie>{
        val dbHelper = DbHelper(contex)
        val db = dbHelper.writableDatabase

        var listMovies = ArrayList<Movie>()

        var movieTmp : Movie? = null

        var cursor :Cursor? = null

        cursor = db.rawQuery("SELECT * FROM MOVIES", null)


        if (cursor.moveToFirst()){
            do {
                movieTmp = Movie(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getInt(3))
                listMovies.add(movieTmp)
            }while (cursor.moveToNext())

        }

        cursor.close()



        return listMovies



    }

    fun getMovie(id:Int): Movie?{
        val dbHelper = DbHelper(contex)
        val db = dbHelper.writableDatabase

        var movie: Movie? = null

        var cursor: Cursor? = null
        cursor = db.rawQuery("SELECT * FROM MOVIES WHERE id = $id LIMIT 1", null)

        if (cursor.moveToFirst()){

                movie = Movie(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getInt(3))

        }

        cursor.close()


        return movie




    }


    fun update(movie: Movie): Boolean{

        var bandera = false

        val dbHelper = DbHelper(contex)
        val db = dbHelper.writableDatabase

        try {
            db.execSQL("UPDATE MOVIES SET title = '${movie.title}', genre = '${movie.genre}', year = '${movie.año}' WHERE id = ${movie.id}")
            bandera = true

        }catch (e: Exception){
            Toast.makeText(contex,e.toString(), Toast.LENGTH_SHORT).show()

        }
        finally {
            db.close()

        }


        return bandera



    }

    fun deleteMovie(id: Int): Boolean{

        var bandera = false

        val dbHelper = DbHelper(contex)
        val db = dbHelper.writableDatabase



        try {
            db.execSQL("DELETE FROM MOVIES WHERE id = $id")
            bandera = true

        }catch (e: Exception){
            Toast.makeText(contex,e.toString(), Toast.LENGTH_SHORT).show()

        }
        finally {
            db.close()

        }


        return bandera
    }



}