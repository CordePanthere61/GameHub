package com.example.gamehub.services


import com.example.gamehub.models.GameResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface RawgService {
//    @GET("/")
//    fun getMovies(
//            @Query("apikey") apiKey: String,
//            @Query("s") searchValue: String,
//    ): Call<MovieResult>

    @GET("games")
    fun getPopularGames(
        @Query("key") apiKey: String,
        @Query("page_size") nbOfGames: Int,
        @Query("dates") dates: String
    ): Call<GameResult>


    companion object {
        fun create(): RawgService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    ).baseUrl("https://api.rawg.io/api/")
                    .build()

            return retrofit.create(RawgService::class.java)
        }
    }
}