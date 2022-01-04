package com.tuwaiq.caspstone2.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.caspstone2.R
import com.tuwaiq.caspstone2.home

class LogIn : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp :Button
    private lateinit var mAuth :FirebaseAuth

        private lateinit var ForgetPass:TextView
//        private lateinit var btn_login :Button
//        private lateinit var tv_forget_password: TextView

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()
        edtEmail =findViewById(R.id.edt_email)
        edtPassword= findViewById(R.id.edt_password)
        btnLogin=findViewById(R.id.btnlogin)
        btnSignUp=findViewById(R.id.btnSignUp)

         ForgetPass=findViewById(R.id.ForgetPass)
//         btn_login=findViewById(R.id.et_login_password)
//         tv_forget_password=findViewById(R.id.til_forget_email)


        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java )
            startActivity(intent)
        }

         btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
             val password =edtPassword.text.toString()

    login(email,password);
}
//         btn_login.setOnClickListener {
//
//         }
         ForgetPass.setOnClickListener {
             startActivity(Intent(this@LogIn, ForgetPassWord::class.java))
         }
    }
    private fun login(email:String,password:String){
        //logic for logging user
        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                task->
                if (task.isSuccessful) {
                    //code for loggin in user
                    val intent = Intent(this@LogIn, home::class.java)
                   finish()
                    startActivity(intent)



                }
                else{
                    Toast.makeText(this@LogIn,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }



    }


    }
