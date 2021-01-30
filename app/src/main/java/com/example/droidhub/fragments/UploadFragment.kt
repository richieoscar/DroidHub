package com.example.droidhub

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class UploadFragment : Fragment() {
    private lateinit var chooseFile: Button
    private lateinit var uploadFile: Button
    private lateinit var progressBar: ProgressBar
    lateinit var storageReference: StorageReference
    lateinit var alertDialog: AlertDialog
    private lateinit var image: ImageView
    lateinit var fileName: TextView
    lateinit var sucessful: TextView
    lateinit var uploading: TextView
    lateinit var mFirebaseAuth: FirebaseAuth

    companion object {
        private const val TAG = "UploadFragment"
        private const val PICK_IMAGE_CODE = 1000
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_upload, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindView()
        mFirebaseAuth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference()

        hideProgressBar()
        choseFile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // showProgressBar()
        if (requestCode == PICK_IMAGE_CODE) {
            // showProgressBar()
            fileName.text = data!!.dataString
            Glide.with(view!!.context).load(data.dataString).into(image)
            Toast.makeText(context!!, "File selected", Toast.LENGTH_SHORT).show()
          //  uploadFile(data)
            upload(data)

        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        uploading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
        uploading.visibility = View.INVISIBLE
    }

    private fun bindView() {
        chooseFile = view!!.findViewById(R.id.choose_button2)
        uploadFile = view!!.findViewById(R.id.upload_button)
        progressBar = view!!.findViewById(R.id.progressBar5)
        image = view!!.findViewById(R.id.image_upload)
        fileName = view!!.findViewById(R.id.textView_file_name)
        sucessful = view!!.findViewById(R.id.textView_succesful)
        uploading = view!!.findViewById(R.id.textView_uploading)
    }

    private fun choseFile() {
        chooseFile.setOnClickListener {
            sucessful.visibility = View.INVISIBLE

            val intent = Intent(Intent.ACTION_GET_CONTENT)
                    .setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_CODE)
        }

    }


    private fun uploadFile(data: Intent?) {
        uploadFile.setOnClickListener {
            if (fileName.text.isEmpty()) {
                uploadFile.isEnabled = false
            } else {
                uploadFile.isEnabled = true
                showProgressBar()
                val chooseTask = storageReference.putFile(data!!.data!!)
                val ref = storageReference.child("myimages/")

                val task = chooseTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(context, "Failed ", Toast.LENGTH_SHORT).show()
                        hideProgressBar()
                    }
                    storageReference!!.downloadUrl
                }.addOnCompleteListener { task ->
                    // showProgressBar()
                    if (task.isSuccessful) {
                        showProgressBar()
                        val downLoadUri = task.result
                        val url = downLoadUri!!.toString()

                        Log.d(TAG, url)

                        // Glide.with(context!!).load(url).into(image)
                        image.setImageResource(R.drawable.ic_baseline_photo_24)
                        hideProgressBar()
                        fileName.text = "filename"
                        sucessful.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private fun upload(data: Intent?) {
        uploadFile.setOnClickListener {
            if (fileName.text.isEmpty()) {
                uploadFile.isEnabled = false
            } else {
                uploadFile.isEnabled = true
                showProgressBar()

                var user = mFirebaseAuth.currentUser
                val ref = storageReference.child("myimages").child(user!!.uid).child(UUID.randomUUID().toString())


                /*bug here images are not added consecutively to firebase storage
                but are overriden and replaced in storage
                i manually uploaded other images from console
                 */



                ref.putFile(data!!.data!!).addOnSuccessListener(OnSuccessListener {
                    image.setImageResource(R.drawable.ic_baseline_photo_24)
                    hideProgressBar()
                    fileName.text = "filename"
                    sucessful.visibility = View.VISIBLE
                }).addOnFailureListener(OnFailureListener {
                    Toast.makeText(context, "Failed ", Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                })


            }
        }

    }
}