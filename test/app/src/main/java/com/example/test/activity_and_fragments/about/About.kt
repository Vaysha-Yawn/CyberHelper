package com.example.test.activity_and_fragments.about

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity_and_fragments.MainActivity
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.databinding.*
import com.example.test.viewModels.SheetVM
import com.example.test.views.HeaderView

private lateinit var FM: FragmentManager
private lateinit var VM: SheetVM
private lateinit var owner: LifecycleOwner

class About : AppCompatActivity(), HeaderView.HeaderBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sheet_example)
        val binding = SheetExampleBinding.bind(findViewById(R.id.root))
        with(binding) {
            try {
                header.setBack(true, this@About, this@About, this@About)
                plusColumn
                plusRow
                FM = supportFragmentManager
                owner = this@About
                VM = ViewModelProvider(this@About)[SheetVM::class.java]

                rowRV.layoutManager =
                    LinearLayoutManager(this@About, LinearLayoutManager.HORIZONTAL, false)
                val adapterRV = AdapterRow()
                VM.map.observe(owner) {
                    adapterRV.setData(it)
                }
                rowRV.adapter = adapterRV
                for (x in 0..5) {
                    val a = VM.addRow()!!
                    for (y in 0..5) {
                        VM.addCell(a, "$x $y")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@About, "$e", Toast.LENGTH_SHORT).show()
            }
        }
        /*
        название правила: "основное правило"
        подготовка: выбор предмета (тогда можно использовать характеристики выбранного предмета), можно указать конкретный тип предмета(только магическое, только оружие)
        выбор целей, если они необходимы, какие-то еще вопросы
        бросок: "команда 1" против "команды 2" - в итоге это должно быть число, но по факту это формула, которая рассчитывает число.
        В арсенале у нас все характеристики максимального персонажа, формула должна учитывать возможность отсутствия характеристики.
        Так же возможность установить логическое если(если есть, если равна, если макс, если мин).
        стандартные бросок или броски (цели можно установить по дефолту(атакующий) или выбрать на месте)
        В броске необходимо указать, какой максимальный кубик применяется
        победила "команда 1", ничья или победила "команда 2":
        здесь говорится, что происходит:
        1: срабатывает эффект по формуле: здесь так же можно использовать данные параметры и если, чтобы получить число урона
        (если у проигравшей цели параметр сопротивление огненному урону равно true, то урон пополам, иначе ..., если это числовой параметр, то его можно подставить в формулу)
        2: выводится сообщение
         */
    }

    override fun back() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    ///////////////////////////////////////////////// АДАПТЕР ДЛЯ ДИАЛОГА СТРОКОВОГО

    class EditString(
        private val positionRow: Int,
        private val positionCell: Int,
    ) : DialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            val view = inflater.inflate(R.layout.dialog_edit_string, container, false)

            val binding = DialogEditStringBinding.bind(view)
            fun bind() = with(binding) {
                (editString as TextView).text =
                    VM.map.value?.get(positionRow)?.value?.get(positionCell)?.value
                editString.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        VM.resetCell(positionRow, positionCell, p0.toString())
                    }

                    override fun afterTextChanged(p0: Editable?) {}
                })
                close
                apply
            }
            bind()

            return view
        }
    }

    ///////////////////////////////////////////////// АДАПТЕР ДЛЯ СТРОК ТАБЛИЦЫ

    class AdapterRow(
    ) :
        RecyclerView.Adapter<AdapterRowTemplateHolder>(), AdapterRowTemplateHolder.OnChange {

        var list = mutableListOf<MutableLiveData<MutableList<MutableLiveData<String>>>>()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterRowTemplateHolder  {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_row, parent, false)
            return AdapterRowTemplateHolder(view, this)
        }

        override fun onBindViewHolder(holder: AdapterRowTemplateHolder, position: Int) {
            holder.bind(position)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(
            list:
            MutableList<MutableLiveData<MutableList<MutableLiveData<String>>>>
        ) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun onChange() {
            notifyDataSetChanged()
        }


    }

    class AdapterRowTemplateHolder(
        private val view: View,
        private val onChange: OnChange,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = CardRowBinding.bind(view)
        fun bind(positionRow: Int) = with(binding) {
            delete.setOnClickListener {
                try {
                    VM.deleteRow(positionRow)
                    onChange.onChange()
                } catch (e: Exception) {
                    Toast.makeText(it.context, "$e", Toast.LENGTH_SHORT).show()
                }
            }
            cellRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            val adapterRV = AdapterCell(positionRow)
            cellRV.adapter = adapterRV
            VM.map.value?.get(positionRow)?.observe(owner) {
                adapterRV.setData(it)
            }
        }
        interface OnChange{
            fun onChange()
        }
    }

    ///////////////////////////////////////////////// АДАПТЕР ДЛЯ ЯЧЕЕК ТАБЛИЦЫ

    class AdapterCell(
        private val positionRow: Int
    ) :
        RecyclerView.Adapter<AdapterCellTemplateHolder>() {
        var list = mutableListOf<MutableLiveData<String>>()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterCellTemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_cell, parent, false)
            return AdapterCellTemplateHolder(view)
        }

        override fun onBindViewHolder(
            holder: AdapterCellTemplateHolder,
            position: Int
        ) {
            holder.bind(positionRow, position)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(list: MutableList<MutableLiveData<String>>) {
            this.list = list
            notifyDataSetChanged()
        }
    }
    class AdapterCellTemplateHolder(
        private val view: View,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = CardCellBinding.bind(view)
        fun bind(positionRow: Int, positionCell: Int) = with(binding) {

            VM.map.value?.get(positionRow)?.value?.get(positionCell)?.observe(owner) {
                cell.text = it
            }
            cell.setOnClickListener {
                val dialogFragment = EditString(positionRow, positionCell)
                dialogFragment.show(FM, "cell")
            }
        }
    }
}

// сделать по количеству строк (элементов столбца) отдельный рв с кнопками удалить строку



