package com.link.ratel.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.MySharedPreferences
import com.link.ratel.R
import com.link.ratel.activity.NoteActivity
import com.link.ratel.activity.NoteProfileDetailActivity
import com.link.ratel.adapter.BookMarkAdapter
import com.link.ratel.dataClass.UserDataClass
import com.link.ratel.databinding.FragmentBookmarkBinding
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.collections.ArrayList

class BookmarkFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var bookmarkRecyclerView: RecyclerView

    val first =
        firestore
            ?.collection("teams")
            ?.document("50Sr1i18FXV5PLHJ9T8k")
            ?.collection("User")?.whereArrayContainsAny("bookmark", listOf("010-2281-4489"))
            ?.limit(20)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
        val TAG = "과연"
        val logPhoneNum = MySharedPreferences.getUserId(requireContext())
        bookmarkRecyclerView = binding.bookmarkRecyclerView

        var UserList = arrayListOf<UserDataClass>()


        db.collection("teams")
            .document("50Sr1i18FXV5PLHJ9T8k")
            .collection("User").whereArrayContainsAny("bookmark", listOf(logPhoneNum)).limit(20)
            .get().addOnSuccessListener { result ->
                for (document in result) {

                    val item = document.toObject(UserDataClass::class.java)


                    UserList.add(item)


                }

                val bookmarkAdapter = BookMarkAdapter(logPhoneNum, NoteActivity(), UserList)
                bookmarkRecyclerView.adapter = bookmarkAdapter
                bookmarkRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
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


}