package com.yazlab.yazlabson1_3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_altiya_alti.*
import kotlinx.android.synthetic.main.fragment_altiya_alti.textTimer
import kotlinx.android.synthetic.main.fragment_ikiye_iki.*

import java.util.*

class AltiyaAltiFragment : Fragment() {
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
    var g_raw_array  = ArrayList<HashMap<String,Any?>>()
    var h_raw_array  = ArrayList<HashMap<String,Any?>>()
    var r_raw_array  = ArrayList<HashMap<String,Any?>>()
    var s_raw_array  = ArrayList<HashMap<String,Any?>>()
    var id_array = ArrayList<String>()
    var score_array = ArrayList<Long>()
    var image_array = ArrayList<Any>()
    var katsayi_array = ArrayList<Int>()
    var firstActiveCard = -1
    var secActiveCard = -1
    var isActive = false
    var selectedId = ""
    var activeCards_array = arrayOf<Boolean>(
        false,false,false,false,
        false,false,false,false,
        false,false,false,false,
        false,false,false,false,
        false,false,false,false,
        false,false,false,false,
        false,false,false,false,
        false,false,false,false,
        false,false,false,false,
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
                    activeCards_array = arrayOf<Boolean>(
                        false,false,false,false,
                        false,false,false,false,
                        false,false,false,false,
                        false,false,false,false,
                        false,false,false,false,
                        false,false,false,false,
                        false,false,false,false,
                        false,false,false,false,
                        false,false,false,false,
                    )
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
                    g_raw_array.add(data)
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
                    h_raw_array.add(data)
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
                    r_raw_array.add(data)
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
                    s_raw_array.add(data)
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
        return inflater.inflate(R.layout.fragment_altiya_alti, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        t.schedule(tt, 1000, 1000);
        arguments?.let {

            mode = AltiyaAltiFragmentArgs.fromBundle(it).mode

        }
        if (mode == 1) {
            textScoreSec5.text = ""
            textScoreSec6.text = ""
            textTimer.text = 45.toString()
        }
        if (mode==2){
            textTimer.text = 60.toString()
            sayac =60
        }
        soundButton4.setOnClickListener{
            if(!stopSound){
                soundButton4.setImageResource(android.R.drawable.ic_lock_silent_mode)
                mediaPlayer?.stop()
            }else{
                soundButton4.setImageResource(android.R.drawable.ic_lock_silent_mode_off)
            }
            stopSound=!stopSound

        }
        altiButton1.setOnClickListener {
            if (activeCards_array[0]) {
                onClick1(view)
            }
        }
        altiButton2.setOnClickListener {
            if (activeCards_array[1]) {
                onClick2(view)
            }
        }
        altiButton3.setOnClickListener {
            if (activeCards_array[2]) {
                onClick3(view)
            }
        }
        altiButton4.setOnClickListener {
            if (activeCards_array[3]) {
                onClick4(view)
            }
        }
        altiButton5.setOnClickListener {
            if (activeCards_array[4]) {
                onClick5(view)
            }
        }
        altiButton6.setOnClickListener {
            if (activeCards_array[5]) {
                onClick6(view)
            }
        }
        altiButton7.setOnClickListener {
            if (activeCards_array[6]) {
                onClick7(view)
            }
        }
        altiButton8.setOnClickListener {
            if (activeCards_array[7]) {
                onClick8(view)
            }
        }
        altiButton9.setOnClickListener {
            if (activeCards_array[8]) {
                onClick9(view)
            }
        }
        altiButton10.setOnClickListener {
            if (activeCards_array[9]) {
                onClick10(view)
            }
        }
        altiButton11.setOnClickListener {
            if (activeCards_array[10]) {
                onClick11(view)
            }
        }
        altiButton12.setOnClickListener {
            if (activeCards_array[11]) {
                onClick12(view)
            }
        }
        altiButton13.setOnClickListener {
            if (activeCards_array[12]) {
                onClick13(view)
            }
        }
        altiButton14.setOnClickListener {
            if (activeCards_array[13]) {
                onClick14(view)
            }
        }
        altiButton15.setOnClickListener {
            if (activeCards_array[14]) {
                onClick15(view)
            }
        }
        altiButton16.setOnClickListener {
            if (activeCards_array[15]) {
                onClick16(view)
            }
        }
        altiButton17.setOnClickListener {
            if (activeCards_array[16]) {
                onClick17(view)
            }
        }
        altiButton18.setOnClickListener {
            if (activeCards_array[17]) {
                onClick18(view)
            }
        }
        altiButton19.setOnClickListener {
            if (activeCards_array[18]) {
                onClick19(view)
            }
        }
        altiButton20.setOnClickListener {
            if (activeCards_array[19]) {
                onClick20(view)
            }
        }
        altiButton21.setOnClickListener {
            if (activeCards_array[20]) {
                onClick21(view)
            }
        }
        altiButton22.setOnClickListener {
            if (activeCards_array[21]) {
                onClick22(view)
            }
        }
        altiButton23.setOnClickListener {
            if (activeCards_array[22]) {
                onClick23(view)
            }
        }
        altiButton24.setOnClickListener {
            if (activeCards_array[23]) {
                onClick24(view)
            }
        }
        altiButton25.setOnClickListener {
            if (activeCards_array[24]) {
                onClick25(view)
            }
        }
        altiButton26.setOnClickListener {
            if (activeCards_array[25]) {
                onClick26(view)
            }
        }
        altiButton27.setOnClickListener {
            if (activeCards_array[26]) {
                onClick27(view)
            }
        }
        altiButton28.setOnClickListener {
            if (activeCards_array[27]) {
                onClick28(view)
            }
        }
        altiButton29.setOnClickListener {
            if (activeCards_array[28]) {
                onClick29(view)
            }
        }
        altiButton30.setOnClickListener {
            if (activeCards_array[29]) {
                onClick30(view)
            }
        }
        altiButton31.setOnClickListener {
            if (activeCards_array[30]) {
                onClick31(view)
            }
        }
        altiButton32.setOnClickListener {
            if (activeCards_array[31]) {
                onClick32(view)
            }
        }
        altiButton33.setOnClickListener {
            if (activeCards_array[32]) {
                onClick33(view)
            }
        }
        altiButton34.setOnClickListener {
            if (activeCards_array[33]) {
                onClick34(view)
            }
        }
        altiButton35.setOnClickListener {
            if (activeCards_array[34]) {
                onClick35(view)
            }
        }
        altiButton36.setOnClickListener {
            if (activeCards_array[35]) {
                onClick36(view)
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
        altiButton1.setImageBitmap(image_array[0] as Bitmap)
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
        altiButton2.setImageBitmap(image_array[1] as Bitmap)
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
        altiButton3.setImageBitmap(image_array[2] as Bitmap)
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
        altiButton4.setImageBitmap(image_array[3] as Bitmap)
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
        altiButton5.setImageBitmap(image_array[4] as Bitmap)
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
        altiButton6.setImageBitmap(image_array[5] as Bitmap)
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
        altiButton7.setImageBitmap(image_array[6] as Bitmap)
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
        altiButton8.setImageBitmap(image_array[7] as Bitmap)
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
        altiButton9.setImageBitmap(image_array[8] as Bitmap)
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
        altiButton10.setImageBitmap(image_array[9] as Bitmap)
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
        altiButton11.setImageBitmap(image_array[10] as Bitmap)
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
        altiButton12.setImageBitmap(image_array[11] as Bitmap)
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
        altiButton13.setImageBitmap(image_array[12] as Bitmap)
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
        altiButton14.setImageBitmap(image_array[13] as Bitmap)
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
        altiButton15.setImageBitmap(image_array[14] as Bitmap)
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
        altiButton16.setImageBitmap(image_array[15] as Bitmap)
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
    fun onClick17(v: View?) {
        println("tıkla4")
        altiButton17.setImageBitmap(image_array[16] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 17) {
                secActiveCard = 17
                if (selectedId == id_array[16]) {
                    findCorrect(view)


                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[16]
                isActive = true
                firstActiveCard = 17
            }
    }

    fun onClick18(v: View?) {
        println("tıkla4")
        altiButton18.setImageBitmap(image_array[17] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 18) {
                secActiveCard =18
                if (selectedId == id_array[17]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[17]
                isActive = true
                firstActiveCard =18
            }
    }

    fun onClick19(v: View?) {
        println("tıkla4")
        altiButton19.setImageBitmap(image_array[18] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 19) {
                secActiveCard = 19
                if (selectedId == id_array[18]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[18]
                isActive = true
                firstActiveCard = 19
            }
    }
    fun onClick20(v: View?) {
        println("tıkla1" + isActive)
        altiButton20.setImageBitmap(image_array[19] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 20) {
                secActiveCard = 20
                println(selectedId)
                println(" " + id_array[19])
                if (selectedId == id_array[19]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }
                isActive = false

            }
        } else
            if (!isActive) {
                selectedId = id_array[19]
                isActive = true
                firstActiveCard = 20
            }
    }

    fun onClick21(v: View?) {
        println("tıkla2")
        altiButton21.setImageBitmap(image_array[20] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 21) {
                secActiveCard = 21
                if (selectedId == id_array[20]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }
                isActive = false


            }
        } else
            if (!isActive) {
                selectedId = id_array[20]
                isActive = true
                firstActiveCard = 21
            }
    }
    fun onClick22(v: View?) {
        println("tıkla2")
        altiButton22.setImageBitmap(image_array[21] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 22) {
                secActiveCard = 22
                if (selectedId == id_array[21]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }
                isActive = false


            }
        } else
            if (!isActive) {
                selectedId = id_array[21]
                isActive = true
                firstActiveCard = 22
            }
    }
    fun onClick23(v: View?) {
        println("tıkla3")
        altiButton23.setImageBitmap(image_array[22] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 23) {
                secActiveCard = 23
                if (selectedId == id_array[22]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }
                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[22]
                isActive = true
                firstActiveCard = 23
            }
    }

    fun onClick24(v: View?) {
        println("tıkla4")
        altiButton24.setImageBitmap(image_array[23] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 24) {
                secActiveCard = 24
                if (selectedId == id_array[23]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[23]
                isActive = true
                firstActiveCard = 24
            }
    }

    fun onClick25(v: View?) {
        println("tıkla4")
        altiButton25.setImageBitmap(image_array[24] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 25) {
                secActiveCard = 25
                if (selectedId == id_array[24]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[24]
                isActive = true
                firstActiveCard = 25
            }
    }

    fun onClick26(v: View?) {
        println("tıkla4")
        altiButton26.setImageBitmap(image_array[25] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 26) {
                secActiveCard = 26
                if (selectedId == id_array[25]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[25]
                isActive = true
                firstActiveCard = 26
            }
    }

    fun onClick27(v: View?) {
        println("tıkla4")
        altiButton27.setImageBitmap(image_array[26] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 27) {
                secActiveCard = 27
                if (selectedId == id_array[26]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[26]
                isActive = true
                firstActiveCard = 27
            }
    }

    fun onClick28(v: View?) {
        println("tıkla4")
        altiButton28.setImageBitmap(image_array[27] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 28) {
                secActiveCard = 28
                if (selectedId == id_array[27]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[27]
                isActive = true
                firstActiveCard = 28
            }
    }

    fun onClick29(v: View?) {
        println("tıkla4")
        altiButton29.setImageBitmap(image_array[28] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 29) {
                secActiveCard = 29
                if (selectedId == id_array[28]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[28]
                isActive = true
                firstActiveCard = 29
            }
    }
    fun onClick30(v: View?) {
        println("tıkla4")
        altiButton30.setImageBitmap(image_array[29] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 30) {
                secActiveCard = 30
                if (selectedId == id_array[29]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[29]
                isActive = true
                firstActiveCard = 30
            }
    }

    fun onClick31(v: View?) {
        println("tıkla1" + isActive)
        altiButton31.setImageBitmap(image_array[30] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 31) {
                secActiveCard = 31
                if (selectedId == id_array[30]) {
                    findCorrect(view)
                } else {
                    notFindCorect(view)
                }
                isActive = false

            }
        } else
            if (!isActive) {
                selectedId = id_array[30]
                isActive = true
                firstActiveCard = 31
            }
    }

    fun onClick32(v: View?) {
        println("tıkla2")
        altiButton32.setImageBitmap(image_array[31] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 32) {
                secActiveCard = 32
                if (selectedId == id_array[31]) {
                    findCorrect(view)
                } else {
                    notFindCorect(view)
                }
                isActive = false


            }
        } else
            if (!isActive) {
                selectedId = id_array[31]
                isActive = true
                firstActiveCard =32
            }
    }

    fun onClick33(v: View?) {
        println("tıkla3")
        altiButton33.setImageBitmap(image_array[32] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 33) {
                secActiveCard = 33
                if (selectedId == id_array[32]) {
                    findCorrect(view)
                } else {
                    notFindCorect(view)
                }
                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[32]
                isActive = true
                firstActiveCard = 33
            }
    }

    fun onClick34(v: View?) {
        println("tıkla4")
        altiButton34.setImageBitmap(image_array[33] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 34) {
                secActiveCard = 34
                if (selectedId == id_array[33]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[33]
                isActive = true
                firstActiveCard = 34
            }
    }

    fun onClick35(v: View?) {
        println("tıkla4")
        altiButton35.setImageBitmap(image_array[34] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 35) {
                secActiveCard = 35
                if (selectedId == id_array[34]) {
                    findCorrect(view)

                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[34]
                isActive = true
                firstActiveCard = 35
            }
    }

    fun onClick36(v: View?) {
        println("tıkla4")
        altiButton36.setImageBitmap(image_array[35] as Bitmap)
        if (isActive) {
            if (firstActiveCard != 36) {
                secActiveCard = 36
                if (selectedId == id_array[35]) {
                    findCorrect(view)
                } else {
                    notFindCorect(view)
                }

                isActive = false
            }
        } else
            if (!isActive) {
                selectedId = id_array[35]
                isActive = true
                firstActiveCard = 36
            }
    }
    fun closeCards(v: View?) {
        if (firstActiveCard == 1 || secActiveCard == 1) {
            altiButton1.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 2 || secActiveCard == 2) {
            altiButton2.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 3 || secActiveCard == 3) {
            altiButton3.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 4 || secActiveCard == 4) {
            altiButton4.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 5 || secActiveCard == 5) {
            altiButton5.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 6 || secActiveCard == 6) {
            altiButton6.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 7 || secActiveCard == 7) {
            altiButton7.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 8 || secActiveCard == 8) {
            altiButton8.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 9 || secActiveCard == 9) {
            altiButton9.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 10 || secActiveCard == 10) {
            altiButton10.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 11 || secActiveCard == 11) {
            altiButton11.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 12 || secActiveCard == 12) {
            altiButton12.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 13 || secActiveCard == 13) {
            altiButton13.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 14 || secActiveCard == 14) {
            altiButton14.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 15 || secActiveCard == 15) {
            altiButton15.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 16 || secActiveCard == 16) {
            altiButton16.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 17 || secActiveCard == 17) {
            altiButton17.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 18 || secActiveCard == 18) {
            altiButton18.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 19 || secActiveCard == 19) {
            altiButton19.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 20 || secActiveCard == 20) {
            altiButton20.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 21 || secActiveCard == 21) {
            altiButton21.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 22 || secActiveCard == 22) {
            altiButton22.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 23 || secActiveCard == 23) {
            altiButton23.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 24 || secActiveCard == 24) {
            altiButton24.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 25 || secActiveCard == 25) {
            altiButton25.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 26 || secActiveCard == 26) {
            altiButton26.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 27 || secActiveCard == 27) {
            altiButton27.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 28 || secActiveCard == 28) {
            altiButton28.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 29 || secActiveCard == 29) {
            altiButton29.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 30 || secActiveCard == 30) {
            altiButton30.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 31 || secActiveCard == 31) {
            altiButton31.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 32 || secActiveCard == 32) {
            altiButton32.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 33 || secActiveCard == 33) {
            altiButton33.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 34 || secActiveCard == 34) {
            altiButton34.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 35 || secActiveCard == 35) {
            altiButton35.setImageResource(R.drawable.kart_arka_yuz)
        }
        if (firstActiveCard == 36 || secActiveCard == 36) {
            altiButton36.setImageResource(R.drawable.kart_arka_yuz)
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
        if (firstActiveCard == 17 || secActiveCard == 17) {
            activeCards_array[16] = false
        }
        if (firstActiveCard == 18 || secActiveCard == 18) {
            activeCards_array[17] = false
        }
        if (firstActiveCard == 19 || secActiveCard == 19) {
            activeCards_array[18] = false
        }
        if (firstActiveCard == 20 || secActiveCard == 20) {
            activeCards_array[19] = false
        }
        if (firstActiveCard == 21 || secActiveCard == 21) {
            activeCards_array[20] = false
        }
        if (firstActiveCard == 22 || secActiveCard == 22) {
            activeCards_array[21] = false
        }
        if (firstActiveCard == 23 || secActiveCard == 23) {
            activeCards_array[22] = false
        }
        if (firstActiveCard == 24 || secActiveCard == 24) {
            activeCards_array[23] = false
        }
        if (firstActiveCard == 25 || secActiveCard == 25) {
            activeCards_array[24] = false
        }
        if (firstActiveCard == 26 || secActiveCard == 26) {
            activeCards_array[25] = false
        }
        if (firstActiveCard == 27 || secActiveCard == 27) {
            activeCards_array[26] = false
        }
        if (firstActiveCard == 28 || secActiveCard == 28) {
            activeCards_array[27] = false
        }
        if (firstActiveCard == 29 || secActiveCard == 29) {
            activeCards_array[28] = false
        }
        if (firstActiveCard == 30 || secActiveCard == 30) {
            activeCards_array[29] = false
        }
        if (firstActiveCard == 31 || secActiveCard == 31) {
            activeCards_array[30] = false
        }
        if (firstActiveCard == 32 || secActiveCard == 32) {
            activeCards_array[31] = false
        }
        if (firstActiveCard == 33 || secActiveCard == 33) {
            activeCards_array[32] = false
        }
        if (firstActiveCard == 34 || secActiveCard == 34) {
            activeCards_array[33] = false
        }
        if (firstActiveCard == 35 || secActiveCard == 35) {
            activeCards_array[34] = false
        }
        if (firstActiveCard == 36 || secActiveCard == 36) {
            activeCards_array[35] = false
        }

        if (mode == 2) {
            if (order == 1) {
                score1 = score1 + (2*score_array[firstActiveCard-1]*katsayi_array[firstActiveCard-1])
                textScoreFirst6.text = score1.toString()

            }
            if (order == 2) {
                score2 = score2 + (2*score_array[firstActiveCard-1]*katsayi_array[firstActiveCard-1])
                textScoreSec6.text = score2.toString()
            }
        } else {
            score1 = score1 + ((score_array[firstActiveCard-1]*2*katsayi_array[firstActiveCard-1])*
                    sayac/10)
            textScoreFirst6.text = score1.toString()
        }
        firstActiveCard = -1
        secActiveCard = -1
        correctCount++


        if (correctCount == 18) {
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
                println(score1)
                textScoreFirst6.text = score1.toString()

            }
            if (order == 2) {
                if(katsayi_array[firstActiveCard-1]==katsayi_array[secActiveCard-1]){
                    score2 = score2 - (score_array[firstActiveCard-1]+score_array[secActiveCard-1])/katsayi_array[firstActiveCard-1]
                }else{
                    score2 = score2 - ((((score_array[firstActiveCard-1]+score_array[secActiveCard-1])/2)*
                            katsayi_array[firstActiveCard-1]*katsayi_array[secActiveCard-1]))
                }
                textScoreSec6.text = score2.toString()
            }
        } else {
            if(katsayi_array[firstActiveCard-1]==katsayi_array[secActiveCard-1]){
                score1 = score1 - (((score_array[firstActiveCard-1]+score_array[secActiveCard-1])
                        /katsayi_array[firstActiveCard-1])*((45-sayac)/10.0))
            }else{
                score1 = score1 - ((((score_array[firstActiveCard-1]+score_array[secActiveCard-1])/2)*
                        katsayi_array[firstActiveCard-1]*katsayi_array[secActiveCard-1])*((45-sayac)/10.0))
            }

            textScoreFirst6.text = score1.toString()
        }
        if (order == 1) {
            order = 2
        } else {
            order = 1
        }
        val tempActiveVards = activeCards_array
        activeCards_array = arrayOf<Boolean>(
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
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
        temp_array = g_raw_array.toMutableList()
        var shufled = temp_array.shuffled()
        for(i in 0..7){
            final_array.add(shufled[i])
            final_array.add(shufled[i])
        }
        temp_array = h_raw_array.toMutableList()
        shufled = temp_array.shuffled()
        for(i in 0..7){
            final_array.add(shufled[i])
            final_array.add(shufled[i])
        }
        temp_array = r_raw_array.toMutableList()
        shufled = temp_array.shuffled()
        for(i in 0..5){
            final_array.add(shufled[i])
            final_array.add(shufled[i])
        }
        temp_array = s_raw_array.toMutableList()
        shufled = temp_array.shuffled()
        for(i in 0..5){
            final_array.add(shufled[i])
            final_array.add(shufled[i])
        }
        var tempArray = final_array.toMutableList()
        var finalList = tempArray.shuffled()
        for(i in 0..35){
            image_array.add(finalList[i]["image"]!!)
            id_array.add(finalList[i]["id"]!! as String)
            score_array.add(finalList[i]["score"]!! as Long)
            katsayi_array.add(finalList[i]["katsayi"]!! as Int)
            println("${finalList[i]["image"]!!} ${finalList[i]["id"]!! as String} ${finalList[i]["score"]!! as Long} ${finalList[i]["katsayi"]!! as Int}")
        }
        activeCards_array = arrayOf<Boolean>(true,true,true,true,
            true,true,true,true,true,true,true,true,true,true,true,true
            ,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true
            ,true,true,true,true,true,true,true,true,true,true)
    }

}