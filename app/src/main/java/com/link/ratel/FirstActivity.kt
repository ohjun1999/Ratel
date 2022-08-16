package com.link.ratel


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.link.ratel.databinding.ActivityFirstBinding

class FirstActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityFirstBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        // SharedPreferences 안에 값이 저장되어 있지 않을 때 -> Login
        if( MySharedPreferences.getUserPass(this).isNullOrBlank()) {
            binding.goSignUp.setOnClickListener {
                val intent = Intent(this, AuthorityActivity::class.java)
                startActivity(intent)
                finish()
            }
            Log.d("test1", MySharedPreferences.getUserId(this))
        }
        else { // SharedPreferences 안에 값이 저장되어 있을 때 -> SimplePassword2Activity로 이동
            val intent = Intent(this, SimplePassword2Activity::class.java)
            startActivity(intent)
            Log.d("test1", MySharedPreferences.getUserId(this))
            Log.d("test2", MySharedPreferences.getUserPass(this))
            finish()
        }

    }

}