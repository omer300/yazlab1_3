package com.yazlab.yazlabson1_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_ana_ekran.*


class AnaEkranFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ana_ekran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonGiriseGit.setOnClickListener {

            val action = AnaEkranFragmentDirections.actionAnaEkranFragmentToGirisYapFragment()
            Navigation.findNavController(it).navigate(action)
        }

        buttonKayitaGit.setOnClickListener {

            val action = AnaEkranFragmentDirections.actionAnaEkranFragmentToKayitOlFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }
}