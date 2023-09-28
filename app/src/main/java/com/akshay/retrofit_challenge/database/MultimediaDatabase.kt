package com.akshay.retrofit_challenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akshay.retrofit_challenge.model.MultiMediaModel

@Database(entities = [MultiMediaModel::class], version = 1)
abstract class MultimediaDatabase: RoomDatabase() {
    abstract fun mediaDao(): MultimediaDao

    companion object{

        @Volatile
        private var Instance : MultimediaDatabase? = null

        fun getDataBaseDetails(context: Context): MultimediaDatabase {
            return Instance ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context, MultimediaDatabase::class.java,"books_database"
                ).allowMainThreadQueries().build()
                Instance = instance
                instance
            }
        }

    }
}
