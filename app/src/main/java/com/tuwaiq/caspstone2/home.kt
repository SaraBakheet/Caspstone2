package com.tuwaiq.caspstone2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tuwaiq.caspstone2.Adapter.User
import com.tuwaiq.caspstone2.Adapter.UserAdapter
import com.tuwaiq.caspstone2.notification.MyWorker
import com.tuwaiq.caspstone2.register.LogIn
import java.util.concurrent.TimeUnit

class home : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var profile: ImageView
    // private lateinit var maps: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        //simpleWork()
        //simpleWork()//simpleWork()//simpleWork()
        //simpleWork()
        //simpleWork()

        myWorkManager()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)


        profile = findViewById(R.id.profile1)
        // maps = findViewById(R.id.maps1)
        userRecyclerView = findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter
        //show in recylrerview

//        var uid: String = ""
//        var id:String ? = mAuth.currentUser?.uid
//
//        if (id != null) {
//
//            uid = id;
//
//            mDbRef.child("user").child(uid).get().addOnSuccessListener {
//                Log.i("firebase", "Got value ${it.value}")
//            }.addOnFailureListener{
//                Log.e("firebase", "Error getting data", it)
//            }
//
//        }


        // المكان
        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java )
            startActivity(intent)
        }

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

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logout) {
            //write the loginnnn for logout
            mAuth.signOut()
            val intent = Intent(this@home, LogIn::class.java)
            finish()
            startActivity(intent)

            return true
        }
        return true

    }

    private fun simpleWork(){
        val mRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .build()
        WorkManager.getInstance(this)
            .enqueue(mRequest)
    }
    private fun myWorkManager(){
        val constraints= Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()
        //minimum interval is 15 min , just wait is min , i will cut this ..
        //to show u quickly
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "my_id",
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest

            )
    }

}






