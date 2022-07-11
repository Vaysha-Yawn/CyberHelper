package com.example.test.load.presentaion.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.present_game.presentation.activity.PresentHost
import com.example.test.data_base.realm.game.Game

class LoadAdapter() : BaseAdapter() {

    private var gameList = emptyList<Game>()

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = LayoutInflater.from(parent!!.context)
        val view = convertView ?: inflater.inflate(R.layout.card_load, parent, false)

        val game: Game = getItem(position)

        view.findViewById<TextView>(R.id.LoadItem_NameGame).text = game.name
        view.findViewById<TextView>(R.id.LoadItem_SaveNumber).text =
            "Сохранение №" + game.id.toString()

        view.findViewById<Button>(R.id.LoadItem_Delete).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", game.id.toString())
            bundle.putString("param", "game")
            bundle.putString("key", "игру №${game.id}")
            view.findNavController().navigate(R.id.action_loadGame_to_delete4, bundle)
        }

        view.findViewById<Button>(R.id.LoadItem_Load).setOnClickListener {
            val intent = Intent(parent.context, PresentHost::class.java)
            parent.context.getSharedPreferences("id", 0).edit()
                .putString("PresentGame", game.id.toString()).apply()
            parent.context.startActivity(intent)
        }

        return view
    }

    override fun getItem(position: Int): Game = gameList[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = gameList.size

    fun setGameList(liveGameList: List<Game>) {
        this.gameList = liveGameList
        notifyDataSetChanged()
    }

}
