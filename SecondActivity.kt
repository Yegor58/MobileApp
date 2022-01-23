package com.example.myapplication111

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import android.widget.Button
import android.media.MediaPlayer
import android.support.v4.content.ContextCompat.getSystemService
import android.media.SoundPool
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.AudioManager
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Geocoder
import java.util.*
import kotlin.collections.ArrayList
import android.icu.util.ULocale.getCountry
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import org.jetbrains.anko.doAsync
import java.net.URI
import java.net.URL


class SecondActivity : Activity() {
    private lateinit var qTextView: TextView
    private lateinit var trueButton:Button
    private lateinit var falseButton:Button
    private lateinit var db: DataBaseHandler
    private lateinit var listQuestions: ArrayList<Question>
    private lateinit var listReplyF: ArrayList<Int>
    private lateinit var listReplyH: ArrayList<Int>
    private lateinit var listReplyB: ArrayList<Int>
    private var currentIndex = 0
    private var numberQuestions = 0
    private var locale:String? = ""
    var mMediaPlayer: MediaPlayer? = null

    private var mSoundPool: SoundPool? = null
    private val mSoundId = 1
    private val mStreamId: Int = 0

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Нельзя!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        db = DataBaseHandler(this)

        locale = this.getResources().getConfiguration().locale.getDisplayCountry()

        qTextView = findViewById(R.id.questionTextView)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        numberQuestions = intent.extras.getString("Number").toInt()
        //qTextView.setText(numberQuestions.toString())
        //var a = 5*2
        //Toast.makeText(this, numberQuestions.toString(), Toast.LENGTH_SHORT).show()


        //db.addQuestionInDB()

        listQuestions = ArrayList()
        // колв-во вопросов по рубрике; кол-во верных ответов
        listReplyF = ArrayList()
        listReplyF.add(0)
        listReplyF.add(0)
        listReplyH = ArrayList()
        listReplyH.add(0)
        listReplyH.add(0)
        listReplyB = ArrayList()
        listReplyB.add(0)
        listReplyB.add(0)
        var cursor = db.getQuestions()
        var iter = 0
        if (cursor != null && cursor.count != 0) {
            while (cursor.moveToNext()) {

                if (listQuestions.size == numberQuestions)
                    break

                var rnds = 1
                if (cursor.count - iter > numberQuestions)
                    rnds = (0..1).random()

                if (rnds == 1) {

                    if (locale == "Россия")
                    {
                        val question = Question(
                            cursor.getString(4),
                            cursor.getInt(2),
                            cursor.getInt(3)
                        )

                        listQuestions.add(question)
                    }
                    else
                    {
                        val question = Question(
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)
                        )

                        listQuestions.add(question)
                    }

                }
                iter += 1
            }
        }

        listQuestions.shuffle()

        // сделать перевод вопросов


        //val client = HttpClient.newBuilder().build();
        //val request = HttpRequest.newBuilder()
        //    .uri(URI.create("http://webcode.me"))
         //   .build();

        //val response = client.send(request, HttpResponse.BodyHandlers.ofString());

        /*var text:String = "hello%20my%20brother"
        var lang1:String = "en"
        var lang2:String = "ru"
        var url:String = "https://translate.google.ru/?sl=$lang1&tl=$lang2&text=$text&op=translate"

        doAsync {

            val apiResponse = URL(url).readText()
            Log.d("INFO", apiResponse)
        }*/

        //

        UpdateQuestion()

        trueButton.setOnClickListener()
        {
            CheckAnswer(1)
        }

        falseButton.setOnClickListener()
        {
            CheckAnswer(0)
        }

    }

    private fun UpdateQuestion()
    {
        //val oldProductName : String =

        qTextView.setText(" " + listQuestions[currentIndex].question + " ")
        var a = 5
    }

    private fun CheckAnswer (userAnswer:Int)
    {
        when (listQuestions[currentIndex].idSport)
        {
            1 -> listReplyF[0]++
            2 -> listReplyH[0]++
            3 -> listReplyB[0]++
        }

        val correcAnswer = listQuestions[currentIndex].Answer

        if (userAnswer == correcAnswer)
        {
            MediaPlayer.create(this, R.raw.rep).start()

            if (locale == "Россия")
                Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Yeah", Toast.LENGTH_SHORT).show()

            when (listQuestions[currentIndex].idSport)
            {
                1 -> listReplyF[1]++
                2 -> listReplyH[1]++
                3 -> listReplyB[1]++
            }
        }
        else
        {
            MediaPlayer.create(this, R.raw.no).start()

            if (locale == "Россия")
                Toast.makeText(this, "Неверно", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
        }

        currentIndex++

        // подведение итогов, переход на новую активити
        if (currentIndex == numberQuestions )
        {

            val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            intent.putExtra("listReplyF", listReplyF)
            intent.putExtra("listReplyH", listReplyH)
            intent.putExtra("listReplyB", listReplyB)
            startActivity(intent)
        }
        else
        {
            UpdateQuestion()
        }
    }
}