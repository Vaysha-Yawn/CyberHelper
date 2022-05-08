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
import com.example.test.data_base.SpecialGameData
import com.example.test.data_base.TemplateParamNum
import com.example.test.data_base.TemplateParamOptions
import com.example.test.data_base.TemplateParamStr
import com.example.test.databinding.AddNewParamItemBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.views.DropDownView
import com.example.test.views.HeaderView

class AddNewItem : Fragment(), HeaderView.HeaderBack,
    DropDownAdapterRV.TemplateHolder.WhenValueTo {

    private val mCharacterVM: CharacterDAO by activityViewModels()

    private var indexItem: Int = 0
    private var options = mutableListOf<String>()
    private var groupTitle: String = ""
    private var newOrPres: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Требуемые аргументы
        val args = this.arguments
        groupTitle = args?.getString("groupTitle", "") ?:""
        indexItem = args?.getInt("indexItem", -1) ?: -1

        val gameId = mCharacterVM.gameId
        val characterId = mCharacterVM.characterId

        val r = requireContext().getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
        newOrPres = gameId == r

        // здесь дополнить options недостающими шаблонами
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_new_param_item, container, false)
        // todo: обработать нажатие на кнопку создать параметр
        val binding = AddNewParamItemBinding.bind(view)
        with(binding){
            title.text = "Создать $groupTitle"
            addParamNum.text = "Создать с нуля"
            dropDownFragment.setMainText("Выберите шаблон")
        }
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
        // навигация к редактированию шаблона
    }

}