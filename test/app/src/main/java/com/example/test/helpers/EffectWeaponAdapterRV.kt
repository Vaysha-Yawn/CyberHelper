package com.example.test.helpers


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.databinding.CardEffectWeaponBinding
import io.realm.RealmList


class EffectWeaponAdapterRV():
    RecyclerView.Adapter<EffectWeaponAdapterRV.TemplateHolder>() {

    private var list = listOf<EffectWeapon>()
    private var newOrPres = true
    private var groupTitle = ""
    private var readOrEdit = true// true - read, edit - false
    private var indexItem = -1


    class TemplateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardEffectWeaponBinding.bind(view)
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(effect: EffectWeapon, groupTitle:String, newOrPres: Boolean, readOrEdit: Boolean, indexItem:Int, indexEff:Int) = with(binding){
            type.text = effect.fightType.name
            damage.text = "${effect.numCount}d${effect.dX}"
            if (effect.wearout!=null){
                wearout.text = "${effect.wearout} раз"
            }else{
                linlayWearout.visibility = View.GONE
            }
            if (readOrEdit){// при чтении
                linearLayout3.visibility = View.GONE
            }else{// при редактировании
                background.background = background.context.getDrawable(R.drawable.background_grey_circle)
                linearLayout3.visibility = View.VISIBLE
                edit.setOnClickListener {view->
                    val bundle = Bundle()
                    bundle.putInt("indexItem", indexItem)
                    bundle.putInt("indexEff", indexEff)
                    bundle.putString("groupTitle", groupTitle)
                    if (newOrPres) {
                        view.findNavController()
                            .navigate(R.id.action_new_itemEdit_to_new_editEffectWeapon3, bundle)
                    } else {
                        view.findNavController()
                            .navigate(R.id.action_pres_itemEdit_to_pres_editEffectWeapon3, bundle)
                    }
                }
                delete.setOnClickListener { view->
                    val bundle = Bundle()
                    bundle.putInt("indexEff", indexEff)
                    bundle.putInt("indexItem", indexItem)
                    bundle.putString("titleGroup", groupTitle)
                    bundle.putString("param", "effectWeapon")
                    if (newOrPres) {
                        view.findNavController()
                            .navigate(R.id.action_new_itemEdit_to_new_delete, bundle)
                    } else {
                        view.findNavController()
                            .navigate(R.id.action_pres_itemEdit_to_pres_delete, bundle)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_effect_weapon, parent, false)
        return TemplateHolder(view)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(list[position], groupTitle, newOrPres, readOrEdit, indexItem, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: RealmList<EffectWeapon>, groupTitle:String, newOrPres: Boolean, readOrEdit: Boolean, indexItem:Int){
        this.list = list
        this.groupTitle = groupTitle
        this.newOrPres = newOrPres
        this.readOrEdit = readOrEdit
        this.indexItem = indexItem
        notifyDataSetChanged()
    }
}