package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.data_base.DTemplateParamOptions
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameSystemDAO
import com.example.test.views.DropDownView
import com.example.test.views.HeaderView

class AddNewParam : Fragment(), HeaderView.HeaderBack,
    DropDownAdapterRV.TemplateHolder.WhenValueTo {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameSystemDAO: GameSystemDAO by activityViewModels()

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
                if (mod == 0 || mod == 1) {
                    for (i in mGameSystemDAO.currentGameSystem!!.templateParamOptions){
                        if (i.forItemOrCharacter == false && !arr.contains(i.name) && i.currentGroup == groupTitle){
                            options.add(i.name)
                        }
                    }
                } else {
                    for (i in mGameSystemDAO.currentGameSystem!!.templateParamOptions){
                        if (i.forItemOrCharacter == true && !arr.contains(i.name) && i.currentGroup == groupTitle){
                            options.add(i.name)
                        }
                    }
                }
            }
            "num" -> {
                if (mod == 0 || mod == 1) {
                    for (i in mGameSystemDAO.currentGameSystem!!.templateParamNum){
                        if (i.forItemOrCharacter == false && !arr.contains(i.name) && i.currentGroup == groupTitle){
                            options.add(i.name)
                        }
                    }
                } else {
                    for (i in mGameSystemDAO.currentGameSystem!!.templateParamNum){
                        if (i.forItemOrCharacter == true && !arr.contains(i.name) && i.currentGroup == groupTitle){
                            options.add(i.name)
                        }
                    }
                }
            }
            "string" -> {
                if (mod == 0 || mod == 1) {
                    for (i in mGameSystemDAO.currentGameSystem!!.templateParamStr){
                        if (i.forItemOrCharacter == false && !arr.contains(i.name) && i.currentGroup == groupTitle){
                            options.add(i.name)
                        }
                    }
                } else {
                    for (i in mGameSystemDAO.currentGameSystem!!.templateParamStr){
                        if (i.forItemOrCharacter == true && !arr.contains(i.name) && i.currentGroup == groupTitle){
                            options.add(i.name)
                        }
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

        view.findViewById<Button>(R.id.add_param_num).visibility = View.GONE

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