package com.example.test.character_list.presentation.adapters


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.realm.other_realm_object.EffectAdd
import com.example.test.databinding.CardEffectAddBinding
import io.realm.RealmList


class EffectAddAdapterRV :
    RecyclerView.Adapter<EffectAddAdapterRV.TemplateHolder>() {

    private var list = listOf<EffectAdd>()
    private var newOrPres = true
    private var groupTitle = ""
    private var readOrEdit = true// true - read, edit - false
    private var indexItem = -1

    class TemplateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardEffectAddBinding.bind(view)

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(
            effect: EffectAdd,
            newOrPres: Boolean,
            groupTitle: String,
            readOrEdit: Boolean,
            indexItem: Int,
            indexEff: Int
        ) = with(binding) {
            if (readOrEdit) {//read
                delete.visibility = View.GONE
                edit.visibility = View.GONE
            } else {//edit
                background.background =
                    background.context.getDrawable(R.drawable.background_grey_circle)
                delete.visibility = View.VISIBLE
                edit.visibility = View.VISIBLE
                use.visibility = View.GONE
                delete.setOnClickListener { view ->
                    val bundle = Bundle()
                    bundle.putInt("indexEff", indexEff)
                    bundle.putInt("indexItem", indexItem)
                    bundle.putString("titleGroup", groupTitle)
                    bundle.putString("param", "effectAdd")
                    if (newOrPres) {
                        view.findNavController()
                            .navigate(R.id.action_new_itemEdit_to_new_delete, bundle)
                    } else {
                        view.findNavController()
                            .navigate(R.id.action_pres_itemEdit_to_pres_delete, bundle)
                    }
                }
                edit.setOnClickListener { view ->
                    val bundle = Bundle()
                    bundle.putInt("indexItem", indexItem)
                    bundle.putInt("indexEff", indexEff)
                    bundle.putString("groupTitle", groupTitle)
                    if (newOrPres) {
                        view.findNavController()
                            .navigate(R.id.action_new_itemEdit_to_new_editEffectAdd, bundle)
                    } else {
                        view.findNavController()
                            .navigate(R.id.action_pres_itemEdit_to_pres_editEffectAdd, bundle)
                    }
                }
            }

            if (!effect.permanent && readOrEdit) {
                use.setOnClickListener {
                    // мы удаляем эффект если он использован и закончился,
                    // мы показываем, что эффект используется, если он используется в течении нескольких ходов,
                    // если есть откат,то он не удаяется, а показывет через вколько откатиться , если эффект восстанавливается после долгого отдыха, то отслеживаем это.

                }
            } else {
                use.visibility = View.GONE
            }

            name.text = effect.property

            if (effect.sign) {
                impact.text = "+${effect.impact}"
            } else {
                impact.text = "-${effect.impact}"
            }

            if (effect.duration != null) {
                duration.text = effect.duration.toString()
            } else {
                duration.visibility = View.GONE
            }

            if (effect.rollback != null) {
                rollback.text = effect.rollback.toString()
            } else {
                rollback.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_effect_add, parent, false)
        return TemplateHolder(view)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(list[position], newOrPres, groupTitle, readOrEdit, indexItem, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(
        list: RealmList<EffectAdd>,
        groupTitle: String,
        newOrPres: Boolean,
        readOrEdit: Boolean,
        indexItem: Int
    ) {
        this.groupTitle = groupTitle
        this.newOrPres = newOrPres
        this.list = list
        this.readOrEdit = readOrEdit
        this.indexItem = indexItem
        notifyDataSetChanged()
    }
}