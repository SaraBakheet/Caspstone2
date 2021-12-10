package com.tuwaiq.caspstone2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
   private lateinit var edtName:EditText
    private lateinit var btnSignUp : Button
    private lateinit var mAuth:FirebaseAuth

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
           val email =edtEmail.text.toString()
            val password =edtPassword.text.toString()

            signUp(email,password)
        }
    }
    private fun signUp(email:String,password:String){
    //logic of creating user
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task->
                if (task.isSuccessful){
                    //code for jumping to home
                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    startActivity(intent)

                } else{
                    Toast.makeText(this@SignUp,"errrror ya Sara error",Toast.LENGTH_SHORT)
                        .show()
                }

            }

}
}
