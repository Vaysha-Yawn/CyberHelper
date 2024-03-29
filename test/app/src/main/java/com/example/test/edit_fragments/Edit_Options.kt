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
import com.example.test.data_base.realm.other_realm_object.ParamOptions
import com.example.test.databinding.EditOptionBinding
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.data_base.realm.game_system.GameSystemDAO

class Edit_Options : Fragment(){

    private val mCharacterVM: CharacterVM by activityViewModels()
    private val mGameSystemVM: GameSystemDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_option, container, false)

        val binding = EditOptionBinding.bind(view)
        with(binding) {

            val titleView = view.findViewById<TextView>(R.id.key)
            val closeBtn = view.findViewById<Button>(R.id.close)
            val applyBtn = view.findViewById<Button>(R.id.apply)

            val characterId = mCharacterVM.characterId
            val arg = this@Edit_Options.arguments
            val paramName = arg?.getString("paramName", "") ?: ""
            val groupTitle = arg?.getString("titleGroup", "") ?: ""
            val value = arg?.getString("value", "") ?: ""

            val indexItem = arg?.getInt("indexItem", -1) ?: -1
            val indexParam = arg?.getInt("indexParam", -1) ?: -1

            val mod = arg?.getInt("mod", -1) ?: -1
            if (mod == -1) {
                Toast.makeText(view.context, "Ошибка выбора модификатора", Toast.LENGTH_SHORT)
                    .show()
                view.findNavController().popBackStack()
            }

            var param = ParamOptions()
            if (mod == 0 || mod == 1) {
                for(i in mGameSystemVM.currentGameSystem!!.templateParamOptions) {
                    if (i.forItemOrCharacter == false && i.name == paramName){
                        param = i.getCopy()
                    }
                }
            } else {
                for(i in mGameSystemVM.currentGameSystem!!.templateParamOptions) {
                    if (i.forItemOrCharacter == true && i.name == paramName){
                        param = i.getCopy()
                    }
                }
            }

            titleView.text = paramName

            val options = mutableListOf<String>()
            for (i in param.options) {
                options.add(i)
            }

            dropDown.setDDArrayAndListener(options, null, null)
            dropDown.setMainText(value)

            closeBtn.setOnClickListener {
                view.findNavController().popBackStack()
            }

            applyBtn.setOnClickListener {
                val res = dropDown.getValueFromDD()
                when (mod) {
                    0 -> {//при добавлении параметра персонажу
                        param.value = res
                        mCharacterVM.addCharacterParamOptions(characterId, groupTitle, param)
                        view.findNavController().popBackStack()
                    }
                    1 -> {// при обновлении параметра персонажа
                        mCharacterVM.updateCharacterParamOptions(
                            characterId,
                            res,
                            groupTitle,
                            paramName
                        )
                    }
                    2 -> {// при добавлении параметра предмету
                        param.value = res
                        param.removable = true
                        mCharacterVM.LOCaddParamOptionsItem(param)
                        view.findNavController().popBackStack()
                    }
                    3 -> {// при обновлении параметра предмета
                        param.value = res
                        param.removable = true
                        mCharacterVM.LOCupdateParamOptionsItem(indexParam, param)
                    }
                }
                Toast.makeText(view.context, "Успено изменено", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack()
            }
        }
        return view
    }
}