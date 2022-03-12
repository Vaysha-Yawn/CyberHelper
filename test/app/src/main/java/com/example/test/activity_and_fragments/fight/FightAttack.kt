package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.AboutBinding.bind
import com.example.test.databinding.CardModDropDownBinding
import com.example.test.databinding.FightAttackBinding
import com.example.test.helpers.ModAdapterRV
import com.example.test.helpers.ModTemplateHolder
import com.example.test.viewModels.CharacterDAO
import com.example.test.widgets.DropDownList
import com.example.test.widgets.PlusAndMinus


class FightAttack : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight_attack, container, false)

        val arg = this.arguments
        val name = arg?.getString("name", "") ?: ""
        val fightType = arg?.getString("fightType", "") ?: ""
        val dX = arg?.getInt("dX", -1) ?: -1
        val numCount = arg?.getInt("numCount", -1) ?: -1
        val wearout = arg?.getInt("wearout", -1) ?: -1

        val binding = FightAttackBinding.bind(view)

        fun bind() = with(binding) {
            title.text = fightType

            back.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }

        bind()

        return view
    }


}