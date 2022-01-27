package com.tuwaiq.caspstone2.utils

import com.tuwaiq.caspstone2.utils.Constants.OPEN_GOOGLE
import com.tuwaiq.caspstone2.utils.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {


            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }


            message.contains("how to log out") -> {
                when (random) {
                    0 -> "Go to the settings page and then click on logout ..then confirm"
                    1 -> "Go to the settings page and then click on logout ..then confirm"
                    2 -> "Go to the settings page and then click on logout ..then confirm "
                    else -> "Go to the settings page and then click on logout .. then confirm" }
            }

            message.contains("how delete my account") -> {
                when (random) {
                    0 -> "Go to the settings page, then click on Delete my account, then confirm..for more information, open Google"
                    1 -> "Go to the settings page, then click on Delete my account, then confirm..for more information, open Google"
                    2 -> "Go to the settings page, then click on Delete my account, then confirm..for more information, open Google"
                    else -> "Go to the settings page, then click on Delete my account, then confirm..for more information, open Google"
                }
            }


            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "I don't understand..."
                    else -> "error"
                }
            }
        }
    }
}