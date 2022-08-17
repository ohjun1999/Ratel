package com.link.ratel.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.link.ratel.InquiryBox
import com.link.ratel.R
import com.link.ratel.databinding.ActivityInquiryBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.MySharedPreferences
import com.link.ratel.adapter.InquiryAdapter
import com.link.ratel.dataClass.InquiryDataClass


class InquiryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInquiryBinding
    var firestore: FirebaseFirestore? = null
    val db = Firebase.firestore
    lateinit var inquiryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityInquiryBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val name = intent.getStringExtra("name")
        var answer = intent.getStringExtra("answer")
        var notice = intent.getStringExtra("notice")
        var profile = intent.getStringExtra("profile")
        var question = intent.getStringExtra("question")
        var reqProfile = intent.getStringExtra("reqProfile")
        var reqQuestion = intent.getStringExtra("reqQuestion")
        var reqUser = intent.getStringExtra("reqUser")
        var schedule = intent.getStringExtra("schedule")
        var user = intent.getStringExtra("user")

        firestore = FirebaseFirestore.getInstance()

        val theTitle = intent.getStringExtra("title")
        val thePubDate = intent.getStringExtra("pubDate")
        val theUid = intent.getStringExtra("uid")
        val theContent = MySharedPreferences.getUserUid(this)
        inquiryRecyclerView = binding.inquiryRecyclerView
        var InquiryList = arrayListOf<InquiryDataClass>()
        db.collection("teams")
            .document("50Sr1i18FXV5PLHJ9T8k")
            .collection("Question").whereEqualTo("title", theContent).limit(1)
            .get().addOnSuccessListener { result ->
                for (document in result) {

                    val item = document.toObject(InquiryDataClass::class.java)
                    Log.d("test123", "${document.data}")

                    if (document.data.toString().contains(theContent)){
                        binding.inquiryRecyclerView.visibility = View.VISIBLE
                        binding.theCenter.visibility = View.GONE
                    }else{

                    }


                    InquiryList.add(item)


                }

                val inquiryAdapter = InquiryAdapter(NoteActivity(), InquiryList)
                inquiryRecyclerView.adapter = inquiryAdapter
                inquiryRecyclerView.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }






        binding.backKey.setOnClickListener {
            finish()
        }
        binding.goRealInquiry.setOnClickListener {
            val intent = Intent(this, InquiryTextActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("answer", answer)
            intent.putExtra("notice", notice)
            intent.putExtra("profile", profile)
            intent.putExtra("question", question)
            intent.putExtra("reqProfile", reqProfile)
            intent.putExtra("reqQuestion", reqQuestion)
            intent.putExtra("reqUser", reqUser)
            intent.putExtra("schedule", schedule)
            intent.putExtra("user", user)
            finish()
            startActivity(intent)
        }


    }

    inner class InquiryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var deInquiry: ArrayList<InquiryBox> = arrayListOf()
        val first =
            firestore?.collection("teams")?.document("50Sr1i18FXV5PLHJ9T8k")
                ?.collection("Question")

        // firebase data 불러오기
        init {
            first
                ?.addSnapshotListener { querySnapshot, _ ->

                    deInquiry.clear()
                    for (snapshot in querySnapshot!!.documents) {
                        val item = snapshot.toObject(InquiryBox::class.java)
                        deInquiry.add(item!!)
                    }

                    notifyDataSetChanged()
                }

        }


        // xml 파일을 inflate 하여 ViewHolder 를 생성
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_inquiry, parent, false)

            return ViewHolder(view)
        }

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder 에서 만든 view 와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            val inquiry: InquiryBox = deInquiry[position]
            holder.title.text = inquiry.title

//            holder.itemView.setOnClickListener {
//                val intent = Intent(holder.itemView?.context, InquiryDetailActivity::class.java)
//                intent.putExtra("content", "원하는 데이터를 보냅니다.")
//                intent.putExtra("title", inquiry.title)
//
//                ContextCompat.startActivity(holder.itemView.context, intent, null)
//
//            }


        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return deInquiry.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val modifiedDate: TextView = itemView.findViewById(R.id.inquiryDate)
            val title: TextView = itemView.findViewById(R.id.inquiryTitle)


        }

    }
}