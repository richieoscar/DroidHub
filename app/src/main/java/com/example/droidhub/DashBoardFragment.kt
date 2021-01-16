package com.example.droidhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController


class DashBoardFragment : Fragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var cardView: CardView = view!!.findViewById(R.id.cardView_Upload)

        cardView.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_dashBoardFragment_to_uploadFragment)

        }
    }


}