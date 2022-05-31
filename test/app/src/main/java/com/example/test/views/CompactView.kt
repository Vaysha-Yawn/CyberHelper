package com.example.test.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.data_base.DSpecialGameData
import com.example.test.data_base.Mod
import com.example.test.databinding.CardModBinding
import com.example.test.databinding.HeaderBinding
import com.example.test.databinding.ViewCompactBinding

class CompactView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewCompactBinding
    private var color: Int? = null
    private var type: Int? = null
    private var hint: String? = null

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.header, this, true)
        binding = ViewCompactBinding.bind(this)
        initAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CompactView,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            color = typedArray.getColor(
                R.styleable.CompactView_CV_item_color,
                ContextCompat.getColor(context, R.color.green)
            )

            type = typedArray.getInteger(
                R.styleable.CompactView_CV_item_type,
                0
            )

            hint = typedArray.getString(
                R.styleable.CompactView_CV_item_hint
            )

            val titleText = typedArray.getString(
                R.styleable.CompactView_CV_title
            )

            val addText = typedArray.getString(
                R.styleable.CompactView_CV_add_text
            )

            title.text = titleText

            add.text = addText

        }
        typedArray.recycle()

    }

    fun initRV(adapter: AdapterRV){
        with(binding){
            RV.adapter = adapter
        }
    }

    fun getValue(){

    }

    class AdapterRV(
        private val deleteMod: TemplateHolder.DeleteMod,
        private val listener: TemplateHolder.PutModValue,

    ) :
        RecyclerView.Adapter<TemplateHolder>(), TemplateHolder.updView {

        var list = mutableListOf<Mod>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_mod, parent, false)
            return TemplateHolder(view, deleteMod, this, listener)
        }

        override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(list: MutableList<Mod>) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun updateView() {
            notifyDataSetChanged()
        }

    }

    class TemplateHolder(
        view: View,
        private val delete: DeleteMod,
        private val updViewr: updView,
        private val listener: PutModValue,
    ) : RecyclerView.ViewHolder(view), DropDownAdapterRV.TemplateHolder.WhenValueTo, PlusMinusView.NumberEvent {
        private val binding = CardModBinding.bind(view)

        fun bind(style:Int, value:String, ) = with(binding) {

                PMLinear.visibility = View.GONE
                DD.visibility = View.VISIBLE
                DD.setMainText(value)
                DD.setDDArrayAndListener(DSpecialGameData().modName, this@TemplateHolder, null)

                PMLinear.visibility = View.VISIBLE
                DD.visibility = View.GONE
                PM.setValue(value.toInt())
                PM.setListener(99, 0, this@TemplateHolder)

            delete.setOnClickListener { view ->
                this@TemplateHolder.delete.deleteMod(adapterPosition)
                updViewr.updateView()
            }
        }

        interface updView {
            fun updateView()
        }

        interface DeleteMod {
            fun deleteMod(position: Int)
        }

        interface PutModValue {
            fun putModValue(position: Int, value:Int)
        }

        override fun whenValueTo(position: Int) {
            listener.putModValue(adapterPosition, position)
        }

        override fun numberEvent(number: Int) {
            listener.putModValue(adapterPosition, number)
        }
    }
}