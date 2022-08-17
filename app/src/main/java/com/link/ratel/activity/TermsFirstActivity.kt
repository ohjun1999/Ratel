package com.link.ratel.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.link.ratel.databinding.ActivityTermsFirstBinding

class TermsFirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityTermsFirstBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        binding.backKey.setOnClickListener {
            finish()
        }

    }
}