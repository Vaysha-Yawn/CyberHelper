package com.example.test.character_list.presentation.adapters


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.realm.other_realm_object.GroupParam
import com.example.test.databinding.CardGroupParamBinding


class GroupAdapterRV :
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
            val numList = attributes!!.listParamNum!!
            adapterNum.setData(numList, groupTitle, newOrPres, false, -1, 1)

            val adapterStr = ParamStringAdapterRV()
            stringRV.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            stringRV.adapter = adapterStr
            val paramStrList = attributes.listParamStr!!
            adapterStr.setData(paramStrList, groupTitle, newOrPres, false, -1, 1)

            val adapterOptions = ParamOptionsAdapterRV()
            optionsRV.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            optionsRV.adapter = adapterOptions
            val optionsList = attributes.listParamOptions!!
            adapterOptions.setData(optionsList, groupTitle, newOrPres, false, -1, 1)

            val adapterItem = ItemAdapterRV()
            itemRV.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            itemRV.adapter = adapterItem
            val itemList = attributes.listItem!!
            adapterItem.setData(itemList, groupTitle, newOrPres)

            // настроить неотобржение для тех списков которых нет
            addParamStr.visibility = View.GONE
            addParamNum.visibility = View.GONE
            addParamOptions.visibility = View.GONE
            addItem.visibility = View.GONE

            //val gpPref = DSpecialGameData().groupPreferences[groupTitle]!!

            val arrayList = ArrayList<String>()

            /*for ((key, value) in gpPref) {
                when (key) {
                    "str" -> {*/
            addParamStr.visibility = View.VISIBLE
            for (i in attributes.listParamStr!!) {
                arrayList.add(i.name)
            }

            //"num" -> {
            addParamNum.visibility = View.VISIBLE
            for (i in attributes.listParamNum!!) {
                arrayList.add(i.name)
            }

            //"options" -> {
            addParamOptions.visibility = View.VISIBLE
            for (i in attributes.listParamOptions!!) {
                arrayList.add(i.name)
            }

            //"item" -> {
            addItem.visibility = View.VISIBLE
            for (i in attributes.listItem!!) {
                arrayList.add(i.name)
            }


            fun openAddParam(view: View, type: String) {
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putString("type", type)
                bundle.putInt("indexItem", -1)
                bundle.putInt("mod", 0)
                bundle.putStringArrayList("arr", arrayList)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_addNewParamItem, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_characterList_to_pres_addNewParamItem, bundle)
                }
            }

            addParamStr.setOnClickListener { view ->
                openAddParam(view, "string")
            }

            addParamNum.setOnClickListener { view ->
                openAddParam(view, "num")
            }

            addParamOptions.setOnClickListener { view ->
                openAddParam(view, "options")
            }

            addItem.setOnClickListener { view ->
                val bundle = Bundle()
                bundle.putString("groupTitle", groupTitle)
                bundle.putStringArrayList("arr", arrayList)
                bundle.putInt("index", -1)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_addNewItem2, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_characterList_to_addNewItem, bundle)
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