package com.link.ratel.activity



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.link.ratel.databinding.ActivityScheduleDetailBinding

class ScheduleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityScheduleDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.backKey.setOnClickListener {
            finish()
        }


        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val content = intent.getStringExtra("content")



        binding.scheduleDate.text = date
        binding.scheduleText.text = content
        binding.scheduleTitle.text = title



    }
}