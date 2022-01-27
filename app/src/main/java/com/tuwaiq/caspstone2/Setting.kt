package com.tuwaiq.caspstone2

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.tuwaiq.caspstone2.register.LogIn
import org.intellij.lang.annotations.Language
import java.util.*

class Setting : AppCompatActivity() {
    private lateinit var deleteAccount: ImageView
    private lateinit var logOut: ImageView
    private lateinit var call: ImageView
    private lateinit var mDbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseAuth
    private lateinit var builder: AlertDialog.Builder
    private lateinit var mBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_setting)
        supportActionBar?.hide()



        user = FirebaseAuth.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        deleteAccount = findViewById(R.id.delete)
        logOut = findViewById(R.id.logOut)
        call = findViewById(R.id.call)
        mBtn = findViewById(R.id.chang_lan)
        deleteAccount = findViewById(R.id.delete)

        builder = AlertDialog.Builder(this)

        deleteAccount.setOnClickListener {

            builder.setTitle(getString(R.string.DeleteDi))

                .setMessage(getString(R.string.MsgDelete))

                .setCancelable(true)

                .setPositiveButton(getString(R.string.YesDel)) { dialogInterface, it ->

                    // to delete user

                    deleteUser()
                }

                .setNegativeButton(getString(R.string.NoDel)) { dialogInterface, it ->

                    dialogInterface.cancel()


                }
                .show()

        }

        logOut.setOnClickListener {
            builder.setTitle(getString(R.string.titleLogOut))

                .setMessage(getString(R.string.MsgLog))

                .setCancelable(true)

                .setPositiveButton(getString(R.string.LogYes)) { dialogInterface, it ->


                    LogOutUser()

                    val intent = Intent(this, LogIn::class.java)

                    startActivity(intent)

                }


                .setNegativeButton(getString(R.string.Nolog)) { dialogInterface, it ->

                    dialogInterface.cancel()

                    // to cancel

                }

                .show()


        }
        call.setOnClickListener {


            val i: Intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("")
            startActivity(i)
        }
        mBtn.setOnClickListener {
            showChangeLang()
        }
    }

    private fun showChangeLang() {
        val listLanguege = arrayOf("Arabic", "English","French","Chinese")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Language")
        builder.setSingleChoiceItems(listLanguege, -1) { dialog, which ->
            when (which) {
                0 -> {
                    setLocate("ar")
                    recreate()
                }
                1 -> {
                    setLocate("en")
                    recreate()
                }
                2 ->{
                    setLocate("Fr")
                    recreate()
                }
                3 ->{
                    setLocate("ch")
                    recreate()
                }

            }
            dialog.dismiss()

        }
        val mDialog = builder.create()
        mDialog.show()
    }

    private fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources?.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit()
        editor.putString("MY_LANG", Lang)
            .apply()
    }

    private fun loadLocate() {
        val pref = getSharedPreferences("Setting", Activity.MODE_PRIVATE)
        val language = pref.getString("MY_LANG", "")
        setLocate(language.toString())
    }



    private fun deleteUser() {

        // [START delete_user]

        val user = Firebase.auth.currentUser!!

        user.delete()

            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Log.d(TAG, "User account deleted.")

                }

            }
        val intent = Intent(this, LogIn::class.java)

        startActivity(intent)
        finish()

    }


    private fun LogOutUser() {
        user.signOut()
    }




    }



