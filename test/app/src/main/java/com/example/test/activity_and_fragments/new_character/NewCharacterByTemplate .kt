package com.example.test.activity_and_fragments.new_character

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.*
import com.example.test.viewModels.NewCharacterVM
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import io.realm.RealmList
import java.lang.Exception


class NewCharacterByTemplate : Fragment() {

    private lateinit var mNewCharacterVM: NewCharacterVM
    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_character_by_template, container, false)
        mNewCharacterVM = ViewModelProvider(this)[NewCharacterVM::class.java]

        val gameId = mCharacterVM.gameId

        val tvgender = view.findViewById<Switch>(R.id.newByTemplate_EditGender)
        mNewCharacterVM.gender.observe(viewLifecycleOwner) { gender ->
            tvgender.text = gender
        }

        tvgender.setOnClickListener {
            if (tvgender.isChecked){
                mNewCharacterVM.genderSetCheck()
            }
            else{
                mNewCharacterVM.genderSetUnCheck()
            }
        }

        val tvage = view.findViewById<TextView>(R.id.newByTemplate_EditAge)
        mNewCharacterVM.age.observe(viewLifecycleOwner) { age ->
            tvage.text = age.toString()
        }

        val plus = view.findViewById<Button>(R.id.newByTemplate_plus)
        val minus = view.findViewById<Button>(R.id.newByTemplate_minus)

        plus.setOnClickListener {
            mNewCharacterVM.plusAge()
        }

        minus.setOnClickListener {
            mNewCharacterVM.minusAge()
        }

        val done = view.findViewById<Button>(R.id.newByTemplate_Done)
        done.setOnClickListener {
            val nameCharacter = view.findViewById<EditText>(R.id.newByTemplate_EditNameCharacter).text.toString()
            if (nameCharacter!="") {
                try{
                val name = this.requireArguments().getString("name", "")
                val templateCharacter = TemplateCharacter().mapCharacter[name]
                if (templateCharacter==null){
                    Toast.makeText(view.context, "Error", Toast.LENGTH_SHORT).show()
                }
                else{
                    val gender = tvgender.text.toString()
                    val age = tvage.text.toString().toInt()
                    val attributes = templateCharacter.attributes
                    attributes.forEach { gp ->
                        if (gp.title == "Базовые параметры") {
                            gp.attributes!!.listParamStr.forEach { ps ->
                                if (ps.name == "Имя персонажа") {
                                    ps.value = nameCharacter
                                }
                            }
                            gp.attributes!!.listParamNum.forEach { ps ->
                                if (ps.name == "Возраст") {
                                    ps.value = age
                                }
                            }
                            gp.attributes!!.listParamOptions.forEach { ps ->
                                if (ps.name == "Пол") {
                                    ps.value = gender
                                }
                            }
                        }
                    }
                    addCharacter(gameId, attributes)
                }

                val r = view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
                if (gameId==r){
                    view.findNavController().navigate(R.id.action_new_newCharacterByTemplate_to_newGame)
                }
                else{
                    view.findNavController().navigate(R.id.action_pres_newCharacterByTemplate_to_home2)
                }
                }catch (e: Exception){Toast.makeText(view.context, "$e", Toast.LENGTH_SHORT).show()}
            } else {
                Toast.makeText(view.context, "Введите, пожалуйста, имя", Toast.LENGTH_SHORT).show()
            }

        }

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            view.findNavController().popBackStack()
        }
        return view
    }

    private fun addCharacter(gameId: Int, attributes: RealmList<GroupParam>
    ){

        mCharacterVM.addCharacter(gameId, attributes)

        Toast.makeText (  view?.context, "Персанаж создан", LENGTH_SHORT).show()
    }

}