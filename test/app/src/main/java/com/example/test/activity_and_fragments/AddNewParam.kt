package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.data_base.DTemplateParamNum
import com.example.test.data_base.DTemplateParamOptions
import com.example.test.data_base.TemplateParamStr
import com.example.test.viewModels.CharacterDAO
import com.example.test.views.DropDownView
import com.example.test.views.HeaderView

class AddNewParam : Fragment(), HeaderView.HeaderBack,
    DropDownAdapterRV.TemplateHolder.WhenValueTo {

    private val mCharacterVM: CharacterDAO by activityViewModels()

    private var type: String = ""
    private var indexItem: Int = 0
    private var options = mutableListOf<String>()
    private var groupTitle: String = ""
    private var mod: Int = 0
    private var newOrPres: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Требуемые аргументы
        val args = this.arguments
        groupTitle = args?.getString("groupTitle", "") ?: ""
        type = args?.getString("type", "") ?: ""
        indexItem = args?.getInt("indexItem", -1) ?: -1
        mod = args?.getInt("mod", -1) ?: -1
        val arr = args?.getStringArrayList("arr") ?: ArrayList<String>()

        val gameId = mCharacterVM.gameId
        val characterId = mCharacterVM.characterId

        val r = requireContext().getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
        newOrPres = gameId == r

        when (type) {
            "options" -> {
                val listAlreadyParam = mutableListOf<String>()
                val arrParam = mCharacterVM.characterList.value?.singleOrNull {
                    it.id == characterId
                }?.attributes?.singleOrNull {
                    it.title == groupTitle
                }?.attributes?.listParamOptions
                if (arrParam?.isNotEmpty() == true) {
                    arrParam.forEach {
                        listAlreadyParam.add(it.name)
                    }
                }
                if (mod == 0 || mod == 1) {
                    if (arr.isEmpty()) {
                        for (key in DTemplateParamOptions().mapParamOptions.keys) {
                            options.add(key)
                        }
                    } else {
                        arr.forEach {
                            if (!listAlreadyParam.contains(it)) {
                                options.add(it)
                            }
                        }
                    }
                } else {
                    for (key in DTemplateParamOptions().mapParamOptionsItem.keys) {
                        options.add(key)
                    }
                }
            }
            "num" -> {
                val listAlreadyParam = mutableListOf<String>()
                val arrParam = mCharacterVM.characterList.value?.singleOrNull {
                    it.id == characterId
                }?.attributes?.singleOrNull {
                    it.title == groupTitle
                }?.attributes?.listParamNum
                if (arrParam?.isNotEmpty() == true) {
                    arrParam.forEach {
                        listAlreadyParam.add(it.name)
                    }
                }
                if (mod == 0 || mod == 1) {
                    if (arr.isEmpty()) {
                        for (key in DTemplateParamNum().mapParamNum.keys) {
                            options.add(key)
                        }
                    } else {
                        arr.forEach {
                            if (!listAlreadyParam.contains(it)) {
                                options.add(it)
                            }
                        }
                    }
                } else {
                    for (key in DTemplateParamNum().mapParamNumItem.keys) {
                        options.add(key)
                    }
                }
            }
            "string" -> {
                val listAlreadyParam = mutableListOf<String>()
                val arrParam = mCharacterVM.characterList.value?.singleOrNull {
                    it.id == characterId
                }?.attributes?.singleOrNull {
                    it.title == groupTitle
                }?.attributes?.listParamStr
                if (arrParam?.isNotEmpty() == true) {
                    arrParam.forEach {
                        listAlreadyParam.add(it.name)
                    }
                }
                if (mod == 0 || mod == 1) {
                    if (arr.isEmpty()) {
                        for (key in TemplateParamStr().mapParamStr.keys) {
                            options.add(key)
                            options.remove("Имя персонажа")
                            options.remove("Название игры")
                        }
                    } else {
                        arr.forEach {
                            if (!listAlreadyParam.contains(it)) {
                                options.add(it)
                            }
                        }
                    }
                } else {
                    for (key in TemplateParamStr().mapParamStrItem.keys) {
                        options.add(key)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_new_param_item, container, false)
        // todo: обработать нажатие на кнопку создать параметр
        // todo: при возвращении оно снова создает список

        view.findViewById<DropDownView>(R.id.drop_down_fragment)
            .setDDArrayAndListener(options, this, null)

        view.findViewById<HeaderView>(R.id.header)
            .setBack(true, this, requireActivity(), viewLifecycleOwner)

        /* } catch (e: Exception) {
             Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
         }*/
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    override fun whenValueTo(position: Int) {
        val res = options[position]
        when (type) {
            "options" -> {
                val param = if (indexItem == -1) {
                    DTemplateParamOptions().mapParamOptions[res]!!
                } else {
                    DTemplateParamOptions().mapParamOptionsItem[res]!!
                }
                val bundle = Bundle()
                bundle.putString("paramName", res)
                bundle.putString("value", param.defMain)
                // todo: изменить получение groupTitle, чтобы оно соответствовало параметру,
                //  сейчас откуда взялось, туда и прибавилось
                //   или добавить ограничение, перечисление во всех группах
                bundle.putString("titleGroup", groupTitle)
                bundle.putInt("indexItem", indexItem)
                bundle.putInt("indexParam", -1)
                bundle.putInt("mod", mod)
                if (newOrPres) {
                    view?.findNavController()?.navigate(
                        R.id.action_new_addNewParamItem_to_new_dropDownEdit,
                        bundle
                    )
                } else {
                    view?.findNavController()?.navigate(
                        R.id.action_pres_addNewParamItem_to_pres_dropDownEdit,
                        bundle
                    )
                }
            }
            "num" -> {
                val bundle = Bundle()
                bundle.putString("paramName", res)
                bundle.putString("value", "0")
                bundle.putString("groupTitle", groupTitle)
                bundle.putInt("indexItem", indexItem)
                bundle.putInt("indexParam", -1)
                bundle.putInt("mod", mod)
                if (newOrPres) {
                    view?.findNavController()?.navigate(
                        R.id.action_new_addNewParamItem_to_new_edit_number,
                        bundle
                    )
                } else {
                    view?.findNavController()?.navigate(
                        R.id.action_pres_addNewParamItem_to_pres_edit_number,
                        bundle
                    )
                }
            }
            "string" -> {
                val bundle = Bundle()
                bundle.putString("paramName", res)
                bundle.putString("value", "")
                bundle.putString("groupTitle", groupTitle)
                bundle.putInt("indexItem", indexItem)
                bundle.putInt("indexParam", -1)
                bundle.putInt("mod", mod)
                if (newOrPres) {
                    view?.findNavController()?.navigate(
                        R.id.action_new_addNewParamItem_to_new_edit_string,
                        bundle
                    )
                } else {
                    view?.findNavController()?.navigate(
                        R.id.action_pres_addNewParamItem_to_pres_edit_string,
                        bundle
                    )
                }
            }
        }
    }

}