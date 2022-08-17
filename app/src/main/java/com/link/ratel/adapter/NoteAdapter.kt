package com.link.ratel.adapter


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.link.ratel.MySharedPreferences
import com.link.ratel.R
import com.link.ratel.activity.MainActivity
import com.link.ratel.activity.NoteProfileDetailActivity
import com.link.ratel.dataClass.UserDataClass
import com.link.ratel.fragment.BookmarkFragment
import kotlinx.coroutines.NonDisposableHandle.parent

class NoteAdapter(

    val iduser: String,
    val context: Context,
    val UserList: ArrayList<UserDataClass>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val storage = Firebase.storage

    private val deNote: ArrayList<UserDataClass> = arrayListOf()
    var firestore: FirebaseFirestore? = null
    var db = Firebase.firestore
    val storageRef = storage.reference
    // Create a reference with an initial file path and name




    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val user = UserList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as NoteAdapter.ViewHolder).itemView
        val user: UserDataClass = UserList[position]
//        val pathReference = storageRef.child(user.filenames.toString())
//        val gsReference = storage.getReferenceFromUrl("gs://moilsurok-f60eb.appspot.com")
//        val storageReference = Firebase.storage.reference
//        pathReference.downloadUrl.addOnSuccessListener {
//            Glide.with(holder.profileImage.context)
//                .load(storageReference)
//                .into(holder.profileImage)
//        }.addOnFailureListener {
//            // Handle any errors
//        }
        holder.name.text = user.name
        holder.phoneNum.text = user.phoneNum
        holder.email.text = user.email
        holder.company.text = user.company
        holder.year.text = user.year
        holder.comPosition.text = user.comPosition

        Log.d("test12345", user.filenames.toString())

        if (user.bookmark!!.contains(iduser)) {
            holder.check2.visibility = View.VISIBLE
        } else {
            holder.check2.visibility = View.GONE
        }


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



        holder.check.setOnClickListener {

            db.collection("teams")
                .document("50Sr1i18FXV5PLHJ9T8k")
                .collection("User").document(user.uid.toString())
                .update("bookmark", FieldValue.arrayUnion(iduser))
            holder.check2.visibility = View.VISIBLE


        }
        holder.check2.setOnClickListener {

            db.collection("teams")
                .document("50Sr1i18FXV5PLHJ9T8k")
                .collection("User").document(user.uid.toString())
                .update("bookmark", FieldValue.arrayRemove(iduser))
            holder.check2.visibility = View.GONE


        }
    }

    override fun getItemCount(): Int {
        return UserList.size
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
        val profileImage: ImageView = itemView.findViewById(R.id.noteImage)


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
                Log.d("test", "이거 안되=ㅏ ")
                notifyDataSetChanged()
            }
    }
}