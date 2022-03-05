package com.example.test.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.Character
import com.example.test.data_base.TemplateParamStr
import java.lang.Exception


class CharacterAdapter() : BaseAdapter() {

    private var characterList = emptyList<Character>()
    private var presOrNew = true//true - Present, false - New

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = LayoutInflater.from(parent!!.context)
        val view = convertView ?: inflater.inflate(R.layout.card_character, parent, false)

        val character: Character = getItem(position)
        val name = TemplateParamStr().readParamStr(character, "Базовые параметры", "Имя персонажа")

        view.findViewById<TextView>(R.id.HomeCard_Name).text = name

        view.findViewById<ImageButton>(R.id.HomeCard_Avatar).setOnClickListener {
            try {
                val bundle = Bundle()
                bundle.putInt("characterId", character.id)
                if (presOrNew) {
                    view.findNavController().navigate(R.id.action_home2_to_characterMenu, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_newGame_to_new_characterList3, bundle)
                }
            } catch (e: Exception) {
                Toast.makeText(view.context, "$e", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun getItem(position: Int): Character = characterList[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = characterList.size

    fun setCharacterList(charList: List<Character>, presentOrNew: Boolean) {
        characterList = charList
        presOrNew = presentOrNew
        notifyDataSetChanged()
    }

}
