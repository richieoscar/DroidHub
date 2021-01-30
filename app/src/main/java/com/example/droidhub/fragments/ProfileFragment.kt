package com.example.droidhub

import android.app.usage.StorageStats
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.StorageReference


class ProfileFragment : Fragment() {

    lateinit var mfAuth: FirebaseAuth
    lateinit var profileEmail: TextView
    lateinit var profileName: TextView
    lateinit var fab: FloatingActionButton
    lateinit var displayNameIcon: ImageView
    lateinit var alertDialog: AlertDialog
    lateinit var name: EditText
    lateinit var analytics:StorageReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mfAuth = FirebaseAuth.getInstance()


        bindView()
        profileName.text = mfAuth.currentUser!!.displayName.toString()
        profileEmail.text = mfAuth.currentUser!!.email.toString()

        editDisplayName()


        signOut()

    }

    private fun editDisplayName() {
        displayNameIcon.setOnClickListener {
            editNameAlert()
        }
    }

    private fun bindView() {
        profileEmail = view!!.findViewById(R.id.textView_profile_email)
        profileName = view!!.findViewById(R.id.textView_prifile_name)
        fab = view!!.findViewById(R.id.floatingActionButton)
        displayNameIcon = view!!.findViewById(R.id.imageView_set_display_name)


    }

    private fun signOut() {
        fab.setOnClickListener {
            mfAuth!!.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            context!!.startActivity(intent)
            Toast.makeText(context!!, "Logged Out", Toast.LENGTH_SHORT).show()
            activity!!.finish()

        }
    }


    private fun editNameAlert() {
        var alertBuilder = AlertDialog.Builder(context!!)
        var inflater = LayoutInflater.from(context!!)
        var view = inflater.inflate(R.layout.set_name, null)
        alertBuilder.setView(view)

        alertDialog = alertBuilder.create()
        alertDialog.show()

        var name: EditText = view.findViewById(R.id.edit_set_name)
        var ok: Button = view.findViewById(R.id.button_ok)
        var cancel: Button = view.findViewById(R.id.button_cancel)



        ok.setOnClickListener {
            var setName = name.text.toString()
            setDisplayName(setName)
            alertDialog.dismiss()
        }
        cancel.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    private fun setDisplayName(name: String) {
        val setName = UserProfileChangeRequest
                .Builder()
                .setDisplayName(name)
                .build()
        mfAuth.currentUser!!.updateProfile(setName)
        profileName.text = mfAuth.currentUser!!.displayName.toString()

    }


}