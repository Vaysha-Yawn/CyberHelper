package com.example.test.characters_grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.realm.character.Character
import com.example.test.data_base.data_sample.test_data.DTemplateParamStr
import com.example.test.databinding.CardCharacterBinding


class CharacterAdapter : BaseAdapter() {

    private var characterList = emptyList<Character>()
    private var presOrNew = true//true - Present, false - New
    private var iniciativaId:Int? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = LayoutInflater.from(parent!!.context)
        val view = convertView ?: inflater.inflate(R.layout.card_character, parent, false)

        val character: Character = getItem(position)
        val name = DTemplateParamStr().readParamStr(character, "Базовые параметры", "Имя персонажа")

        view.findViewById<TextView>(R.id.HomeCard_Name).text = name

        view.findViewById<ImageButton>(R.id.HomeCard_Avatar).setOnClickListener {
            try {
                val bundle = Bundle()
                bundle.putInt("characterId", character.id)
                if (position==0 && iniciativaId!=null){
                    bundle.putInt("fightId", iniciativaId!!)
                }
                if (presOrNew) {
                    view.findNavController().navigate(R.id.action_home2_to_characterMenu2, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_newGame_to_new_characterList, bundle)
                }
            } catch (e: Exception) {
                Toast.makeText(view.context, "$e", Toast.LENGTH_SHORT).show()
            }
        }
        val binding = CardCharacterBinding.bind(view)
        with(binding) {
            if (iniciativaId!=null){
                iniciative.visibility = View.VISIBLE
                iniciative.text = (position+1).toString()
            }else{
                iniciative.visibility = View.GONE
            }
        }


        return view
    }

    override fun getItem(position: Int): Character = characterList[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = characterList.size

    fun setCharacterList(charList: List<Character>, presentOrNew: Boolean, iniciativaId: Int?) {
        this.characterList = charList
        this.presOrNew = presentOrNew
        this.iniciativaId = iniciativaId
        notifyDataSetChanged()
    }

}
