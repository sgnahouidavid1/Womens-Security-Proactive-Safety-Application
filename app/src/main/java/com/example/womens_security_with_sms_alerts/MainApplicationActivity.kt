package com.example.womens_security_with_sms_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainApplicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_application)
        val btLogOut: Button = findViewById(R.id.btLogOut)
        btLogOut.setOnClickListener {
            val intent = Intent(this, LoginActivity:: class.java )
            startActivity(intent)
            finish()
        }
    }
}