package com.example.test.adapters


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.ParamNum
import com.example.test.databinding.CardStringBinding
import io.realm.RealmList


class ParamNumAdapterRV():
    RecyclerView.Adapter<ParamNumAdapterRV.TemplateHolder>() {

    private var newOrPres = true
    private var groupTitle = ""
    private var list = listOf<ParamNum>()
    private var readOrEdit = true// true - read, edit - false
    private var indexItem = -1
    private var modEdit = -1

    class TemplateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardStringBinding.bind(view)
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(param: ParamNum, groupTitle:String, newOrPres: Boolean, readOrEdit:Boolean, indexItem:Int, indexParam:Int, modEdit:Int) = with(binding){
            if (readOrEdit){
                background.background = background.context.getDrawable(R.drawable.background_black)
                delete.visibility = View.INVISIBLE
                edit.visibility = View.GONE
            }else{
                delete.visibility = View.VISIBLE
                if (param.removable){
                    delete.setOnClickListener {view->
                        val bundle = Bundle()
                        bundle.putString("nameParam", param.name)
                        bundle.putString("titleGroup", groupTitle)
                        bundle.putString("param", "paramNum")
                        bundle.putInt("indexItem", indexItem)
                        bundle.putInt("indexParam", indexParam)
                        if (modEdit == 0 || modEdit == 1) {
                            if (newOrPres) {
                                view.findNavController()
                                    .navigate(R.id.action_new_characterList_to_new_delete, bundle)
                            } else {
                                view.findNavController()
                                    .navigate(R.id.action_characterList_to_delete, bundle)
                            }
                        }else{
                            if (newOrPres) {
                                view.findNavController()
                                    .navigate(R.id.action_new_itemEdit_to_new_delete, bundle)
                            } else {
                                view.findNavController()
                                    .navigate(R.id.action_pres_itemEdit_to_pres_delete, bundle)
                            }
                        }
                    }
                }else{
                    delete.visibility = View.INVISIBLE
                }
                edit.visibility = View.VISIBLE
                edit.setOnClickListener { view->
                    val bundle = Bundle()
                    bundle.putString("paramName", param.name)
                    bundle.putString("value", param.value.toString())
                    bundle.putString("groupTitle", groupTitle)
                    bundle.putInt("indexItem", indexItem)
                    bundle.putInt("indexParam", indexParam)
                    bundle.putInt("mod", modEdit)
                    if (modEdit == 0 || modEdit == 1) {
                        if (newOrPres) {
                            view.findNavController()
                                .navigate(R.id.action_new_characterList_to_new_edit_string, bundle)
                        } else {
                            view.findNavController()
                                .navigate(R.id.action_characterList_to_edit_string, bundle)
                        }
                    }else{
                        if (newOrPres) {
                            view.findNavController()
                                .navigate(R.id.action_new_itemEdit_to_new_edit_number, bundle)
                        } else {
                            view.findNavController()
                                .navigate(R.id.action_pres_itemEdit_to_pres_edit_number, bundle)
                        }
                    }
                }
            }
            name.text = param.name
            str.text = param.value.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_string, parent, false)
        return TemplateHolder(view)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(list[position], groupTitle, newOrPres, readOrEdit, indexItem, position, modEdit)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: RealmList<ParamNum>, groupTitle:String, newOrPres: Boolean, readOrEdit:Boolean, indexItem:Int, modEdit:Int ){
        this.list = list
        this.groupTitle = groupTitle
        this.newOrPres = newOrPres
        this.readOrEdit = readOrEdit
        this.indexItem = indexItem
        this.modEdit = modEdit
        notifyDataSetChanged()
    }
}