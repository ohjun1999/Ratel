package com.link.ratel.activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.link.ratel.databinding.ActivityProfileDetailBinding

class NoteProfileDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.backKey.setOnClickListener {
            finish()
        }
        binding.request.setOnClickListener {
            val input = binding.dePhoneNum.text.toString()
            val myUri = Uri.parse("tel:${input}")
            val myIntent = Intent(Intent.ACTION_DIAL, myUri)
            startActivity(myIntent)
        }

        val year = intent.getStringExtra("year")
        val name = intent.getStringExtra("name")
        val birthdate = intent.getStringExtra("birthdate")
        val phoneNum = intent.getStringExtra("phoneNum")
        val email = intent.getStringExtra("email")
        val company = intent.getStringExtra("company")
        val department = intent.getStringExtra("department")
        val comPosition = intent.getStringExtra("comPosition")
//        val comTel = intent.getStringExtra("comTel")
        val comAdr = intent.getStringExtra("comAdr")
        val faxNum = intent.getStringExtra("faxNum")


        binding.deYear.text = year
        binding.deName.text = name
        binding.deBirthDate.text = birthdate
        binding.dePhoneNum.text = phoneNum
        binding.deEmail.text = email
        binding.deCompany.text = company
        binding.deDepartment.text = department
        binding.deComPosition.text = comPosition
        binding.deComAdr.text = comAdr
        binding.deFaxNum.text = faxNum


    }
}