package com.example.test.edit_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.realm.other_realm_object.ParamNum
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.data_base.realm.game_system.GameSystemDAO
import com.example.test.components.views.PlusMinusView


class Edit_number : Fragment() {

    private val mCharacterVM: CharacterVM by activityViewModels()
    private val mGameSystemVM: GameSystemDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_number, container, false)

        val titleView = view.findViewById<TextView>(R.id.key)
        val closeBtn = view.findViewById<Button>(R.id.close)
        val applyBtn = view.findViewById<Button>(R.id.apply)

        val characterId = mCharacterVM.characterId
        val arg = this.arguments
        val paramName = arg?.getString("paramName", "") ?: ""
        val groupTitle = arg?.getString("groupTitle", "") ?: ""
        val value = arg?.getString("value", "") ?: ""

        val indexItem = arg?.getInt("indexItem", -1) ?: -1
        val indexParam = arg?.getInt("indexParam", -1) ?: -1

        val mod = arg?.getInt("mod", -1) ?: -1
        if (mod == -1) {
            Toast.makeText(view.context, "Ошибка выбора модификатора", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack()
        }

        var param = ParamNum()
            if (mod == 0 || mod == 1) {
            for(i in mGameSystemVM.currentGameSystem!!.templateParamNum) {
                if (i.forItemOrCharacter == false && i.name == paramName){
                    param = i.getCopy()
                }
            }
        } else {
                for(i in mGameSystemVM.currentGameSystem!!.templateParamNum) {
                    if (i.forItemOrCharacter == true && i.name == paramName){
                        param = i.getCopy()
                    }
                }
        }

        titleView.text = param.name

        // подключаем фрагмент плюс и минус
        val PM = view.findViewById<PlusMinusView>(R.id.fr)
        PM.setListener(param.maxValue, param.minValue, null)

        // при отмене
        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        // при подтверждении
        applyBtn.setOnClickListener {
            val result = PM.getValue().toIntOrNull()
            var res = 1
            if (result != null) {
                // проверки
                if (param.minValue != null) {
                    if (result < param.minValue!!) {
                        res = 0
                        Toast.makeText(
                            view.context,
                            "Значение параметра не может быть меньше ${param.minValue!!}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                if (param.maxValue != null) {
                    if (result > param.maxValue!!) {
                        res = 0
                        Toast.makeText(
                            view.context,
                            "Значение параметра не должно превышать ${param.maxValue!!}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                if (res == 1) {
                    when (mod) {
                        0 -> {//при добавлении параметра персонажу
                            param.value = result
                            mCharacterVM.addCharacterParamNum(characterId, groupTitle, param)
                            view.findNavController().popBackStack()
                        }
                        1 -> {// при обновлении параметра персонажа
                            mCharacterVM.updateCharacterParamNum(
                                characterId,
                                result,
                                groupTitle,
                                paramName
                            )
                        }
                        2 -> {// при добавлении параметра предмету
                            param.value = result
                            param.removable = true
                            mCharacterVM.LOCaddParamNumItem(param)
                            view.findNavController().popBackStack()

                        }
                        3 -> {// при обновлении параметра предмета
                            param.value = result
                            param.removable = true
                            mCharacterVM.LOCupdateParamNumItem(indexParam, param)
                        }
                    }
                    view.findNavController().popBackStack()
                }
            } else {
                Toast.makeText(view.context, "Введено не число, а ${PM.getValue()}", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}