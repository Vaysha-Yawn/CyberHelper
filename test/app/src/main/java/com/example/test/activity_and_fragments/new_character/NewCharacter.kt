package com.example.test.activity_and_fragments.new_character

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
import com.example.test.data_base.ParamNum
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import io.realm.RealmList


class NewCharacter : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_character, container, false)

        val gameId = mCharacterVM.gameId

        val textHP = view.findViewById<TextView>(R.id.newCharacter_HealthPoint)
        val textSkillLevel = view.findViewById<TextView>(R.id.newCharacter_SkillLevel)
        val textArmor = view.findViewById<TextView>(R.id.newCharacter_Armor)
        val textWeapon = view.findViewById<TextView>(R.id.newCharacter_Weapon)

        val maxHP = 100
        val maxSkillLevel = 10
        val maxArmor = 20
        val maxWeapon = 10

        textHP.append(maxHP.toString())
        textSkillLevel.append(maxSkillLevel.toString())
        textArmor.append(maxArmor.toString())
        textWeapon.append(maxWeapon.toString())

        val done = view.findViewById<Button>(R.id.NewCharacter_Done)
        done.setOnClickListener {
            /*
            val nameCharacter = view.findViewById<EditText>(R.id.NewCharacter_EditNameCharacter).text.toString()
            val healthPoint = view.findViewById<EditText>(R.id.NewCharacter_EditHealthPoint).text.toString().toInt()
            val skillLevel = view.findViewById<EditText>(R.id.NewCharacter_EditSkillLevel).text.toString().toInt()
            val armor = view.findViewById<EditText>(R.id.NewCharacter_EditArmor).text.toString().toInt()
            val weapon = view.findViewById<EditText>(R.id.NewCharacter_EditWeapon).text.toString().toInt()

            val arrayResult = arrayOf(healthPoint, skillLevel, armor, weapon)
            val arrayParamsNum = arrayOf(mDefaultDB.mapParamNum["HP"]!!, mDefaultDB.mapParamNum["level"]!!, mDefaultDB.mapParamNum["armor"]!!,mDefaultDB.mapParamNum["weapon"]!!)

            val bol = checkParams(arrayResult, arrayParamsNum)
            if (bol!=0) {
                /*
                addCharacter(gameId, nameCharacter, intel, ref, dex, tech,
                    cool, will, luck, move, body, emp,
                    armorHead, armorBody,
                    Perception, Tracking, Education, Local_Expert,
                    Interface, Marksmanship,Driving, Evasion, Athletics,
                    Stealth, Brawling, Melee_Weapon, Basic_Tech,
                    Cybertech, First_Aid, Bribery, Interrogation, Persuasion,
                    Concentration, Conversation, Human_Perception, Play_Instrument,
                    role, gender, importance, age,
                    weapons, programs, cyberware, gear)
                 */

                val r = view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
                if (gameId==r){
                    view.findNavController().navigate(R.id.action_new_newCharacter_to_newGame)
                }
                else{
                    view.findNavController().navigate(R.id.action_pres_newCharacter_to_home2)
                }
            }

             */
        }

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            view.findNavController().popBackStack()
        }
        return view
    }
/*
    private fun checkParams(arrayResult: Array<Int>, arrayParams: Array<ParamNum>):Int {
        var res = 1
        for ((index, value) in arrayParams.withIndex()){
            val k = checkAndToast( arrayResult[index], value )
            res *= k
        }
        if (res==1){
            Toast.makeText(
                view?.context, "Успешно",
                Toast.LENGTH_SHORT
            ).show()
        }
        return res
    }

    private fun checkAndToast(result: Int, paramNum: ParamNum): Int {
        return if ((result > 0) && (result <= paramNum.maxValue.toInt())) {
            1
        } else if (result < 0) {
            Toast.makeText(
                view?.context, " ${paramNum.title} ${paramNum.toast} быть больше нуля",
                Toast.LENGTH_SHORT
            ).show()
            0
        } else {
            Toast.makeText(
                view?.context, " ${paramNum.title} ${paramNum.toast} не превышать ${paramNum.maxValue}",
                Toast.LENGTH_SHORT
            ).show()
            0
        }
    }
    private fun addCharacter(gameId: Int, name: String, intel: Int, ref: Int, dex: Int, tech: Int, cool: Int, will: Int, luck: Int, move: Int, body: Int, emp: Int,
                             armorHead: Int, armorBody: Int,
                             Perception: Int = 0,
                             Tracking: Int = 0,
                             Education: Int = 0,
                             Local_Expert: Int = 0,
                             Interface: Int = 0,
                             Marksmanship: Int = 0,
                             Driving: Int = 0,
                             Evasion: Int = 0,
                             Athletics: Int = 0,
                             Stealth: Int = 0,
                             Brawling: Int = 0,
                             Melee_Weapon: Int = 0,
                             Basic_Tech: Int = 0,
                             Cybertech: Int = 0,
                             First_Aid: Int = 0,
                             Bribery: Int = 0,
                             Interrogation: Int = 0,
                             Persuasion: Int = 0,
                             Concentration: Int = 0,
                             Conversation: Int = 0,
                             Human_Perception: Int = 0,
                             Play_Instrument: Int = 0,
                             role: String, gender: String, importance: String, age: Int,
                             weapons: RealmList<Weapon>,
                             programs: RealmList<Program>,
                             cyberware: RealmList<Item>,
                             gear: RealmList<Item>
    ){

        mCharacterVM.addCharacter(gameId, name, intel, ref, dex, tech, cool, will, luck, move, body,emp,
            armorHead, armorBody, weapons,
            Perception, Tracking, Education, Local_Expert, Interface, Marksmanship, Driving, Evasion, Athletics, Stealth, Brawling, Melee_Weapon, Basic_Tech,
            Cybertech, First_Aid, Bribery, Interrogation, Persuasion, Concentration, Conversation, Human_Perception, Play_Instrument,
            role, gender, importance, age, programs, cyberware, gear )

        Toast.makeText (  view?.context, "Персанаж создан", LENGTH_SHORT).show()
    }

 */
}