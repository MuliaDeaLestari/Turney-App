package com.codemul.pabmul.helloworld

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codemul.pabmul.helloworld.data.DaftarEvent
import com.codemul.pabmul.helloworld.data.Event
import com.codemul.pabmul.helloworld.databinding.ActivityEventBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class EventActivity : AppCompatActivity() {
    private lateinit var adapterEvent: EventAdapter
    private lateinit var rvEvent: RecyclerView

    private lateinit var binding: ActivityEventBinding
    private var storage: FirebaseStorage? = null
    private var databaseRef: DatabaseReference? = null
    private var database: FirebaseDatabase? = null
    private var dbListener: ValueEventListener? = null
    private val listKey = mutableListOf<String>()
    private val listEvent = mutableListOf<Event>()
    private lateinit var eventList: MutableList<Event>

    //nampung id event
    lateinit var idEvent: String

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

//    private val databaseRef by lazy {
//        FirebaseDatabase.getInstance()
//    }

    private val currentUser by lazy {
        firebaseAuth.currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "List Event"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_event)

        rvEvent = findViewById(R.id.rv_event)

        getData()
    }

    private fun getData() {
        rvEvent.setHasFixedSize(true)
        rvEvent.layoutManager =
            LinearLayoutManager(this@EventActivity, LinearLayoutManager.VERTICAL, false)

        eventList = ArrayList()
        adapterEvent = EventAdapter(this, eventList)
        rvEvent.adapter = adapterEvent

        val event = Event()
        storage = FirebaseStorage.getInstance()

        databaseRef = FirebaseDatabase.getInstance().getReference("event")
        dbListener = databaseRef?.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear()
                for (eventSnapshot in snapshot.children){
                    val upload = eventSnapshot.getValue(Event::class.java)
                    upload!!.id = eventSnapshot.key
                    eventList.add(upload)
                }

                adapterEvent.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EventActivity,error.message, Toast.LENGTH_SHORT).show()
            }

        })

        adapterEvent.setOnItemClickCallback(object : EventAdapter.OnItemClickCallback {
            override fun onItemClicked(event: Event) {
                showDetailEvent(event)
            }
//
//            override fun onItemHistory() {
//                TODO("Not yet implemented")
//            }

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showDetailEvent(event: Event) {
//        var intent = Intent(this, DetailEventActivity::class.java)
        startActivity(Intent(this,
            DetailEventActivity::class.java).putExtra(DetailEventActivity.INTENT_DETAIL, event))
    }


//    private fun initRecylerView() {
//        rvEvent.apply {
//            layoutManager = LinearLayoutManager(this@EventActivity, LinearLayoutManager.VERTICAL, false)
//            adapter = adapterEvent
//        }
//    }

}