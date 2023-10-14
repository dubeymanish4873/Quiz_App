package com.manishdubey.quiz_app_

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var questionsList:ArrayList<question_model>
    private var index:Int = 0
    lateinit var questionModel: question_model


    private var correctAnswerCount:Int=0
    private var wrongAnswerCount:Int=0

    lateinit var countDown:TextView
    lateinit var questions:TextView
    lateinit var option1:Button
    lateinit var option2:Button
    lateinit var option3:Button
    lateinit var option4:Button



    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    private var timer:CountDownTimer?=null

    private val handler = Handler()
    private val interval = 1000L  // 1 second interval
    private var elapsedTime = 0L
    private var duration = TimeUnit.SECONDS.toMillis(15)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
       var next=findViewById<Button>(R.id.nextBtn)
        countDown=findViewById(R.id.countdown)
        questions=findViewById(R.id.questions)
        option1=findViewById(R.id.option1)
        option2=findViewById(R.id.option2)
        option3=findViewById(R.id.option3)
        option4=findViewById(R.id.option4)



        questionsList= ArrayList()
        questionsList.add(question_model("What is the famous platform for product sourcing??","Ebay.com","daraz.pk","Alibaba.com","Walmart.com","Alibaba.com"))
        questionsList.add(question_model("B2B stand for??","Buy 2 Business","Business 2 Buy","Business to Business","Buy to Buy","Business to Business"))
        questionsList.add(question_model("VA stand for??","Virtual Assistant","Virtual Amazon","Virtual Affect","Video Effect","Virtual Assistant"))
        questionsList.add(question_model("What is UPC?","Universal Power Control","Universe Product Control"," Universal Product Code","Universe Product Code","Universal Product Code"))
        questionsList.add(question_model("EAN stands for:","European Article Number","European Access Number","European Amazon Number","Europe Access Number","European Article Number"))


        //questionsList.shuffle()
        questionModel= questionsList[index]

        setAllQuestions()

        countdown()


        next.setOnClickListener{
        handleCountdownFinish()
        }







    }


    private fun countdown() {
        handler.removeCallbacksAndMessages(null) // Cancel any pending callbacks
        handler.postDelayed(object : Runnable {
            override fun run() {
                // Update the countdown here
                val remainingTime = duration - elapsedTime
                if (remainingTime > 0) {
                    countDown.text = formatTime(remainingTime)
                    elapsedTime += interval
                    handler.postDelayed(this, interval)
                } else {
                    handleCountdownFinish()
                }
            }
        }, interval)
    }

    private fun formatTime(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds)
    }

    private fun handleCountdownFinish() {
        index++
        if (index < questionsList.size) {
            questionModel = questionsList[index]
            setAllQuestions()
            resetBackground()
            enableButton()
            elapsedTime = 0L // Reset elapsed time
            countdown() // Start a new countdown
        } else {
            gameResult()
        }
    }

    private fun correctAns(option: Button){
        option.background=getDrawable(R.drawable.right_ans)

        correctAnswerCount++



    }
    private fun wrongAns(option:Button){

        option.background=resources.getDrawable(R.drawable.wrong_btn)

        wrongAnswerCount++


    }

    private fun gameResult(){
        var intent=Intent(this,result_activity::class.java)

        intent.putExtra("correct",correctAnswerCount.toString())
        intent.putExtra("total",questionsList.size.toString())

        startActivity(intent)
    }


    private fun setAllQuestions() {
        questions.text=questionModel.question
        option1.text=questionModel.option1
        option2.text=questionModel.option2
        option3.text=questionModel.option3
        option4.text=questionModel.option4
    }
    private fun enableButton(){
        option1.isClickable=true
        option2.isClickable=true
        option3.isClickable=true
        option4.isClickable=true

    }
    private fun disableButton(){
        option1.isClickable=false
        option2.isClickable=false
        option3.isClickable=false
        option4.isClickable=false

    }
    private fun resetBackground(){
        option1.background=resources.getDrawable(R.drawable.btn_design)
        option2.background=resources.getDrawable(R.drawable.btn_design)
        option3.background=resources.getDrawable(R.drawable.btn_design)
        option4.background=resources.getDrawable(R.drawable.btn_design)

    }
    fun option1Clicked(view:View){
        disableButton()
        if(questionModel.option1==questionModel.ans){
            option1.background=resources.getDrawable(R.drawable.right_ans)


            correctAns(option1)

        }
        else{
            wrongAns(option1)
        }
    }

    fun option2Clicked(view:View){
        disableButton()
        if(questionModel.option2==questionModel.ans){
            option2.background=resources.getDrawable(R.drawable.right_ans)


            correctAns(option2)

        }
        else{
            wrongAns(option2)
        }
    }
    fun option3Clicked(view:View){
        disableButton()
        if(questionModel.option3==questionModel.ans){

            option3.background=resources.getDrawable(R.drawable.right_ans)


            correctAns(option3)


        }
        else{
            wrongAns(option3)
        }
    }
    fun option4Clicked(view:View){
        disableButton()
        if(questionModel.option4==questionModel.ans){
            option4.background=resources.getDrawable(R.drawable.right_ans)


            correctAns(option4)

        }
        else{
            wrongAns(option4)
        }
    }

    override fun onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            finish()
        }

        else {
            backToast = Toast.makeText(baseContext, "DOUBLE PRESS TO QUIT GAME", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()

    }
}