package com.tuwaiq.caspstone2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.tuwaiq.caspstone2.data.User


class ProfileActivity : AppCompatActivity() {

    private lateinit var mDbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseAuth
    private lateinit var email: TextView
    private lateinit var name: TextView
    private lateinit var prgBar: ProgressBar
    private lateinit var save: Button
    private lateinit var currentName: String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)


        user = FirebaseAuth.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        name = findViewById(R.id.name1)
        email = findViewById(R.id.email1)
        save = findViewById(R.id.saveEmail)
        prgBar = findViewById(R.id.progressBar)

        var uid: String = ""

        var id: String? = mAuth.currentUser?.uid
        if (id != null) {

            uid = id;

            mDbRef.child("user").child(uid).get().addOnSuccessListener {

                // Log.i("firebase", "Got value ${it.getValue(User::class.java)}")

                val user: User? = it.getValue(User::class.java);

                name.setText(user?.name)
                currentName = user?.name!!

                email.setText(user?.email)

            }.addOnFailureListener {

                Log.e("firebase", "Error getting data", it)

            }

        }



        save.setOnClickListener {

            val userEmail =  user.currentUser?.email!!

          //  if (email.text != userEmail && pass.text.isNotEmpty()) {

            //    updateEmail(email.text.toString() , pass.text.toString())

         //   }

            if (name.text.isNotEmpty() && name.text.toString() != currentName){

                updateNameInDb(name.text.toString())

            }



        }

    }



    private fun updateEmail(newEmail:String , password: String ){

        val email = user.currentUser?.email!!

        val user = Firebase.auth.currentUser!!
        val credential  = EmailAuthProvider.getCredential(email, password);

        user.reauthenticate(credential).addOnCompleteListener {

            if(it.isSuccessful) {

                val _user = Firebase.auth.currentUser!!

                _user.updateEmail(newEmail).addOnCompleteListener {

                    if(it.isSuccessful){

                        //email changed in authentication
                        //now should be changed in db
                        updateEmailInDb(newEmail)

                    } else {
                        //SHOW ERROR
                    }



                }


            } else {
                //SHOW ERROR
            }


        }




    }

    private  fun updateEmailInDb(newEmail: String) {

        mDbRef.child("user").child(user.uid!!).child("email").setValue(newEmail).addOnCompleteListener {

            if(it.isSuccessful){

                //EMAIL updated in db

            } else {
                //SHOW ERROR
            }


        }


    }
 
    private fun updateNameInDb(newName: String) {

        prgBar.visibility = View.VISIBLE;
        mDbRef.child("user").child(user.uid!!).child("name").setValue(newName).addOnCompleteListener {

            if(it.isSuccessful){

                currentName = newName;
                //NAME updated in db

            } else {
                //SHOW ERROR
            }
            prgBar.visibility = View.INVISIBLE;

        }


    }

}












