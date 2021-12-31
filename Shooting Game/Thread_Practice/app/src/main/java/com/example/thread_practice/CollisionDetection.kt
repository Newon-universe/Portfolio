package com.example.thread_practice

import android.widget.ImageView
import kotlin.math.abs
import kotlin.random.Random
import java.lang.Runnable as Runnable

fun crash(image1: ImageView, image2: ImageView):Boolean {
    var check = false

    if (abs(   (image1.x - image2.x) + ( (image1.width - image2.width) / 2)    )
        < (image2.width + image1.width )/2 &&
        abs(   (image1.y - image2.y) + ( (image1.height - image2.height) / 2)  )
        < (image2.height + image1.height )/2){
        check = true
    }

    return check
}

fun timeRandom(from: Int, to: Int) : Int {
    var number = Random
    return number.nextInt(to - from) + from
}