package com.tuwaiq.caspstone2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.caspstone2.Adapter.Messaging2Adapter
import com.tuwaiq.caspstone2.R
import com.tuwaiq.caspstone2.data.Message2
import com.tuwaiq.caspstone2.utils.BotResponse
import com.tuwaiq.caspstone2.utils.Constants.OPEN_GOOGLE
import com.tuwaiq.caspstone2.utils.Constants.OPEN_SEARCH
import com.tuwaiq.caspstone2.utils.Constants.RECEIVE_ID
import com.tuwaiq.caspstone2.utils.Constants.SEND_ID
import com.tuwaiq.caspstone2.utils.Time
import kotlinx.coroutines.*


class ChatBot : AppCompatActivity() {
    private val TAG = "ChatBot"
    var messagesList = mutableListOf<Message2>()
    private lateinit var btn_send : Button
    private lateinit var et_message : EditText
    private lateinit var adapter: Messaging2Adapter
    lateinit var rv_messages: RecyclerView

    private val botList = listOf("Technical support", "Sara", "Application manager", "Technical support")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatbot)
        btn_send=findViewById(R.id.btn_send)
        et_message=findViewById(R.id.et_message)
        rv_messages=findViewById(R.id.rv_messages)
        recyclerView()

        clickEvents()

        // supportActionBar?.hide()

        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
    }

    private fun clickEvents() {

        //Send a message
        btn_send.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = Messaging2Adapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message2(message,SEND_ID,""))
            et_message.setText("")

            adapter.insertMessage(Message2(message, SEND_ID , ""))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        // val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message2(response, RECEIVE_ID,""))

                //Inserts our message into the adapter
                adapter.insertMessage(Message2(response, RECEIVE_ID,""))

                //Scrolls us to the position of the latest message
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                // val timeStamp = Time.timeStamp()
                messagesList.add(Message2(message, RECEIVE_ID,""))
                adapter.insertMessage(Message2(message, RECEIVE_ID,""))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}