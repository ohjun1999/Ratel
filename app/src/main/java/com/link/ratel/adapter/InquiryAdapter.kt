package com.link.ratel.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.R
import com.link.ratel.activity.InquiryContextActivity
import com.link.ratel.activity.NoteProfileDetailActivity
import com.link.ratel.dataClass.InquiryDataClass
import com.link.ratel.dataClass.UserDataClass

class InquiryAdapter (val context: Context, val InquiryList: ArrayList<InquiryDataClass>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth
    private val deNote: ArrayList<UserDataClass> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_inquiry, parent, false)
        val inquiry = InquiryList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as InquiryAdapter.ViewHolder).itemView
        val inquiry: InquiryDataClass = InquiryList[position]
        holder.inquiryTitle.text = inquiry.title.toString()
        holder.inquiryDate.text = inquiry.pubDate.toString().substring(0,10)

        if (inquiry.check == "X"){
            holder.inquiryEnd.visibility = View.GONE
            holder.inquiryIng.visibility = View.VISIBLE
        }


        holder.itemView.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, InquiryContextActivity::class.java)
            intent.putExtra("title", inquiry.title)
            intent.putExtra("content", inquiry.content)
            intent.putExtra("creator", inquiry.creator)
            intent.putExtra("pubDate", inquiry.pubDate)
            intent.putExtra("check", inquiry.check)
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }


    }

    override fun getItemCount(): Int {
        return InquiryList.size
    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val inquiryTitle: TextView = itemView.findViewById(R.id.inquiryTitle)
        val inquiryIng: ImageView = itemView.findViewById(R.id.inquiry_ing)
        val inquiryEnd: ImageView = itemView.findViewById(R.id.inquiry_end)
        val inquiryDate: TextView = itemView.findViewById(R.id.inquiryDate)


    }
}