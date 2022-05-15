package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.test.R
import com.example.test.viewModels.CharacterDAO

class EditEffectWeapon : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_effect_weapon, container, false)

        /*val characterId = mCharacterVM.characterId
        val args = this.arguments
        val groupTitle = args?.getString("groupTitle", "") ?: ""
        val indexItem = args?.getInt("indexItem", -1) ?: -1
        val indexEff = args?.getInt("indexEff", -1) ?: -1

        var effectWeapon = EffectWeapon()
        if (indexEff != -1) {
            effectWeapon = mCharacterVM.item.value!!.effectsWeapon[indexEff]!!
        }

        // подключаем фрагмент выпадающего списка
        val bundle = Bundle()
        val typeWeapon = TemplateParamOptions().mapParamOptionsSupporting["Тип оружия"]
        if (effectWeapon.fightType == "") {
            bundle.putString("main", typeWeapon?.defMain)
        } else {
            //bundle.putString("main", effectWeapon.fightType?.name?:"")
        }
        bundle.putString("them", "green")
        val options = ArrayList<String>()
        TemplateFightType().mapFightType.keys.forEach {
            options.add(it)
        }
        bundle.putStringArrayList("listId", options)
        loadFragment(R.id.weapon_edit_type_RV, DropDownList(), bundle)


        //  подключаем фрагмент кубиков урона
        val bundleNumCount = Bundle()
        bundleNumCount.putInt("value", effectWeapon.numCount)
        bundleNumCount.putInt("minValue", 0)
        bundleNumCount.putString("them", "green")
        loadFragment(R.id.weapon_edit_plus_and_minus_num_count, PlusAndMinus(), bundleNumCount)

        // подключаем фрагмент урона на один кубик
        val bundleDx = Bundle()
        bundleDx.putInt("value", effectWeapon.dX)
        bundleDx.putInt("minValue", 0)
        bundleDx.putString("them", "green")
        loadFragment(R.id.weapon_edit_plus_and_minus_dx, PlusAndMinus(), bundleDx)

        // подключаем фрагмент урона на один кубик
        val bundleWearout = Bundle()
        bundleWearout.putInt("value", effectWeapon.wearout ?: 0)
        bundleWearout.putInt("minValue", 0)
        bundleWearout.putString("them", "green")
        loadFragment(R.id.weapon_edit_plus_and_minus_wearout, PlusAndMinus(), bundleWearout)

        val closeBtn = view.findViewById<Button>(R.id.weapon_edit_close)
        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val applyBtn = view.findViewById<Button>(R.id.weapon_edit_apply)
        applyBtn.setOnClickListener {
            // достаем значения и делаем проверку
            var res = 1
            val frag3: Fragment = childFragmentManager.findFragmentById(R.id.weapon_edit_type_RV)!!
            val type = frag3.view?.findViewById<TextView>(R.id.main)?.text.toString()
            if (type == "Выберите тип оружия") {
                res = 0
                Toast.makeText(view.context, "Выберите тип", Toast.LENGTH_SHORT).show()
            }

            val typeAttack = TemplateFightType().mapFightType[type]?:FightType()

            val frag1: Fragment =
                childFragmentManager.findFragmentById(R.id.weapon_edit_plus_and_minus_num_count)!!
            val numCount =
                frag1.view?.findViewById<EditText>(R.id.edit)?.text.toString()
                    .toIntOrNull()
            if (numCount == null) {
                res = 0
                Toast.makeText(
                    view.context,
                    "Колличество кубиков урона должно быть числом",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (numCount == 0) {
                res = 0
                Toast.makeText(
                    view.context,
                    "Колличество кубиков урона должно быть больше нуля",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val frag2: Fragment =
                childFragmentManager.findFragmentById(R.id.weapon_edit_plus_and_minus_dx)!!
            val dX = frag2.view?.findViewById<EditText>(R.id.edit)?.text.toString()
                .toIntOrNull()
            if (dX == null) {
                res = 0
                Toast.makeText(
                    view.context,
                    "Значение максимального урона должно быть числом",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (dX == 0) {
                res = 0
                Toast.makeText(
                    view.context, "Значение максимального урона должно быть больше нуля",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val frag4: Fragment =
                childFragmentManager.findFragmentById(R.id.weapon_edit_plus_and_minus_wearout)!!
            val wearout =
                frag4.view?.findViewById<EditText>(R.id.edit)?.text.toString()
                    .toIntOrNull()
            if (wearout == null) {
                res = 0
                Toast.makeText(
                    view.context,
                    "Значение износа должно быть числом",
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
                        EffectWeapon(
                            name,
                            ,
                            numCount!!,
                            dX!!,
                            wearout
                        )
                    )
                } else {
                    mCharacterVM.LOCupdateEffectWeaponItem(
                        indexEff,
                        EffectWeapon( name, typeAttack, numCount!!, dX!!, wearout)
                    )
                }
                view.findNavController().popBackStack()
            }
        }*/
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
