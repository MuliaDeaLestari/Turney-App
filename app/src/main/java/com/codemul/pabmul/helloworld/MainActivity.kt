package com.codemul.pabmul.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var firebaseAuth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val viewList = findViewById<RecyclerView>(R.id.rv_featured_events)

        val eventList = ArrayList<EsportEvent>()
        // type nentuin output gambar yg dipakai
        eventList.add(EsportEvent("valorant"))
        eventList.add(EsportEvent("porsematik"))
        eventList.add(EsportEvent("DOTA 2"))


        val adapter = CustomAdapter(this, eventList)
        viewList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        viewList.adapter = adapter
        adapter.setOnItemClickListener(object: CustomAdapter.OnItemListClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity, "No. $position", Toast.LENGTH_LONG).show()
            }

        })

        onClickComponentListener()

    }

    override fun onStart() {
        var firebaseUser: FirebaseUser = firebaseAuth?.currentUser!!
        if (firebaseUser!=null){
            //there is some user log in
        } else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        super.onStart()
    }

    private fun onClickComponentListener(){
        val scrimComponent: LinearLayout = findViewById(R.id.ll_list_scrim)
        val daftarScrimComponent: LinearLayout = findViewById(R.id.ll_daftar_scrim)
        val daftarEventComponent: LinearLayout = findViewById(R.id.ll_list_event)

        scrimComponent.setOnClickListener {
            val scrimIntent = Intent(this, ScrimActivity::class.java)
            startActivity(scrimIntent)
        }

        daftarScrimComponent.setOnClickListener {
            val daftarScrimIntent = Intent(this, DaftarScrimActivity::class.java)
            startActivity(daftarScrimIntent)
        }

        daftarEventComponent.setOnClickListener {
            val eventIntent = Intent(this, EventActivity::class.java)
            startActivity(eventIntent)
        }
    }
}