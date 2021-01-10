package com.example.droidhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var signUp: TextView
    lateinit var progressbar:ProgressBar
    lateinit var login:Button
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var forgotPassword:TextView
   private lateinit var mfAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mfAuth = FirebaseAuth.getInstance()

        bindViews()
        hideProgressBar()
        gotoSignUp()
       loginUser()
       gotoForgotPassword()


    }

    private fun gotoForgotPassword() {
        forgotPassword.setOnClickListener{
            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun loginUser() {
        login.setOnClickListener{
         gotoDashBoard()
        }
    }

    private fun gotoDashBoard() {
        var intent= Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun bindViews() {
        signUp = findViewById(R.id.textView_signUp)
        progressbar = findViewById(R.id.progressBar2)
        login = findViewById(R.id.login_button)
        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        forgotPassword = findViewById(R.id.textView_forgotPassword)

    }

    private fun gotoSignUp() {
        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validate(){
        if(email.text.toString().isEmpty()){
            email.error = "required"
            return
        }

        if (password.text.toString().isEmpty()){
            password.error = "required"
            return;
        }


    }

    private fun showProgressBar(){
        progressbar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        progressbar.visibility = View.INVISIBLE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }


}