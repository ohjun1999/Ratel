package com.link.ratel.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.link.ratel.R
import com.link.ratel.activity.NoteProfileDetailActivity
import com.link.ratel.dataClass.UserDataClass

class BookMarkAdapter (val iduser: String, val context: Context, val UserList: ArrayList<UserDataClass>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth
    private val deNote: ArrayList<UserDataClass> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val user = UserList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as BookMarkAdapter.ViewHolder).itemView
        val user: UserDataClass = UserList[position]
        holder.name.text = user.name
        holder.phoneNum.text = user.phoneNum
        holder.email.text = user.email
        holder.company.text = user.company
        holder.year.text = user.year
        holder.comPosition.text = user.comPosition
        holder.check2.visibility = View.VISIBLE

        holder.itemView.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, NoteProfileDetailActivity::class.java)
            intent.putExtra("content", "원하는 데이터를 보냅니다.")
            intent.putExtra("year", user.year)
            intent.putExtra("name", user.name)
            intent.putExtra("birthdate", user.birthdate)
            intent.putExtra("phoneNum", user.phoneNum)
            intent.putExtra("email", user.email)
            intent.putExtra("company", user.company)
            intent.putExtra("department", user.department)
            intent.putExtra("comPosition", user.comPosition)
            intent.putExtra("comTel", user.comTel)
            intent.putExtra("comAdr", user.comAdr)
            intent.putExtra("faxNum", user.faxNum)
            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }
        holder.check2.setOnClickListener {

            db.collection("teams")
                .document("FxRFio9hTwGqAsU5AIZd")
                .collection("User").document(user.uid.toString()).update("bookmark", FieldValue.arrayRemove(iduser))
            holder.check2.visibility = View.GONE


        }

    }

    override fun getItemCount(): Int {
        return UserList.size
    }

    // 파이어스토어에서 데이터를 불러와서 검색어가 있는지 판단
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
                notifyDataSetChanged()
            }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.noteName)
        val phoneNum: TextView = itemView.findViewById(R.id.phoneNum)
        val email: TextView = itemView.findViewById(R.id.mailAdress)
        val company: TextView = itemView.findViewById(R.id.companyName)
        val year: TextView = itemView.findViewById(R.id.noteYear)
        val comPosition: TextView = itemView.findViewById(R.id.companyInfo)
        val check: ImageButton = itemView.findViewById(R.id.bookmark)
        val check2: ImageButton = itemView.findViewById(R.id.bookmark2)



    }
}