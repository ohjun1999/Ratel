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
            ?.document("50Sr1i18FXV5PLHJ9T8k")
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

        Log.d("test1234", logPhoneNum)


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
                .document("50Sr1i18FXV5PLHJ9T8k")
                .collection("User")
                .get().addOnSuccessListener { result ->
                    UserList.clear()
                    for (document in result!!.documents) {
                        if (document.getString(option)?.contains(searchWord) == true) {
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
                    (activity as NoteActivity).goneSearch()
                    context?.hideKeyboard(noteRecyclerView)

                }
        }








        db.collection("teams")
            .document("50Sr1i18FXV5PLHJ9T8k")
            .collection("User").orderBy("year", Query.Direction.ASCENDING).limit(20)
            .get().addOnSuccessListener { result ->
                for (document in result) {

                    var item = document.toObject(UserDataClass::class.java)
                    MySharedPreferences.setUserUid(requireContext(), document.id)
                    val uid123 = MySharedPreferences.getUserUid(requireContext())

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
                        .document("50Sr1i18FXV5PLHJ9T8k")
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
                                            .document("50Sr1i18FXV5PLHJ9T8k")
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



            }
    }

    fun btn01(){
        binding.search.visibility = View.VISIBLE
    }

    fun btn02(){
        binding.search.visibility = View.GONE
    }


    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}