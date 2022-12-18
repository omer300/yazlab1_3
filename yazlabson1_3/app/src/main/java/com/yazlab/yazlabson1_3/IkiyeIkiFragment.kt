package com.yazlab.yazlabson1_3

import kotlin.random.Random
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_altiya_alti.*
import kotlinx.android.synthetic.main.fragment_ikiye_iki.*
import kotlinx.android.synthetic.main.fragment_ikiye_iki.textTimer
import java.util.*


class IkiyeIkiFragment : Fragment() {
    val db = Firebase.firestore
    var mode = 1;
    var order = 1
    var score1 = 0.0;
    var score2 = 0.0;
    var stopTimer = false
    var mediaPlayer: MediaPlayer? = null
    var correctCount = 0;
    var sayac = 45;
    var t: Timer = Timer()
    lateinit var temp_array : MutableList<HashMap<String,Any?>>
    var final_array = ArrayList<HashMap<String,Any?>>()
    var raw_array  = ArrayList<HashMap<String,Any?>>()
    var id_array = ArrayList<String>()
    var score_array = ArrayList<Long>()
    var image_array = ArrayList<Any>()
    var katsayi_array = ArrayList<Int>()
    var firstActiveCard = -1
    var secActiveCard = -1
    var isActive = false
    var selectedId = ""
    var activeCards_array = arrayOf<Boolean>(false,false,false,false)
    var stopSound = false


