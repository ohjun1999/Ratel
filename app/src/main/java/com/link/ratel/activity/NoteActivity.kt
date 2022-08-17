package com.link.ratel.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.link.ratel.R
import com.link.ratel.dataClass.UserDataClass
import com.link.ratel.databinding.ActivityNoteBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.link.ratel.MySharedPreferences
import com.link.ratel.adapter.NoteAdapter
import com.link.ratel.dataClass.checkboxData
import com.link.ratel.fragment.BookmarkFragment
import com.link.ratel.fragment.NoteFragment


class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
//    private lateinit var noteAdapter: NoteAdapter

    var firestore: FirebaseFirestore? = null
    var isCheck = BooleanArray(50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNoteBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)


        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        val id = intent.getStringExtra("id")
        val phoneNum = intent.getStringExtra("phoneNum")




//        binding.noteRecyclerView.apply {
//            binding.noteRecyclerView.layoutManager = LinearLayoutManager(context)
//            noteAdapter = NoteAdapter()
//            binding.noteRecyclerView.adapter = noteAdapter
//        }
//        binding.noteRecyclerView.adapter = NoteAdapter()
//        binding.noteRecyclerView.layoutManager = LinearLayoutManager(this)

        // 검색 옵션 변수
        var searchOption = "name"
        binding.btn1.isChecked = true
        // 스피너 옵션에 따른 동작
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.spinner.getItemAtPosition(position)) {
                    "이름" -> {
                        searchOption = "name"
                    }
                    "기수" -> {
                        searchOption = "year"
                    }
                }
            }
        }
//        binding.searchBtn.setOnClickListener {
//            (binding.noteRecyclerView.adapter as NoteAdapter).search(
//                binding.searchWord.text.toString(),
//                searchOption
//            )
//            binding.search.visibility = View.GONE
//            binding.menuBtn2.visibility = View.GONE
//            hideKeyboard()
//
//        }

        binding.backKey.setOnClickListener {
            finish()
        }
//
//        binding.menuBtn.setOnClickListener {
//            binding.search.visibility = View.VISIBLE
//            binding.menuBtn2.visibility = View.VISIBLE
//        }
//
//        binding.menuBtn2.setOnClickListener {
//            binding.search.visibility = View.GONE
//            binding.menuBtn2.visibility = View.GONE
//
//        }

//        binding.goBookmark.setOnClickListener {
//            val intent = Intent(this, BookmarkActivity::class.java)
//            intent.putExtra("phoneNum", phoneNum)
//            intent.putExtra("id", id)
//            startActivity(intent)
//        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.noteFrame, NoteFragment())
            .commit()


        binding.btn1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.noteFrame, NoteFragment())
                .commit()
            Log.d("test123", "이거 되나")
        }
        binding.btn2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.noteFrame, BookmarkFragment())
                .commit()
        }
    }

    inner class NoteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val checkboxStatus = SparseBooleanArray()
        private val deNote: ArrayList<UserDataClass> = arrayListOf()
        val heart = intent.getStringExtra("phoneNum")
        var checkboxList = arrayListOf<checkboxData>()
        val uid = intent.getStringExtra("id")
        private var ck = 0
        private val limit = 10
        val first =
            firestore
                ?.collection("teams")
                ?.document("50Sr1i18FXV5PLHJ9T8k")
                ?.collection("User")?.orderBy("year", Query.Direction.ASCENDING)?.limit(20)

        ////        val roomDB = Room.databaseBuilder(
