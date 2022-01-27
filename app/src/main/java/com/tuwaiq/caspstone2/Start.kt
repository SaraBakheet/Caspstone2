package com.tuwaiq.caspstone2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Start : AppCompatActivity() {
    private lateinit var start: ImageView
    private lateinit var new :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_start)
        start=findViewById(R.id.start_chat)
        new=findViewById(R.id.new2)
        new.setOnClickListener {
                startActivity(Intent(this, Loading::class.java))

            }
        }
    }

