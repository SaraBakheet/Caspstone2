package com.tuwaiq.caspstone2.register

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LogInTest{
    private lateinit var validation : LogIn
    @Before
    fun setUp (){
        validation= LogIn()
    }
    @Test
    fun checkEmail(){
    val result = validation.login("test123@gmail.com")
        assertThat(result).isTrue()
    }

}