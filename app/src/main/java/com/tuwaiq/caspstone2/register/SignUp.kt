package com.tuwaiq.caspstone2.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tuwaiq.caspstone2.R
import com.tuwaiq.caspstone2.Adapter.User
import com.tuwaiq.caspstone2.home

class SignUp : AppCompatActivity() {
     private lateinit var edtEmail: EditText
     private lateinit var edtPassword: EditText
     private lateinit var edtName:EditText
     private lateinit var btnSignUp : Button
     private lateinit var mAuth:FirebaseAuth
     private lateinit var mDbRef :DatabaseReference

     private lateinit var db :DatabaseReference
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail =findViewById(R.id.edt_email)

        edtPassword= findViewById(R.id.edt_password)

       edtName=findViewById(R.id.edt_name)


        btnSignUp=findViewById(R.id.btnSignUp)


        btnSignUp.setOnClickListener {
          val name=edtName.text.toString()
           val email =edtEmail.text.toString()
            val password =edtPassword.text.toString()

            signUp(name,email,password)
        }
    }
        private fun signUp(name:String,email:String,password:String){

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task->
                if (task.isSuccessful){
                    //code for jumping to home
                        addUserToDatabase(name,email,mAuth.currentUser?.uid!!)


                }
                else{
                    Toast.makeText(this@SignUp,getString(R.string.SignUpToast),Toast.LENGTH_SHORT)
                        .show()
                }

            }

    }

       private fun addUserToDatabase(name: String,email: String,uid:String){
          mDbRef=FirebaseDatabase.getInstance().getReference() // Java code

           //mDbRef = Firebase.database.reference // kotlin code
           val TAG = "SignupActivity"
          // Log.d(TAG, "addUserToDatabase: " + mDbRef)
          val user = User(name,email, uid)

           //db = FirebaseDatabase.getInstance().getReference("chats")



           mDbRef.child("user").child(uid).setValue(user)
               .addOnSuccessListener {
                   Toast.makeText(this, "Added", Toast.LENGTH_LONG).show()
                   Log.d(TAG, "addUserToDatabase: ")
               }
               .addOnFailureListener {
                   Toast.makeText(this ,"failed" ,Toast.LENGTH_LONG).show()
               }



           val intent = Intent(this@SignUp, home::class.java)
           finish()
           startActivity(intent)


    }


    }














