package com.example.getpostdeleteapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "post")
@Parcelize
data class Post(
    @ColumnInfo(name = "user_id") val userId : Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "body") val body : String
) : Parcelable