package com.example.droidhub.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.droidhub.ImageAdapter
import com.example.droidhub.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

private const val TAG = "ImagesFragment"
class ImagesFragment : Fragment() {
    lateinit var storageRef: StorageReference


    lateinit var cardView: CardView
    lateinit var recyclerView: RecyclerView
    lateinit var databaseReference: DatabaseReference
    lateinit var mFirebaseAuth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mFirebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()


        getImages()

    }

    private fun getImages() {
        var path = "myimages"
        storageRef = storage.reference.child(path)

        storageRef.downloadUrl.addOnSuccessListener {
            var imgUrls = it.toString()
            Log.d(TAG, "onActivityCreated: $imgUrls")

            var images = ArrayList<String>()
            images.add(imgUrls)


            val adapter = ImageAdapter(view!!.context, images)
            recyclerView = view!!.findViewById(R.id.recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(view!!.context!!)
            recyclerView.adapter = adapter
        }
    }


}