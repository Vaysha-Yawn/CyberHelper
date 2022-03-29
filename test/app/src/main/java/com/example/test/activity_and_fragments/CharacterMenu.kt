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
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.data_base.SpecialGameData
import com.example.test.data_base.TemplateParamStr
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.DropDownList


class CharacterMenu : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_menu, container, false)

        val presentGame = mCharacterVM.gameId
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
                .navigate(R.id.action_characterMenu_to_pres_characterList, bundle)
        }

        // настраиваем выпадающий список
        val arr = ArrayList<String>()
        SpecialGameData().mapParameterToSkill.forEach { (key, _) ->
            arr.add(key)
        }
        val bundleB = Bundle()
        bundleB.putString("main", "Выберите навык")
        bundleB.putString("them", "yellow")
        bundleB.putStringArrayList("list", arr)
        bundleB.putString("goal", "characterMenu")
        val fragment = DropDownList()
        fragment.arguments = bundleB
        childFragmentManager.commit {
            replace(R.id.CharacterMenu_CheckLevel, fragment)
            addToBackStack(null)
        }

        /*}catch (e:Exception){Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()}*/

        view.findViewById<ImageButton>(R.id.CharacterMenu_Fight).setOnClickListener {
            (activity as PresentHost).openFight()
        }
        view.findViewById<ImageButton>(R.id.CharacterMenu_Choke).setOnClickListener { }
        view.findViewById<ImageButton>(R.id.CharacterMenu_Netrunning).setOnClickListener { }
        view.findViewById<ImageButton>(R.id.CharacterMenu_Face).setOnClickListener { }
        view.findViewById<Button>(R.id.CharacterMenu_UseItem).setOnClickListener { }
        view.findViewById<Button>(R.id.CharacterMenu_EndMove).setOnClickListener { }

        view.findViewById<Button>(R.id.editFightTypePrev).setOnClickListener {
            view.findNavController().navigate(R.id.action_characterMenu_to_settingFightType)
        }

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            view.findNavController().popBackStack()
        }

        return view
    }

}
