package com.example.test.activity_and_fragments.setting

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
import com.example.test.viewModels.GameSystemDAO
import com.example.test.views.HeaderView


class SettingsChoseSystem : Fragment(), HeaderView.HeaderBack,
    DropDownAdapterRV.TemplateHolder.WhenValueTo {

    private val mGameSystemDAO: GameSystemDAO by activityViewModels()

    private val options = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (system in mGameSystemDAO.readGameSystems()) {
            options.add(system.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_new_param_item, container, false)
        val binding = AddNewParamItemBinding.bind(view)
        with(binding) {
            title.text = "Создать игровую систему"
            addParamNum.text = "Создать с нуля"
            dropDownFragment.setMainText("Изменить существующую")

            addParamNum.setOnClickListener {

            }

            dropDownFragment.setDDArrayAndListener(options, this@SettingsChoseSystem, null)
            header.setBack(true, this@SettingsChoseSystem, requireActivity(), viewLifecycleOwner)
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    override fun whenValueTo(position: Int) {
        val res = options[position]
        mGameSystemDAO.initGameSystemById(mGameSystemDAO.findGameSystemId(res)!!)
        view?.findNavController()?.navigate(R.id.action_settingsChoseSystem_to_settingsMainFragment)
    }

}