package com.example.test.activity_and_fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.adapters.*
import com.example.test.data_base.Item
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameSystemDAO

class EditItem : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameSystemVM: GameSystemDAO by activityViewModels()

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
        val template = args?.getString("template", "") ?: ""
        val setTemplate = args?.getBoolean("template", false) ?: false

        val nameTV = view.findViewById<TextView>(R.id.edit_name)
        val descriptionTV = view.findViewById<TextView>(R.id.edit_description)
        var editItem: Item = Item()
        if (index != -1) {
            editItem = mCharacterVM.characterList.value!!.singleOrNull { character ->
                character.id == characterId
            }?.attributes?.singleOrNull { gp ->
                gp.title == groupTitle
            }?.attributes?.listItem?.get(index)!!

            mCharacterVM.item.value = editItem
            nameTV.text = editItem.name
            descriptionTV.text = editItem.description
        } else {
            if (template != "") {
                editItem = mGameSystemVM.currentGameSystem!!.templateItem.singleOrNull {
                    it.group == groupTitle && it.name == template
                }!!
            } else {
                mCharacterVM.item.value = Item()
            }
        }
        mCharacterVM.item.value = editItem
        nameTV.text = editItem.name
        descriptionTV.text = editItem.description

        // Подключаем RV отображение параметров предмета
        val numRV = view.findViewById<RecyclerView>(R.id.num_rv)
        val effectAddRV = view.findViewById<RecyclerView>(R.id.effect_add_RV)
        val effectWeaponRV = view.findViewById<RecyclerView>(R.id.effect_weapon_RV)
        val strRV = view.findViewById<RecyclerView>(R.id.str_RV)
        val optionsRV = view.findViewById<RecyclerView>(R.id.options_RV)

        val r = view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
        val newOrPres = gameId == r

        //////////////////////////////////////////////////

        val adapterAdd = EffectAddAdapterRV()
        effectAddRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        effectAddRV.adapter = adapterAdd

        val adapterWeapon = EffectWeaponAdapterRV()
        effectWeaponRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        effectWeaponRV.adapter = adapterWeapon

        val adapterNum = ParamNumAdapterRV()
        numRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        numRV.adapter = adapterNum

        val adapterStr = ParamStringAdapterRV()
        strRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        strRV.adapter = adapterStr

        val adapterOptions = ParamOptionsAdapterRV()
        optionsRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        optionsRV.adapter = adapterOptions

        // следим за изменениями
        mCharacterVM.item.observe(viewLifecycleOwner) { item ->

            val addList = item!!.effectsAdd
            adapterAdd.setData(addList, groupTitle, newOrPres, false, index)

            val weaponList = item.effectsWeapon
            adapterWeapon.setData(weaponList, groupTitle, newOrPres, false, index)

            val numList = item.otherParamNum
            adapterNum.setData(numList, groupTitle, newOrPres, false, index, 3)

            val paramStrList = item.otherParamStr
            adapterStr.setData(paramStrList, groupTitle, newOrPres, false, index, 3)

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
                    .navigate(R.id.action_new_itemEdit_to_new_editEffectAdd, bundle)
            } else {
                view.findNavController()
                    .navigate(R.id.action_pres_itemEdit_to_pres_editEffectAdd, bundle)
            }
        }
        view.findViewById<Button>(R.id.add_effect_weapon).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("groupTitle", groupTitle)
            bundle.putInt("indexItem", index)
            bundle.putInt("indexEff", -1)
            if (newOrPres) {
                view.findNavController()
                    .navigate(R.id.action_new_itemEdit_to_new_editEffectWeapon, bundle)
            } else {
                view.findNavController()
                    .navigate(R.id.action_pres_itemEdit_to_pres_editEffectWeapon, bundle)
            }
        }

        // кнопки закрыть или подтвердить
        val closeBtn = view.findViewById<Button>(R.id.weapon_edit_close)
        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val applyBtn = view.findViewById<Button>(R.id.weapon_edit_apply)
        applyBtn.setOnClickListener {
            try {
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
                    if (index == -1) {
                        if (!setTemplate) {
                            mCharacterVM.addCharacterItem(characterId, groupTitle, item)
                            Toast.makeText(view.context, "Предмет добавлен", Toast.LENGTH_SHORT)
                                .show()
                            mCharacterVM.LOCitemClear()
                            view.findNavController().popBackStack()
                            view.findNavController().popBackStack()
                        } else {
                            // todo: изменения не сохраняются, нужна более жесткая артиллерия вроде Реалма
                            /*mGameSystemVM.currentGameSystem.templateItem.singleOrNull {
                                it.group == groupTitle && it.name == template
                            }!!*/
                            //TemplateItem().mapGroupToItems[groupTitle]?.set(item.name, item)
                            mCharacterVM.LOCitemClear()
                            view.findNavController().popBackStack()
                        }
                    } else {
                        mCharacterVM.updateCharacterParamItem(characterId, item, groupTitle, index)
                        Toast.makeText(view.context, "Предмет успешно изменен", Toast.LENGTH_SHORT)
                            .show()
                        mCharacterVM.LOCitemClear()
                        view.findNavController().popBackStack()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    fun getEditItemBundle(groupTitle: String, index: Int, template: String?): Bundle {
        val bundle = Bundle()
        bundle.putString("groupTitle", groupTitle)
        bundle.putInt("index", index)
        if (template != null) {
            bundle.putString("template", template)
        } else {
            bundle.putString("template", "")
        }
        return bundle
    }

    fun getEditItemBundleForTemplate(
        groupTitle: String,
        index: Int,
        template: String?,
        setTemplate: Boolean
    ): Bundle {
        val bundle = Bundle()
        bundle.putString("groupTitle", groupTitle)
        bundle.putInt("index", index)
        if (template != null) {
            bundle.putString("template", template)
        } else {
            bundle.putString("template", "")
        }
        bundle.putBoolean("template", setTemplate)
        return bundle
    }
}