package com.example.getpostdeleteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.getpostdeleteapp.model.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(posts : MutableList<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(post : Post)

    @Query("SELECT * FROM post")
    fun findAllData(): LiveData<MutableList<Post>>

    @Query("DELETE FROM post")
    fun clearData()

    @Delete
    fun deleteData(post : Post)
}