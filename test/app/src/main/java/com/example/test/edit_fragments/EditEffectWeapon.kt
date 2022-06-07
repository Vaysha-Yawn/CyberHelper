package com.example.test.edit_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.data_base.EffectDamage
import com.example.test.databinding.EditEffectWeaponBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameSystemDAO
import com.example.test.components.views.DropDownView
import com.example.test.components.views.PlusMinusView

class EditEffectWeapon : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameSystemDAO: GameSystemDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_effect_weapon, container, false)

        val characterId = mCharacterVM.characterId
        val args = this.arguments
        val groupTitle = args?.getString("groupTitle", "") ?: ""
        val indexItem = args?.getInt("indexItem", -1) ?: -1
        val indexEff = args?.getInt("indexEff", -1) ?: -1

        var effectWeapon = EffectDamage()
        if (indexEff != -1) {
            effectWeapon = mCharacterVM.item.value!!.effectsDamage[indexEff]!!
        }

        // подключаем фрагмент выпадающего списка
        val binding = EditEffectWeaponBinding.bind(view)
        with(binding) {
            val listTypes = mutableListOf<String>()
            for (i in mGameSystemDAO.currentGameSystem!!.typesItem) {
                if (i.key == groupTitle) {
                    for (a in i.value) {
                        listTypes.add(a)
                    }
                }
            }
            weaponEditTypeRV.setDDArrayAndListener(
                listTypes,
                object : DropDownAdapterRV.TemplateHolder.WhenValueTo {
                    override fun whenValueTo(position: Int) {
                        effectWeapon.fightType = listTypes[position]
                    }
                },
                null
            )

            //  подключаем фрагмент кубиков урона
            weaponEditPlusAndMinusNumCount.setValue(effectWeapon.numCount)
            weaponEditPlusAndMinusNumCount.setListener(null, 0, object : PlusMinusView.NumberEvent {
                override fun numberEvent(number: Int) {
                    effectWeapon.numCount = number
                }
            })

            // подключаем фрагмент урона на один кубик
            weaponEditPlusAndMinusDx.setValue(effectWeapon.dX)
            weaponEditPlusAndMinusDx.setListener(null, 0, object : PlusMinusView.NumberEvent {
                override fun numberEvent(number: Int) {
                    effectWeapon.dX = number
                }
            })

        }


        val closeBtn = view.findViewById<Button>(R.id.weapon_edit_close)
        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val applyBtn = view.findViewById<Button>(R.id.weapon_edit_apply)
        applyBtn.setOnClickListener {
            // достаем значения и делаем проверку
            var res = 1

            val type = view.findViewById<DropDownView>(R.id.weapon_edit_type_RV).getValueFromDD()
            if (type == "Выберите тип оружия") {
                res = 0
                Toast.makeText(view.context, "Выберите тип", Toast.LENGTH_SHORT).show()
            }

            if (effectWeapon.numCount == 0) {
                res = 0
                Toast.makeText(
                    view.context,
                    "Колличество кубиков урона должно быть больше нуля",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (effectWeapon.dX == 0) {
                res = 0
                Toast.makeText(
                    view.context, "Значение максимального урона должно быть больше нуля",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val name = view.findViewById<EditText>(R.id.name).text.toString()

            if (name == ""){
                res = 0
                Toast.makeText(
                    view.context,
                    "Введите название атаки",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (res == 1) {// если проверка прошла успешно
                if (indexEff == -1) {
                    mCharacterVM.LOCaddEffectWeaponItem(
                        EffectDamage(
                            name,
                            effectWeapon.fightType,
                            numCount = effectWeapon.numCount,
                            dX = effectWeapon.dX,
                        )
                    )
                } else {
                    mCharacterVM.LOCupdateEffectWeaponItem(
                        indexEff,
                        EffectDamage(
                            name, effectWeapon.fightType,
                            numCount = effectWeapon.numCount,
                            dX = effectWeapon.dX,
                        )
                    )
                }
                view.findNavController().popBackStack()
            }
        }
        return view
    }

}
