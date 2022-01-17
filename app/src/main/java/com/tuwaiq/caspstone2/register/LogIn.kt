package com.tuwaiq.caspstone2.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.caspstone2.R
import com.tuwaiq.caspstone2.Validator
import com.tuwaiq.caspstone2.home

class LogIn : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var validator: Validator

    private lateinit var ForgetPass: TextView
    private val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         mAuth = FirebaseAuth.getInstance()
//         if (mAuth.uid != null)  openApp()
        //عشان اذا المستخدم مسجل من قبل مايطلب منه تسجيل ثاني
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnlogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        validator = Validator()
        ForgetPass = findViewById(R.id.ForgetPass)


        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password);
        }

        ForgetPass.setOnClickListener {
            startActivity(Intent(this@LogIn, ForgetPassWord::class.java))
        }
    }



    

   private fun login(email: String, password: String){


    if (validator.isEmail(email))
            // logic for logging user

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    // code for loggin in user

                    val intent = Intent(this@LogIn, home::class.java)

                    finish()

                    startActivity(intent)

                } else {

                    Toast.makeText(this@LogIn, getString(R.string.LoginToast), Toast.LENGTH_SHORT).show()

                }

            }





        }





    }





