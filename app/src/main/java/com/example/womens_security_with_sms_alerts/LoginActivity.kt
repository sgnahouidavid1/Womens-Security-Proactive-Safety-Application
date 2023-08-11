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

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Initialize Firebase Auth
        auth = Firebase.auth
        val tvRegisterButton: TextView = findViewById(R.id.tvRegisterButton)
        val btLogin: Button = findViewById(R.id.btLogin)
        tvRegisterButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        btLogin.setOnClickListener {
            performLogin()
        }
    }
    private fun performLogin() {
        var tfLoginMobile_EmailAddress: TextInputEditText? = null
        var tfLoginPassword: TextInputEditText? = null
        tfLoginMobile_EmailAddress = findViewById(R.id.tfMobile)
        tfLoginPassword = findViewById(R.id.tfPassword)

        val mobileLoginOrEmailAddress = tfLoginMobile_EmailAddress?.text.toString()
        val passwordLogin = tfLoginPassword?.text.toString()
        if(mobileLoginOrEmailAddress.isEmpty() || passwordLogin.isEmpty()){
            Toast.makeText(this,
                "One or both of the login fields has not been enter", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(mobileLoginOrEmailAddress, passwordLogin)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to main activity of the application
                    val intent = Intent(this, MainApplicationActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(baseContext, "Authentication successful.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                // If sign in fails, display a message to the user.
                Toast.makeText(baseContext, "Login Failed: \n  ${it.localizedMessage}", Toast.LENGTH_LONG,).show()
            }
    }
}