package com.tuwaiq.caspstone2.register

class Validator {

    private val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    constructor()


    fun isEmail(email: String):Boolean{
        if (email.matches(EMAIL_PATTERN.toRegex()))
        {

            return true
        }
        return false
    }


}