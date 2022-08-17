package com.link.ratel.activity



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.link.ratel.databinding.ActivityNoticeDetailBinding

class NoticeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNoticeDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.backKey.setOnClickListener {
            finish()
        }

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val modifiedDate = intent.getStringExtra("modifiedDate")

        binding.title.text = title
        binding.modifiedDate.text = modifiedDate
        binding.noticeText.text = content
    }
}