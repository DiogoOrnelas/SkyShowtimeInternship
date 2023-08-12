package com.example.skyshowtime.ui.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class SearchAsset (val id: String, val attributes : Attribute)

@Parcelize
data class Attribute(
    val images: List<Image>,
    val title: String,
    val classification: List<String>,
    val synopsisLong: String,
    val programmeUuid: String?
) :Parcelable


