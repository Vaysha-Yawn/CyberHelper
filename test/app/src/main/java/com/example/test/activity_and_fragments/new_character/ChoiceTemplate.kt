package com.example.test.activity_and_fragments.new_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.helpers.TemplateAdapterRecycler
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.data_base.Character
import com.example.test.data_base.TemplateCharacter
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import kotlin.Exception


class ChoiceTemplate : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.choice_template_for_character, container, false)
        val characterRV = view.findViewById<RecyclerView>(R.id.character_Grid)

        val gameId = mCharacterVM.gameId

        try {
            //CharacterAdapter ищем, подключаем
            val adapterMain = TemplateAdapterRecycler()
            characterRV.layoutManager =
                GridLayoutManager(view.context, 3)
            characterRV.adapter = adapterMain
            val mainList = listOf(TemplateCharacter().mapCharacter["Гриз"]!!)
            adapterMain.setData(mainList, gameId, "Main")
        }catch (e:Exception){
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            view.findNavController().popBackStack()
        }
        return view
    }
}