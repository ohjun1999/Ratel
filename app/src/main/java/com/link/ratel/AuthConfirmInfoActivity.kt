package com.link.ratel


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.link.ratel.databinding.ActivityAuthConfirmInfoBinding

class AuthConfirmInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthConfirmInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAuthConfirmInfoBinding.inflate(layoutInflater)
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
        val bookMark = intent.getStringExtra("bookMark")
        val id = intent.getStringExtra("id")

        binding.goPass.setOnClickListener {
            val intent = Intent(this, SimplePasswordActivity::class.java)
            intent.putExtra("content", "원하는 데이터를 보냅니다.")
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
            intent.putExtra("bookMark", bookMark)
            startActivity(intent)
        }
        binding.conYear.text = year
        binding.conName.text = name
        binding.conBirthDate.text = birthdate
        binding.conPhoneNum.text = phoneNum
        binding.conEmail.text = email
        binding.conFaxNum.text = faxNum
        binding.conCompany.text = company
        binding.conDepartment.text = department
        binding.conComPosition.text = comPosition
        binding.conComAdr.text = comAdr


    }


}