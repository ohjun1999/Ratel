package com.link.ratel.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.link.ratel.*
import com.link.ratel.dataClass.EventDataClass
import com.link.ratel.dataClass.ScheduleDataClass
import com.link.ratel.databinding.ActivityDateBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.link.ratel.databinding.Example3CalendarDayBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class DateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDateBinding


    private var scheduleDate = LocalDate.now()
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MM")
    private val titleFormatter = DateTimeFormatter.ofPattern("yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("MM월의 일정")
    private val selectionDateFormatter = DateTimeFormatter.ofPattern("MM월 dd일의 일정")
    private val events = mutableMapOf<LocalDate, List<EventDataClass>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityDateBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding = ActivityDateBinding.bind(view)

        binding.backKey.setOnClickListener {
            finish()
        }

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()


        binding.exThreeCalendar.apply {
            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
            selectDate(today)
        }

        if (savedInstanceState == null) {
            binding.exThreeCalendar.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = Example3CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }

                }
            }
        }




        binding.exThreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exThreeDayText
                val dotView = container.binding.exThreeDotView


                textView.text = day.date.dayOfMonth.toString()
//                itemView.setTextColor(when(position % 7) {
//                    0 -> Color.RED
//                    6 -> Color.BLUE
//                    else -> Color.BLACK
//                })


                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.example_3_white)
                            textView.setBackgroundResource(R.drawable.example_3_today_bg)
                            dotView.makeInVisible()
                            binding.selectDateRecyclerView.adapter = selectDateScheduleAdapter()
                            binding.selectDateRecyclerView.layoutManager =
                                LinearLayoutManager(this@DateActivity)

                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.example_3_blue)
                            textView.setBackgroundResource(R.drawable.example_3_selected_bg)
                            dotView.makeInVisible()
                            binding.selectDateRecyclerView.adapter = selectDateScheduleAdapter()
                            binding.selectDateRecyclerView.layoutManager =
                                LinearLayoutManager(this@DateActivity)
                        }

                        scheduleDate -> {
                            textView.setTextColorRes(R.color.example_3_black)
                            textView.background = null
                            dotView.makeVisible()
                        }

                        else -> {
                            textView.setTextColorRes(R.color.example_3_black)
                            textView.background = null
                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                    }
                } else if (textView == events[day.date]) {
                    textView.setTextColorRes(R.color.example_3_black)
                    textView.background = null
                    dotView.makeVisible()
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }
            }
        }

        binding.exThreeCalendar.monthScrollListener = {

            selectDate(it.yearMonth.atDay(1))
            binding.exThreeRv.adapter = ScheduleAdapter()
            binding.exThreeRv.layoutManager = LinearLayoutManager(this)
        }


        binding.selectDateRecyclerView.adapter = selectDateScheduleAdapter()
        binding.selectDateRecyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.exThreeCalendar.notifyDateChanged(it) }
            binding.exThreeCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)

        }
    }

    private fun scheduleDate(date: LocalDate) {
        if (scheduleDate != date) {
            val oldDate = scheduleDate
            scheduleDate = date
            oldDate?.let { binding.exThreeCalendar.notifyDateChanged(it) }
            binding.exThreeCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)

        }
    }


    private fun updateAdapterForDate(date: LocalDate) {
        val db = Firebase.firestore
        var deEvent: ArrayList<EventDataClass> = arrayListOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val formatted = selectedDate.toString().format(formatter)


        val first =
            db
                .collection("teams")
                .document("50Sr1i18FXV5PLHJ9T8k")
                .collection("Schedule")
                .whereGreaterThanOrEqualTo("date", formatted)
                .whereLessThanOrEqualTo("date", formatted + "\uF7FF")


        // firebase data 불러오기


        first

            .addSnapshotListener { querySnapshot, _ ->
                // ArrayList 비워줌

                deEvent.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(EventDataClass::class.java)
                    deEvent.add(item!!)
//                    Log.d("test", formatted)
                }


            }


        binding.exThreeSelectedDateText.text = selectionFormatter.format(date)
        binding.exOneYearText.text = titleFormatter.format(date)
        binding.exOneMonthText.text = titleSameYearFormatter.format(date)
        binding.selectedDate.text = selectionDateFormatter.format(date)

    }


    inner class ScheduleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val db = Firebase.firestore
        var deSchedule: ArrayList<ScheduleDataClass> = arrayListOf()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val formatted = selectedDate?.toString()?.substring(0, 7)


        val first =
            db
                .collection("teams")
                .document("50Sr1i18FXV5PLHJ9T8k")
                .collection("Schedule")
                .whereGreaterThanOrEqualTo("date", formatted.toString())
                .whereLessThanOrEqualTo("date", formatted.toString() + "\uF7FF")


        // firebase data 불러오기
        init {
            first

                .addSnapshotListener { querySnapshot, _ ->
                    // ArrayList 비워줌

                    deSchedule.clear()
                    for (snapshot in querySnapshot!!.documents) {
                        var item = snapshot.toObject(ScheduleDataClass::class.java)
                        deSchedule.add(item!!)

                    }
                    notifyDataSetChanged()

                }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.example_3_event_item_view, parent, false)

            return ViewHolder(view)
        }

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            val schedule: ScheduleDataClass = deSchedule[position]
            holder.title.text = schedule.title



            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ScheduleDetailActivity::class.java)
                intent.putExtra("title", schedule.title)
                intent.putExtra("date", schedule.date!!.format("yyyy-MM-dd-hh-mm"))
                intent.putExtra("content", schedule.content)
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }


        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return deSchedule.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.daTitle)
//


        }


    }


    inner class selectDateScheduleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val db = Firebase.firestore
        var deSchedule: ArrayList<ScheduleDataClass> = arrayListOf()
        val sFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val sFormatted = selectedDate.toString().format(sFormatter)

        val first =
            db
                .collection("teams")
                .document("50Sr1i18FXV5PLHJ9T8k")
                .collection("Schedule")
                .whereGreaterThanOrEqualTo("date", sFormatted)
                .whereLessThanOrEqualTo("date", sFormatted + "\uF7FF")


        // firebase data 불러오기
        init {

            first

                .addSnapshotListener { querySnapshot, _ ->
                    // ArrayList 비워줌

                    deSchedule.clear()

                    for (snapshot in querySnapshot!!.documents) {
                        var item = snapshot.toObject(ScheduleDataClass::class.java)
                        deSchedule.add(item!!)
                    }
                    notifyDataSetChanged()

                }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.example_3_event_item_view, parent, false)


            return ViewHolder(view)
        }

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            val schedule: ScheduleDataClass = deSchedule[position]
            holder.title.text = schedule.title


            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ScheduleDetailActivity::class.java)
                intent.putExtra("title", schedule.title)
                intent.putExtra("date", schedule.date!!.format("yyyy-MM-dd-hh-mm"))
                intent.putExtra("content", schedule.content)
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }


        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return deSchedule.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.daTitle)
//


        }


    }


}