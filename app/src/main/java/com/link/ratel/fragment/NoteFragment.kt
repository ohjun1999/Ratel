package com.link.ratel.fragment


import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.link.ratel.MySharedPreferences
import com.link.ratel.activity.NoteActivity
import com.link.ratel.adapter.NoteAdapter
import com.link.ratel.dataClass.UserDataClass
import com.link.ratel.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val deNote: ArrayList<UserDataClass> = arrayListOf()
    //firestore 변수

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var noteRecyclerView: RecyclerView
    val first =
        firestore
            ?.collection("teams")
            ?.document("FxRFio9hTwGqAsU5AIZd")
            ?.collection("User")?.orderBy("year", Query.Direction.ASCENDING)?.limit(20)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val root: View = binding.root
//firestore 변수 초기화
        db = Firebase.firestore
        auth = Firebase.auth

        val logPhoneNum = MySharedPreferences.getUserId(requireContext())



        noteRecyclerView = binding.noteRecyclerView

        var UserList = arrayListOf<UserDataClass>()

        // 검색 옵션 변수
        var searchOption = "name"

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
        binding.searchBtn.setOnClickListener {

            val option = searchOption
            val searchWord = binding.searchWord.text.toString()
            Log.d("test", option)
            db.collection("teams")
                .document("FxRFio9hTwGqAsU5AIZd")
                .collection("User")
                .get().addOnSuccessListener { result ->
                    UserList.clear()
                    for (document in result!!.documents) {
                        if (document.getString(option)!!.contains(searchWord)) {
                            var item = document.toObject(UserDataClass::class.java)
                            UserList.add(item!!)
                        }

                        //Adapter
                        val noteAdapter = NoteAdapter(logPhoneNum, NoteActivity(), UserList)
                        noteRecyclerView.adapter = noteAdapter
                        noteRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)


                    }
                    binding.search.visibility = View.GONE
                    binding.menuBtn2.visibility = View.GONE
                    context?.hideKeyboard(noteRecyclerView)

                }
        }


        binding.menuBtn.setOnClickListener {
            binding.search.visibility = View.VISIBLE
            binding.menuBtn2.visibility = View.VISIBLE
        }

        binding.menuBtn2.setOnClickListener {
            binding.search.visibility = View.GONE
            binding.menuBtn2.visibility = View.GONE

        }


        db.collection("teams")
            .document("FxRFio9hTwGqAsU5AIZd")
            .collection("User").orderBy("year", Query.Direction.ASCENDING).limit(20)
            .get().addOnSuccessListener { result ->
                for (document in result) {

                    var item = document.toObject(UserDataClass::class.java)
                    MySharedPreferences.setUserUid(requireContext(), document.id)
                    val uid123 = MySharedPreferences.getUserUid(requireContext())
                    Log.d("test1234", "${document.id}=>${document.data}")

                    UserList.add(item)


                }


                //Adapter
                val noteAdapter = NoteAdapter(logPhoneNum, NoteActivity(), UserList)
                noteRecyclerView.adapter = noteAdapter
                noteRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

                var lastVisible = result.documents[result.size() - 1]
                var next =
                    db
                        .collection("teams")
                        .document("FxRFio9hTwGqAsU5AIZd")
                        .collection("User")
                        .orderBy("year", Query.Direction.ASCENDING)
                        .startAfter(lastVisible)
                        .limit(20)
                binding.noteRecyclerView.addOnScrollListener(object :
                    RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                        val itemTotalCount =
                            recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                        // 스크롤이 끝에 도달했는지 확인
                        if (lastVisibleItemPosition == itemTotalCount && result.size() > 0) {
                            next
                                .get().addOnSuccessListener { result ->


                                    // ArrayList 비워줌
                                    UserList.clone()
                                    for (document in result) {
                                        val item =
                                            document.toObject(UserDataClass::class.java)
                                        UserList.add(item!!)

                                        lastVisible =
                                            result.documents[result.size() - 1]
                                        next = db
                                            .collection("teams")
                                            .document("FxRFio9hTwGqAsU5AIZd")
                                            .collection("User")
                                            .orderBy("year", Query.Direction.ASCENDING)
                                            .startAfter(lastVisible)
                                            .limit(20)


                                    }

                                }

                        } else if (lastVisibleItemPosition == itemTotalCount && result.size() < 0) {
                            lastVisible =
                                result.documents[result.size() - 1]
                            Toast.makeText(
                                context,
                                "더이상 불러올 데이터가 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })


            }




        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun search(searchWord: String, option: String) {

        firestore?.collection("teams")
            ?.document("FxRFio9hTwGqAsU5AIZd")
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



            }
    }


    //    inner class NoteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//        private val checkboxStatus = SparseBooleanArray()
//        private val deNote: ArrayList<UserDataClass> = arrayListOf()
//        var checkboxList = arrayListOf<checkboxData>()
//        private var ck = 0
//        private val limit = 10
//        val first =
//            firestore
//                ?.collection("teams")
//                ?.document("FxRFio9hTwGqAsU5AIZd")
//                ?.collection("User")?.orderBy("year", Query.Direction.ASCENDING)?.limit(20)
//
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
//                        ?.document("FxRFio9hTwGqAsU5AIZd")
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
//                                                ?.document("FxRFio9hTwGqAsU5AIZd")
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
//                                    context,
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
//            Log.d("test12345", "이거 되나")
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
//            holder.name.text = user.name
//            holder.phoneNum.text = user.phoneNum
//            holder.email.text = user.email
//            holder.company.text = user.company
//            holder.year.text = user.year
//            holder.comPosition.text = user.comPosition
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
//        fun search(searchWord: String, option: String) {
//            firestore?.collection("teams")
//                ?.document("FxRFio9hTwGqAsU5AIZd")
//                ?.collection("User")
//                ?.addSnapshotListener { querySnapshot, _ ->
//                    // ArrayList 비워줌
//                    deNote.clear()
//
//                    for (snapshot in querySnapshot!!.documents) {
//                        if (snapshot.getString(option)!!.contains(searchWord)) {
//                            var item = snapshot.toObject(UserDataClass::class.java)
//                            deNote.add(item!!)
//                        }
//                    }
//                    notifyDataSetChanged()
//                }
//        }
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
//
//
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}