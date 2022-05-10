package com.example.test.activity_and_fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.SettingFightTypeBinding
import com.example.test.views.HeaderView

class SettingFightType : Fragment(), HeaderView.HeaderBack {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting_fight_type, container, false)

        fun setVisibility(view:View, visible:Boolean){
            if (visible){
                view.visibility = View.VISIBLE
            }else{
                view.visibility = View.GONE
            }
        }

        val binding = SettingFightTypeBinding.bind(view)
        fun bind() = with(binding) {
            header.setBack(true, this@SettingFightType, requireActivity(), viewLifecycleOwner)
            /*loadDD("Сложность", "green", "", arrayListOf("Бросок одного персоонажа", "Бросок нескольких персоонажей", "Устанавливается произвольно",
                "Выбирается из списка", "Устанавливается по таблице в зависимости от значения характеристики"),
                R.id.difficult)
            loadDD("Тип бросока", "green", "", arrayListOf("Бросок одного персоонажа", "Бросок нескольких персоонажей", "Устанавливается произвольно"), R.id.roll)*/
            switchFor.setOnClickListener {
                if (switchFor.isChecked){
                    switchFor.text =  "При наличии предмета с этим типом атаки"
                }else{
                    switchFor.text =  "Всегда"
                }
            }
            // todo: настроить эффект влияния именно для влиния при атаке,
            /*val adapterAdd = EffectAddAdapterRV()
            effectAddRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            effectAddRV.adapter = adapterAdd
            val addList = item!!.effectsAdd
            adapterAdd.setData(addList, groupTitle, newOrPres, false, index)*/

            apply.setOnClickListener {
                //view.findNavController().navigate(R.id.action_fightAttack_to_fightThreeGoal)
            }
        }

        bind()
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }


}