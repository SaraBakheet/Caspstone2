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
    private lateinit var deleteAccount: Button
    private lateinit var logOut:Button
    private lateinit var call:Button


    // private lateinit var UpdateProfile:Button

    private lateinit var builder: AlertDialog.Builder



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)


        user=FirebaseAuth.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        name = findViewById(R.id.name1)
        pass = findViewById(R.id.pass1)
        email = findViewById(R.id.email1)
        deleteAccount = findViewById(R.id.delete)
        logOut=findViewById(R.id.logOut)
        call=findViewById(R.id.call1)


        //  UpdateProfile=findViewById(R.id.UpdateProfile)



        //here

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

        deleteAccount = findViewById(R.id.delete)

        builder = AlertDialog.Builder(this)

        deleteAccount.setOnClickListener {

            builder.setTitle("Delete")

                .setMessage("Do you want to Delete?")

                .setCancelable(true)

                .setPositiveButton("yes") { dialogInterface, it ->

                    // to delete user

                    deleteUser()

                    val intent = Intent(this, LogIn::class.java)

                    startActivity(intent)

                }


                .setNegativeButton("no") { dialogInterface, it ->

                    dialogInterface.cancel()

                    // to cancel

                }

                .show()

        }

        logOut.setOnClickListener {
            builder.setTitle("Chat me")

                .setMessage("Do you want to Log out")

                .setCancelable(true)

                .setPositiveButton("yes") { dialogInterface, it ->



                    LogOutUser()

                    val intent = Intent(this, LogIn::class.java)

                    startActivity(intent)

                }


                .setNegativeButton("no") { dialogInterface, it ->

                    dialogInterface.cancel()

                    // to cancel

                }

                .show()




        }
        call.setOnClickListener {


            val i :Intent = Intent(Intent.ACTION_DIAL)
            intent.data=Uri.parse("")
            startActivity(i)
        }
    }



    private fun deleteUser() {

        // [START delete_user]

        val user = Firebase.auth.currentUser!!

        user.delete()

            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Log.d(TAG, "User account deleted.")

                }

            }

        finish()

    }


    private fun LogOutUser(){
    user.signOut()
    }



        }



























































