package com.example.thread_practice

import android.animation.ObjectAnimator
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import io.github.controlwear.virtual.joystick.android.JoystickView
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class JoyStick(context: Context) {
    var handler: Handler = Handler()
    var runnable: Runnable = Runnable {  }

    private var x: Float = 0.0F
    private var y: Float = 0.0F

    fun joyStickCall(joystick: JoystickView, player: LottieAnimationView, topMargin: ImageView, playGround: LinearLayout){

        val sensitivity = 0.2

        val joystick: JoystickView = joystick
        joystick.setOnMoveListener({ angle: Int, strength: Int ->

            val nextX = (strength * sensitivity * cos((1 - angle.toFloat() / 360) * 2 * PI)).toFloat()
            val nextY = (strength * sensitivity * sin((1 - angle.toFloat() / 360) * 2 * PI)).toFloat()

            val restrictionX:Float = playGround.x - playGround.width/2 - 14

            val restrictionTopY:Float = topMargin.y
            val restrictionBottomY:Float = playGround.height.toFloat()

            // 튀는거 보정
            if (player.x + nextX in 0.0..restrictionX.toDouble())
                x += nextX

            if (player.y + nextY in restrictionTopY..restrictionBottomY)
                y += nextY

            joystickMove(player, x, y, nextX, nextY, topMargin,playGround,25)

        }, 25)
    }

    fun joystickMove(playerView: ImageView, posix:Float, posiy:Float, nextX:Float, nextY:Float, topMargin: ImageView, playGround: LinearLayout, duration1: Long) {

        val restrictionX:Float = playGround.x - playGround.width/2 - 14

        val restrictionTopY:Float = topMargin.y
        val restrictionBottomY:Float = playGround.height.toFloat()




        // 이동 예외처리 - 경계선 스턱 보정
        runnable = Runnable {
            when {
                playerView.x + nextX <= 0 -> playerView.x = 0.toFloat()
                playerView.x + nextX >= restrictionX -> playerView.x = restrictionX
                else -> {
                    ObjectAnimator.ofFloat(playerView, "translationX", posix).apply {
                        duration = duration1
                        start()
                    }
                }
            }
            when {
                playerView.y + nextY <= restrictionTopY -> playerView.y = restrictionTopY
                playerView.y + nextY >= restrictionBottomY -> playerView.y = restrictionBottomY
                else -> {
                    ObjectAnimator.ofFloat(playerView, "translationY", posiy).apply {
                        duration = duration1
                        start()
                    }
                }
            }

        }
        handler.post(runnable)
        //                handler.postDelayed(runnable, duration1)
    }

}
