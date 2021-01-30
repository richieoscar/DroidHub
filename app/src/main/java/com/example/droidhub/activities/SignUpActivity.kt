package com.example.droidhub.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.droidhub.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpActivity : AppCompatActivity() {

    lateinit var progressbar:ProgressBar
    lateinit var login: TextView
    lateinit var name:EditText
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var confirmPassword:EditText
    lateinit var signUp: Button
    lateinit var alertDialog:AlertDialog
    lateinit var fAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        fAuth = FirebaseAuth.getInstance()

        bindViews()
        hideProgressBar()
        signUp()
        gotoLogin()
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


    private fun gotoLogin() {
        login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun signUp(){
       signUp.setOnClickListener{
           //showSuccessAlert()
           var userName:String = name.text.toString()
           var userEmail:String = email.text.toString().trimEnd()
           var userPassword:String = password.text.toString()
           var userConfirmPassword:String = confirmPassword.text.toString()

           if (userName.isEmpty() || userEmail.isEmpty()|| userPassword.isEmpty()|| userConfirmPassword.isEmpty()){
               Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
           }
           else if(userPassword!= userConfirmPassword){
               confirmPassword.error = "Password do not match"

           }
           else if(userPassword.length < 7){
               password.error = "password must be more than 7 characters"
           }
           else{
               fAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(OnCompleteListener { task ->
                   if (task.isSuccessful) {
                       showProgressBar()
                       showSuccessAlert()
                       setDisplayName(userName)
                       fAuth.currentUser!!.sendEmailVerification()
                       fAuth.signOut()
                       hideProgressBar()
                   } else {
                       Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                       Log.d(TAG, "signUp: sign up failed ")
                   }
               })

           }

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
    private fun setDisplayName(name:String){
        val setName = UserProfileChangeRequest
                .Builder()
                .setDisplayName(name)
                .build()
        fAuth.currentUser!!.updateProfile(setName)
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