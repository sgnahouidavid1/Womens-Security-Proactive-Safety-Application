package com.example.womens_security_with_sms_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var tfLoginMobile: TextInputEditText? = null
        var tfLoginPassword: TextInputEditText? = null
        val tvRegisterButton: TextView = findViewById(R.id.tvRegisterButton)
        val btLogin: Button = findViewById(R.id.btLogin)
        tfLoginMobile = findViewById(R.id.tfMobile)
        tfLoginPassword = findViewById(R.id.tfPassword)
        tvRegisterButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        btLogin.setOnClickListener {
            if(tfLoginMobile?.text.toString().isEmpty() || tfLoginPassword?.text.toString().isEmpty()){
                Toast.makeText(this,
                    "Password or Phone Number has not been Entered", Toast.LENGTH_SHORT).show()
            }
        }
    }
}