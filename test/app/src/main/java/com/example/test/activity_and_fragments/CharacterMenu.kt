package com.example.test.activity_and_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.TemplateParamStr
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.HeaderView


class CharacterMenu : Fragment(), HeaderView.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()
    private val arr = mutableListOf<String>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_menu, container, false)

        val characterId = this.requireArguments().getInt("characterId", 0)
        mCharacterVM.initCharacterId(characterId)

        val tvName = view.findViewById<TextView>(R.id.CharacterMenu_NameCharacter)

        mCharacterVM.characterList.observe(viewLifecycleOwner) { list ->
            list.forEach {
                if (it.id == characterId) {
                    tvName.text =
                        TemplateParamStr().readParamStr(it, "Базовые параметры", "Имя персонажа")
                }
            }
        }

        view.findViewById<Button>(R.id.CharacterMenu_OpenCharacterList).setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("characterId", characterId)
            view.findNavController()
                .navigate(R.id.action_characterMenu_to_characterList, bundle)
        }

        view.findViewById<ImageButton>(R.id.CharacterMenu_UseItem).setOnClickListener { }
        view.findViewById<ImageButton>(R.id.CharacterMenu_EndMove).setOnClickListener { }

        view.findViewById<HeaderView>(R.id.header).setBack(true, this, requireActivity(), viewLifecycleOwner)

        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}
