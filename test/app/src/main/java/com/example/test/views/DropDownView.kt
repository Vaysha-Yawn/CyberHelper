package com.example.test.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.databinding.DropDownListBinding
import com.example.test.helpers.DropDownAdapterRV

class DropDownView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: DropDownListBinding

    private lateinit var more: Drawable
    private lateinit var less: Drawable

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
        inflater.inflate(R.layout.drop_down_list, this, true)
        binding = DropDownListBinding.bind(this)
        initAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.DropDownView,
            defStyleAttr,
            defStyleRes
        )
        val dDColor = typedArray.getColor(
            R.styleable.DropDownView_DD_color,
            ContextCompat.getColor(context, R.color.green)
        )

        val textColor = typedArray.getColor(
            R.styleable.DropDownView_DD_color,
            ContextCompat.getColor(context, R.color.black)
        )

        val mainText = typedArray.getText(R.styleable.DropDownView_main_text)
        with(binding) {

            main.text = mainText
            items.visibility = View.GONE

            more = ContextCompat.getDrawable(context, R.drawable.more).also {
                it?.setTint(textColor)
            }!!
            less = ContextCompat.getDrawable(context, R.drawable.less).also {
                it?.setTint(textColor)
            }!!

            main.setTextColor(textColor)

            main.backgroundTintList = ColorStateList.valueOf(dDColor)

            main.setCompoundDrawables(null, null, more, null)
        }
        typedArray.recycle()
    }

    fun getValueFromDD(): String {
        return binding.main.text.toString()
    }

    fun setDDArrayAndListener(mainText: String, list: List<String>) {
        with(binding) {
            main.setOnClickListener {
                if (items.visibility != View.VISIBLE) {
                    items.visibility = View.VISIBLE
                    setMoreOrLess(false)
                } else {
                    items.visibility = View.GONE
                    setMoreOrLess(true)
                }
            }
        }

        val adapterItems =
            DropDownAdapterRV(list, them, this)
        itemsRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        itemsRV.adapter = adapterItems

        if (goal == "modification") {
            val value = mSkillVM.mapMod[key]?.value?.get(indexMod)?.value ?: 1
            if (indexMod >= 0 && value > 0) {
                tvMainTypeWeapon.text = list[value - 1]
            }

        }

    }

    private fun setMoreOrLess(moreOrLess: Boolean) {
        if (moreOrLess) {
            binding.main.setCompoundDrawables(null, null, more, null)
        } else {
            binding.main.setCompoundDrawables(null, null, less, null)
        }
    }

    /*interface OnItemClickListener{
        fun superOnItemClick(position: Int){
            with(binding){
            tvMainTypeWeapon.text = list[position]
            itemsRV.visibility = View.GONE
            tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                more,
                0
            )
        }
        }
        fun onItemClick(position: Int)
    }*/
    // я короче хз, что делать с этим интерфейсом ....
}

