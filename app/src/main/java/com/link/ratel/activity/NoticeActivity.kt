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
import com.link.ratel.R
import com.link.ratel.adapter.NoticeAdapter
import com.link.ratel.dataClass.NoticeDataClass
import com.link.ratel.databinding.ActivityNoticeBinding
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding

    var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        firestore = FirebaseFirestore.getInstance()



        binding.noticeRecyclerView.adapter = NoticeAdapter()
        binding.noticeRecyclerView.layoutManager = LinearLayoutManager(this)


        binding.backKey.setOnClickListener {
            finish()
        }


    }

    inner class NoticeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var deNotice: ArrayList<NoticeDataClass> = arrayListOf()
        val first =
            firestore?.collection("teams")?.document("50Sr1i18FXV5PLHJ9T8k")?.collection("Notice")

        // firebase data 불러오기
        init {
            first
                ?.addSnapshotListener { querySnapshot, _ ->

                    deNotice.clear()
                    for (snapshot in querySnapshot!!.documents) {
                        var item = snapshot.toObject(NoticeDataClass::class.java)
                        deNotice.add(item!!)
                    }

                    notifyDataSetChanged()
                }

        }


        // xml 파일을 inflate 하여 ViewHolder 를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_notice, parent, false)





            return ViewHolder(view)
        }

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder 에서 만든 view 와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            val notice: NoticeDataClass = deNotice[position]
            holder.title.text = notice.title
            holder.pubDate.text = notice.pubDate

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView?.context, NoticeDetailActivity::class.java)
                intent.putExtra("content", "원하는 데이터를 보냅니다.")
                intent.putExtra("content", notice.content)
                intent.putExtra("title", notice.title)
                intent.putExtra("pubDate", notice.pubDate)

                ContextCompat.startActivity(holder.itemView.context, intent, null)

            }


        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return deNotice.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val pubDate: TextView = itemView.findViewById(R.id.modifiedDate)
            val title: TextView = itemView.findViewById(R.id.title)


        }

    }


}