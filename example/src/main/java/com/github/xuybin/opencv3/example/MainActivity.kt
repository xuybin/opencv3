package com.github.xuybin.opencv3.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.xuybin.opencv3.HelloNDKforJava
import com.github.xuybin.opencv3.HelloNDKforKotlin
import kotlinx.android.synthetic.main.activity_main.*;
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_15_puzzle.setOnClickListener {
            startActivity(Intent(this,Puzzle15Activity::class.java))
        }

        btn_hello_ndk.setOnClickListener {
           if (btn_hello_ndk.text.toString().contains("java",true)) {
               btn_hello_ndk.text=HelloNDKforKotlin().sayHelloKotlin()
           }else{
               btn_hello_ndk.text=HelloNDKforJava.sayHelloJava()
           }
        }
        btn_face_detection.setOnClickListener {
            startActivity(Intent(this,FdActivity::class.java))
        }
        btn_face_detection_recognizer.setOnClickListener {
            startActivity(Intent(this,FdActivity1::class.java))
        }

    }

}
