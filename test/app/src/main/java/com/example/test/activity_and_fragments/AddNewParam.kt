package com.example.test.activity_and_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.TemplateParamNum
import com.example.test.data_base.TemplateParamOptions
import com.example.test.data_base.TemplateParamStr
import com.example.test.viewModels.CharacterDAO
import com.example.test.widgets.DropDownList

class AddNewParam : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_new_param_item, container, false)
        try {
            // Требуемые аргументы
            val gameId = mCharacterVM.gameId
            val characterId = mCharacterVM.characterId
            val args = this.arguments
            val groupTitle = args?.getString("groupTitle", "") ?: ""
            val type = args?.getString("type", "") ?: ""
            val indexItem = args?.getInt("indexItem", -1) ?: -1
            val mod = args?.getInt("mod", -1) ?: -1
            val arr = args?.getStringArrayList("arr") ?: ArrayList<String>()

            // обработать нажатие на кнопку создать параметр
            // выпадающий список
            val main = "Выберите параметр"
            val options = arrayListOf<String>()

            when (type) {
                "options" -> {
                    val listAlreadyParam = mutableListOf<String>()
                    val arrParam = mCharacterVM.characterList.value?.singleOrNull {
                        it.id == characterId
                    }?.attributes?.singleOrNull {
                        it.title == groupTitle
                    }?.attributes?.listParamOptions
                    if (arrParam?.isNotEmpty() == true){
                        arrParam.forEach {
                            listAlreadyParam.add(it.name)
                        }
                    }
                    if (mod == 0 || mod == 1) {
                        if (arr.isEmpty()) {
                            for (key in TemplateParamOptions().mapParamOptions.keys) {
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
                        for (key in TemplateParamOptions().mapParamOptionsItem.keys) {
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
                    if (arrParam?.isNotEmpty() == true){
                        arrParam.forEach {
                            listAlreadyParam.add(it.name)
                        }
                    }
                    if (mod == 0 || mod == 1) {
                        if (arr.isEmpty()) {
                            for (key in TemplateParamNum().mapParamNum.keys) {
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
                        for (key in TemplateParamNum().mapParamNumItem.keys) {
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
                    if (arrParam?.isNotEmpty() == true){
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

            // фрагмент выпадающего списка
            val bundleQ = Bundle()
            bundleQ.putString("main", main)
            bundleQ.putString("them", "green")
            bundleQ.putStringArrayList("list", options)
            val fragment = DropDownList()
            fragment.arguments = bundleQ
            childFragmentManager.commit {
                replace(R.id.drop_down_fragment, fragment)
                addToBackStack(null)
            }

            view.findViewById<ImageButton>(R.id.back).setOnClickListener {
                view.findNavController().popBackStack()
            }

            val r = view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
            val newOrPres = gameId == r

            // по нажаию кнопки далее переходим к введению значения
            view.findViewById<Button>(R.id.next).setOnClickListener {
                val frag1: Fragment? =
                    childFragmentManager.findFragmentById(R.id.drop_down_fragment)
                val res =
                    frag1?.view?.findViewById<TextView>(R.id.main)?.text.toString()
                when (type) {
                    "options" -> {
                        val param = if (indexItem == -1) {
                            TemplateParamOptions().mapParamOptions[res]!!
                        } else {
                            TemplateParamOptions().mapParamOptionsItem[res]!!
                        }
                        val bundle = Bundle()
                        bundle.putString("paramName", res)
                        bundle.putString("value", param.defMain)
                        bundle.putString("titleGroup", groupTitle)
                        bundle.putInt("indexItem", indexItem)
                        bundle.putInt("indexParam", -1)
                        bundle.putInt("mod", mod)
                        if (newOrPres) {
                            view.findNavController().navigate(
                                R.id.action_new_addNewParamItem_to_new_dropDownEdit,
                                bundle
                            )
                        } else {
                            view.findNavController().navigate(
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
                            view.findNavController().navigate(
                                R.id.action_new_addNewParamItem_to_new_edit_number,
                                bundle
                            )
                        } else {
                            view.findNavController().navigate(
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
                            view.findNavController().navigate(
                                R.id.action_new_addNewParamItem_to_new_edit_string,
                                bundle
                            )
                        } else {
                            view.findNavController().navigate(
                                R.id.action_pres_addNewParamItem_to_pres_edit_string,
                                bundle
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }
        return view
    }

}