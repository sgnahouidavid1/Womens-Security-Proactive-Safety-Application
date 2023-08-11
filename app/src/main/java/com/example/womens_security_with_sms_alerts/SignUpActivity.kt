package com.example.womens_security_with_sms_alerts


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        // Initialize Firebase Auth
        auth = Firebase.auth
        var tfFullName: TextInputEditText? = null
        var tfMobileNum:  TextInputEditText? = null
        var tfEmail: TextInputEditText? = null
        var tfPassword: TextInputEditText? = null
        var tfPasswordTwo: TextInputEditText? = null
        val tvLoginButton: TextView = findViewById(R.id.tvLoginButton)
        val btRegister: Button = findViewById(R.id.btRegister)
        tfFullName = findViewById(R.id.tfFirstLastName)
        tfMobileNum = findViewById(R.id.tfPhoneNumber)
        tfEmail = findViewById(R.id.tfEmailAddress)
        tfPassword = findViewById(R.id.tfAccountPassword)
        tfPasswordTwo = findViewById(R.id.tfAccountPasswordTwo)
        tvLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        btRegister.setOnClickListener {
            if(tfFullName?.text.toString().isEmpty() || tfMobileNum?.text.toString().isEmpty() ||
                tfEmail?.text.toString().isEmpty() || tfPassword?.text.toString().isEmpty() || tfPasswordTwo?.text.toString().isEmpty()){
                Toast.makeText(this,
                    "One or more of the registering information has not been enter", Toast.LENGTH_SHORT).show()
            }else if(tfPassword?.text.toString() != tfPasswordTwo?.text.toString()){
                Toast.makeText(this,
                    "The passwords do not match", Toast.LENGTH_SHORT).show()
            }

        }

    }
}