package com.example.photoviewerapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(val description: String?, val url: String?) : Parcelable
