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
import kotlinx.android.synthetic.main.fragment_change_password.*


class ChangePasswordFragment : Fragment() {
    var id = ""
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {

            id = ChangePasswordFragmentArgs.fromBundle(it).id
            println(id)

        }


        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        correctUser.setText(id)
        val docRef = db.collection("users").document(id)
        buttonSifreyiDegistir.setOnClickListener{
            var newPass = SifreDegistirme.getText().toString()
            docRef.update("pass",newPass)
                .addOnSuccessListener { Toast.makeText(activity,"İşlem Başarılı", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { Toast.makeText(activity,"İşlem Başarısız",Toast.LENGTH_SHORT).show() }
            val action = ChangePasswordFragmentDirections.actionChangePasswordToFixedSelectGameFragment(id)
            Navigation.findNavController(it).navigate(action)
        }
    }


}