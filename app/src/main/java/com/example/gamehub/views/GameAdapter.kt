package com.example.gamehub.views

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamehub.R
import com.example.gamehub.models.Game

class GameAdapter(private val games: ArrayList<Game>, private val activity: MainActivity): RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.gameTitle)
        private val ratingTextView: TextView = itemView.findViewById(R.id.gameRating)
        private val metacriticTextView: TextView = itemView.findViewById(R.id.gameMetacritic)
        private val releasedTextView: TextView = itemView.findViewById(R.id.gameReleased)
        private val imageTextView: ImageView = itemView.findViewById(R.id.gameImage)
        private val infoLayout: LinearLayout = itemView.findViewById(R.id.infoLayout)

        fun setContent(
            title: String,
            rating: Double,
            metacritic: Double,
            released: String,
            imageUrl: String,
            id: Int
        ) {
            titleTextView.text = title
            ratingTextView.text = itemView.context.getString(R.string.game_rating_label, rating)
            metacriticTextView.text = itemView.context.getString(
                R.string.game_metacritic_label,
                metacritic
            )
            releasedTextView.text = itemView.context.getString(
                R.string.game_released_label,
                released
            )
            Glide.with(itemView.context).load(imageUrl).fitCenter().into(imageTextView)

            var btn = Button(itemView.context)
            btn.backgroundTintList = ColorStateList.valueOf(itemView.context.getColor(R.color.black))
            btn.text = "Details"
            btn.setTextColor(itemView.context.getColor(R.color.white))
            btn.textSize = 12F
            //btn.setOnClickListener { activity.startGameActivity(id) }
            btn.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            infoLayout.addView(btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]
        holder.setContent(game.name, game.rating, game.metacritic, game.released, game.imageUrl, game.id)
    }

    override fun getItemCount(): Int {
        return games.size
    }
}