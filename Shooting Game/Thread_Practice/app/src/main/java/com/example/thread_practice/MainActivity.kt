package com.example.thread_practice

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.thread_practice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var bgm: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val joystick = binding.joystick
        val player = binding.player
        val topMargin = binding.topMargin
        val playGround = binding.playGround

        bgm = MediaPlayer.create(this, R.raw.bgm_klee_ost_extended_to_make_you_happy_tnbee_mix)
        bgm!!.start()
        JoyStick(this).joyStickCall(joystick, player, topMargin, playGround)

        val minute = binding.minute
        val second = binding.second
        val duration = bgm!!.duration/1000 + 1
        with(Timer(this, duration)) {
                timeThread(minute, second).isDaemon = true
                timeThread(minute, second).start()
        }

        val missile1 = binding.something1
        val missile2 = binding.something2
        val missile3 = binding.something3
        val missile4 = binding.something4
        val oops = binding.oops
        val heart1 = binding.heart1
        val heart2 = binding.heart2
        val heart3 = binding.heart3

        with(Missile(this)){
            shooting(4, missile1, player, 2000, oops, heart1, heart2, heart3)
            shooting(10, missile1, player, 2000, oops, heart1, heart2, heart3)
            shooting(10, missile2, player, 2500, oops, heart1, heart2, heart3)
            shooting(10, missile3, player, 3000, oops, heart1, heart2, heart3)
            shooting(10, missile4, player, 3500, oops, heart1, heart2, heart3)
            shooting(13, missile1, player, 2000, oops, heart1, heart2, heart3)
            shooting(14, missile4, player, 2000, oops, heart1, heart2, heart3)
            shooting(15, missile2, player, 2000, oops, heart1, heart2, heart3)
            shooting(15, missile3, player, 2000, oops, heart1, heart2, heart3)
        }
    }

    override fun onPause() {
        super.onPause()
        bgm!!.pause()
    }

    override fun onRestart() {
        super.onRestart()
        bgm!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        bgm!!.release()
        bgm = null
    }
}