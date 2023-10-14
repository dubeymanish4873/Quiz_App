package com.manishdubey.quiz_app_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {
    lateinit var start:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportActionBar?.hide()

        start=findViewById(R.id.start_btn)
        start.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}