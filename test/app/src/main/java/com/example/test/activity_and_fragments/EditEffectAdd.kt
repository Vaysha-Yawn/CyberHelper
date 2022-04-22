package com.example.test.activity_and_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.EffectAdd
import com.example.test.data_base.TemplateParamOptions
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.widgets.PlusAndMinus

class EditEffectAdd : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_effect_add, container, false)

        // Требуемые аргументы
        val characterId = mCharacterVM.characterId
        val args = this.arguments
        val groupTitle = args?.getString("groupTitle", "") ?: ""
        val indexItem = args?.getInt("indexItem", -1) ?: -1
        val indexEff = args?.getInt("indexEff", -1) ?: -1

        // ищем старый эффект или создаем новый
        var effectAdd = EffectAdd()
        if (indexEff != -1) {
            effectAdd = mCharacterVM.item.value!!.effectsAdd[indexEff]!!
        }

        // настраиваем логику для параметра permanent постоянный или временный

        fun setfill(style: Boolean, btn: Button) {
            if (style) {
                btn.setBackgroundResource(R.drawable.btn_green_full)
                btn.setTextColor(android.graphics.Color.BLACK)
            } else {
                btn.setBackgroundResource(R.drawable.btn_green_outline)
                btn.setTextColor(android.graphics.Color.parseColor("#00FF99"))
            }
        }

        val linPermanentFalse = view.findViewById<LinearLayout>(R.id.lin_permanent_false)
        val btn_permanent_true = view.findViewById<Button>(R.id.permanent_true)
        val btn_permanent_false = view.findViewById<Button>(R.id.permanent_false)
        var permanent = effectAdd.permanent
        setfill(permanent, btn_permanent_true)
        setfill(!permanent, btn_permanent_false)
        if (permanent) {
            linPermanentFalse.visibility = View.GONE
        } else {
            linPermanentFalse.visibility = View.VISIBLE
        }
        btn_permanent_true.setOnClickListener {
            if (!permanent) {
                permanent = true
                setfill(permanent, btn_permanent_true)
                setfill(!permanent, btn_permanent_false)
                linPermanentFalse.visibility = View.GONE
            }
        }

        btn_permanent_false.setOnClickListener {
            if (permanent) {
                permanent = false
                setfill(permanent, btn_permanent_true)
                setfill(!permanent, btn_permanent_false)
                linPermanentFalse.visibility = View.VISIBLE
            }
        }

        // подключаем выбор параметра
        val bundle = Bundle()
        bundle.putString("them", "green")
        val param = TemplateParamOptions().mapParamOptionsSupporting["Параметры для влияния"]
        if (effectAdd.property == "") {
            bundle.putString("main", param?.defMain)
        } else {
            bundle.putString("main", effectAdd.property)
        }
        val options = arrayListOf<String>()
        param?.options?.forEach { options.add(it) }
        bundle.putStringArrayList("list", options)
        //loadFragment(R.id.property, DropDownList(), bundle)

        // настраиваем переключение и запоминание знака
        val btnSign = view.findViewById<Button>(R.id.sign)
        var sign = effectAdd.sign
        if (sign) {
            btnSign.setBackgroundResource(R.drawable.btn_sign_plus_green)
        } else {
            btnSign.setBackgroundResource(R.drawable.btn_sign_minus_green)
        }
        btnSign.setOnClickListener {
            if (sign) {
                sign = false
                btnSign.setBackgroundResource(R.drawable.btn_sign_minus_green)
            } else {
                sign = true
                btnSign.setBackgroundResource(R.drawable.btn_sign_plus_green)
            }
        }

        //  подключаем фрагмент значение влияния
        val bundleImpact = Bundle()
        bundleImpact.putInt("value", effectAdd.impact)
        bundleImpact.putInt("minValue", 0)
        bundleImpact.putString("them", "green")
        loadFragment(R.id.impact, PlusAndMinus(), bundleImpact)


        // подключаем фрагмент длительность
        val bundleDuration = Bundle()
        bundleDuration.putInt("value", effectAdd.duration ?: 0)
        bundleDuration.putInt("minValue", 0)
        bundleDuration.putString("them", "green")
        loadFragment(R.id.duration, PlusAndMinus(), bundleDuration)

        // подключаем фрагмент откат
        val bundleRollback = Bundle()
        bundleRollback.putInt("value", effectAdd.rollback ?: 0)
        bundleRollback.putInt("minValue", 0)
        bundleRollback.putString("them", "green")
        loadFragment(R.id.rollback, PlusAndMinus(), bundleRollback)


        // закрыть и подтвердить
        val closeBtn = view.findViewById<Button>(R.id.weapon_edit_close)
        closeBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val applyBtn = view.findViewById<Button>(R.id.weapon_edit_apply)
        applyBtn.setOnClickListener {
            var res = 1

            val frag1: Fragment = childFragmentManager.findFragmentById(R.id.property)!!
            val property =
                frag1.view?.findViewById<TextView>(R.id.main)?.text.toString()
            if (property == param?.defMain) {
                res = 0
                Toast.makeText(view.context, "Выберите параметр", Toast.LENGTH_SHORT).show()
            }

            val frag2: Fragment =
                childFragmentManager.findFragmentById(R.id.impact)!!
            val impact =
                frag2.view?.findViewById<EditText>(R.id.edit)?.text.toString()
                    .toIntOrNull()
            if (impact == null) {
                res = 0
                Toast.makeText(
                    view.context, "Значение влияния должно быть числом",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (impact == 0) {
                res = 0
                Toast.makeText(
                    view.context, "Значение влияния должно быть больше нуля",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val frag3: Fragment =
                childFragmentManager.findFragmentById(R.id.duration)!!
            var duration: Int? =
                frag3.view?.findViewById<EditText>(R.id.edit)?.text.toString()
                    .toIntOrNull()
            if (duration == 0) {
                if (!permanent) {
                    res = 0
                    Toast.makeText(
                        view.context, "Значение длительности должно быть больше нуля",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            val frag4: Fragment =
                childFragmentManager.findFragmentById(R.id.rollback)!!
            var rollback: Int? =
                frag4.view?.findViewById<EditText>(R.id.edit)?.text.toString()
                    .toIntOrNull()

            if (rollback == 0) {
                if (!permanent) {
                    res = 0
                    Toast.makeText(
                        view.context, "Значение отката должно быть больше нуля",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (!permanent) {
                if (duration == null) {
                    res = 0
                    Toast.makeText(
                        view.context, "Значение длительности должно быть числом",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (rollback == null) {
                    res = 0
                    Toast.makeText(
                        view.context, "Значение отката должно быть числом",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                duration = null
                rollback = null
            }

            if (res == 1) {// если проверка прошла успешно
                if (indexEff == -1) {//новый эффект
                        mCharacterVM.LOCaddEffectAddItem(
                            EffectAdd(
                                permanent,
                                property,
                                impact!!,
                                sign,
                                duration,
                                rollback
                            )
                        )
                } else {
                        mCharacterVM.LOCupdateEffectAddItem(
                            indexEff, EffectAdd(
                                permanent,
                                property,
                                impact!!,
                                sign,
                                duration,
                                rollback
                            )
                        )
                }
                view.findNavController().popBackStack()
            }
        }

        return view
    }

    private fun loadFragment(frCont: Int, fragment: Fragment, bundle: Bundle?) {
        if (bundle != null) {
            fragment.arguments = bundle
        }
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(frCont, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
