package com.example.droidhub.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.droidhub.ImageAdapter
import com.example.droidhub.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


private const val TAG = "FilesFragment"
lateinit var storage: FirebaseStorage

class FilesFragment : Fragment() {
    lateinit var image:Button
    lateinit var audio:Button
    lateinit var video:Button
    lateinit var documents:Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_files, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       bindViews()
        seeImages()
    }

    private fun bindViews() {
        image =view!!.findViewById(R.id.button_images)
        audio = view!!.findViewById(R.id.button_Audio)
        video  = view!!.findViewById(R.id.button_video)
         documents = view!!.findViewById(R.id.button_documents)
    }

    fun seeImages(){
        image.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_filesFragment_to_imagesFragment)
        }
    }


}

//    private fun listFiles() = CoroutineContext(Dispatchers.io)
//        val  images = storageRef.child("images/").listAll().
//        val imgUrls = mutableListOf<String>()
//        for(image in images.
//    }

