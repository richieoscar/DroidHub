package com.example.droidhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth


class DashBoardFragment : Fragment() {


    lateinit var displayName:TextView
    lateinit var mfAuth :FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mfAuth = FirebaseAuth.getInstance()
        var user = mfAuth.currentUser


        var cardView: CardView = view!!.findViewById(R.id.cardView_Upload)
        var cardViewProfile:CardView = view!!.findViewById(R.id.cardView_profile)
        var cardViewFiles:CardView =view!!.findViewById(R.id.cardView_myFiles)
        displayName = view!!.findViewById(R.id.textView_displayName)

        displayName.text = user?.displayName

        cardView.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_dashBoardFragment_to_uploadFragment)

        }

        cardViewProfile.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_dashBoardFragment_to_profileFragment)
        }

        cardViewFiles.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_dashBoardFragment_to_filesFragment)
        }
    }


}