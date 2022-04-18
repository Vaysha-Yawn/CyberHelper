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
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.viewModels.CharacterDAO
import com.example.test.helpers.CharacterAdapter
import com.example.test.viewModels.GameDAO
import java.lang.Exception

class Home : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM:GameDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home, container, false)
        try{
            val gameId = mCharacterVM.gameId

            val nameGame: TextView = view.findViewById(R.id.Home_NameGame)
            mGameVM.initGameName(gameId)
            mGameVM.gameName.observe(viewLifecycleOwner) { name ->
                nameGame.text = name
            }

            //CharacterAdapter ищем, подключаем
            val hGridView: GridView = view.findViewById(R.id.Home_Grid)
            val adapter = CharacterAdapter()
            hGridView.adapter = adapter

            // Устанавливаем данные
            mCharacterVM.characterList.observe( viewLifecycleOwner) {
                adapter.setCharacterList(it, true)
            }

            view.findViewById<Button>(R.id.edit_game_name).setOnClickListener {
                val bundle = Bundle()
                bundle.putString("paramName", "Название игры")
                bundle.putString("value", nameGame.text.toString())
                bundle.putString("groupTitle", "Название игры")
                bundle.putInt("indexItem", -1)
                bundle.putInt("indexParam", -1)
                bundle.putInt("mod", 1)
                view.findNavController().navigate(R.id.action_home2_to_edit_string2, bundle)
            }

            val newCharacter = view.findViewById<Button>(R.id.Home_NewCharacter)
            newCharacter.setOnClickListener {
                view.findNavController().navigate(R.id.action_home2_to_choiceTemplate)
            }

            view.findViewById<Button>(R.id.Home_button).setOnClickListener {
                (activity as PresentHost).openIniciativa(mCharacterVM.characterId)
            }

            view.findViewById<ImageButton>(R.id.back).setOnClickListener {
                (activity as PresentHost).backToMain()       }
        }catch (e:Exception){Toast.makeText(view.context, "$e", Toast.LENGTH_SHORT).show()}

        return view
    }

}
