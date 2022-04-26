package com.example.test.activity_and_fragments.character_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.activity_and_fragments.*
import com.example.test.data_base.TemplateParamStr
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.views.HeaderView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception


class CharacterList : Fragment(), HeaderView.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_list, container, false)
        try {
            val gameId = mCharacterVM.gameId
            val characterId = this.requireArguments().getInt("characterId", 0)
            mCharacterVM.initCharacterId(characterId)

            val tvName = view.findViewById<TextView>(R.id.CharacterList_NameCharacter)
            mCharacterVM.characterList.observe(viewLifecycleOwner) { list ->
                list.forEach {
                    if (it.id == characterId) {
                        tvName.text =
                            TemplateParamStr().readParamStr(
                                it,
                                "Базовые параметры",
                                "Имя персонажа"
                            )
                    }
                }
            }

            val menu = view.findViewById<BottomNavigationView>(R.id.CharacterList_Menu)
            childFragmentManager.commit {
                replace(R.id.Character_list_fragment_container, CharacterList_Base())
                addToBackStack(null)
            }

            menu.setOnItemSelectedListener setOnNavigationItemSelectedListener@{
                when (it.itemId) {
                    R.id.Base -> {
                        childFragmentManager.commit {
                            replace(R.id.Character_list_fragment_container, CharacterList_Base())
                            addToBackStack(null)
                        }
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.Skill_and_HP -> {
                        childFragmentManager.commit {
                            replace(R.id.Character_list_fragment_container, CharacterList_Skill())
                            addToBackStack(null)
                        }
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.Armor_and_weapon -> {
                        childFragmentManager.commit {
                            replace(R.id.Character_list_fragment_container, CharacterList_Armor())
                            addToBackStack(null)
                        }
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.Stuff -> {
                        childFragmentManager.commit {
                            replace(R.id.Character_list_fragment_container, CharacterList_Stuff())
                            addToBackStack(null)
                        }
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.About -> {
                        childFragmentManager.commit {
                            replace(R.id.Character_list_fragment_container, CharacterList_About())
                            addToBackStack(null)
                        }
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }

            view.findViewById<Button>(R.id.CharacterMenu_Delete).setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id", characterId.toString())
                bundle.putString("param", "character")
                bundle.putString("key", "персонажа")
                val r =
                    view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
                if (gameId == r) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_delete2, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_pres_characterList_to_pres_delete, bundle)
                }
            }

            view.findViewById<HeaderView>(R.id.headerView).setBack(true, this, requireActivity(), viewLifecycleOwner)

        } catch (e: Exception) {
            Toast.makeText(view?.context, "$e", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}
