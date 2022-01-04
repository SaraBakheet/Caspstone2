package com.tuwaiq.caspstone2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tuwaiq.caspstone2.Adapter.User

class ProfileActivity : AppCompatActivity() {


    private lateinit var mDbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var name: TextView
    private lateinit var pass: TextView
    private lateinit var email: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        name = findViewById(R.id.name1)
        pass = findViewById(R.id.pass1)
        email = findViewById(R.id.email1)

        //here
        var uid: String = ""
        var id:String ? = mAuth.currentUser?.uid

        if (id != null) {

            uid = id;

            mDbRef.child("user").child(uid).get().addOnSuccessListener {
               // Log.i("firebase", "Got value ${it.getValue(User::class.java)}")
                val user: User? = it.getValue(User::class.java);
                name.setText(user?.name)
                email.setText(user?.email)
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

        }

    }


}