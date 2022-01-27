package com.tuwaiq.caspstone2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tuwaiq.caspstone2.register.LogIn

class Loading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        supportActionBar?.hide()
        Handler().postDelayed({
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }, 3000)
    }
}