package com.example.test.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.test.R
import com.example.test.adapters.AbstractAdapterRV
import com.example.test.adapters.AbstractTemplateHolder
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.databinding.CardRvCompactDdBinding
import com.example.test.databinding.ViewCompactBinding
import java.util.*

class CompactViewDD(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewCompactBinding
    private var color: Int? = null
    private var defMain: String = ""
    private var listDD = listOf<String>()
    private var values = mutableListOf<Int?>()

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
            R.styleable.CompactViewDD,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            color = typedArray.getColor(
                R.styleable.CompactViewDD_CV_item_color,
                ContextCompat.getColor(context, R.color.green)
            )

            defMain = typedArray.getString(
                R.styleable.CompactViewDD_CV_item_defMain
            )?:""

            val titleText = typedArray.getString(
                R.styleable.CompactViewDD_CV_title
            )

            val addText = typedArray.getString(
                R.styleable.CompactViewDD_CV_add_text
            )

            title.text = titleText

            add.text = addText

            val adapterDD = AbstractAdapterRV<Int?>(
                object : AbstractTemplateHolder.InitBinding<Int?> {
                    override fun funBinding(
                        view: View,
                        param:  Int?,
                        updView: AbstractTemplateHolder.UpdView,
                        pos:Int
                    ) {
                        val binding = CardRvCompactDdBinding.bind(view)
                        with(binding) {
                            delete.setOnClickListener {
                                values.removeAt(pos)
                                updView.updateView()
                            }
                            if(param == null){
                                DD.setMainText(defMain)
                            }else{
                                DD.setMainText(listDD[param])
                            }
                            DD.setDDArrayAndListener(
                                listDD,
                                object : DropDownAdapterRV.TemplateHolder.WhenValueTo {
                                    override fun whenValueTo(position: Int) {
                                        values[pos] = position
                                    }
                                },
                                null
                            )
                        }
                    }
                },
                R.layout.card_rv_compact_dd
            )
            RV.adapter = adapterDD

            add.setOnClickListener {
                values.add(null)
                adapterDD.setData(values)
            }
        }

        typedArray.recycle()

    }

    fun setData(listValues: MutableList<Int?>) {
        values = listValues
    }

    fun getData():MutableList<Int?>{
        return values
    }


}