    var tt: TimerTask = object : TimerTask() {
        override fun run() {
            if (sayac > 0) {
                if (!stopTimer) {
                    sayac--
                    textTimer.text = sayac.toString()
                }
            } else
                if (sayac == 0) {
                    activeCards_array = arrayOf<Boolean>(false,false,false,false)
                    if(!stopSound){
                        mediaPlayer = MediaPlayer.create(activity, R.raw.timeup)
                        mediaPlayer?.start()
                    }
                    sayac--
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var temp = 0;
        db.collection("gryffindor")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val katsayi = 2
                    println(document.id)
                    println(document.data["puan"])
                    println(document.data["resim"])
                    val imageBytes = stringToBitmap(document.data["resim"] as String)
                    //println(imageBytes)
                    var data = hashMapOf(
                        "score" to document.data["puan"],
                        "image" to imageBytes,
                        "katsayi" to katsayi,
                        "id" to document.id
                    )
                    raw_array.add(data)
                    temp++
                    if(temp==44){
                        createShufledArray()
                    }
                }}
        db.collection("hufflepuff")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val katsayi = 1
                    println(document.id)
                    println(document.data["puan"])
                    println(document.data["resim"])
                    val imageBytes = stringToBitmap(document.data["resim"] as String)
                    //println(imageBytes)
                    var data = hashMapOf(
                        "score" to document.data["puan"],
                        "image" to imageBytes,
                        "katsayi" to katsayi,
                        "id" to document.id
                    )
                    raw_array.add(data)
                    temp++
                    if(temp==44){
                        createShufledArray()

                    }
                }}
        db.collection("ravenclaw")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val katsayi = 1
                    println(document.id)
                    println(document.data["puan"])
                    println(document.data["resim"])
                    val imageBytes = stringToBitmap(document.data["resim"] as String)
                    //println(imageBytes)
                    var data = hashMapOf(
                        "score" to document.data["puan"],
                        "image" to imageBytes,
                        "katsayi" to katsayi,
                        "id" to document.id
                    )
                    raw_array.add(data)
                    temp++
                    if(temp==44){
                        createShufledArray()

                    }
                }}
        db.collection("slytherin")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val katsayi = 2
                    println(document.id)
                    println(document.data["puan"])
                    println(document.data["resim"])
                    val imageBytes = stringToBitmap(document.data["resim"] as String)
                    //println(imageBytes)
                    var data = hashMapOf(
                        "score" to document.data["puan"],
                        "image" to imageBytes,
                        "katsayi" to katsayi,
                        "id" to document.id
                    )
                    raw_array.add(data)
                    temp++
                    if(temp==44){
                        createShufledArray()

                    }
                }}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mediaPlayer = MediaPlayer.create(context, R.raw.mainsound)
        mediaPlayer?.start()

        return inflater.inflate(R.layout.fragment_ikiye_iki, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        t.schedule(tt, 1000, 1000);
        arguments?.let {

            mode = IkiyeIkiFragmentArgs.fromBundle(it).mode

        }
        if (mode == 1) {
            textTimer.text = 45.toString()
            textScoreSec.text = ""
            textScoreSec2.text = ""
        }
        if (mode==2){
            sayac =60
            textTimer.text = 60.toString()
        }
        soundButton2.setOnClickListener{
            if(!stopSound){
                soundButton2.setImageResource(android.R.drawable.ic_lock_silent_mode)
                mediaPlayer?.stop()
            }else{
                soundButton2.setImageResource(android.R.drawable.ic_lock_silent_mode_off)
            }
            stopSound=!stopSound

        }
        ikiButton1.setOnClickListener {
            if (activeCards_array[0]) {
                onClick1(view)
            }

        }
        dortButton2.setOnClickListener {
            if (activeCards_array[1]) {
                onClick2(view)
            }

        }
        ikiButton3.setOnClickListener {
            if (activeCards_array[2]) {
                onClick3(view)
            }

        }
        ikiButton4.setOnClickListener {
            if (activeCards_array[3]) {
                onClick4(view)
            }

        }


    }

    override fun onStop() {
        super.onStop()
        tt.cancel();
        t.cancel();

    }

    fun onClick1(v: View?) {
        println("tıkla1" + isActive)
        ikiButton1.setImageBitmap(image_array[0] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 1) {
                secActiveCard = 1
                println(selectedId)
                println(" " + id_array[0])
                if (selectedId == id_array[0]) {
                    findCorrect(view)
                } else {
                    notFindCorect(view)
                }
                isActive = false

            }
        } else
            if (!isActive) {
                selectedId = id_array[0]
                isActive = true
                firstActiveCard = 1
            }
    }

    fun onClick2(v: View?) {
        println("tıkla2")
        dortButton2.setImageBitmap(image_array[1] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 2) {
                secActiveCard = 2
                if (selectedId == id_array[1]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }
                isActive = false


            }
        } else
            if (!isActive) {
                selectedId = id_array[1]
                isActive = true
                firstActiveCard = 2
            }
    }

    fun onClick3(v: View?) {
        println("tıkla3")
        ikiButton3.setImageBitmap(image_array[2] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 3) {
                secActiveCard = 3
                if (selectedId == id_array[2]) {
                    findCorrect(view)
                } else {
                    notFindCorect(view)
                }
                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[2]
                isActive = true
                firstActiveCard = 3
            }
    }

    fun onClick4(v: View?) {
        println("tıkla4")
        ikiButton4.setImageBitmap(image_array[3] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 4) {
                secActiveCard = 4
                if (selectedId == id_array[3]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[3]
                isActive = true
                firstActiveCard = 4
            }
    }

    fun closeCards(v: View?) {
        if (firstActiveCard == 1 || secActiveCard == 1) {
            ikiButton1.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 2 || secActiveCard == 2) {
            dortButton2.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 3 || secActiveCard == 3) {
            ikiButton3.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 4 || secActiveCard == 4) {
            ikiButton4.setImageResource(R.drawable.kart_arka_yuz)
        }

        firstActiveCard = -1
        secActiveCard = -1

    }

    fun findCorrect(v: View?) {
        println("eşleşti")
        if (firstActiveCard == 1 || secActiveCard == 1) {
            activeCards_array[0] = false
        }
        if (firstActiveCard == 2 || secActiveCard == 2) {
            activeCards_array[1] = false
        }
        if (firstActiveCard == 3 || secActiveCard == 3) {
            activeCards_array[2] = false
        }
        if (firstActiveCard == 4 || secActiveCard == 4) {
            activeCards_array[3] = false
        }
        if (mode == 2) {
            if (order == 1) {
                score1 = score1 + (2*score_array[firstActiveCard-1]*katsayi_array[firstActiveCard-1])
                textScoreFirst.text = score1.toString()

            }
            if (order == 2) {
                score2 = score2 + (2*score_array[firstActiveCard-1]*katsayi_array[firstActiveCard-1])
                textScoreSec.text = score2.toString()
            }
        } else {
            score1 = score1 + ((score_array[firstActiveCard-1]*2*katsayi_array[firstActiveCard-1])*
                    sayac/10)
            textScoreFirst.text = score1.toString()
        }

        firstActiveCard = -1
        secActiveCard = -1
        correctCount++


        if (correctCount == 2) {
            println("kazandın")
            Toast.makeText(activity,"Kazandınız !!",Toast.LENGTH_SHORT).show()
            stopTimer = true
            mediaPlayer = MediaPlayer.create(activity, R.raw.win)
            if(!stopSound){
                mediaPlayer?.start()
            }


            /* Handler().postDelayed({
                 stopTimer = false
             }, 2000)*/
        } else {
            mediaPlayer = MediaPlayer.create(activity, R.raw.correct)
            if(!stopSound){
            mediaPlayer?.start()}
        }


    }

    fun notFindCorect(v: View?) {
        println("eşleşmedi")

        if (mode == 2) {
            if (order == 1) {
                if(katsayi_array[firstActiveCard-1]==katsayi_array[secActiveCard-1]){
                    score1 = score1 - (score_array[firstActiveCard-1]+score_array[secActiveCard-1])/katsayi_array[firstActiveCard-1]
                }else{
                    score1 = score1 - ((((score_array[firstActiveCard-1]+score_array[secActiveCard-1])/2)*
                            katsayi_array[firstActiveCard-1]*katsayi_array[secActiveCard-1]))
                }
                textScoreFirst.text = score1.toString()

            }
            if (order == 2) {
                if(katsayi_array[firstActiveCard-1]==katsayi_array[secActiveCard-1]){
                    score2 = score2 - (score_array[firstActiveCard-1]+score_array[secActiveCard-1])/katsayi_array[firstActiveCard-1]
                }else{
                    score2 = score2 - ((((score_array[firstActiveCard-1]+score_array[secActiveCard-1])/2)*
                            katsayi_array[firstActiveCard-1]*katsayi_array[secActiveCard-1]))
                }
                textScoreSec.text = score2.toString()
            }
        } else {
            if(katsayi_array[firstActiveCard-1]==katsayi_array[secActiveCard-1]){
                score1 = score1 - (((score_array[firstActiveCard-1]+score_array[secActiveCard-1])
                        /katsayi_array[firstActiveCard-1])*((45-sayac)/10))
            }else{
                score1 = score1 - ((((score_array[firstActiveCard-1]+score_array[secActiveCard-1])/2)*
                        katsayi_array[firstActiveCard-1]*katsayi_array[secActiveCard-1])*((45-sayac)/10))
            }

            textScoreFirst.text = score1.toString()
        }
        if (order == 1) {
            order = 2
        } else {
            order = 1
        }
        val tempActiveVards = activeCards_array
        activeCards_array = arrayOf<Boolean>(false,false,false,false)
        Handler().postDelayed({
            closeCards(view)
            activeCards_array = tempActiveVards
            sayac++
        }, 1000)
    }
    private fun stringToBitmap(encodedString: String): Bitmap {
        val imageBytes = decode(encodedString, DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun createShufledArray(){
        temp_array = raw_array.toMutableList()
        var shufled = temp_array.shuffled()
        for(i in 0..1){
            final_array.add(shufled[i])
            final_array.add(shufled[i])
        }
        var tempArray = final_array.toMutableList()
        var finalList = tempArray.shuffled()
        for(i in 0..3){
            image_array.add(finalList[i]["image"]!!)
            id_array.add(finalList[i]["id"]!! as String)
            score_array.add(finalList[i]["score"]!! as Long)
            katsayi_array.add(finalList[i]["katsayi"]!! as Int)
            println("${finalList[i]["image"]!!} ${finalList[i]["id"]!! as String} ${finalList[i]["score"]!! as Long} ${finalList[i]["katsayi"]!! as Int}")

        }
        activeCards_array = arrayOf<Boolean>(true,true,true,true)
    }
}


