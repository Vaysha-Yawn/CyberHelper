package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.data_base.DTemplateParamOptions
import com.example.test.data_base.EffectAdd
import com.example.test.databinding.EditEffectAddBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.GameSystemDAO
import com.example.test.views.DropDownView
import com.example.test.views.HeaderView

class EditEffectAdd : Fragment(), HeaderView.HeaderBack, DropDownAdapterRV.TemplateHolder.WhenValueTo {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mGameSystemDAO: GameSystemDAO by activityViewModels()

    private var groupTitle = ""
    private var indexItem = -1
    private var indexEff = -1
    private lateinit var effectAdd: EffectAdd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Требуемые аргументы
        val characterId = mCharacterVM.characterId
        val args = this.arguments
        groupTitle = args?.getString("groupTitle", "") ?: ""
        indexItem = args?.getInt("indexItem", -1) ?: -1
        indexEff = args?.getInt("indexEff", -1) ?: -1

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

        with(binding){

            if (Effpermanent) {
                Effpermanent = true
                permanent.isChecked = true
                permanent.text = "Постоянный"
                linPermanentFalse.visibility = View.GONE
            } else {
                permanent.isChecked = false
                permanent.text = "Временный"
                linPermanentFalse.visibility = View.VISIBLE
            }

            permanent.setOnClickListener {
                if(permanent.isChecked){
                    Effpermanent = false
                    permanent.isChecked = false
                    permanent.text = "Временный"
                    linPermanentFalse.visibility = View.VISIBLE
                }else{
                    Effpermanent = true
                    permanent.isChecked = true
                    permanent.text = "Постоянный"
                    linPermanentFalse.visibility = View.GONE
                }
            }
            // подключаем выбор параметра
            property.setDDArrayAndListener(getListParamNumNames(), this@EditEffectAdd, null)
        }

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
        //loadFragment(R.id.impact, PlusAndMinus(), bundleImpact)


        // подключаем фрагмент длительность
        val bundleDuration = Bundle()
        bundleDuration.putInt("value", effectAdd.duration ?: 0)
        bundleDuration.putInt("minValue", 0)
        bundleDuration.putString("them", "green")
        //loadFragment(R.id.duration, PlusAndMinus(), bundleDuration)

        // подключаем фрагмент откат
        val bundleRollback = Bundle()
        bundleRollback.putInt("value", effectAdd.rollback ?: 0)
        bundleRollback.putInt("minValue", 0)
        bundleRollback.putString("them", "green")
        //loadFragment(R.id.rollback, PlusAndMinus(), bundleRollback)


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
            if (property == "Выберите характеристику") {
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
                if (!Effpermanent) {
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
                if (!Effpermanent) {
                    res = 0
                    Toast.makeText(
                        view.context, "Значение отката должно быть больше нуля",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (!Effpermanent) {
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
                                Effpermanent,
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
                                Effpermanent,
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

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    override fun whenValueTo(position: Int) {
        TODO("Not yet implemented")
    }

    private fun getListParamNumNames():MutableList<String>{
        val list = mutableListOf<String>()
        for (i in mGameSystemDAO.currentGameSystem!!.templateParamNum){
            list.add(i.name)
        }
        return  list
    }
}
