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
import com.example.test.databinding.AddNewParamItemBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameSystemDAO
import com.example.test.views.HeaderView

class AddNewItem : Fragment(), HeaderView.HeaderBack,
    DropDownAdapterRV.TemplateHolder.WhenValueTo {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameSystemDAO: GameSystemDAO by activityViewModels()

    private var indexItem: Int = 0
    private var groupTitle: String = ""
    private var newOrPres: Boolean = true
    private val options = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Требуемые аргументы
        val args = this.arguments
        groupTitle = args?.getString("groupTitle", "") ?: ""
        indexItem = args?.getInt("indexItem", -1) ?: -1

        val gameId = mCharacterVM.gameId
        val r = requireContext().getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
        newOrPres = gameId == r
        val templateItems = mGameSystemDAO.currentGameSystem!!.templateItem
        for (item in templateItems) {
            if (item.group == groupTitle){
                options.add(item.name)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_new_param_item, container, false)
        val binding = AddNewParamItemBinding.bind(view)
        with(binding) {
            title.text = "Создать предмет"
            addParamNum.text = "Создать с нуля"
            dropDownFragment.setMainText("Выберите шаблон")

            addParamNum.setOnClickListener {
                val bundle = EditItem().getEditItemBundle(groupTitle, indexItem, null)
                if (newOrPres) {
                    view?.findNavController()
                        ?.navigate(R.id.action_addNewItem2_to_new_itemEdit, bundle)
                } else {
                    view?.findNavController()
                        ?.navigate(R.id.action_addNewItem_to_pres_itemEdit, bundle)
                }
            }

            dropDownFragment.setDDArrayAndListener(options, this@AddNewItem, null)
            header.setBack(true, this@AddNewItem, requireActivity(), viewLifecycleOwner)
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    override fun whenValueTo(position: Int) {
        val res = options[position]
        val bundle = EditItem().getEditItemBundle(groupTitle, indexItem, res)
        if (newOrPres) {
            view?.findNavController()?.navigate(R.id.action_addNewItem2_to_new_itemEdit, bundle)
        } else {
            view?.findNavController()?.navigate(R.id.action_addNewItem_to_pres_itemEdit, bundle)
        }
    }

}