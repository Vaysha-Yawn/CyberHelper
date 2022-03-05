package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.LoadHost
import com.example.test.helpers.LoadAdapter
import com.example.test.viewModels.GameDAO


class LoadGame : Fragment() {

    private val mGameVM: GameDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.load_game, container, false)

        val vGridView: GridView = view.findViewById(R.id.loadGame_GridView)
        val adapter = LoadAdapter()
        vGridView.adapter = adapter

        mGameVM.gameList.observe(viewLifecycleOwner) {
            adapter.setGameList(it)
        }

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            (activity as LoadHost).backToMain()
        }
        return view
    }

}
