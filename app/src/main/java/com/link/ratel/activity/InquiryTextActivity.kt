package com.link.ratel.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.link.ratel.dataClass.InquiryDataClass

import com.link.ratel.databinding.ActivityInquiryTextBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.MySharedPreferences
import com.link.ratel.dataClass.CounterDataClass
import java.text.SimpleDateFormat
import java.util.*

class InquiryTextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInquiryTextBinding
    var auth: FirebaseAuth? = null
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
//    private val database by lazy { FirebaseDatabase.getInstance() }
//    private val userRef = database.getReference("teams")
//    private val userRefChild = userRef.child("50Sr1i18FXV5PLHJ9T8k")
//    private val question = userRefChild.child("Question")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityInquiryTextBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val name = intent.getStringExtra("name")
        var answer = intent.getStringExtra("answer")
        var notice = intent.getStringExtra("notice")
        var profile = intent.getStringExtra("profile")
        var question = intent.getStringExtra("question")
        var reqProfile = intent.getStringExtra("reqProfile")
//        val reqQuestion = intent.getStringExtra("reqQuestion")!!.toInt()
        var reqUser = intent.getStringExtra("reqUser")
        var schedule = intent.getStringExtra("schedule")
        var user = intent.getStringExtra("user")
        val reNum = 1
//        val sum = reqQuestion + reNum
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)
        Log.d("test1234", name.toString())
        binding.constraintLayout.setOnClickListener {
            hideKeyboard()
        }

        binding.backKey.setOnClickListener {
            finish()
        }
        binding.goInquiryFire.setOnClickListener {
            val intent = Intent(this, InquiryActivity::class.java)


            var inquiryDataClass = InquiryDataClass()
            inquiryDataClass.uid = auth?.currentUser?.uid
            inquiryDataClass.title = binding.inquiryTitle.text.toString()
            inquiryDataClass.content = binding.inquiryContent.text.toString()
            inquiryDataClass.pubDate = nowDate.toString()
            inquiryDataClass.creator = name.toString()
            inquiryDataClass.check = "X"



            firestore?.collection("teams")?.document("50Sr1i18FXV5PLHJ9T8k")?.collection("Question")
                ?.document()?.set(inquiryDataClass)
            Toast.makeText(this, "문의가 접수 되었습니다", Toast.LENGTH_SHORT).show()

            intent.putExtra("title", inquiryDataClass.title.toString())
            intent.putExtra("pubDate", inquiryDataClass.pubDate.toString())
            intent.putExtra("uid", inquiryDataClass.uid.toString())


            MySharedPreferences.setUserUid(this, inquiryDataClass.title.toString())

            val counterDataClass = CounterDataClass()
            counterDataClass.answer = answer.toString()
            counterDataClass.notice = notice.toString()
            counterDataClass.profile = profile.toString()
            counterDataClass.question = question.toString()
            counterDataClass.reqProfile = reqProfile.toString()
//            counterDataClass.reqQuestion = sum.toString()
            counterDataClass.reqUser = reqUser.toString()
            counterDataClass.schedule = schedule.toString()
            counterDataClass.user = user.toString()

            db.collection("teams").document("50Sr1i18FXV5PLHJ9T8k")
                .collection("Counter").document("counter").set(counterDataClass)



            finish()

            startActivity(intent)
        }


    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.inquiryContent.windowToken, 0)
    }
}


