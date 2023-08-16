package com.example.womens_security_with_sms_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider


class MainApplicationActivity : AppCompatActivity() {
    private  lateinit var auth : FirebaseAuth
    private  lateinit var btSendOTP: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_application)

        var tfPhoneNumber: TextInputEditText? = null
        var tfAreaNumber: TextInputEditText? = null
        tfPhoneNumber = findViewById(R.id.tfPhone)
        tfAreaNumber = findViewById(R.id.tfNumber)

        init()
        btSendOTP.setOnClickListener {
            var phoneNum = tfPhoneNumber?.text.toString()
            var areaNum = tfAreaNumber?.text.toString()
            if(phoneNum.isNotEmpty()){
                if(phoneNum.length == 10 && areaNum.isNotEmpty()){
                    phoneNum ="$areaNum$phoneNum"
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNum) // Phone number to verify
                        .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this) // Activity (for callback binding)
                        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }else {
                    Toast.makeText(this, "Please Enter the correct Area Code and Phone Number", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "Please Enter the Phone Number", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private  fun init() {
        btSendOTP = findViewById(R.id.btSendOTP)
        auth = FirebaseAuth.getInstance()
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            val intent = Intent(this@MainApplicationActivity, MainApplicationTwoActivity::class.java)
            intent.putExtra("OTP",verificationId)
            intent.putExtra("resendToken", token)
            startActivity(intent)

        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null)
            startActivity(Intent(this, SignUpActivity::class.java))

    }
}