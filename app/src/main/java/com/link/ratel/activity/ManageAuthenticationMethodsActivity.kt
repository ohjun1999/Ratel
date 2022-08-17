package com.link.ratel.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.link.ratel.databinding.ActivityManageAuthenticationMethodsBinding

class ManageAuthenticationMethodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageAuthenticationMethodsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityManageAuthenticationMethodsBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.backKey.setOnClickListener {
            finish()
        }

    }
}