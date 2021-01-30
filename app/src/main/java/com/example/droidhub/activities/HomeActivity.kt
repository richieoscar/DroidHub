package com.example.droidhub.activities

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.droidhub.R
@Suppress("Deprecation")
class HomeActivity : AppCompatActivity() {

    lateinit var alertDialogNetwork: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        isNetworkAvailable()

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()

    }

    private fun isNetwork(): Boolean {
        var cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
}