package com.example.test.components.views

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
import com.example.test.adapters.DropDownAdapterRV

class DropDownView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes),
    DropDownAdapterRV.TemplateHolder.Select {

    private var binding: DropDownListBinding

    private lateinit var more: Drawable
    private lateinit var less: Drawable

    private var colorBack: Int = 0
    private var colorText: Int = 0
    private lateinit var list: List<String>

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

        colorBack = typedArray.getColor(
            R.styleable.DropDownView_DD_color,
            ContextCompat.getColor(context, R.color.green)
        )

        colorText = typedArray.getColor(
            R.styleable.DropDownView_text_color,
            ContextCompat.getColor(context, R.color.black)
        )

        val mainText = typedArray.getText(R.styleable.DropDownView_main_text)
        with(binding) {

            main.text = mainText
            items.visibility = View.GONE

            more = ContextCompat.getDrawable(context, R.drawable.more).also {
                it?.setTint(colorText)
            }!!
            less = ContextCompat.getDrawable(context, R.drawable.less).also {
                it?.setTint(colorText)
            }!!

            main.setTextColor(colorText)

            main.backgroundTintList = ColorStateList.valueOf(colorBack)

            main.setCompoundDrawablesWithIntrinsicBounds(null, null, more, null)
        }
        typedArray.recycle()
    }

    fun getValueFromDD(): String {
        return binding.main.text.toString()
    }

    fun setMainText(text:String){
        binding.main.text = text
    }

    fun setDDArrayAndListener(
        list: List<String>,
        ob: DropDownAdapterRV.TemplateHolder.WhenValueTo?,
        check: DropDownAdapterRV.TemplateHolder.CheckChoose?
    ) {
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

            this@DropDownView.list = list

            val adapterItems =
                DropDownAdapterRV(list, colorBack, colorText, ob, this@DropDownView, check)
            items.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            items.adapter = adapterItems
        }
    }

    private fun setMoreOrLess(moreOrLess: Boolean) {
        if (moreOrLess) {
            binding.main.setCompoundDrawablesWithIntrinsicBounds(null, null, more, null)
        } else {
            binding.main.setCompoundDrawablesWithIntrinsicBounds(null, null, less, null)
        }
    }

    override fun select(position: Int) {
        with(binding) {
            main.text = list[position]
            items.visibility = View.GONE
            setMoreOrLess(true)
        }
    }
}

