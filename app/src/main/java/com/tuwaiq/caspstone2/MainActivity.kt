    package com.tuwaiq.caspstone2

    import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
    import android.widget.Button
    import androidx.fragment.app.Fragment
    import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
    import com.tuwaiq.caspstone2.Adapter.User
    import com.tuwaiq.caspstone2.Adapter.UserAdapter
    import com.tuwaiq.caspstone2.Fragment.homeFragment
    import com.tuwaiq.caspstone2.register.LogIn

    class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList:ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
     private lateinit var homeFragment: Fragment
    private lateinit var setting:Fragment
   // private lateinit var call:Button

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

           // replaceFragment(homeFragment as homeFragment)
        mAuth= FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this,userList)

        userRecyclerView= findViewById(R.id.userRecyclerView)
//        call=findViewById(R.id.call)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.adapter=adapter
        //show in recylrerview

        mDbRef.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //snapshot get data from database
                userList.clear()
                 for (postSnapshot in snapshot.children){
                     val currentUser = postSnapshot.getValue(User::class.java)

                     if(mAuth.currentUser?.uid != currentUser?.uid){
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
        menuInflater.inflate(R.menu.menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId==R.id.logout){
            //write the loginnnn for logout
                mAuth.signOut()
            val intent = Intent(this@MainActivity, LogIn::class.java)
            finish()
            startActivity(intent)

            return  true
        }
        return true
//call.setOnClickListener {
//startActivity(this,call)
}

    }

//        private fun replaceFragment(fragment:homeFragment){
//            if (fragment !=null){
//                val transaction =supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.fragment_container,fragment)
//                transaction.commit()




