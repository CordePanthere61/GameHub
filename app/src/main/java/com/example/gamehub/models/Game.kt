package com.example.gamehub.models

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("metacritic") val metacritic: Double,
    @SerializedName("released") val released: String,
    @SerializedName("background_image") val imageUrl: String
)