////            applicationContext, AppDatabase::class.java,"database"
////        ).allowMainThreadQueries().build()
//
//
//        // firebase data 불러오기
//        init {
//            first
//                ?.addSnapshotListener { querySnapshot, _ ->
//                    // ArrayList 비워줌
//                    deNote.clear()
//
//
//                    for (snapshot in querySnapshot!!.documents) {
//                        val item = snapshot.toObject(UserDataClass::class.java)
//                        var fId = snapshot.id
//
//                        deNote.add(item!!)
//
//
//                    }
//                    var lastVisible = querySnapshot.documents[querySnapshot.size() - 1]
//                    var next = firestore
//                        ?.collection("teams")
//                        ?.document("50Sr1i18FXV5PLHJ9T8k")
//                        ?.collection("User")
//                        ?.orderBy("year", Query.Direction.ASCENDING)
//                        ?.startAfter(lastVisible)
//                        ?.limit(20)
//
//                    binding.noteRecyclerView.addOnScrollListener(object :
//                        RecyclerView.OnScrollListener() {
//                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                            super.onScrolled(recyclerView, dx, dy)
//
//                            val lastVisibleItemPosition =
//                                (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
//                            val itemTotalCount =
//                                recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1
//
//                            // 스크롤이 끝에 도달했는지 확인
//                            if (lastVisibleItemPosition == itemTotalCount && querySnapshot.size() > 0) {
//                                next
//                                    ?.addSnapshotListener { querySnapshot, _ ->
//
//                                        // ArrayList 비워줌
//                                        deNote.clone()
//                                        for (snapshot in querySnapshot!!.documents) {
//                                            val item =
//                                                snapshot.toObject(UserDataClass::class.java)
//                                            deNote.add(item!!)
//
//                                            lastVisible =
//                                                querySnapshot.documents[querySnapshot.size() - 1]
//                                            next = firestore
//                                                ?.collection("teams")
//                                                ?.document("50Sr1i18FXV5PLHJ9T8k")
//                                                ?.collection("User")
//                                                ?.orderBy("year", Query.Direction.ASCENDING)
//                                                ?.startAfter(lastVisible)
//                                                ?.limit(20)
//
//                                            notifyDataSetChanged()
//                                        }
//
//                                    }
//
//                            } else if (lastVisibleItemPosition == itemTotalCount && querySnapshot.size() < 0) {
//                                lastVisible =
//                                    querySnapshot.documents[querySnapshot.size() - 1]
//                                Toast.makeText(
//                                    this@NoteActivity,
//                                    "더이상 불러올 데이터가 없습니다.",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    })
//                    notifyDataSetChanged()
//
//
//                }
//
//        }
//
//
//        // xml 파일을 inflate 하여 ViewHolder 를 생성
//        override fun onCreateViewHolder(
//            parent: ViewGroup,
//            viewType: Int
//        ): RecyclerView.ViewHolder {
//            var view =
//                LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
//
//
//
//            return ViewHolder(view)
//        }
//
//        inner class Holder(view: View) : RecyclerView.ViewHolder(view)
//
//        // onCreateViewHolder 에서 만든 view 와 실제 데이터를 연결
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            var viewHolder = (holder as ViewHolder).itemView
//            val user: UserDataClass = deNote[position]
//            val uid = intent.getStringExtra("id")
//            holder.name.text = user.name
//            holder.phoneNum.text = user.phoneNum
//            holder.email.text = user.email
//            holder.company.text = user.company
//            holder.year.text = user.year
//            holder.comPosition.text = user.comPosition
//
//
//
//
//
////            holder.check.setOnClickListener {
////                MySharedPreferences.setCheckBox(this@NoteActivity, heart.toString())
////
////            }
////            if (MySharedPreferences.getCheckBox(this@NoteActivity).isNullOrBlank()) {
////                holder.check.isChecked = true
////                Log.d("test123", "기건 되나요")
////
////            } else {
////                holder.check.isChecked = false
////            }
//
//
//
//
//
//            holder.itemView.setOnClickListener {
//                val intent =
//                    Intent(holder.itemView.context, NoteProfileDetailActivity::class.java)
//                intent.putExtra("content", "원하는 데이터를 보냅니다.")
//                intent.putExtra("year", user.year)
//                intent.putExtra("name", user.name)
//                intent.putExtra("birthdate", user.birthdate)
//                intent.putExtra("phoneNum", user.phoneNum)
//                intent.putExtra("email", user.email)
//                intent.putExtra("company", user.company)
//                intent.putExtra("department", user.department)
//                intent.putExtra("comPosition", user.comPosition)
//                intent.putExtra("comTel", user.comTel)
//                intent.putExtra("comAdr", user.comAdr)
//                intent.putExtra("faxNum", user.faxNum)
//                ContextCompat.startActivity(holder.itemView.context, intent, null)
//
//            }
//
//
//        }
//
//        // 리사이클러뷰의 아이템 총 개수 반환
//        override fun getItemCount(): Int {
//            return deNote.size
//
//        }
//
//
//        // 파이어스토어에서 데이터를 불러와서 검색어가 있는지 판단
        fun search(searchWord: String, option: String) {
            firestore?.collection("teams")
                ?.document("50Sr1i18FXV5PLHJ9T8k")
                ?.collection("User")
                ?.addSnapshotListener { querySnapshot, _ ->
                    // ArrayList 비워줌
                    deNote.clear()

                    for (snapshot in querySnapshot!!.documents) {
                        if (snapshot.getString(option)!!.contains(searchWord)) {
                            var item = snapshot.toObject(UserDataClass::class.java)
                            deNote.add(item!!)
                        }
                    }
                    notifyDataSetChanged()
                }
        }
//
//        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val name: TextView = itemView.findViewById(R.id.noteName)
//            val phoneNum: TextView = itemView.findViewById(R.id.phoneNum)
//            val email: TextView = itemView.findViewById(R.id.mailAdress)
//            val company: TextView = itemView.findViewById(R.id.companyName)
//            val year: TextView = itemView.findViewById(R.id.noteYear)
//            val comPosition: TextView = itemView.findViewById(R.id.companyInfo)
//            val check: CheckBox = itemView.findViewById(R.id.bookmark)
//
//
//        }
//
//
//    }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }


    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.noteFrame.windowToken, 0)
    }
}