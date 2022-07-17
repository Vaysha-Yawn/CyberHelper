package com.example.test.edit_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.data_base.realm.game.GameDAO
import com.example.test.iniciative.presentation.view_model.InitiativeFightVM

class Delete : Fragment() {

    private val mCharacterVM: CharacterVM by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.delete, container, false)
        val param = this.requireArguments().getString("param", "")

        when (param) {
            "character" -> {
                val id = mCharacterVM.characterId
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить персонажа №${id} ?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                    try {
                        mCharacterVM.deleteCharacter(id)
                    }catch (e:java.lang.Exception){Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()}
                    view.findNavController().popBackStack()
                    view.findNavController().popBackStack()
                    view.findNavController().popBackStack()
                }
            }
            "game" -> {
                val id = this.requireArguments().getString("id", "").toInt()
                val key = this.requireArguments().getString("key", "")
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить $key ?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                    mGameVM.deleteGameById(id)
                    val gameId =
                        view.context.getSharedPreferences("id", 0).getString("PresentGame", "0")!!
                            .toInt()
                    if (mGameVM.gameList.value.isNullOrEmpty()) {
                        view.context.getSharedPreferences("id", 0).edit()
                            .putString("PresentGame", "0").apply()
                    } else if (id == gameId) {
                        val newGameId = mGameVM.gameList.value?.last()?.id!!
                        view.context.getSharedPreferences("id", 0).edit()
                            .putString("PresentGame", newGameId.toString()).apply()
                    }
                    view.findNavController().popBackStack()
                }
            }
            "paramStr" -> {
                val titleGroup = this.requireArguments().getString("titleGroup", "")
                val nameParam = this.requireArguments().getString("nameParam", "")
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить $nameParam ?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                    val id = mCharacterVM.characterId
                    val indexItem = this.requireArguments().getInt("indexItem", -1)
                    if (indexItem == -1) {
                        mCharacterVM.deleteCharacterParamStr(id, titleGroup, nameParam)
                    } else {
                        val indexParam = this.requireArguments().getInt("indexParam", -1)
                        mCharacterVM.deleteItemParamStr(id, titleGroup, indexItem, indexParam)
                    }
                    view.findNavController().popBackStack()
                }
            }
            "paramNum" -> {
                val titleGroup = this.requireArguments().getString("titleGroup", "")
                val nameParam = this.requireArguments().getString("nameParam", "")
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить $nameParam ?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                    val id = mCharacterVM.characterId
                    try {
                        val indexItem = this.requireArguments().getInt("indexItem", -1)
                        if (indexItem == -1) {
                            mCharacterVM.deleteCharacterParamNum(id, titleGroup, nameParam)
                        } else {
                            val indexParam = this.requireArguments().getInt("indexParam", -1)
                            mCharacterVM.deleteItemParamNum(id, titleGroup, indexItem, indexParam)
                        }
                    } catch (e: Exception) {
                        Toast.makeText(view.context, "from delete $e ", Toast.LENGTH_LONG).show()
                    }
                    view.findNavController().popBackStack()
                }
            }
            "paramOptions" -> {
                val titleGroup = this.requireArguments().getString("titleGroup", "")
                val nameParam = this.requireArguments().getString("nameParam", "")
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить $nameParam ?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                    val id = mCharacterVM.characterId
                    val indexItem = this.requireArguments().getInt("indexItem", -1)
                    if (indexItem == -1) {
                        mCharacterVM.deleteCharacterParamOptions(id, titleGroup, nameParam)
                    } else {
                        val indexParam = this.requireArguments().getInt("indexParam", -1)
                        mCharacterVM.deleteItemParamOption(id, titleGroup, indexItem, indexParam)
                    }
                    view.findNavController().popBackStack()
                }
            }
            "item" -> {
                val titleGroup = this.requireArguments().getString("titleGroup", "")
                val nameItem = this.requireArguments().getString("nameItem", "")
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить $nameItem ?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                    val id = mCharacterVM.characterId
                    mCharacterVM.deleteCharacterParamItem(id, titleGroup, nameItem)
                    view.findNavController().popBackStack()
                }
            }
            "effectWeapon" -> {
                val titleGroup = this.requireArguments().getString("titleGroup", "")
                val indexItem = this.requireArguments().getInt("indexItem", -1)
                val indexEff = this.requireArguments().getInt("indexEff", -1)
                if (indexItem == -1 || indexEff == -1) {
                    Toast.makeText(view.context, "Ошибка", Toast.LENGTH_SHORT)
                        .show()
                    view.findNavController().popBackStack()
                }
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить этот эффект?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                    val id = mCharacterVM.characterId
                    mCharacterVM.deleteEffectWeapon(id, titleGroup, indexItem, indexEff)
                    view.findNavController().popBackStack()
                }
            }
            "effectAdd" -> {
                val titleGroup = this.requireArguments().getString("titleGroup", "")
                val indexItem = this.requireArguments().getInt("indexItem", -1)
                val indexEff = this.requireArguments().getInt("indexEff", -1)
                if (indexItem == -1 || indexEff == -1) {
                    Toast.makeText(view.context, "Ошибка", Toast.LENGTH_SHORT)
                        .show()
                    view.findNavController().popBackStack()
                }
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить этот эффект?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                    val id = mCharacterVM.characterId
                    mCharacterVM.deleteEffectAdd(id, titleGroup, indexItem, indexEff)
                    view.findNavController().popBackStack()
                }
            }

            "InitiativeFight"->{
                val id = this.requireArguments().getInt("id", 0)
                val gameId = this.requireArguments().getInt("gameId", 0)
                val key = this.requireArguments().getString("key", "")
                view.findViewById<TextView>(R.id.description).text =
                    "Вы уверены, что хотите удалить $key ?"
                view.findViewById<Button>(R.id.delete).setOnClickListener {
                   val mInitiativeFightVM = ViewModelProvider(this).get(InitiativeFightVM::class.java)
                    mInitiativeFightVM.deleteInitiativeFight(id)
                    view.findNavController().popBackStack()
                }
            }
        }

        view.findViewById<Button>(R.id.close).setOnClickListener {
            view.findNavController().popBackStack()
        }

        return view
    }
}