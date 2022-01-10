package com.tuwaiq.caspstone2

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.tuwaiq.caspstone2.Adapter.User
import com.tuwaiq.caspstone2.register.LogIn


class ProfileActivity : AppCompatActivity() {

    private lateinit var mDbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseAuth
    private lateinit var pass: TextView
    private lateinit var email: TextView
    private lateinit var name: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)
        updateProfile()

        user = FirebaseAuth.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        name = findViewById(R.id.name1)
        pass = findViewById(R.id.pass1)
        email = findViewById(R.id.email1)


        var uid: String = ""

        var id: String? = mAuth.currentUser?.uid
        if (id != null) {

            uid = id;

            mDbRef.child("user").child(uid).get().addOnSuccessListener {

                // Log.i("firebase", "Got value ${it.getValue(User::class.java)}")

                val user: User? = it.getValue(User::class.java);

                name.setText(user?.name)

                email.setText(user?.email)

            }.addOnFailureListener {

                Log.e("firebase", "Error getting data", it)

            }

        }


    }


    private fun updateProfile() {
        // [START update_profile]
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = "Jane Q. User"
            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }


    }
}



























































