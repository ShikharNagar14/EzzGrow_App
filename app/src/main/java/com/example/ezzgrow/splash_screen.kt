package com.example.ezzgrow

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class splash_screen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
            auth = FirebaseAuth.getInstance()
            Handler(Looper.getMainLooper()).postDelayed({
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    // User is already logged in
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, start_activity::class.java))
                }
                finish()
            }, 2000)
        }
}