package com.example.droidhub

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.getSystemService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    lateinit var signUp: TextView
    lateinit var progressbar: ProgressBar
    lateinit var login: Button
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var forgotPassword: TextView
    private lateinit var mfAuth: FirebaseAuth
    private lateinit var alertDialog: AlertDialog
    private lateinit var alertDialogNetwork: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mfAuth = FirebaseAuth.getInstance()

        bindViews()

        isNetworkAvailable()
        hideProgressBar()
        loginUser()
        gotoSignUp()

        gotoForgotPassword()


    }


    private fun bindViews() {
        signUp = findViewById(R.id.textView_signUp)
        progressbar = findViewById(R.id.progressBar2)
        login = findViewById(R.id.login_button)
        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        forgotPassword = findViewById(R.id.textView_forgotPassword)

    }

    private fun gotoForgotPassword() {
        forgotPassword.setOnClickListener {
            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun loginUser() {
        login.setOnClickListener {

            var newEmail: String = email.text.toString()
            var newPassword: String = password.text.toString()

            if (newEmail.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Both entries are required", Toast.LENGTH_SHORT).show()
                email.error = "required"
            } else {

                showProgressBar()

                mfAuth.signInWithEmailAndPassword(newEmail, newPassword).addOnCompleteListener(OnCompleteListener { task ->

                    if (isEmailVerified()) {
                        if (task.isSuccessful) {
                            gotoDashBoard()
                            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show()
                            hideProgressBar()
                        } else {
                            hideProgressBar()
                            Toast.makeText(this, "Invalid Details", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        hideProgressBar()
                        showVerifyAlert()
                    }
                })
            }

        }
    }

    private fun isEmailVerified(): Boolean {
        var user = mfAuth.currentUser
        if (user != null && user.isEmailVerified) {
            return true
            Toast.makeText(this, "Email is verified", Toast.LENGTH_SHORT).show()
        } else {
            return false
        }

    }

    private fun isUserSignedIn() {
        if (mfAuth.currentUser != null) {
            gotoDashBoard()
        }
    }

    private fun isNetwork(): Boolean {
        var cm: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun isNetworkAvailable() {
        if (!isNetwork()) {
            showNetworklAlert()
        }
    }

    private fun showNetworklAlert() {
        var alertBuilder = AlertDialog.Builder(this)
        var inflater = LayoutInflater.from(this)
        var view = inflater.inflate(R.layout.network, null)
        alertBuilder.setView(view)

        alertDialogNetwork = alertBuilder.create()
        alertDialogNetwork.show()

        var message: TextView = view.findViewById(R.id.textview_regSuccesful)
        var ok: Button = view.findViewById(R.id.button_ok)

        ok.setOnClickListener {
            alertDialogNetwork.dismiss()
        }
    }


    private fun showVerifyAlert() {
        var alertBuilder = AlertDialog.Builder(this)
        var inflater = LayoutInflater.from(this)
        var view = inflater.inflate(R.layout.verify, null)
        alertBuilder.setView(view)

        alertDialog = alertBuilder.create()
        alertDialog.show()

        var message: TextView = view.findViewById(R.id.textview_regSuccesful)
        var ok: Button = view.findViewById(R.id.button_ok)

        ok.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun gotoDashBoard() {
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun gotoSignUp() {
        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressbar.visibility = View.INVISIBLE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }


}