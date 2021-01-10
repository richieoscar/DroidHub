package com.example.droidhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var done:Button
    lateinit var gotoLogin:TextView
    lateinit var alertDialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        email = findViewById(R.id.editTextTextEmailAddress)
        done = findViewById(R.id.forgot_password_button)
        gotoLogin = findViewById(R.id.textView_forgot_goto_login)

        sendResetPasswordLink()

        gotoLogin()
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
            showResetAlert()
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
}