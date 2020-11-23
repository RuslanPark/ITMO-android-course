package com.example.getpostdeleteapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.getpostdeleteapp.model.Post


@Database(entities = [Post::class], version = 1)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao() : PostDao

    companion object {
        @Volatile
        private var INSTANCE: PostDatabase? = null

        fun getDatabase(context: Context): PostDatabase {
            val tempInstanse = INSTANCE
            if (tempInstanse != null) {
                return tempInstanse
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostDatabase::class.java,
                    "DATABASE"
                ).build()

                INSTANCE = instance
                return instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}