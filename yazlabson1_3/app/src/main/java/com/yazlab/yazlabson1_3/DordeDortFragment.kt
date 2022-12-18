package com.yazlab.yazlabson1_3
import kotlin.random.Random
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_dorde_dort.*
import kotlinx.android.synthetic.main.fragment_dorde_dort.dortButton2
import kotlinx.android.synthetic.main.fragment_dorde_dort.textTimer
import kotlinx.android.synthetic.main.fragment_ikiye_iki.*
import java.util.*


class DordeDortFragment : Fragment() {
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
    var activeCards_array = arrayOf<Boolean>(
        false,false,false,false,false,false,false,false,
        false,false,false,false,false,false,false,false
    )
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
        return inflater.inflate(R.layout.fragment_dorde_dort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundButton3.setOnClickListener{
            if(!stopSound){
                soundButton3.setImageResource(android.R.drawable.ic_lock_silent_mode)
                mediaPlayer?.stop()
            }else{
                soundButton3.setImageResource(android.R.drawable.ic_lock_silent_mode_off)
            }
            stopSound=!stopSound

        }
        t.schedule(tt, 1000, 1000);
        arguments?.let {

            mode = DordeDortFragmentArgs.fromBundle(it).mode

        }
        if (mode == 1) {
            textScoreSec3.text = ""
            textScoreSec4.text = ""
        }
        if (mode==2){
            sayac =60
        }
        dortButton1.setOnClickListener {
            if (activeCards_array[0]) {
                onClick1(view)
            }
        }
        dortButton2.setOnClickListener {
            if (activeCards_array[1]) {
                onClick2(view)
            }
        }
        dortButton3.setOnClickListener {
            if (activeCards_array[2]) {
                onClick3(view)
            }
        }
        dortButton4.setOnClickListener {
            if (activeCards_array[3]) {
                onClick4(view)
            }
        }
        dortButton5.setOnClickListener {
            if (activeCards_array[4]) {
                onClick5(view)
            }
        }
        dortButton6.setOnClickListener {
            if (activeCards_array[5]) {
                onClick6(view)
            }
        }
        dortButton7.setOnClickListener {
            if (activeCards_array[6]) {
                onClick7(view)
            }
        }
        dortButton8.setOnClickListener {
            if (activeCards_array[7]) {
                onClick8(view)
            }
        }
        dortButton9.setOnClickListener {
            if (activeCards_array[8]) {
                onClick9(view)
            }
        }
        dortButton10.setOnClickListener {
            if (activeCards_array[9]) {
                onClick10(view)
            }
        }
        dortButton11.setOnClickListener {
            if (activeCards_array[10]) {
                onClick11(view)
            }
        }
        dortButton12.setOnClickListener {
            if (activeCards_array[11]) {
                onClick12(view)
            }
        }
        dortButton13.setOnClickListener {
            if (activeCards_array[12]) {
                onClick13(view)
            }
        }
        dortButton14.setOnClickListener {
            if (activeCards_array[13]) {
                onClick14(view)
            }
        }
        dortButton15.setOnClickListener {
            if (activeCards_array[14]) {
                onClick15(view)
            }
        }
        dortButton16.setOnClickListener {
            if (activeCards_array[15]) {
                onClick16(view)
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
        dortButton1.setImageBitmap(image_array[0] as Bitmap)
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
        dortButton3.setImageBitmap(image_array[2] as Bitmap)
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
        dortButton4.setImageBitmap(image_array[3] as Bitmap)
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

    fun onClick5(v: View?) {
        println("tıkla4")
        dortButton5.setImageBitmap(image_array[4] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 5) {
                secActiveCard = 5
                if (selectedId == id_array[4]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[4]
                isActive = true
                firstActiveCard = 5
            }
    }

    fun onClick6(v: View?) {
        println("tıkla4")
        dortButton6.setImageBitmap(image_array[5] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 6) {
                secActiveCard = 6
                if (selectedId == id_array[5]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[5]
                isActive = true
                firstActiveCard = 6
            }
    }

    fun onClick7(v: View?) {
        println("tıkla4")
        dortButton7.setImageBitmap(image_array[6] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 7) {
                secActiveCard = 7
                if (selectedId == id_array[6]) {
                    findCorrect(view)
                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[6]
                isActive = true
                firstActiveCard = 7
            }
    }

    fun onClick8(v: View?) {
        println("tıkla4")
        dortButton8.setImageBitmap(image_array[7] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 8) {
                secActiveCard = 8
                if (selectedId == id_array[7]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[7]
                isActive = true
                firstActiveCard = 8
            }
    }

    fun onClick9(v: View?) {
        println("tıkla4")
        dortButton9.setImageBitmap(image_array[8] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 9) {
                secActiveCard = 9
                if (selectedId == id_array[8]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[8]
                isActive = true
                firstActiveCard = 9
            }
    }

    fun onClick10(v: View?) {
        println("tıkla4")
        dortButton10.setImageBitmap(image_array[9] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 10) {
                secActiveCard = 10
                if (selectedId == id_array[9]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[9]
                isActive = true
                firstActiveCard = 10
            }
    }

    fun onClick11(v: View?) {
        println("tıkla4")
        dortButton11.setImageBitmap(image_array[10] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 11) {
                secActiveCard = 11
                if (selectedId == id_array[10]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[10]
                isActive = true
                firstActiveCard = 11
            }
    }

    fun onClick12(v: View?) {
        println("tıkla4")
        dortButton12.setImageBitmap(image_array[11] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 12) {
                secActiveCard = 12
                if (selectedId == id_array[11]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[11]
                isActive = true
                firstActiveCard = 12
            }
    }

    fun onClick13(v: View?) {
        println("tıkla4")
        dortButton13.setImageBitmap(image_array[12] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 13) {
                secActiveCard = 13
                if (selectedId == id_array[12]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[12]
                isActive = true
                firstActiveCard = 13
            }
    }

    fun onClick14(v: View?) {
        println("tıkla4")
        dortButton14.setImageBitmap(image_array[13] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 14) {
                secActiveCard = 14
                if (selectedId == id_array[13]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[13]
                isActive = true
                firstActiveCard = 14
            }
    }

    fun onClick15(v: View?) {
        println("tıkla4")
        dortButton15.setImageBitmap(image_array[14] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 15) {
                secActiveCard = 15
                if (selectedId == id_array[14]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[14]
                isActive = true
                firstActiveCard = 15
            }
    }

    fun onClick16(v: View?) {
        println("tıkla4")
        dortButton16.setImageBitmap(image_array[15] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 16) {
                secActiveCard = 16
                if (selectedId == id_array[15]) {
                    findCorrect(view)
                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[15]
                isActive = true
                firstActiveCard = 16
            }
    }

    fun closeCards(v: View?) {
        if (firstActiveCard == 1 || secActiveCard == 1) {
            dortButton1.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 2 || secActiveCard == 2) {
            dortButton2.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 3 || secActiveCard == 3) {
            dortButton3.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 4 || secActiveCard == 4) {
            dortButton4.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 5 || secActiveCard == 5) {
            dortButton5.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 6 || secActiveCard == 6) {
            dortButton6.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 7 || secActiveCard == 7) {
            dortButton7.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 8 || secActiveCard == 8) {
            dortButton8.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 9 || secActiveCard == 9) {
            dortButton9.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 10 || secActiveCard == 10) {
            dortButton10.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 11 || secActiveCard == 11) {
            dortButton11.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 12 || secActiveCard == 12) {
            dortButton12.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 13 || secActiveCard == 13) {
            dortButton13.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 14 || secActiveCard == 14) {
            dortButton14.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 15 || secActiveCard == 15) {
            dortButton15.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 16 || secActiveCard == 16) {
            dortButton16.setImageResource(R.drawable.kart_arka_yuz)
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
        if (firstActiveCard == 5 || secActiveCard == 5) {
            activeCards_array[4] = false
        }
        if (firstActiveCard == 6 || secActiveCard == 6) {
            activeCards_array[5] = false
        }
        if (firstActiveCard == 7 || secActiveCard == 7) {
            activeCards_array[6] = false
        }
        if (firstActiveCard == 8 || secActiveCard == 8) {
            activeCards_array[7] = false
        }
        if (firstActiveCard == 9 || secActiveCard == 9) {
            activeCards_array[8] = false
        }
        if (firstActiveCard == 10 || secActiveCard == 10) {
            activeCards_array[9] = false
        }
        if (firstActiveCard == 11 || secActiveCard == 11) {
            activeCards_array[10] = false
        }
        if (firstActiveCard == 12 || secActiveCard == 12) {
            activeCards_array[11] = false
        }
        if (firstActiveCard == 13 || secActiveCard == 13) {
            activeCards_array[12] = false
        }
        if (firstActiveCard == 14 || secActiveCard == 14) {
            activeCards_array[13] = false
        }
        if (firstActiveCard == 15 || secActiveCard == 15) {
            activeCards_array[14] = false
        }
        if (firstActiveCard == 16 || secActiveCard == 16) {
            activeCards_array[15] = false
        }
        if (mode == 2) {
            if (order == 1) {
                score1 = score1 + (2*score_array[firstActiveCard-1]*katsayi_array[firstActiveCard-1])
                textScoreFirst4.text = score1.toString()

            }
            if (order == 2) {
                score2 = score2 + (2*score_array[firstActiveCard-1]*katsayi_array[firstActiveCard-1])
                textScoreSec4.text = score2.toString()
            }
        } else {
            score1 = score1 + ((score_array[firstActiveCard-1]*2*katsayi_array[firstActiveCard-1])*
                    sayac/10)
            textScoreFirst4.text = score1.toString()
        }
        firstActiveCard = -1
        secActiveCard = -1
        correctCount++


        if (correctCount == 8) {
            println("kazandın")
            Toast.makeText(activity,"Kazandınız !!", Toast.LENGTH_SHORT).show()
            stopTimer = true
            mediaPlayer = MediaPlayer.create(activity, R.raw.win)
            mediaPlayer?.start()
            /* Handler().postDelayed({
                 stopTimer = false
             }, 2000)*/
        } else {
            mediaPlayer = MediaPlayer.create(activity, R.raw.correct)
            mediaPlayer?.start()
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
                textScoreFirst4.text = score1.toString()

            }
            if (order == 2) {
                if(katsayi_array[firstActiveCard-1]==katsayi_array[secActiveCard-1]){
                    score2 = score2 - (score_array[firstActiveCard-1]+score_array[secActiveCard-1])/katsayi_array[firstActiveCard-1]
                }else{
                    score2 = score2 - ((((score_array[firstActiveCard-1]+score_array[secActiveCard-1])/2)*
                            katsayi_array[firstActiveCard-1]*katsayi_array[secActiveCard-1]))
                }
                textScoreSec4.text = score2.toString()
            }
        } else {
            if(katsayi_array[firstActiveCard-1]==katsayi_array[secActiveCard-1]){
                score1 = score1 - (((score_array[firstActiveCard-1]+score_array[secActiveCard-1])
                        /katsayi_array[firstActiveCard-1])*((45-sayac)/10))
            }else{
                score1 = score1 - ((((score_array[firstActiveCard-1]+score_array[secActiveCard-1])/2)*
                        katsayi_array[firstActiveCard-1]*katsayi_array[secActiveCard-1])*((45-sayac)/10))
            }

            textScoreFirst4.text = score1.toString()
        }
        if (order == 1) {
            order = 2
        } else {
            order = 1
        }
        val tempActiveVards = activeCards_array
        activeCards_array = arrayOf<Boolean>(
            false,false,false,false,false,false,false,false,
            false,false,false,false,false,false,false,false
        )
        Handler().postDelayed({
            closeCards(view)
            activeCards_array = tempActiveVards
            sayac++
        }, 1000)
    }
    private fun stringToBitmap(encodedString: String): Bitmap {
        val imageBytes = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
    fun createShufledArray(){
        temp_array = raw_array.toMutableList()
        var shufled = temp_array.shuffled()
        for(i in 0..7){
            final_array.add(shufled[i])
            final_array.add(shufled[i])
        }
        var tempArray = final_array.toMutableList()
        var finalList = tempArray.shuffled()
        for(i in 0..15){
            image_array.add(finalList[i]["image"]!!)
            id_array.add(finalList[i]["id"]!! as String)
            score_array.add(finalList[i]["score"]!! as Long)
            katsayi_array.add(finalList[i]["katsayi"]!! as Int)
        }
        activeCards_array = arrayOf<Boolean>(true,true,true,true,
            true,true,true,true,true,true,true,true,true,true,true,true)
    }

}