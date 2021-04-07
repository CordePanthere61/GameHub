package com.example.gamehub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamehub.models.Game
import com.example.gamehub.models.GameResult
import com.example.gamehub.services.RawgService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val NUMBER_OF_GAMES = 10
private const val API_KEY = "bc864a4af8474efdb0015d7069ebd502"

class MainViewModel: ViewModel() {
    enum class States(val value: Int) {
        NONE(0),
        LOADING(1),
        SUCCESS(2),
        ERROR(3)
    }

    private val popularGames = MutableLiveData(listOf<Game>())
    private val state = MutableLiveData(States.NONE)
    var error = ""
        private set

    private val rawgService by lazy{
        RawgService.create()
    }

    fun getPopularGames(): LiveData<List<Game>> {
        return popularGames
    }

    fun getState(): LiveData<States> {
        return state
    }

    fun fetchPopularGames() {
        state.value = States.LOADING
        var dates = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "," + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        rawgService.getPopularGames(API_KEY, NUMBER_OF_GAMES, dates).enqueue(object : Callback<GameResult> {
            override fun onResponse(call: Call<GameResult>, response: Response<GameResult>) {
                popularGames.value = response.body()!!.games
                state.value = States.SUCCESS
            }

            override fun onFailure(call: Call<GameResult>, t: Throwable) {
                error = t.message?: "Unrecognized Error"
                state.value = States.ERROR
            }
        })
    }
}