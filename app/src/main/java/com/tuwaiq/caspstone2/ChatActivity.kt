package com.tuwaiq.caspstone2

import android.app.Activity
import android.app.VoiceInteractor
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.bumptech.glide.load.Option
import com.bumptech.glide.load.Options
import com.fxn.pix.Options.init
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tuwaiq.caspstone2.Adapter.Message
import com.tuwaiq.caspstone2.Adapter.MessageAdapter
import com.tuwaiq.caspstone2.register.SignUp
import java.security.cert.PKIXRevocationChecker


class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference



    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receveirUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receveirUid + senderUid
        receiverRoom = senderUid + receveirUid

        supportActionBar?.title = name


        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //logic adding msg to list and show recycler view
                    messageList.clear()

                    for (postSnapshot in snapshot.children) {

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })



        //adding the message to database
        sendButton.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {

                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject).addOnSuccessListener {
                        }
                    messageBox.setText("")
                }

        }

        }
    private fun pickImage() {

        val options: com.fxn.pix.Options = com.fxn.pix.Options.init()
            .setRequestCode(100)
            .setCount(5)
            .setFrontfacing(true)
            .setSpanCount(4)
            .setExcludeVideos(true)
            .setScreenOrientation(com.fxn.pix.Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath("/Chat Me/Media/Sent")


        Pix.start(this@ChatActivity, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode==Activity.RESULT_OK && requestCode==200){

            val returnValue =data?.getStringArrayExtra(Pix.IMAGE_RESULTS)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
    PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS ->{
        //if request is cancelled, the result array are empty.
        if (grantResults.isNotEmpty()&& grantResults[0]==PackageManager.PERMISSION_GRANTED) {
            pickImage()
        }
        else{
            Toast.makeText(this,"Approve permission to open Pix ImagePicker",Toast.LENGTH_SHORT).show()
        }


    return
}}



        }

    }











