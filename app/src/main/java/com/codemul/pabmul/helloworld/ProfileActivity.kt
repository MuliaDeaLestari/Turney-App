package com.codemul.pabmul.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codemul.pabmul.helloworld.databinding.ActivityProfileBinding
import com.codemul.pabmul.helloworld.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val databaseRef by lazy {
        FirebaseDatabase.getInstance()
    }

    private val currentUser by lazy {
        firebaseAuth.currentUser
    }

    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profile()

        binding.tvLogout.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java)).apply {
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                Intent.FLAG_ACTIVITY_NEW_TASK
            }
            this@ProfileActivity.finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun profile(){
        val df = databaseRef.getReference("Users")
        df.child(currentUser!!.uid).get().addOnSuccessListener {
            val username = it.child("name").value.toString()
            val email = it.child("email").value.toString()
            val id = it.child("id").value.toString()

            binding.tvName.text = username
            binding.tvId.text = id
            binding.edtUsername.text = username
            binding.edtEmail.text = email
        }
    }
}