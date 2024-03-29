package com.example.test.edit_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.drop_down.DropDownAdapterRV
import com.example.test.data_base.realm.other_realm_object.EffectAdd
import com.example.test.databinding.EditEffectAddBinding
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.data_base.realm.game.GameDAO
import com.example.test.data_base.realm.game_system.GameSystemDAO
import com.example.test.components.views.drop_down.DropDownView
import com.example.test.components.views.HeaderView
import com.example.test.components.views.PlusMinusView

class EditEffectAdd : Fragment(), HeaderView.HeaderBack,
    DropDownAdapterRV.TemplateHolder.WhenValueTo {

    private val mCharacterVM: CharacterVM by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mGameSystemDAO: GameSystemDAO by activityViewModels()

    private var groupTitle = ""
    private var indexItem = -1
    private var indexEff = -1
    private lateinit var effectAdd: EffectAdd
    private var arr = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Требуемые аргументы
        val characterId = mCharacterVM.characterId
        val args = this.arguments
        groupTitle = args?.getString("groupTitle", "") ?: ""
        indexItem = args?.getInt("indexItem", -1) ?: -1
        indexEff = args?.getInt("indexEff", -1) ?: -1

        arr = getListParamNumNames()

        // ищем старый эффект или создаем новый
        effectAdd = EffectAdd()
        if (indexEff != -1) {
            effectAdd = mCharacterVM.item.value!!.effectsAdd[indexEff]!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_effect_add, container, false)

        // настраиваем логику для параметра permanent постоянный или временный
        var Effpermanent = effectAdd.permanent
        val binding = EditEffectAddBinding.bind(view)

        with(binding) {

            if (Effpermanent) {
                permanent.isChecked = false
                permanent.text = "Постоянный"
                linPermanentFalse.visibility = View.GONE
            } else {
                permanent.isChecked = true
                permanent.text = "Временный"
                linPermanentFalse.visibility = View.VISIBLE
            }

            permanent.setOnClickListener {
                if (permanent.isChecked) {
                    Effpermanent = false
                    permanent.text = "Временный"
                    linPermanentFalse.visibility = View.VISIBLE
                } else {
                    Effpermanent = true
                    permanent.text = "Постоянный"
                    linPermanentFalse.visibility = View.GONE
                }
            }
            // подключаем выбор параметра
            property.setDDArrayAndListener(arr, this@EditEffectAdd, null)

            //  подключаем фрагмент значение влияния
            impact.setValue(effectAdd.impact)
            impact.setListener(null, 0, object : PlusMinusView.NumberEvent {
                override fun numberEvent(number: Int) {
                    effectAdd.impact = number
                }

            })

            // подключаем фрагмент длительность
            duration.setValue(effectAdd.duration ?: 0)
            duration.setListener(null, 0, object : PlusMinusView.NumberEvent {
                override fun numberEvent(number: Int) {
                    effectAdd.duration = number
                }
            })

            // подключаем фрагмент откат
            rollback.setValue(effectAdd.rollback ?: 0)
            rollback.setListener(null, 0, object : PlusMinusView.NumberEvent {
                override fun numberEvent(number: Int) {
                    effectAdd.rollback = number
                }
            })
        }

        // настраиваем переключение и запоминание знака
        val btnSign = view.findViewById<Button>(R.id.sign)
        if (effectAdd.sign) {
            btnSign.setBackgroundResource(R.drawable.btn_sign_plus_green)
        } else {
            btnSign.setBackgroundResource(R.drawable.btn_sign_minus_green)
        }
        btnSign.setOnClickListener {
            if (effectAdd.sign) {
                effectAdd.sign = false
                btnSign.setBackgroundResource(R.drawable.btn_sign_minus_green)
            } else {
                effectAdd.sign = true
                btnSign.setBackgroundResource(R.drawable.btn_sign_plus_green)
            }
        }

        // закрыть и подтвердить
        val closeBtn = view.findViewById<Button>(R.id.weapon_edit_close)
        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val applyBtn = view.findViewById<Button>(R.id.weapon_edit_apply)
        applyBtn.setOnClickListener {
            var res = 1

            if (view.findViewById<DropDownView>(R.id.property)
                    .getValueFromDD() == "Выберите характеристику"
            ) {
                res = 0
                Toast.makeText(view.context, "Выберите параметр", Toast.LENGTH_SHORT).show()
            }

            if (effectAdd.impact == 0) {
                res = 0
                Toast.makeText(
                    view.context, "Значение влияния должно быть больше нуля",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (!Effpermanent) {
                if (effectAdd.duration == 0) {
                    res = 0
                    Toast.makeText(
                        view.context, "Значение длительности должно быть больше нуля",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                if (effectAdd.rollback == 0) {
                    res = 0
                    Toast.makeText(
                        view.context, "Значение отката должно быть больше нуля",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                effectAdd.duration = null
                effectAdd.rollback = null
            }

            if (res == 1) {// если проверка прошла успешно
                if (indexEff == -1) {//новый эффект
                    mCharacterVM.LOCaddEffectAddItem(
                        EffectAdd(
                            Effpermanent,
                            effectAdd.property,
                            effectAdd.impact,
                            effectAdd.sign,
                            effectAdd.duration,
                            effectAdd.rollback
                        )
                    )
                } else {
                    mCharacterVM.LOCupdateEffectAddItem(
                        indexEff, EffectAdd(
                            Effpermanent,
                            effectAdd.property,
                            effectAdd.impact,
                            effectAdd.sign,
                            effectAdd.duration,
                            effectAdd.rollback
                        )
                    )
                }
                view.findNavController().popBackStack()
            }
        }

        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    override fun whenValueTo(position: Int) {
        effectAdd.property = arr[position]
    }

    private fun getListParamNumNames(): MutableList<String> {
        val list = mutableListOf<String>()
        for (i in mGameSystemDAO.currentGameSystem!!.templateParamNum) {
            list.add(i.name)
        }
        return list
    }
}
