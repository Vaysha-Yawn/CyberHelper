package com.example.test.components.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.LayerDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.test.R
import com.example.test.databinding.PlusAndMinusSmallGreenBinding

class PlusMinusView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: PlusAndMinusSmallGreenBinding

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
        inflater.inflate(R.layout.plus_and_minus_small_green, this, true)
        binding = PlusAndMinusSmallGreenBinding.bind(this)
        initAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.PlusMinusView,
            defStyleAttr,
            defStyleRes
        )
        with(binding) {

            val color = typedArray.getColor(
                R.styleable.PlusMinusView_PM_color,
                ContextCompat.getColor(context, R.color.green)
            )
            try {
                val bcgMinus = minus.background as LayerDrawable
                bcgMinus.findDrawableByLayerId(R.id.minus_background).also {
                    it.setTint(color)
                    it.mutate()
                }
                val bcgPlus = plus.background as LayerDrawable
                bcgPlus.findDrawableByLayerId(R.id.plus_background).also {
                    it.setTint(color)
                    it.mutate()
                }
            } catch (e: Exception) {

            }
            edit.backgroundTintList = ColorStateList.valueOf(color)

            val small = typedArray.getBoolean(R.styleable.PlusMinusView_PM_small, true)
            if (small) {
                minusSpace.visibility = View.GONE
                plusSpace.visibility = View.GONE
                frame.background =
                    ContextCompat.getDrawable(context, R.drawable.background_grey_circle)
            } else {
                root.background =
                    ContextCompat.getDrawable(context, R.drawable.background_grey_circle)
            }
        }
        typedArray.recycle()
    }

    fun setListener(maxValue: Int?, minValue: Int?, obj: NumberEvent?) {
        with(binding) {
            val edit = edit as TextView
            plus.setOnClickListener {
                if (maxValue != null) {
                    var res = edit.text.toString().toInt()
                    if (res < maxValue) {
                        res += 1
                        edit.text = res.toString()
                        obj?.numberEvent(res)
                    } else {
                        Toast.makeText(
                            context, "Значение параметра не должно превышать $maxValue",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    var res = edit.text.toString().toInt()
                    res += 1
                    edit.text = res.toString()
                    obj?.numberEvent(res)
                }
            }

            minus.setOnClickListener {
                if (minValue != null) {
                    var res = edit.text.toString().toInt()
                    if (res > minValue) {
                        res -= 1
                        edit.text = res.toString()
                        obj?.numberEvent(res)
                    } else {
                        Toast.makeText(
                            context, "Значение параметра не может быть меньше $minValue",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    var res = edit.text.toString().toInt()
                    res -= 1
                    edit.text = res.toString()
                    obj?.numberEvent(res)
                }
            }


            edit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val x = edit.text.toString().toIntOrNull()
                    if (x != null && obj != null) {
                        obj.numberEvent(x)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    fun getValue(): String {
        return binding.edit.text.toString()
    }

    fun setValue(value:Int) {
         (binding.edit as TextView).text = value.toString()
    }

    interface NumberEvent {
        fun numberEvent(number: Int)
    }

}