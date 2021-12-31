package com.example.youtube_shorts_practice

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.youtube_shorts_practice.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat

class CameraActivity : AppCompatActivity() {

    val TAG: String ="로그"

    private lateinit var binding: ActivityCameraBinding

    private val REQUEST_VIDEO_CAPTURE_CODE =1
    private var videoUri: Uri?=null
    private var isVideoPlaying = false

    private val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    private val CAMERA_PERMISSION_FLAG = 100
    private val STORAGE_PERMISSION = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val STORAGE_PERMISSION_FLAG = 200




    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            when(requestCode){
                REQUEST_VIDEO_CAPTURE_CODE->{
                    binding.video.setVideoURI(videoUri)
                    binding.video.requestFocus()
                }
            }
        }
    }

    private fun checkPermission(permissions: Array<out String>, flag:Int):Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(permission in permissions) {
                if(ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,permissions,flag)
                    return false
                }
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_PERMISSION_FLAG -> {
                for(grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "카메라 권한을 승인해야지만 앱을 사용 할 수 있습니다.", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        checkPermission(STORAGE_PERMISSION, STORAGE_PERMISSION_FLAG)
                    }
                }
            }
            STORAGE_PERMISSION_FLAG -> {
                for(grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "저장소 권한을 승인해야지만 앱을 사용 할 수 있습니다.", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        var bindingView = binding.root

        setContentView(bindingView)

        binding.cancel.setOnClickListener {
            finish()
        }

        if (checkPermission(CAMERA_PERMISSION, CAMERA_PERMISSION_FLAG)) {
            checkPermission(STORAGE_PERMISSION, STORAGE_PERMISSION_FLAG)
        }

        fun newVideoFileName():String{
            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
            val filename = sdf.format(System.currentTimeMillis())
            return "${filename}.mp4"
        }

        binding.cameraBtn.setOnClickListener{
            val recordVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            val videoFile= File(
                File("${filesDir}/video").apply{
                    if(!this.exists()){
                        this.mkdirs()
                    }
                },
                newVideoFileName()
            )
            videoUri = FileProvider.getUriForFile(
                this,
                "com.blacklog.cameraxvideo.fileprovider",
                videoFile
            )
            recordVideoIntent.resolveActivity(packageManager)?.also{
                recordVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,videoUri)
                startActivityForResult(recordVideoIntent, REQUEST_VIDEO_CAPTURE_CODE)
            }
        }

        binding.cameraBtn.setOnClickListener {
            when(isVideoPlaying){
                true -> {
                    isVideoPlaying = false
                    binding.video.stopPlayback()
                    binding.video.setVideoURI(videoUri)
                }
                false -> {
                    isVideoPlaying = true
                    binding.video.start()
                }
            }
        }
        binding.video.setOnCompletionListener {
            isVideoPlaying = false
        }
    }


}
