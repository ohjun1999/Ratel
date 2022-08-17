package com.link.ratel.activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.messaging.FirebaseMessaging
import com.link.ratel.R
import com.link.ratel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityMainBinding

    private var doubleBackToExit = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        Log.d("test", name.toString())
//        FirebaseMessaging.getInstance().subscribeToTopic("1")




        // 접근 가능
//        binding.goAlarm.setOnClickListener {
//            val intent = Intent(this, AlarmActivity::class.java)
//            startActivity(intent)
//        }
        binding.goExtra.setOnClickListener {
            val intent = Intent(this, ExtraActivity::class.java)
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
            intent.putExtra("id",id)
            startActivity(intent)
        }
        binding.goNote.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("bookMark",bookMark)
            intent.putExtra("phoneNum", phoneNum)
            startActivity(intent)
        }
        binding.goNotice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }
//        binding.goDate.setOnClickListener {
//            val intent = Intent(this, DateActivity::class.java)
//            startActivity(intent)
//
//        }
        binding.goExplain.setOnClickListener {
            val intent = Intent(this, ExplainGroupActivity::class.java)
            startActivity(intent)
        }
        binding.goHome.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moilsurok.shop/"))
            startActivity(intent)
        }




        binding.maYear.text = year
        binding.maName.text = name
        binding.maCompany.text = company
//        binding.banner.adapter = ViewPagerAdapter(getIdolList()) // 어댑터 생성
        binding.banner.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로


    }

    // 뷰 페이저에 들어갈 아이템
    private fun getIdolList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.idol1)
    }
    override fun onBackPressed() {
        if (doubleBackToExit) {
            finishAffinity()
        } else {
            Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L) {
                doubleBackToExit = false
            }
        }
    }


    fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }
}