package com.tuwaiq.caspstone2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tuwaiq.caspstone2.data.User
import com.tuwaiq.caspstone2.Adapter.UserAdapter
import com.tuwaiq.caspstone2.notification.MyWorker
import com.tuwaiq.caspstone2.ui.ChatBot

import java.util.concurrent.TimeUnit


class home : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var searchItem: SearchView
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var profile: ImageView
    private lateinit var setting: ImageView
    private lateinit var chatBot:ImageView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


         simpleWork()
        myWorkManager()

        // replaceFragment(homeFragment as homeFragment)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)
        profile = findViewById(R.id.profile1)
        setting = findViewById(R.id.setting22)
        chatBot=findViewById(R.id.chatbot)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter
        //show in recylrerview

        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //snapshot get data from database
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                        //عشان مايسوي شات مع نفسه نفس الحساب اللي سجل فيه
                    }

                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java )
            startActivity(intent)
        }
        setting.setOnClickListener {
            val intent = Intent(this, Setting::class.java )
            startActivity(intent)
        }
        chatBot.setOnClickListener{
            val intent = Intent(this, ChatBot::class.java )
            startActivity(intent)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        searchItem = (menu!!.findItem(R.id.search).actionView as SearchView)
        searchItem.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.userList = ArrayList(userList.filter { it.name!!.toLowerCase().contains(newText!!.toLowerCase().toString()) })
        adapter.notifyDataSetChanged()
        return true
    }


    override fun onBackPressed() {
        if (!searchItem.isIconified) {
            searchItem.onActionViewCollapsed();
            onQueryTextChange("")
            return
        } else super.onBackPressed()


    }

    private fun simpleWork(){
        val mRequest:WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .build()
        WorkManager.getInstance(this)
            .enqueue(mRequest)
    }
    private fun myWorkManager(){
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            4,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()
        //minimum interval is 15min , just wait 15 min
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "my_id",
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest
            )
    }
}




























