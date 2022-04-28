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
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.databinding.DropDownListBinding

class GoalDDView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes),
    DropDownAdapterRV.TemplateHolder.OnDDChosen {

    private var binding: DropDownListBinding

    private lateinit var more: Drawable
    private lateinit var less: Drawable

    private var color: Int = 0
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

        color = typedArray.getColor(
            R.styleable.DropDownView_DD_color,
            ContextCompat.getColor(context, R.color.green)
        )

        val textColor = typedArray.getColor(
            R.styleable.DropDownView_text_color,
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

            main.backgroundTintList = ColorStateList.valueOf(color)

            main.setCompoundDrawablesWithIntrinsicBounds(null, null, more, null)
        }
        typedArray.recycle()
    }

    fun getValueFromDD(): String {
        return binding.main.text.toString()
    }

    fun setDDArrayAndListener(
        list: List<String>,
        ob: DropDownAdapterRV.TemplateHolder.WhenValueTo?
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

            this@GoalDDView.list = list

            val adapterItems =
                DropDownAdapterRV(list, color, ob, this@GoalDDView)
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

    override fun onDDChosen(position: Int) {
        with(binding) {
            main.text = list[position]
            items.visibility = View.GONE
            setMoreOrLess(true)
        }
    }


    /*

     if (goal == "goal") {
         val chosenGoal = mSkillVM.allGoals.value?.get(position)
         if (chosenGoal!=null){
             mSkillVM.chosenGoals.value?.set(position, chosenGoal)
             mSkillVM.allGoals.value?.remove(chosenGoal)
         }
     }*/
}

