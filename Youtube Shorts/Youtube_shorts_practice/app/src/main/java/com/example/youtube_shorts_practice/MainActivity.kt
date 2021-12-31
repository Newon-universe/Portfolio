package com.example.youtube_shorts_practice

import android.animation.ValueAnimator
import android.content.Intent
import android.media.MediaController2
import android.media.MediaPlayer
import android.media.session.MediaController
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.VideoView
import com.example.youtube_shorts_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    val TAG: String ="로그"
    private var isLiked = false
    private var moveShorts = false
    private var videoTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val bindingView = binding.root

        setContentView(bindingView)

        val mediaController = android.widget.MediaController(this)
        mediaController.hide()

        // 어플에 uri 를 올려놓는
        val uri = Uri.parse("android.resource://$packageName/${R.raw.cat_sample}")

        binding.videoView.setVideoURI(uri)
        binding.videoView.seekTo(500)
        binding.videoView.requestFocus()
        binding.progressBar.max = binding.videoView.duration
        binding.videoView.start()



        // 영상 클릭으로 일시정지 및 재생

        binding.videoView.setOnClickListener{
            if (binding.videoView.isPlaying) {
                binding.videoView.pause()
                val playAnimator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(1000)
                playAnimator.addUpdateListener { animation: ValueAnimator ->
                    binding.player.progress = animation.getAnimatedValue() as Float
                }
                binding.player.visibility = View.VISIBLE
                playAnimator.start()
                Handler().postDelayed(Runnable {
                    binding.player.visibility = View.INVISIBLE
                    //여기에 딜레이 후 시작할 작업들을 입력
                }, 1000)
            } else {
                binding.videoView.start()
                val stopAnimator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(1000)
                stopAnimator.addUpdateListener { animation: ValueAnimator ->
                    binding.player.progress = animation.animatedValue as Float
                }
                binding.player.visibility = View.VISIBLE
                stopAnimator.start()
                Handler().postDelayed(Runnable {
                    binding.player.visibility = View.INVISIBLE
                    //여기에 딜레이 후 시작할 작업들을 입력
                }, 1000)
            }
        }

        // 애니메이션 커스텀
        // Custom animation speed or duration.텀
        // ofFloat(시작지점 %, 종료지점 %) .setDuration(지속시간, 1000 = 1초)텀

        binding.thumbsUp.setOnClickListener {

            if(isLiked == false) {
                val thumbsUpAnimator = ValueAnimator.ofFloat(0f, 0.6f).setDuration(1000)
                thumbsUpAnimator.addUpdateListener { animation: ValueAnimator ->
                    binding.thumbsUp.setProgress(
                        animation.getAnimatedValue() as Float
                    )
                }
                thumbsUpAnimator.start()
                isLiked = true
                binding.isLikedCount.setText("776")
            } else {
                val thumbsDownAnimator = ValueAnimator.ofFloat(0.6f, 1f).setDuration(700)
                thumbsDownAnimator.addUpdateListener { animation: ValueAnimator ->
                    binding.thumbsUp.setProgress(
                        animation.getAnimatedValue() as Float
                    )
                }
                thumbsDownAnimator.start()
                isLiked = false
                binding.isLikedCount.setText("775")
            }
        }


        // 부메랑 만들기
        binding.createShorts.setOnClickListener{
            val cameraIntent = Intent(this, CameraActivity::class.java)
            startActivity(cameraIntent)
            moveShorts = true
        }


        // 공유하기
        binding.share.setOnClickListener{

            binding.videoView.pause()

            val sharingIntent = Intent (Intent.ACTION_SEND)

            sharingIntent.addCategory(Intent.CATEGORY_DEFAULT)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=$packageName")

            sharingIntent.putExtra(Intent.EXTRA_TITLE, "Youtube_shrots")
            sharingIntent.setType("text/plain")
            startActivity(Intent.createChooser(sharingIntent, "앱을 선택해주세요."))

        }
        Log.d(TAG, "MainActivity - onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity - onStart() called")
    }

    override fun onResume() {
        super.onResume()

        moveShorts = false

        if(binding.videoView.isPlaying == false){
            binding.videoView.start()
        }

    }

    override fun onPause() {
        super.onPause()
        videoTime = binding.videoView.currentPosition
        Log.d(TAG, "MainActivity - onPause() called / 영상 재생 프레임 $videoTime")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity - onStop() called / $videoTime")
        if(moveShorts == true) {
            Toast.makeText(this, "부메랑으로 이동합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "MainActivity - onRestart() called")
        if(videoTime != 0) {
            binding.videoView.seekTo(videoTime)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.stopPlayback()
        Log.d(TAG, "MainActivity - onDestroy() called")
    }

}