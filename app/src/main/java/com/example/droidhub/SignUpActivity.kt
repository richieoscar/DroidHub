package com.example.droidhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    lateinit var progressbar:ProgressBar
    lateinit var login: TextView
    lateinit var name:EditText
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var confirmPassword:EditText
    lateinit var signUp: Button
    lateinit var alertDialog:AlertDialog

    var mfAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        bindViews()
        hideProgressBar()
        signUp()
        gotoLogin()
    }

    private fun gotoLogin() {
        login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun bindViews() {
        progressbar = findViewById(R.id.progressBar)
        login = findViewById(R.id.textView_signUp_login)
        email = findViewById(R.id.signUp_email)
        name= findViewById(R.id.signUp_name)
        password = findViewById(R.id.signUp_password)
        confirmPassword = findViewById(R.id.signUp_confirm_password)
        signUp = findViewById(R.id.signup_button)
    }

    private fun signUp(){
       signUp.setOnClickListener{
           showSuccessAlert()
       }
    }

    private fun showSuccessAlert(){
        var alertBuilder = AlertDialog.Builder(this)
        var inflater = LayoutInflater.from(this)
        var view = inflater.inflate(R.layout.success, null)
        alertBuilder.setView(view)

        alertDialog = alertBuilder.create()
        alertDialog.show()

        var message :TextView = view.findViewById(R.id.textview_regSuccesful)
        //errorMessage.text = "$message \n Network Unavailable"
        var ok:Button = view.findViewById(R.id.button_ok)

        ok.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showProgressBar(){
        progressbar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        progressbar.visibility = View.INVISIBLE
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}