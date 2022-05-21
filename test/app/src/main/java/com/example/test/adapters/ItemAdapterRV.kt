package com.example.test.adapters


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.*
import com.example.test.databinding.*
import io.realm.RealmList


class ItemAdapterRV() :
    RecyclerView.Adapter<ItemAdapterRV.TemplateHolder>() {

    private var newOrPres = true
    private var groupTitle = ""
    private var list = listOf<Item>()

    class TemplateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardItemBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(item: Item, groupTitle: String, newOrPres: Boolean, index: Int) = with(binding) {
            name.text = item.name
            description.text = item.description
            delete.setOnClickListener { view ->
                val bundle = Bundle()
                bundle.putString("nameItem", item.name)
                bundle.putString("titleGroup", groupTitle)
                bundle.putString("param", "item")
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_delete, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_characterList_to_delete, bundle)
                }
            }

            edit.setOnClickListener { view ->
                val bundle = Bundle()
                bundle.putInt("index", index)
                bundle.putString("groupTitle", groupTitle)
                if (newOrPres) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_itemEdit, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_characterList_to_pres_itemEdit, bundle)
                }
            }

            if (item.effectsAdd == RealmList<EffectAdd>()) {
                nameEffectAddRV.visibility = View.GONE
                effectAddRV.visibility = View.GONE
            } else {
                val adapterAdd = EffectAddAdapterRV()
                effectAddRV.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                effectAddRV.adapter = adapterAdd
                val addList = item.effectsAdd
                adapterAdd.setData(addList, groupTitle, newOrPres, true, index)
            }

            if (item.effectsWeapon == RealmList<EffectWeapon>()) {
                nameEffectWeaponRV.visibility = View.GONE
                effectWeaponRV.visibility = View.GONE
            } else {
                val adapterWeapon = EffectWeaponAdapterRV()
                effectWeaponRV.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                effectWeaponRV.adapter = adapterWeapon
                val weaponList = item.effectsWeapon
                adapterWeapon.setData(weaponList, groupTitle, newOrPres, true, index)
            }

            if (item.otherParamNum == RealmList<ParamNum>()) {
                nameNumRV.visibility = View.GONE
                numGv.visibility = View.GONE
            } else {
                val adapterNum = ItemNumAdapterRV()
                try {
                    numGv.layoutManager =
                        GridLayoutManager(itemView.context, 3)
                    numGv.adapter = adapterNum
                    val numList = item.otherParamNum
                    adapterNum.setData(numList)
                }catch (e:Exception){
                    Toast.makeText(numGv.context, "$e", Toast.LENGTH_LONG).show()
                }
            }

            if (item.otherParamStr == RealmList<ParamStr>()) {
                nameStrRV.visibility = View.GONE
                strRV.visibility = View.GONE
            } else {
                val adapterStr = ParamStringAdapterRV()
                strRV.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                strRV.adapter = adapterStr
                val paramStrList = item.otherParamStr
                adapterStr.setData(paramStrList, groupTitle, newOrPres,true, index,3)
            }

            if (item.otherParamStr == RealmList<ParamStr>()) {
                nameOptionsRV.visibility = View.GONE
                optionsRV.visibility = View.GONE
            } else {
                val adapterOptions = ParamOptionsAdapterRV()
                optionsRV.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                optionsRV.adapter = adapterOptions
                val optionsList = item.otherParamOptions
                adapterOptions.setData(optionsList, groupTitle, newOrPres,true, index,3)
            }
            baseLay.visibility = View.GONE
            titleLay.setOnClickListener {
                if  (baseLay.visibility == View.VISIBLE){
                    baseLay.visibility = View.GONE
                    moreOrLess.background = moreOrLess.context.getDrawable(R.drawable.more)
                }else{
                    baseLay.visibility = View.VISIBLE
                    moreOrLess.background = moreOrLess.context.getDrawable(R.drawable.less)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return TemplateHolder(view)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(list[position], groupTitle, newOrPres, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: List<Item>, groupTitle: String, newOrPres: Boolean) {
        this.groupTitle = groupTitle
        this.newOrPres = newOrPres
        this.list = list
        notifyDataSetChanged()
    }
}