package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.test.R
import com.example.test.viewModels.CharacterDAO
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.data_base.*
import com.example.test.adapters.*
import java.lang.Exception

class EditItem : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_item, container, false)

        // извлекаем данные
        val characterId = mCharacterVM.characterId
        val gameId = mCharacterVM.gameId
        val args = this.arguments
        val groupTitle = args?.getString("groupTitle", "") ?: ""
        val index = args?.getInt("index", -1) ?: -1

        val nameTV = view.findViewById<TextView>(R.id.edit_name)
        val descriptionTV = view.findViewById<TextView>(R.id.edit_description)

        if (index != -1){
            val editItem = mCharacterVM.characterList.value!!.singleOrNull { character ->
                character.id == characterId
            }?.attributes?.singleOrNull { gp ->
                gp.title == groupTitle
            }?.attributes?.listItem?.get(index)
            if (editItem == null) {
                Toast.makeText(view.context, "Предмет с таким индексом не найден", Toast.LENGTH_SHORT)
                    .show()
                view.findNavController().popBackStack()
            }
            mCharacterVM.item.value = editItem!!
            nameTV.text = editItem.name
            descriptionTV.text = editItem.description
        }else{
            mCharacterVM.item.value = Item()
        }

        // Подключаем RV отображение параметров предмета
        val numRV = view.findViewById<RecyclerView>(R.id.num_rv)
        val effectAddRV = view.findViewById<RecyclerView>(R.id.effect_add_RV)
        val effectWeaponRV = view.findViewById<RecyclerView>(R.id.effect_weapon_RV)
        val strRV = view.findViewById<RecyclerView>(R.id.str_RV)
        val optionsRV = view.findViewById<RecyclerView>(R.id.options_RV)

        val r = view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
        val newOrPres = gameId == r

        // следим за изменениями
        mCharacterVM.item.observe(viewLifecycleOwner) { item->

            val adapterAdd = EffectAddAdapterRV()
            effectAddRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            effectAddRV.adapter = adapterAdd
            val addList = item!!.effectsAdd
            adapterAdd.setData(addList, groupTitle, newOrPres, false, index)

            val adapterWeapon = EffectWeaponAdapterRV()
            effectWeaponRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            effectWeaponRV.adapter = adapterWeapon
            val weaponList = item.effectsWeapon
            adapterWeapon.setData(weaponList, groupTitle, newOrPres, false, index)

            val adapterNum = ParamNumAdapterRV()
            numRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            numRV.adapter = adapterNum
            val numList = item.otherParamNum
            adapterNum.setData(numList, groupTitle, newOrPres, false, index, 3)

            val adapterStr = ParamStringAdapterRV()
            strRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            strRV.adapter = adapterStr
            val paramStrList = item.otherParamStr
            adapterStr.setData(paramStrList, groupTitle, newOrPres, false, index, 3)

            val adapterOptions = ParamOptionsAdapterRV()
            optionsRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            optionsRV.adapter = adapterOptions
            val optionsList = item.otherParamOptions
            adapterOptions.setData(optionsList, groupTitle, newOrPres, false, index, 3)
        }

        // кнопки добавить параметр или эффект
            view.findViewById<Button>(R.id.add_param_num).setOnClickListener {
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putString("type", "num")
                bundle.putInt("indexItem", index)
                bundle.putInt("mod", 2)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_itemEdit_to_new_addNewParamItem, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_itemEdit_to_pres_addNewParamItem, bundle)
                }
            }
            view.findViewById<Button>(R.id.add_param_str).setOnClickListener {
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putString("type", "string")
                bundle.putInt("indexItem", index)
                bundle.putInt("mod", 2)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_itemEdit_to_new_addNewParamItem, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_itemEdit_to_pres_addNewParamItem, bundle)
                }
            }
            view.findViewById<Button>(R.id.add_param_options).setOnClickListener {
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putString("type", "options")
                bundle.putInt("indexItem", index)
                bundle.putInt("mod", 2)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_itemEdit_to_new_addNewParamItem, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_itemEdit_to_pres_addNewParamItem, bundle)
                }
            }

        view.findViewById<Button>(R.id.add_effect_add).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("groupTitle", groupTitle)
            bundle.putInt("indexItem", index)
            bundle.putInt("indexEff", -1)
            if (newOrPres) {
                view.findNavController()
                    .navigate(R.id.action_new_itemEdit_to_new_editEffectAdd2, bundle)
            } else {
                view.findNavController()
                    .navigate(R.id.action_pres_itemEdit_to_pres_editEffectAdd3, bundle)
            }
        }
        view.findViewById<Button>(R.id.add_effect_weapon).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("groupTitle", groupTitle)
            bundle.putInt("indexItem", index)
            bundle.putInt("indexEff", -1)
            if (newOrPres) {
                view.findNavController()
                    .navigate(R.id.action_new_itemEdit_to_new_editEffectWeapon2, bundle)
            } else {
                view.findNavController()
                    .navigate(R.id.action_pres_itemEdit_to_pres_editEffectWeapon2, bundle)
            }
        }

        // кнопки закрыть или подтвердить
        val closeBtn = view.findViewById<Button>(R.id.weapon_edit_close)
        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val applyBtn = view.findViewById<Button>(R.id.weapon_edit_apply)
        applyBtn.setOnClickListener {
            try{
            var res = 1

            val name = nameTV.text.toString()
            if (name == "") {
                res = 0
                Toast.makeText(view.context, "Введите название предмета", Toast.LENGTH_SHORT)
                    .show()
            }

            val description = descriptionTV.text.toString()

            if (res == 1) {
                mCharacterVM.LOCupdateNameItem(name)
                mCharacterVM.LOCupdateDescriptionItem(description)
                val item = mCharacterVM.item.value!!

                if (index == -1){
                    mCharacterVM.addCharacterItem(characterId, groupTitle, item)
                    Toast.makeText(view.context, "Предмет добавлен", Toast.LENGTH_SHORT)
                        .show()
                }else{
                    mCharacterVM.updateCharacterParamItem(characterId, item, groupTitle, index)
                    Toast.makeText(view.context, "Предмет успешно изменен", Toast.LENGTH_SHORT)
                        .show()
                }
                mCharacterVM.LOCitemClear()
                view.findNavController().popBackStack()
            }
            }catch (e:Exception){Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()}
        }
        return view
    }
}