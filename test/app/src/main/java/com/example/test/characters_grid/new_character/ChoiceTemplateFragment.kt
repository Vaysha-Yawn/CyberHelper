package com.example.test.characters_grid.new_character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.data_base.realm.game_system.GameSystemDAO
import kotlin.Exception


class ChoiceTemplateFragment : Fragment() {

    private val mCharacterVM: CharacterVM by activityViewModels()
    private val mGameSystemVM: GameSystemDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.choice_template_for_character, container, false)
        val characterRV = view.findViewById<RecyclerView>(R.id.character_Grid)

        val newOrPres = requireArguments().getBoolean("newOrPres")

        try {
            //CharacterAdapter ищем, подключаем
            val adapterMain = TemplateAdapterRecycler(newOrPres)
            characterRV.layoutManager =
                GridLayoutManager(view.context, 3)
            characterRV.adapter = adapterMain
            val mainList = mGameSystemVM.getTemplatesCharacter()
            adapterMain.setData(mainList)
        }catch (e:Exception){
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            view.findNavController().popBackStack()
        }
        return view
    }
}