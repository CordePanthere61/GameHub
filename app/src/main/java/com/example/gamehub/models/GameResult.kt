package com.example.gamehub.models

import com.google.gson.annotations.SerializedName

data class GameResult(
    @SerializedName("results") val games: List<Game>
)