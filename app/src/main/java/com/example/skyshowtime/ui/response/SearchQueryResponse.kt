package com.example.skyshowtime.ui.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SearchQueryResponse (val results: List<Result>):Parcelable

@Parcelize
data class Result (val t : String , val uuid : String):Parcelable

