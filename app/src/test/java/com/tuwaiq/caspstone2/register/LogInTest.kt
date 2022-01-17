package com.tuwaiq.caspstone2.register

//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import androidx.arch.core.executor.DefaultTaskExecutor
import com.tuwaiq.caspstone2.Validator

class LogInTest{

    private lateinit var validator:Validator
   @BeforeTest
    fun setUp(){
       validator = Validator()

    }

    @Test
    fun checkEmail(){

        val result = validator.isEmail("test123@gmail.com")
        assertEquals(result,true)

    }

}

/*

class LogInTest{

   // @get:Rule
   // val rule = DefaultTaskExecutor()

    @Test
    fun testSum() {
        val expected = 42
        assertEquals(expected, 42)
    }

}
 */


/*
    fun isEmail(email: String):Boolean{
        val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (email.matches(EMAIL_PATTERN.toRegex()))
        {
            return true
        }
        return false
    }

    @Test
    fun checkEmail(){

        val result = isEmail("test123com")
        assertEquals(result,true)

    }
 */