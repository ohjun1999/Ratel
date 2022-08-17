package com.link.ratel.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.link.ratel.R
import com.link.ratel.dataClass.NoticeDataClass
import com.google.firebase.firestore.FirebaseFirestore


class NoticeAdapter() :
    RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    var deNotice = mutableListOf<NoticeDataClass>()

    var firestore: FirebaseFirestore? = null

    init {
        firestore?.collection("teams")
            ?.document("50Sr1i18FXV5PLHJ9T8k")
            ?.collection("Notice")
            ?.orderBy("pubDate")
            ?.limitToLast(20)?.addSnapshotListener{ querySnapshot, _ ->
                deNotice.clear()

                for (snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(NoticeDataClass::class.java)
                    deNotice.add(item!!)
                }
            }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notice, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = deNotice.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(deNotice[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modifiedDate: TextView = itemView.findViewById(R.id.modifiedDate)
        val title: TextView = itemView.findViewById(R.id.title)

        fun bind(notice: NoticeDataClass) {
            title.text = notice.title
            modifiedDate.text = notice.modifiedDate
        }


    }

}