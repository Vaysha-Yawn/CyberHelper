package com.example.test.helpers


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.Character
import com.example.test.data_base.TemplateParamStr
import com.example.test.databinding.CardCharacterBinding


class TemplateAdapterRecycler:  RecyclerView.Adapter<TemplateAdapterRecycler.TemplateHolder>() {

    private var characterList = emptyList<Character>()
    private var gameId = 0
    private var type = ""

    class TemplateHolder(view: View, private val gameId: String, private val type: String) : RecyclerView.ViewHolder(view) {
        private val binding = CardCharacterBinding.bind(view)
        fun bind(character:Character) = with(binding){
            val name = TemplateParamStr().readParamStr(character, "Базовые параметры", "Имя персонажа")
            HomeCardName.text = name
            HomeCardAvatar.setOnClickListener { v->
                val r = v.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
                if (gameId.toInt()==r){
                    if (type=="Empty"){ // кастомный персонаж для новой игры
                        v.findNavController().navigate(R.id.action_new_choiceTemplate_to_new_newCharacter2)
                    }
                    else {// шаблонный персонаж для новой игры
                        val bundle = Bundle()
                        bundle.putString("name", name)
                        v.findNavController().navigate(R.id.action_new_choiceTemplate_to_new_newCharacterByTemplate2,bundle)
                    }
                }
                else{
                    if (type=="Empty"){// кастомный персонаж для текущей игры
                        v.findNavController().navigate(R.id.action_pres_choiceTemplate_to_pres_newCharacter)
                    }
                    else {// шаблонный персонаж для текущей игры
                        val bundle = Bundle()
                        bundle.putString("name", name)
                        v.findNavController().navigate(R.id.action_pres_choiceTemplate_to_pres_newCharacterByTemplate, bundle)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_character, parent, false)
        return TemplateHolder(view, gameId.toString(), type)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun setData(list:List<Character>, gamId:Int, typer:String){
        this.characterList = list
        this.gameId = gamId
        this.type = typer
        notifyDataSetChanged()
    }

}