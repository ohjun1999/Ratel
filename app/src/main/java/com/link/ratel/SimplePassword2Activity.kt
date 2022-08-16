package com.link.ratel


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.*
import com.link.ratel.databinding.ActivitySimplePassword2Binding


class SimplePassword2Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySimplePassword2Binding
    private var oldPwd = ""
    private var changePwdUnlock = false
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivitySimplePassword2Binding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        val appLock: com.link.ratel.AppLock? = null
        val pref = this.getPreferences(0)
        val editor = pref.edit()

        setContentView(view)
        hideKeyboard()

        val imageButtonArray = arrayListOf<ImageButton>(
            binding.btnErase
        )
        val buttonArray = arrayListOf<Button>(
            binding.btn0,
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btn7,
            binding.btn8,
            binding.btn9,
            binding.btnClear
        )
        for (imageButton in imageButtonArray) {
            imageButton.setOnClickListener(btnListener)
        }
        for (button in buttonArray) {
            button.setOnClickListener(btnListener)
        }

        binding.goMain.setOnClickListener {


            if (binding.etPasscode1.text.isNullOrBlank() || binding.etPasscode2.text.isNullOrBlank() || binding.etPasscode3.text.isNullOrBlank() || binding.etPasscode4.text.isNullOrBlank() || binding.etPasscode5.text.isNullOrBlank() || binding.etPasscode6.text.isNullOrBlank()) {
                Toast.makeText(this, "모든 비밀번호가 입력 되지 않았습니다.", Toast.LENGTH_SHORT).show()

            } else if (MySharedPreferences.getUserPass(this) == inputedPassword()) {
                var name: String
                var company: String
                var year: String
                var birthdate: String
                var phoneNum: String
                var email: String
                var department: String
                var comPosition: String
                var comTel: String
                var comAdr: String
                var faxNum: String
                var id: String
                var bookMark: String
                val logPhoneNum = MySharedPreferences.getUserId(this)
                val login = db.collection("teams").document("FxRFio9hTwGqAsU5AIZd")
                    .collection("User").whereEqualTo("phoneNum", logPhoneNum)
                    .whereEqualTo("check", "O")
                login
                    .get()
                    //IF문 사용해서 빈값을 받아왔을 때 실패 메시지 document를 받아왔을 때 액티비티 이동
                    .addOnSuccessListener { documents ->


                        for (document in documents) {
                            id = document.id
                            name = document.getString("name").toString()
                            company = document.getString("company").toString()
                            year = document.getString("year").toString()
                            birthdate = document.getString("birthdate").toString()
                            phoneNum = document.getString("phoneNum").toString()
                            email = document.getString("email").toString()
                            department = document.getString("department").toString()
                            comPosition = document.getString("comPosition").toString()
                            comTel = document.getString("comTel").toString()
                            comAdr = document.getString("comAdr").toString()
                            faxNum = document.getString("faxNum").toString()
                            bookMark = document.get("bookmark").toString()

                            val intent = Intent(this, MainActivity::class.java)
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

                            finish()
                        }
                    }


            } else {
                Toast.makeText(
                    this,
                    "입력된 비밀번호가 일치하지 않습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.etPasscode1.inputType = 0
            binding.etPasscode2.inputType = 0
            binding.etPasscode3.inputType = 0
            binding.etPasscode4.inputType = 0
            binding.etPasscode5.inputType = 0
            binding.etPasscode6.inputType = 0
            binding.etPasscode7.inputType = 0


        }
    }


    private val btnListener = View.OnClickListener { view ->
        var currentValue = -1

        when (view.id) {
            R.id.btn0 -> currentValue = 0
            R.id.btn1 -> currentValue = 1
            R.id.btn2 -> currentValue = 2
            R.id.btn3 -> currentValue = 3
            R.id.btn4 -> currentValue = 4
            R.id.btn5 -> currentValue = 5
            R.id.btn6 -> currentValue = 6
            R.id.btn7 -> currentValue = 7
            R.id.btn8 -> currentValue = 8
            R.id.btn9 -> currentValue = 9
            R.id.btnClear -> onClear()
            R.id.btnErase -> onDeleteKey()


        }

        val strCurrentValue = currentValue.toString()//현재 입력 된 번호 String으로 변경
        if (currentValue != -1) {
            when {
                binding.etPasscode1.isFocused -> {
                    setEditText(binding.etPasscode1, binding.etPasscode2, strCurrentValue)
                }
                binding.etPasscode2.isFocused -> {
                    setEditText(binding.etPasscode2, binding.etPasscode3, strCurrentValue)
                }
                binding.etPasscode3.isFocused -> {
                    setEditText(binding.etPasscode3, binding.etPasscode4, strCurrentValue)
                }
                binding.etPasscode4.isFocused -> {
                    setEditText(binding.etPasscode4, binding.etPasscode5, strCurrentValue)
                }
                binding.etPasscode5.isFocused -> {
                    setEditText(binding.etPasscode5, binding.etPasscode6, strCurrentValue)
                }
                binding.etPasscode6.isFocused -> {
                    setEditText(binding.etPasscode6, binding.etPasscode7, strCurrentValue)
                }
            }

        }
        if (binding.etPasscode6.text.isEmpty() && binding.etPasscode5.text.isEmpty() && binding.etPasscode4.text.isEmpty() && binding.etPasscode3.text.isEmpty() && binding.etPasscode2.text.isEmpty() && binding.etPasscode1.text.isEmpty()) {
            inputType(intent.getIntExtra("type", 0))
        }


    }

    private fun onClear() {
        binding.etPasscode1.setText("")
        binding.etPasscode2.setText("")
        binding.etPasscode3.setText("")
        binding.etPasscode4.setText("")
        binding.etPasscode5.setText("")
        binding.etPasscode6.setText("")
        binding.etPasscode7.setText("")
        binding.etPasscode1.requestFocus()
    }

    private fun inputedPassword(): String {
        return "${binding.etPasscode1.text}${binding.etPasscode2.text}${binding.etPasscode3.text}${binding.etPasscode4.text}${binding.etPasscode5.text}${binding.etPasscode6.text}"
    }

    private fun onDeleteKey() {
        when {
            binding.etPasscode1.isFocused -> {
                binding.etPasscode1.setText("")
            }
            binding.etPasscode2.isFocused -> {
                binding.etPasscode1.setText("")
                binding.etPasscode1.requestFocus()
            }
            binding.etPasscode3.isFocused -> {
                binding.etPasscode2.setText("")
                binding.etPasscode2.requestFocus()
            }
            binding.etPasscode4.isFocused -> {
                binding.etPasscode3.setText("")
                binding.etPasscode3.requestFocus()
            }
            binding.etPasscode5.isFocused -> {
                binding.etPasscode4.setText("")
                binding.etPasscode4.requestFocus()
            }
            binding.etPasscode6.isFocused -> {
                binding.etPasscode5.setText("")
                binding.etPasscode5.requestFocus()
            }
            binding.etPasscode7.isFocused -> {
                binding.etPasscode6.setText("")
                binding.etPasscode6.requestFocus()
            }
        }

    }


    private fun setEditText(
        currentEditText: EditText,
        nextEditText: EditText,
        strCurrentValue: String
    ) {
        currentEditText.setText(strCurrentValue)
        nextEditText.requestFocus()
        nextEditText.setText("")
    }

    private fun inputType(type: Int) {
        when (type) {
            AppLockConst.ENABLE_PASSLOCK -> { // 잠금 설정
                if (oldPwd.isEmpty()) {
                    oldPwd = inputedPassword()
                    onClear()
                    binding.etInputInfo.text = "다시한번 입력"
                } else {
                    if (oldPwd == inputedPassword()) {
                        com.link.ratel.AppLock(this).setPassLock(inputedPassword())
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        onClear()
                        oldPwd = ""
                        binding.etInputInfo.text = "비밀번호 입력"
                    }
                }

            }

            AppLockConst.DISABLE_PASSLOCK -> {//잠금 삭제
                if (com.link.ratel.AppLock(this).isPassLockSet()) {
                    if (com.link.ratel.AppLock(this).checkPassLock(inputedPassword())) {
                        com.link.ratel.AppLock(this).removePassLock()
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        binding.etInputInfo.text = "비밀번호가 틀립니다."
                        onClear()
                    }
                } else {
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
            }
            AppLockConst.UNLOCK_PASSWORD ->
                if (com.link.ratel.AppLock(this).checkPassLock(inputedPassword())) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    binding.etInputInfo.text = "비밀번호가 틀립니다."
                    onClear()
                }

            AppLockConst.CHANGE_PASSWORD -> {//비밀번호 변경
                if (com.link.ratel.AppLock(this)
                        .checkPassLock(inputedPassword()) && changePwdUnlock
                ) {
                    onClear()
                    changePwdUnlock = true
                    binding.etInputInfo.text = "새로운 비밀번호 입력"
                } else if (changePwdUnlock) {
                    if (oldPwd.isEmpty()) {
                        oldPwd = inputedPassword()
                        onClear()
                        binding.etInputInfo.text = "새로운 비밀번호 다시 입력"
                    } else {
                        if (oldPwd == inputedPassword()) {
                            com.link.ratel.AppLock(this).setPassLock(inputedPassword())
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else {
                            onClear()
                            oldPwd = ""
                            binding.etInputInfo.text = "현재 비밀번호 다시 입력"
                            changePwdUnlock = false
                        }
                    }
                } else {
                    binding.etInputInfo.text = "비밀번호가 틀립니다."
                    changePwdUnlock = false
                    onClear()
                }
            }


        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.all.windowToken, 0)
    }
}