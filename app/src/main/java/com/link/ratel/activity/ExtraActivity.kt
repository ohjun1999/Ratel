package com.link.ratel.activity


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.databinding.ActivityExtraBinding

class ExtraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExtraBinding

    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityExtraBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val birthdate = intent.getStringExtra("birthdate")
        val phoneNum = intent.getStringExtra("phoneNum")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val department = intent.getStringExtra("department")
        val comPosition = intent.getStringExtra("comPosition")
        val comTel = intent.getStringExtra("comTel")
        val comAdr = intent.getStringExtra("comAdr")
        val faxNum = intent.getStringExtra("faxNum")
        val id = intent.getStringExtra("id")
        Log.d("test12", "testet")
        // 접근 가능
        binding.goAlarmSetting.setOnClickListener {
            val intent = Intent(this, AlarmSettingActivity::class.java)
            startActivity(intent)

        }
        binding.authenticationManage.setOnClickListener {
            val intent = Intent(this, ManageAuthenticationMethodsActivity::class.java)
            startActivity(intent)
        }
        binding.changeProfile.setOnClickListener {
            getProfileReq()
        }
        binding.goTerms.setOnClickListener {
            val intent = Intent(this, TermsActivity::class.java)
            startActivity(intent)
        }
        binding.goInquiry.setOnClickListener {
            getQuestionReq()
        }

        binding.backKey.setOnClickListener {
            finish()
        }

        binding.exYear.text = year
        binding.exName.text = name
    }

    private fun getProfileReq() {
        var answer: String
        var notice: String
        var profile: String
        var question: String
        var reqProfile: String
        var reqQuestion: String
        var reqUser: String
        var schedule: String
        var user: String
        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val birthdate = intent.getStringExtra("birthdate")
        val phoneNum = intent.getStringExtra("phoneNum")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val department = intent.getStringExtra("department")
        val comPosition = intent.getStringExtra("comPosition")
        val comTel = intent.getStringExtra("comTel")
        val comAdr = intent.getStringExtra("comAdr")
        val faxNum = intent.getStringExtra("faxNum")
        val id = intent.getStringExtra("id")



        val getReq = db.collection("teams").document("50Sr1i18FXV5PLHJ9T8k")
            .collection("Counter")
        getReq
            .get()
            //IF문 사용해서 빈값을 받아왔을 때 실패 메시지 document를 받아왔을 때 액티비티 이동
            .addOnSuccessListener { documents ->


                for (document in documents) {

                    answer = document.data["answer"].toString()
                    notice = document.data["notice"].toString()
                    profile = document.data["profile"].toString()
                    question = document.data["question"].toString()
                    reqProfile = document.data["reqProfile"].toString()
                    reqQuestion = document.data["reqQuestion"].toString()
                    reqUser = document.data["reqUser"].toString()
                    schedule = document.data["schedule"].toString()
                    user = document.data["user"].toString()

                    val intent = Intent(this, NoteProfileChangeActivity::class.java)
                    intent.putExtra("company", company)
                    intent.putExtra("name", name)
                    intent.putExtra("year", year)
                    intent.putExtra("birthdate", birthdate)
                    intent.putExtra("phoneNum", phoneNum)
                    intent.putExtra("email", email)
                    intent.putExtra("department", department)
                    intent.putExtra("comPosition", comPosition)
                    intent.putExtra("comTel", comTel)
                    intent.putExtra("comAdr", comAdr)
                    intent.putExtra("faxNum", faxNum)
                    intent.putExtra("id", id)
                    intent.putExtra("answer", answer)
                    intent.putExtra("notice", notice)
                    intent.putExtra("profile", profile)
                    intent.putExtra("question", question)
                    intent.putExtra("reqProfile", reqProfile)
                    intent.putExtra("reqQuestion", reqQuestion)
                    intent.putExtra("reqUser", reqUser)
                    intent.putExtra("schedule", schedule)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }
            }
            //경로가 실패했을 때
            .addOnFailureListener {
            }
    }

    private fun getQuestionReq() {
        var answer: String
        var notice: String
        var profile: String
        var question: String
        var reqProfile: String
        var reqQuestion: String
        var reqUser: String
        var schedule: String
        var user: String



        val getReq = db.collection("teams").document("50Sr1i18FXV5PLHJ9T8k")
            .collection("Counter")
        getReq
            .get()
            //IF문 사용해서 빈값을 받아왔을 때 실패 메시지 document를 받아왔을 때 액티비티 이동
            .addOnSuccessListener { documents ->


                for (document in documents) {

                    answer = document.data["answer"].toString()
                    notice = document.data["notice"].toString()
                    profile = document.data["profile"].toString()
                    question = document.data["question"].toString()
                    reqProfile = document.data["reqProfile"].toString()
                    reqQuestion = document.data["reqQuestion"].toString()
                    reqUser = document.data["reqUser"].toString()
                    schedule = document.data["schedule"].toString()
                    user = document.data["user"].toString()

                    val intent = Intent(this, InquiryActivity::class.java)
                    intent.putExtra("answer", answer)
                    intent.putExtra("notice", notice)
                    intent.putExtra("profile", profile)
                    intent.putExtra("question", question)
                    intent.putExtra("reqProfile", reqProfile)
                    intent.putExtra("reqQuestion", reqQuestion)
                    intent.putExtra("reqUser", reqUser)
                    intent.putExtra("schedule", schedule)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }
            }
            //경로가 실패했을 때
            .addOnFailureListener {
            }
    }

}