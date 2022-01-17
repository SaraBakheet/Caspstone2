package com.tuwaiq.caspstone2.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.caspstone2.R

class ForgetPassWord : AppCompatActivity() {

    private lateinit var tv_title:TextView

    private lateinit var til_forget_email:TextView
    private lateinit var Btn_submit:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pass_word)

        supportActionBar?.hide()

        tv_title=findViewById(R.id.tv_title)

        til_forget_email=findViewById(R.id.til_forget_email)
        Btn_submit=findViewById(R.id.Btn_submit)


        Btn_submit.setOnClickListener {

            val email: String = til_forget_email.text.toString().trim{it <= ' '}
            if (email.isEmpty()){
                Toast.makeText(
                    this,
                    getString(R.string.ForgetToast),
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Toast.makeText(
                                this,
                                getString(R.string.EmailSent),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()}
                        else{
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}


