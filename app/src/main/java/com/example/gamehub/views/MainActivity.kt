package com.example.gamehub.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamehub.R
import com.example.gamehub.models.Game
import com.example.gamehub.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GameAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var moreGamesButton: Button
    private var games = ArrayList<Game>()

    private fun initAdapter() {
        adapter = GameAdapter(games, this)

        recyclerView = findViewById(R.id.listGames)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun bindObservers() {
        viewModel.getPopularGames().observe(this, {
            games.clear()
            games.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.getState().observe(this, {
            recyclerView.visibility = if (it == MainViewModel.States.SUCCESS) View.VISIBLE else View.GONE
            moreGamesButton.visibility = if (it == MainViewModel.States.SUCCESS) View.VISIBLE else View.GONE
            findViewById<ProgressBar>(R.id.loading).visibility = if (it == MainViewModel.States.LOADING) View.VISIBLE else View.GONE

            if (it == MainViewModel.States.ERROR) {
                Toast.makeText(this, viewModel.error, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initAdapter()
        bindObservers()
        moreGamesButton = findViewById(R.id.moreGames)
        viewModel.fetchPopularGames()
    }

}