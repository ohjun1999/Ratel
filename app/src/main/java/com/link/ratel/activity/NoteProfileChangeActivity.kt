package com.link.ratel.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.link.ratel.dataClass.ProfileDataClass
import com.link.ratel.databinding.ActivityNoteProfileChangeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.dataClass.CounterDataClass
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteProfileChangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteProfileChangeBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val formatted = current.format(formatter)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNoteProfileChangeBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        firestore = FirebaseFirestore.getInstance()
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
        var answer = intent.getStringExtra("answer")
        var notice = intent.getStringExtra("notice")
        var profile = intent.getStringExtra("profile")
        var question = intent.getStringExtra("question")
        val reqProfile = intent.getStringExtra("reqProfile")!!.toInt()
        var reqQuestion = intent.getStringExtra("reqQuestion")
        var reqUser = intent.getStringExtra("reqUser")
        var schedule = intent.getStringExtra("schedule")
        var user = intent.getStringExtra("user")
        val reNum = 1
        val sum = reqProfile + reNum

        Log.d("test", sum.toString())



        binding.backKey.setOnClickListener {
            finish()
        }
        binding.request.setOnClickListener {
            binding.request.visibility = View.GONE
            binding.cancellationRequest.visibility = View.VISIBLE
            val profileDataClass = ProfileDataClass()
            profileDataClass.birthdate = binding.frBirthDate.text.toString()
            profileDataClass.check = "X"
            profileDataClass.comAdr = binding.frComAdr.text.toString()
            profileDataClass.comPosition = binding.frComPosition.text.toString()
            profileDataClass.company = binding.frCompany.text.toString()
            profileDataClass.comTel = comTel.toString()
            profileDataClass.department = binding.frDepartment.text.toString()
            profileDataClass.email = binding.frEmail.text.toString()
            profileDataClass.faxNum = binding.frFaxNum.text.toString()
            profileDataClass.modifiedDate = formatted.toString()
            profileDataClass.name = binding.frName.text.toString()
            profileDataClass.phoneNum = binding.frPhoneNum.text.toString()
            profileDataClass.pubDate = formatted.toString()
            profileDataClass.user = id.toString()
            profileDataClass.year = year.toString()
            firestore?.collection("teams")?.document("FxRFio9hTwGqAsU5AIZd")?.collection("Profile")
                ?.document()?.set(profileDataClass)

            val counterDataClass = CounterDataClass()
            counterDataClass.answer = answer.toString()
            counterDataClass.notice = notice.toString()
            counterDataClass.profile = profile.toString()
            counterDataClass.question = question.toString()
            counterDataClass.reqProfile = sum.toString()
            counterDataClass.reqQuestion = reqQuestion.toString()
            counterDataClass.reqUser = reqUser.toString()
            counterDataClass.schedule = schedule.toString()
            counterDataClass.user = user.toString()

            db.collection("teams").document("FxRFio9hTwGqAsU5AIZd")
                .collection("Counter").document("counter").set(counterDataClass)


            Toast.makeText(this, "문의가 접수 되었습니다", Toast.LENGTH_SHORT).show()
            finish()

        }

        binding.cancellationRequest.setOnClickListener {
            binding.request.visibility = View.VISIBLE
            binding.cancellationRequest.visibility = View.GONE


        }


        binding.frYear.text = year.toString()
        binding.frName.text = name.toString()
        binding.frBirthDate.setText(birthdate)
        binding.frPhoneNum.setText(phoneNum)
        binding.frEmail.setText(email)
        binding.frCompany.setText(company)
        binding.frDepartment.setText(department)
        binding.frComPosition.setText(comPosition)
        binding.frComAdr.setText(comAdr)
        binding.frFaxNum.setText(faxNum)


    }


}