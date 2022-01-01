package com.example.thread_practice

import android.content.Context
import android.os.Handler
import android.widget.TextView

class Timer(context: Context, duration: Int) {
    var minute: Int = duration/60
    var second: Int = duration - minute*60
    val totalTime = duration/1000 + 1

    var handler: Handler = Handler()

    fun timeThread(minuteText: TextView, secondText: TextView) :Thread {
        val timeThread = Thread() {
            while (minute >= 0 && second >= 0) {

                second -= 1

                if (second < 0 && minute > 0){
                    second = 59
                    minute -= 1
                } else if (minute == 0 && second == -1)
                    second = 0

                handler.post {
                    minuteText.text = "0$minute "
                    if(second < 10)
                        secondText.text = ": 0$second"
                    else
                        secondText.text = ": $second"
                }

                Thread.sleep(1000L)
            }
        }
        return timeThread
    }
}
