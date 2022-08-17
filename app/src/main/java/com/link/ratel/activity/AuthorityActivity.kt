package com.link.ratel.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.link.ratel.databinding.ActivityAuthorityBinding

class AuthorityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorityBinding
    companion object {
        const val TAG = "PermissionActivity"
        const val PERMISSION_REQUEST_CODE = 1
    }
    private val requiredPermissionList = arrayOf(  //필요한 권한들
        Manifest.permission.READ_PHONE_NUMBERS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.INTERNET
    )

    private lateinit var neededPermissionList: ArrayList<String>  //권한 요청이 필요한 리스트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAuthorityBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.goAgree.setOnClickListener {


            checkPhonePermission()


        }

    }

    //권한 확인
    fun checkPhonePermission() {

        // 1. 위험권한(phone) 권한 승인상태 가져오기
        val phonePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS)
        if (phonePermission == PackageManager.PERMISSION_GRANTED) {
            //전화 권한이 승인된 상태일 경우
            startProcess()
            val intent = Intent(this, AuthorityAgreeActivity::class.java)
            startActivity(intent)
        } else {
            // 전화 권한이 승인되지 않았을 경우
            requestPermission()
            Toast.makeText(this, "필수 접근 권한을 허용해주시길 바랍니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 2. 권한 요청
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_NUMBERS), 99)
    }

    // 권한 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            99 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startProcess()
                } else {
                    Log.d("MainActivity", "종료")
                }
            }
        }
    }

    // 3. 카메라 기능 실행
    fun startProcess() {
        Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
    }
}