package com.yazlab.yazlabson1_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.model.Document
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_giris_yap.*

class GirisYapFragment : Fragment() {
    val db = Firebase.firestore
    var check = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_giris_yap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginbutton.setOnClickListener {
            if(girisKullaniciAdi.text.toString()!=""){
                val docRef = db.collection("users").document(girisKullaniciAdi.text.toString())
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            println(document.data?.get("pass"))
                            if(document.data?.get("pass")==girisSifre.text.toString()){
                                val action = GirisYapFragmentDirections.actionGirisYapFragmentToFixedSelectGameFragment(girisKullaniciAdi.text.toString())
                                Navigation.findNavController(it).navigate(action)
                            }else{
                                Toast.makeText(activity,"Yanlış Şifre !!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(activity,"Kullanıcı Bulunamadı !!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(activity,"Kullanıcı Giriniz !!", Toast.LENGTH_SHORT).show()
            }





        }

    }



}