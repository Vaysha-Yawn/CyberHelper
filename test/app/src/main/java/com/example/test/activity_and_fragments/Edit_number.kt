package com.example.test.activity_and_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.activity_and_fragments.character_list.CharacterList
import com.example.test.data_base.ParamNum
import com.example.test.data_base.TemplateParamNum
import com.example.test.data_base.TemplateParamOptions
import com.example.test.data_base.TemplateParamStr
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.widgets.PlusAndMinus
import java.lang.Exception


class Edit_number : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()

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
        if (mod==-1){
            Toast.makeText(view.context, "Ошибка выбора модификатора", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack()
        }

        val param = if (mod==0 || mod==1){
            TemplateParamNum().mapParamNum[paramName]!!
        } else {
            TemplateParamNum().mapParamNumItem[paramName]!!
        }

        titleView.text = param.name

        // подключаем фрагмент плюс и минус
        val bundle = Bundle()
        bundle.putInt("value", value.toInt())
        if (param.minValue != null) {
            bundle.putInt("minValue", param.minValue!!)
        }
        if (param.maxValue != null) {
            bundle.putInt("maxValue", param.maxValue!!)
        }
        bundle.putString("them", "green")
        loadFragment(R.id.fr, PlusAndMinus(), bundle)

        // при отмене
        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        // при подтверждении
        applyBtn.setOnClickListener {
            val frag1: Fragment =
                childFragmentManager.findFragmentById(R.id.fr)!!
            val result =
                frag1.view?.findViewById<EditText>(R.id.plus_and_minus_edit)?.text.toString().toIntOrNull()
            var res = 1
            if (result!=null){
                // проверки
                if (param.minValue != null) {
                    if (result < param.minValue!!){
                        res = 0
                        Toast.makeText(
                            view.context, "Значение параметра не может быть меньше ${param.minValue!!}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                if (param.maxValue != null) {
                    if (result > param.maxValue!!){
                        res = 0
                        Toast.makeText(
                            view.context, "Значение параметра не должно превышать ${param.maxValue!!}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                if (res == 1) {
                    when(mod){
                        0->{//при добавлении параметра персонажу
                            param.value = result
                            mCharacterVM.addCharacterParamNum(characterId, groupTitle, param)
                            view.findNavController().popBackStack()
                        }
                        1->{// при обновлении параметра персонажа
                            mCharacterVM.updateCharacterParamNum(
                                characterId,
                                result,
                                groupTitle,
                                paramName
                            )
                        }
                        2->{// при добавлении параметра предмету
                            param.value = result
                            param.removable = true
                            mCharacterVM.LOCaddParamNumItem(param)
                            view.findNavController().popBackStack()
                        }
                        3->{// при обновлении параметра предмета
                            param.value = result
                            param.removable = true
                            mCharacterVM.LOCupdateParamNumItem(indexParam, param)
                        }
                    }
                    Toast.makeText(view.context, "Успешо изменено", Toast.LENGTH_SHORT).show()
                    view.findNavController().popBackStack()
                }
            }else{
                Toast.makeText(view.context, "Введено не число", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun loadFragment(frCont: Int, fragment: Fragment, bundle: Bundle?) {
        if (bundle != null) {
            fragment.arguments = bundle
        }
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(frCont, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}