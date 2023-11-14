package com.example.quizapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private var q1result: Long = 0
    private var q2result: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onQuestion1AnswerClicked(view: View) {
        if (view is RadioButton) {
            val checkedAnswer = view.text.toString()
            val correctAnswer = getString(R.string.question1_correct_answer)
            if (checkedAnswer == correctAnswer)
                q1result = 50
        }
    }

    fun onQuestion2AnswerClicked(view: View) {
        if (view is CheckBox) {
            val checkedAnswer = view.text.toString()
            val correctAnswer = getString(R.string.question2_correct_answer)
            if ((view.isChecked && checkedAnswer == correctAnswer) || (!view.isChecked && checkedAnswer != correctAnswer))
                q2result++
            else
                q2result--
        }
    }

    fun reset(view: View) {
        resetOptions()
    }

    private fun resetOptions(){
        rb_question1_answer.clearCheck()
        cb_question2_option1.isChecked=false
        cb_question2_option2.isChecked=false
        cb_question2_option3.isChecked=false
        q1result = 0
        q2result = 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submit(view: View) {
        //getting current date and time
        val currentDateTime = LocalDateTime.now()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
        val date = currentDateTime.format(dateFormatter)
        val time = currentDateTime.format(timeFormatter)

        val result = q1result + if (q2result > 0) 50 else 0
        val message = if(result > 0) "Congratulations! You submitted on current $date and $time, Your achieved $result%"
                      else "Please try again."

        val title = "QuizApp Result"

        showResultDialog(title, message)
    }

    private fun showResultDialog(title: String, message: String){
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("OK")
                { dialog, _ ->
                    resetOptions()
                    dialog.dismiss()
                }
            }
            builder.setMessage(message).setTitle(title)
            builder.create()
        }
        alertDialog.show()
    }
}