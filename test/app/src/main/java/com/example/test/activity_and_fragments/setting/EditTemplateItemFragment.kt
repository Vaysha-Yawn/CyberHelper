package com.example.test.activity_and_fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity_and_fragments.EditItem
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.data_base.TemplateItem
import com.example.test.databinding.CardAddOrChooseBinding
import com.example.test.databinding.EditTemplateItemBinding
import com.example.test.views.HeaderView


class EditTemplateItemFragment : Fragment(), HeaderView.HeaderBack {

    private val options = mutableMapOf<String, MutableList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (i in TemplateItem().mapGroupToItems) {
            val templateItems = i.value.toList()
            for (item in templateItems) {
                val list = mutableListOf<String>()
                list.add(item.second.name)
                options[i.key] = list
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_template_item, container, false)
        val binding = EditTemplateItemBinding.bind(view)
        with(binding) {
            header.setBack(
                true,
                this@EditTemplateItemFragment,
                requireActivity(),
                viewLifecycleOwner
            )
            title.text = "Управление шаблонами предметов"
            RV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            val adapterRV = AdapterRV(options)
            RV.adapter = adapterRV
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    class AdapterRV(
        private val list: MutableMap<String, MutableList<String>>
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
        private val map: MutableMap<String, MutableList<String>>
    ) : RecyclerView.ViewHolder(view), DropDownAdapterRV.TemplateHolder.WhenValueTo {
        private val binding = CardAddOrChooseBinding.bind(view)
        private lateinit var pair : Pair<String, MutableList<String>>
        private lateinit var groupTitle :String
        private lateinit var list :MutableList<String>
        fun bind(pos: Int) = with(binding) {
            pair = map.toList()[pos]
            groupTitle = pair.first
            list = pair.second
            title.text = groupTitle
            add.text = "Создать новый"
            chooseDD.setMainText("Редактировать существующий")
            add.setOnClickListener {
                val bundle = EditItem().getEditItemBundle(groupTitle, -1, null)
                view.findNavController()
                    .navigate(R.id.action_addNewItem2_to_new_itemEdit, bundle)

            }
            chooseDD.setDDArrayAndListener(list, this@AdapterRVTemplateHolder, null)
        }

        override fun whenValueTo(position: Int) {
            val res = list[position]
            val bundle = EditItem().getEditItemBundle(groupTitle, -1, res)
            view.findNavController().navigate(R.id.action_addNewItem2_to_new_itemEdit, bundle)

        }
    }

}