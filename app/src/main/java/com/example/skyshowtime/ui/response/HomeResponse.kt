package com.example.skyshowtime.ui.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class HomeResponse (val data: Data)

data class Data (val group : Group)

data class Group ( val rails : List<Rail>)

data class Rail (val title : String, val items : List<Item>?)

@Parcelize
data class Item (val id :String, var title : String, val classification : String, var images : List<Image>, var synopsisLong :String): Parcelable


@Parcelize
data class Image (val url : String, val type : String): Parcelable
