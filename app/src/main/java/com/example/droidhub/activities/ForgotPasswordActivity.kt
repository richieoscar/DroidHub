package com.example.droidhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var done:Button
    lateinit var gotoLogin:TextView
    lateinit var alertDialog:AlertDialog
    lateinit var mfAuth:FirebaseAuth
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        mfAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.editTextTextEmailAddress)
        done = findViewById(R.id.forgot_password_button)
        gotoLogin = findViewById(R.id.textView_forgot_goto_login)
        progressBar = findViewById(R.id.progressBar3)

        hideProgressBar()

        sendResetPasswordLink()

        gotoLogin()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun gotoLogin() {
        gotoLogin.setOnClickListener{
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun sendResetPasswordLink() {
        done.setOnClickListener{
            var newEmail:String = email.text.toString()

            if (newEmail.isEmpty()){
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
                email.error = "required"
            }
            else{
                showProgressBar()
                    mfAuth.sendPasswordResetEmail(newEmail).addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(Companion.TAG, "sendResetPasswordLink: ")
                            showResetAlert()
                            email.text.clear()
                            hideProgressBar()
                        } else {
                            hideProgressBar()
                            Log.d(TAG, "sendResetPasswordLink: failed")
                            Toast.makeText(this, "Unable to send reset link", Toast.LENGTH_SHORT).show()
                        }


                    })
            }
        }
    }

    private fun showResetAlert() {
        var alertBuilder = AlertDialog.Builder(this)
        var inflater = LayoutInflater.from(this)
        var view = inflater.inflate(R.layout.reset, null)
        alertBuilder.setView(view)

        alertDialog = alertBuilder.create()
        alertDialog.show()
        var ok:Button = view.findViewById(R.id.button_ok)
        ok.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    companion object {
        private const val TAG = "ForgotPasswordActivity"
    }
}