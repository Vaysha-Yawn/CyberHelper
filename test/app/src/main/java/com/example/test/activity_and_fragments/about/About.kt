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

                columnRV.layoutManager =
                    LinearLayoutManager(this@About, LinearLayoutManager.HORIZONTAL, false)
                val adapterRV = AdapterColumn()
                VM.map.observe(owner) { it ->
                    adapterRV.setData(it)
                    it[0].observe(owner){ list->
                        VM.rowCount.value = list.size
                    }
                }
                columnRV.adapter = adapterRV
                columnRV.isNestedScrollingEnabled = false

                deleteRV.layoutManager =
                    LinearLayoutManager(this@About, LinearLayoutManager.VERTICAL, false)
                val adapterDeleteRV = AdapterDelete(adapterRV.getOnChange())
                VM.rowCount.observe(owner) {
                    adapterDeleteRV.setData(it)
                }
                deleteRV.adapter = adapterDeleteRV
                deleteRV.isNestedScrollingEnabled = false

                plusRow.setOnClickListener {
                    VM.plusRow()
                    adapterRV.notifyDataSetChanged()
                    adapterDeleteRV.notifyDataSetChanged()
                    Toast.makeText(this@About, "${VM.rowCount.value}", Toast.LENGTH_SHORT).show()
                }
                plusColumn.setOnClickListener {
                    VM.plusColumn()
                    adapterDeleteRV.notifyDataSetChanged()
                    adapterRV.notifyDataSetChanged()
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
            }
            bind()

            return view
        }
    }

    ///////////////////////////////////////////////// АДАПТЕР ДЛЯ СТРОК ТАБЛИЦЫ

    class AdapterColumn(
    ) :
        RecyclerView.Adapter<AdapterColumnTemplateHolder>(), AdapterColumnTemplateHolder.OnChange {

        var list = mutableListOf<MutableLiveData<MutableList<MutableLiveData<String>>>>()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterColumnTemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_row, parent, false)
            return AdapterColumnTemplateHolder(view, this)
        }

        override fun onBindViewHolder(holder: AdapterColumnTemplateHolder, position: Int) {
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

        fun getOnChange(): AdapterColumnTemplateHolder.OnChange {
            return this
        }

    }

    class AdapterColumnTemplateHolder(
        private val view: View,
        private val onChange: OnChange,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = CardRowBinding.bind(view)
        fun bind(positionRow: Int) = with(binding) {
            if (positionRow == 0){
                delete.visibility = View.INVISIBLE
            }
            delete.setOnClickListener {
                try {
                    VM.deleteColumn(positionRow)
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
            cellRV.isNestedScrollingEnabled = false
        }

        interface OnChange {
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
    /////////////////////////////////////// АДАПТЕР ДЛЯ УДАЛЕНИЯ СТРОК ТАБЛИЦЫ
    class AdapterDelete(
        private val updColumnAdapter: AdapterColumnTemplateHolder.OnChange
    ) :
        RecyclerView.Adapter<AdapterDeleteTemplateHolder>(), AdapterDeleteTemplateHolder.UpdDelAdapter {
        var count = 1
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterDeleteTemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_delete, parent, false)
            return AdapterDeleteTemplateHolder(view, this, updColumnAdapter)
        }

        override fun onBindViewHolder(
            holder: AdapterDeleteTemplateHolder,
            position: Int
        ) {
            holder.bind(position)
        }

        override fun getItemCount(): Int {
            return count
        }

        fun setData(count: Int) {
            this.count = count
            notifyDataSetChanged()
        }

        override fun updDelAdapter() {
            notifyDataSetChanged()
        }

    }

    class AdapterDeleteTemplateHolder(
        private val view: View,
        private val updDelAdapter: UpdDelAdapter,
        private val onChange: AdapterColumnTemplateHolder.OnChange
    ) : RecyclerView.ViewHolder(view) {
        private val binding = CardDeleteBinding.bind(view)
        fun bind(position: Int) = with(binding) {
            if(position == 0){
                delete.visibility = View.INVISIBLE
            }
            delete.setOnClickListener {
                VM.deleteRow(position)
                updDelAdapter.updDelAdapter()
                VM.calcRowCount()
                onChange.onChange()
            }
        }
        interface UpdDelAdapter{
            fun updDelAdapter()
        }
    }
}

// сделать по количеству строк (элементов столбца) отдельный рв с кнопками удалить строку



