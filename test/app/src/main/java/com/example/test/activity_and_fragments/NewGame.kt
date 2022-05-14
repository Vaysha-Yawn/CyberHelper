package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.NewHost
import com.example.test.adapters.CharacterAdapter
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.NewCharacterVM

class NewGame : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mNewVM: NewCharacterVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_game, container, false)
        try {
            val gameId = mCharacterVM.gameId
            val tvGameName = view.findViewById<TextView>(R.id.NewGame_EditNameGame)
            tvGameName.text = mNewVM.gameName.value

            val newCharacter = view.findViewById<Button>(R.id.NewGame_NewCharacter)
            newCharacter.setOnClickListener {
                mNewVM.gameName.value = tvGameName.text.toString()
                view.findNavController().navigate(R.id.action_newGame_to_new_choiceTemplate)
            }

            //CharacterAdapter ищем, подключаем
            val hGridView: GridView = view.findViewById(R.id.NewGame_Grid)
            val adapter = CharacterAdapter()
            hGridView.adapter = adapter

            // Устанавливаем данные

            mCharacterVM.characterList.observe(viewLifecycleOwner) {
                adapter.setCharacterList(it, false, null)
            }

            view.findViewById<Button>(R.id.NewGame_Clear).setOnClickListener {
                mCharacterVM.clearCharactersByGameId(gameId)
                Toast.makeText(view.context, "Данные очищены", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }

            val done = view.findViewById<Button>(R.id.NewGame_Done)
            done.setOnClickListener {
                val gameName = tvGameName.text.toString()
                if (gameName != "") {
                    addGame(gameId, gameName)
                    view.context.getSharedPreferences("id", 0).edit()
                        .putString("PresentGame", gameId.toString()).apply()
                    view.context.getSharedPreferences("id", 0).edit().remove("newGameId").apply()

                    (activity as NewHost).goToPresentGame()
                } else {
                    Toast.makeText(
                        view.context,
                        "Введите, пожалуйста, название игры",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            view.findViewById<ImageButton>(R.id.back).setOnClickListener {
                (activity as NewHost).backToMain()
            }
        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    private fun addGame(gameId: Int, nameGame: String) {
        mGameVM.addGame(gameId, nameGame)
        Toast.makeText(view?.context, "Новая игра № $gameId", Toast.LENGTH_SHORT).show()
    }
}