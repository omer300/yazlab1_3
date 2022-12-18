package com.yazlab.yazlabson1_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_kayit_ol.*

class KayitOlFragment : Fragment() {
    val db = Firebase.firestore
    val users = db.collection("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kayit_ol, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonKayitOl.setOnClickListener {

            val data = hashMapOf(
                "pass" to kayitSifre.text.toString(),
            )
            users.document(kayitKullaniciAdi.text.toString()).set(data)

            val action = KayitOlFragmentDirections.actionKayitOlFragmentToGirisYapFragment()
            Navigation.findNavController(it).navigate(action)
            Toast.makeText(activity,"Kayıt Başarılı !!", Toast.LENGTH_SHORT).show()




        }

    }
}