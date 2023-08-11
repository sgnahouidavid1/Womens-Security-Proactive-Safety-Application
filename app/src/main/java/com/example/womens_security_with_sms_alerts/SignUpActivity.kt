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

        val tvLoginButton: TextView = findViewById(R.id.tvLoginButton)
        val btRegister: Button = findViewById(R.id.btRegister)

        tvLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        btRegister.setOnClickListener {
            performRegistration()
        }

    }
    private  fun performRegistration(){
        var tfFullName: TextInputEditText? = null
        var tfMobileNum:  TextInputEditText? = null
        var tfEmail: TextInputEditText? = null
        var tfPassword: TextInputEditText? = null
        var tfPasswordTwo: TextInputEditText? = null

        tfFullName = findViewById(R.id.tfFirstLastName)
        tfMobileNum = findViewById(R.id.tfPhoneNumber)
        tfEmail = findViewById(R.id.tfEmailAddress)
        tfPassword = findViewById(R.id.tfAccountPassword)
        tfPasswordTwo = findViewById(R.id.tfAccountPasswordTwo)

        val fullName = tfFullName?.text.toString()
        val mobileNum = tfMobileNum?.text.toString()
        val email = tfEmail?.text.toString()
        val password = tfPassword?.text.toString()
        val passwordTwo = tfPasswordTwo?.text.toString()
        if(email.isEmpty() || password.isEmpty() || fullName.isEmpty() || mobileNum.isEmpty() || passwordTwo.isEmpty()){
            Toast.makeText(this,
                "One or more of the registering fields has not been enter", Toast.LENGTH_SHORT).show()
            return
        }else if (password != passwordTwo){
            Toast.makeText(this,
                "The passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in successful, let move to the next activity because the registration was successful.
                    val intent = Intent(this, MainApplicationActivity::class.java)
                    startActivity(intent)
                    finish()
                    // If sign in is success, display a message to the user.
                    Toast.makeText(baseContext, "Authentication successful.", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occurred on account registration ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

    }
}