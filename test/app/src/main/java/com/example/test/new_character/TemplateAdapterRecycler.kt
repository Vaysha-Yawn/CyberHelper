package com.example.test.new_character


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.Character
import com.example.test.test_data.DTemplateParamStr
import com.example.test.databinding.CardCharacterBinding


class TemplateAdapterRecycler (private val newOrPres: Boolean) : RecyclerView.Adapter<TemplateAdapterRecycler.TemplateHolder>() {

    private var characterList = emptyList<Character>()

    class TemplateHolder(view: View, private val newOrPres: Boolean,) :
        RecyclerView.ViewHolder(view) {
        private val binding = CardCharacterBinding.bind(view)
        fun bind(character: Character, position:Int) = with(binding) {
            val name =
                DTemplateParamStr().readParamStr(character, "Базовые параметры", "Имя персонажа")
            HomeCardName.text = name
            iniciative.visibility = View.GONE
            HomeCardAvatar.setOnClickListener { v ->
                if (newOrPres) {
                    val bundle = Bundle()
                    bundle.putInt("position", position)
                    v.findNavController().navigate(
                        R.id.action_new_choiceTemplate_to_new_newCharacterByTemplate,
                        bundle
                    )
                } else {
                    val bundle = Bundle()
                    bundle.putInt("position", position)
                    v.findNavController().navigate(
                        R.id.action_choiceTemplate_to_newCharacterByTemplate2,
                        bundle
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_character, parent, false)
        return TemplateHolder(view, newOrPres)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(characterList[position], position)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun setData(list: List<Character>) {
        this.characterList = list
        notifyDataSetChanged()
    }

}