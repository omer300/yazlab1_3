package com.yazlab.yazlabson1_3

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_fixed_select_game.*
import kotlinx.android.synthetic.main.fragment_giris_yap.*


class fixedSelectGameFragment : Fragment() {
    var mode = 0;
    var dif = 0;
    var id = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fixed_select_game, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            id = fixedSelectGameFragmentArgs.fromBundle(it).id

        }
        textWelcome.text="Hoşgeldiniz ${id} !"
         onRadioButtonClicked()
        buttonChangePass.setOnClickListener{
            val action = fixedSelectGameFragmentDirections.actionFixedSelectGameFragmentToChangePassword(id)
            Navigation.findNavController(it).navigate(action)
        }
        startgamebutton.setOnClickListener {
            if(mode==0 || dif==0){
                Toast.makeText(activity,"Seçenekleri Seçiniz !!", Toast.LENGTH_SHORT).show()

            }else{
                if(dif == 1){
                    val action = fixedSelectGameFragmentDirections.actionFixedSelectGameFragmentToIkiyeIkiFragment(mode)
                    Navigation.findNavController(it).navigate(action)
                }else
                    if(dif == 2){
                        val action = fixedSelectGameFragmentDirections.actionFixedSelectGameFragmentToDordeDortFragment(mode)
                        Navigation.findNavController(it).navigate(action)
                    }else
                        if(dif == 3){
                            val action = fixedSelectGameFragmentDirections.actionFixedSelectGameFragmentToAltiyaAltiFragment(mode)
                            Navigation.findNavController(it).navigate(action)
                        }

            }

        }
        }
    fun onRadioButtonClicked() {
        modegroup.setOnCheckedChangeListener{ group,checkedId ->
            if(checkedId == R.id.radiosingle){
                mode = 1;
            }
            if(checkedId == R.id.radiomany){
                mode = 2;
            }
        }
        difficultgroup.setOnCheckedChangeListener{ group,checkedId ->
            if(checkedId == R.id.radioButton2x2){
                dif = 1;
            }
            if(checkedId == R.id.radioButton4x4){
                dif = 2;
            }
            if(checkedId == R.id.radioButton6x6){
                dif = 3;
            }
        }

    }
}