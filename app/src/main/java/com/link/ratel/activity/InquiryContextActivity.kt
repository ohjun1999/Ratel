package com.link.ratel.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.MySharedPreferences
import com.link.ratel.dataClass.AnswerDataClass
import com.link.ratel.databinding.ActivityInquiryContextBinding

class InquiryContextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInquiryContextBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityInquiryContextBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val creator = intent.getStringExtra("creator")
        val pubDate = intent.getStringExtra("pubDate")
        val check = intent.getStringExtra("check")
        val theContent = MySharedPreferences.getUserUid(this)
        firestore = FirebaseFirestore.getInstance()
        var InquiryAnswerList = arrayListOf<AnswerDataClass>()
        db.collection("teams")
            .document("50Sr1i18FXV5PLHJ9T8k")
            .collection("Answer").whereEqualTo("title", theContent).limit(1)
            .get().addOnSuccessListener { result ->
                for (document in result) {

                    val item = document.toObject(AnswerDataClass::class.java)

                    binding.contextAnswer.text = document.getString("content").toString()
                    binding.contextPubDate.text = document.getString("modifiedDate").toString()


                    InquiryAnswerList.add(item)


                }

            }


        binding.backKey.setOnClickListener {
            finish()
        }

        if (check.toString() == "X"){
            binding.inquiryEnd.visibility = View.GONE
            binding.inquiryIng.visibility = View.VISIBLE
        }
        binding.contextTitle.text = title.toString()
        binding.contextContent.text = content.toString()
        binding.contextDate.text = pubDate.toString()
    }
}