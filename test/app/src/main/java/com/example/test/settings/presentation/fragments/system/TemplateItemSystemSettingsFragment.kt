package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.components.views.drop_down.DropDownAdapterRV
import com.example.test.databinding.FragmentTemplateItemSystemSettingsBinding
import com.example.test.components.views.HeaderView
import com.example.test.data_base.realm.other_realm_object.Item
import com.example.test.databinding.CardAddOrChooseBinding
import com.example.test.edit_fragments.EditItem
import com.example.test.settings.presentation.view_model.CreateSystemVM


class TemplateItemSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    // оптимизировать под эту задачу editItem
    private val createSystemVM: CreateSystemVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_template_item_system_settings, container, false)
        val binding = FragmentTemplateItemSystemSettingsBinding.bind(view)
        with(binding){
            header.setBack(true, this@TemplateItemSystemSettingsFragment, requireActivity(), viewLifecycleOwner)
            RV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            val adapterRV = AdapterRV(createSystemVM.getMapTemplateItem())
            RV.adapter = adapterRV
            next.setOnClickListener {
                view.findNavController().navigate(R.id.action_templateItemSystemSettingsFragment_to_templateCharacterSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    class AdapterRV(
        private val list: MutableMap<String, MutableList<Item>>
    ) :
        RecyclerView.Adapter<AdapterRVTemplateHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterRVTemplateHolder {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_add_or_choose, parent, false)
            return AdapterRVTemplateHolder(view, list)
        }

        override fun onBindViewHolder(holder: AdapterRVTemplateHolder, position: Int) {
            holder.bind(position)
        }

        override fun getItemCount(): Int {
            return list.toList().size
        }

    }

    class AdapterRVTemplateHolder(
        private val view: View,
        private val map: MutableMap<String, MutableList<Item>>
    ) : RecyclerView.ViewHolder(view), DropDownAdapterRV.TemplateHolder.WhenValueTo {
        private val binding = CardAddOrChooseBinding.bind(view)
        private lateinit var pair : Pair<String, MutableList<Item>>
        private lateinit var groupTitle :String
        private var list = mutableListOf<String>()
        fun bind(pos: Int) = with(binding) {
            pair = map.toList()[pos]
            groupTitle = pair.first
            title.text = groupTitle
            add.text = "Создать новый"
            chooseDD.setMainText("Редактировать существующий")
            add.setOnClickListener {
                val bundle = EditItem().getEditItemBundleForTemplate(groupTitle, -1, null, true)
                view.findNavController()
                    .navigate(R.id.action_templateItemSystemSettingsFragment_to_editItem, bundle)
            }
            for (i in pair.second){
                list.add(i.name)
            }
            chooseDD.setDDArrayAndListener(list, this@AdapterRVTemplateHolder, null)
        }

        override fun whenValueTo(position: Int) {
            val res = list[position]
            val bundle = EditItem().getEditItemBundleForTemplate(groupTitle, position, res, true)
            view.findNavController().navigate(R.id.action_templateItemSystemSettingsFragment_to_editItem, bundle)

        }
    }

}