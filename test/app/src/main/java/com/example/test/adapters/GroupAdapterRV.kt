package com.example.test.adapters


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.GroupParam
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.CardGroupParamBinding


class GroupAdapterRV() :
    RecyclerView.Adapter<GroupAdapterRV.TemplateHolder>() {

    private var newOrPres = true
    private var list = listOf<GroupParam>()

    class TemplateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardGroupParamBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(group: GroupParam, newOrPres: Boolean) = with(binding) {
            val groupTitle = group.title
            title.text = groupTitle
            val attributes = group.attributes

            val adapterNum = ParamNumAdapterRV()
            numGv.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            numGv.adapter = adapterNum
            val numList = attributes!!.listParamNum
            adapterNum.setData(numList, groupTitle, newOrPres, false, -1, 1)

            val adapterStr = ParamStringAdapterRV()
            stringRV.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            stringRV.adapter = adapterStr
            val paramStrList = attributes.listParamStr
            adapterStr.setData(paramStrList, groupTitle, newOrPres, false, -1, 1)

            val adapterOptions = ParamOptionsAdapterRV()
            optionsRV.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            optionsRV.adapter = adapterOptions
            val optionsList = attributes.listParamOptions
            adapterOptions.setData(optionsList, groupTitle, newOrPres, false, -1, 1)

            val adapterItem = ItemAdapterRV()
            itemRV.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            itemRV.adapter = adapterItem
            val itemList = attributes.listItem
            adapterItem.setData(itemList, groupTitle, newOrPres)

            // настроить неотобржение для тех списков которых нет
            addParamStr.visibility = View.GONE
            addParamNum.visibility = View.GONE
            addParamOptions.visibility = View.GONE
            addItem.visibility = View.GONE

            val gpPref = SpecialGameData().groupPreferences[groupTitle]!!

            val strArrayList = ArrayList<String>()
            val numArrayList = ArrayList<String>()
            val optionsArrayList = ArrayList<String>()

            for ((key, value) in gpPref) {
                when (key) {
                    "str" -> {
                        addParamStr.visibility = View.VISIBLE
                        if (value.isNotEmpty()) {
                            value.forEach {
                                strArrayList.add(it)
                            }
                        }
                    }
                    "num" -> {
                        addParamNum.visibility = View.VISIBLE
                        if (value.isNotEmpty()) {
                            value.forEach {
                                numArrayList.add(it)
                            }
                        }
                    }
                    "options" -> {
                        addParamOptions.visibility = View.VISIBLE
                        if (value.isNotEmpty()) {
                            value.forEach {
                                optionsArrayList.add(it)
                            }
                         }
                    }
                    "item" -> {
                        addItem.visibility = View.VISIBLE
                    }
                }
            }

            addParamStr.setOnClickListener { view ->
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putString("type", "string")
                bundle.putInt("indexItem", -1)
                bundle.putInt("mod", 0)
                bundle.putStringArrayList("arr", strArrayList)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_addNewParamItem, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_characterList_to_pres_addNewParamItem, bundle)
                }
            }

            addParamNum.setOnClickListener { view ->
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putString("type", "num")
                bundle.putInt("indexItem", -1)
                bundle.putInt("mod", 0)
                bundle.putStringArrayList("arr", numArrayList)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_addNewParamItem, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_characterList_to_pres_addNewParamItem, bundle)
                }
            }

            addParamOptions.setOnClickListener { view ->
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putString("type", "options")
                bundle.putInt("indexItem", -1)
                bundle.putInt("mod", 0)
                bundle.putStringArrayList("arr", optionsArrayList)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_addNewParamItem, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_characterList_to_pres_addNewParamItem, bundle)
                }
            }

            addItem.setOnClickListener { view ->
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putInt("index", -1)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_itemEdit2, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_characterList_to_pres_itemEdit2, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_group_param, parent, false)
        return TemplateHolder(view)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(list[position], newOrPres)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: List<GroupParam>, newOrPres: Boolean) {
        this.list = list
        this.newOrPres = newOrPres
        notifyDataSetChanged()
    }
}