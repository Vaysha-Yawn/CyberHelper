package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.ParamStr
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.GameSystemDAO

class Edit_string : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mGameSystemVM: GameSystemDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_string, container, false)

        val titleView = view.findViewById<TextView>(R.id.key)
        val editView = view.findViewById<TextView>(R.id.edit_string)
        val closeBtn = view.findViewById<TextView>(R.id.close)
        val applyBtn = view.findViewById<TextView>(R.id.apply)

        val characterId = mCharacterVM.characterId
        val gameId = mCharacterVM.gameId
        val arg = this.arguments
        val value = arg?.getString("value", "") ?: ""
        val groupTitle = arg?.getString("groupTitle", "") ?: ""
        val paramName = arg?.getString("paramName", "") ?: ""

        val indexItem = arg?.getInt("indexItem", -1) ?: -1
        val indexParam = arg?.getInt("indexParam", -1) ?: -1

        val mod = arg?.getInt("mod", -1) ?: -1
        if (mod==-1){
            Toast.makeText(view.context, "Ошибка выбора модификатора", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack()
        }
        titleView.text = paramName
        editView.text = value

        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        applyBtn.setOnClickListener {
            val result = editView.text.toString()
            when(mod){
                0-> {//при добавлении параметра персонажу
                    var param: ParamStr = ParamStr()
                    for (i in mGameSystemVM.currentGameSystem.templateParamStr) {
                        if (i.name == paramName) {
                            param = i
                        }
                    }
                    param.value = result
                    mCharacterVM.addCharacterParamStr(characterId, groupTitle, param)
                    view.findNavController().popBackStack()
                }
                1->{// при обновлении параметра персонажа
                    if (paramName == "Название игры") {
                        mGameVM.updateGameName(gameId, result)
                    } else {
                        mCharacterVM.updateCharacterParamStr(
                            characterId,
                            result,
                            groupTitle,
                            paramName
                        )
                    }
                }
                2-> {// при добавлении параметра предмету
                    var param: ParamStr = ParamStr()
                    for (i in mGameSystemVM.currentGameSystem.templateParamStr) {
                        if (i.name == paramName) {
                            param = i
                        }
                    }
                    param.value = result
                    param.removable = true
                    mCharacterVM.LOCaddParamStrItem(param)
                    view.findNavController().popBackStack()
                }
                3-> {// при обновлении параметра предмета
                    var param: ParamStr = ParamStr()
                    for (i in mGameSystemVM.currentGameSystem.templateParamStr) {
                        if (i.name == paramName) {
                            param = i
                        }
                    }
                    param.value = result
                    param.removable = true
                    mCharacterVM.LOCupdateParamStrItem(indexParam, param)
                }
            }
            Toast.makeText(view.context, "Успено изменено", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack()
        }
        return view
    }
}