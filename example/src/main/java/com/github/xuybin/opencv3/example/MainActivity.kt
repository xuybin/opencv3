package com.github.xuybin.opencv3.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.xuybin.opencv3.example.CameraCalibration.CameraCalibrationActivity
import com.github.xuybin.opencv3.example.ColorBlobDetection.ColorBlobDetectionActivity
import com.github.xuybin.opencv3.example.FaceDetection.FdActivity
import com.github.xuybin.opencv3.example.ImageManipulations.ImageManipulationsActivity
import com.github.xuybin.opencv3.example.Puzzle15.Puzzle15Activity
import com.github.xuybin.opencv3.example.Tutorial1CameraPreview.Tutorial1Activity
import com.github.xuybin.opencv3.example.Tutorial2Mixedprocessing.Tutorial2Activity
import com.github.xuybin.opencv3.example.Tutorial3Cameracontrol.Tutorial3Activity
import kotlinx.android.synthetic.main.activity_main.*;
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 15格拼图
        btn_15_puzzle.setOnClickListener {
            startActivity(Intent(this, Puzzle15Activity::class.java))
        }
        // 相机校准
        btn_camera_calibration.setOnClickListener {
            startActivity(Intent(this, CameraCalibrationActivity::class.java))
        }
        // 彩色斑点检测
        btn_color_blob_detection.setOnClickListener{
            startActivity(Intent(this, ColorBlobDetectionActivity::class.java))
        }

        // NDK调用测试
        btn_hello_ndk.setOnClickListener {
           if (btn_hello_ndk.text.toString().contains("java",true)) {
               btn_hello_ndk.text= HelloNDKforKotlin().sayHelloKotlin()
           }else{
               btn_hello_ndk.text= HelloNDKforJava.sayHelloJava()
           }
        }

        // 人脸面部检测
        btn_face_detection.setOnClickListener {
            startActivity(Intent(this, FdActivity::class.java))
        }

        // 人脸面部检测和识别
        btn_face_detection_recognizer.setOnClickListener {
            startActivity(Intent(this,FaceDetectionRecognizerActivity::class.java))
        }


        // 图像处理
        btn_image_manipulations.setOnClickListener {
            startActivity(Intent(this, ImageManipulationsActivity::class.java))
        }

        //教程1相机预览
        btn_tutorial_1_camerapreview.setOnClickListener {
            startActivity(Intent(this, Tutorial1Activity::class.java))
        }

        //教程2混合处理
        btn_tutorial_2_mixedprocessing.setOnClickListener {
            startActivity(Intent(this, Tutorial2Activity::class.java))
        }

        //教程3相机控制
        btn_tutorial_3_cameracontrol.setOnClickListener {
            startActivity(Intent(this,Tutorial3Activity::class.java))
        }
    }

}
