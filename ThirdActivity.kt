package com.example.myapplication111

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.provider.ContactsContract
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication111.R

class ThirdActivity: Activity() {

    private lateinit var footTextView: TextView
    private lateinit var hockTextView: TextView
    private lateinit var baskTextView: TextView
    private lateinit var listReplyF: ArrayList<Int>
    private lateinit var listReplyH: ArrayList<Int>
    private lateinit var listReplyB: ArrayList<Int>
    private lateinit var db:DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        listReplyF = intent.extras.getIntegerArrayList("listReplyF")
        listReplyH = intent.extras.getIntegerArrayList("listReplyH")
        listReplyB = intent.extras.getIntegerArrayList("listReplyB")

        footTextView = findViewById(R.id.footTextView)
        hockTextView = findViewById(R.id.hockTextView)
        baskTextView = findViewById(R.id.baskTextView)

        footTextView.setText(listReplyF[1].toString() + " / " + listReplyF[0].toString() + "")
        hockTextView.setText(listReplyH[1].toString() + " / " + listReplyH[0].toString() + "")
        baskTextView.setText(listReplyB[1].toString() + " / " + listReplyB[0].toString() + "")

        // %-й результат пользователя
        val resultUser = (listReplyF[1] + listReplyH[1] + listReplyB[1]) / (listReplyF[0] + listReplyH[0] + listReplyB[0])
        val totalQuestionCount = (listReplyF[0] + listReplyH[0] + listReplyB[0]).toInt()

        db = DataBaseHandler(this)

        var cursor = db.getResults()
        var countBest: Int = 0
        var countBestCount: Int = 0
        if (cursor != null && cursor.count != 0) {
            while (cursor.moveToNext()) {
                    val result = Result(
                        cursor.getInt(1),
                        cursor.getDouble (2)
                    )

                    // лучший результат за всю историю
                    if (resultUser >= result.percent) {
                        countBest++
                        if (totalQuestionCount >= result.questionCount)
                            countBestCount++
                    }

                }
        }

        if (countBest == cursor?.count && countBestCount == cursor?.count)
        {
            Toast.makeText(this, "Это лучший результат !", Toast.LENGTH_SHORT).show()
            MediaPlayer.create(this, R.raw.winner).start()
        }

        val resultNew = Result((listReplyF[0] + listReplyH[0] + listReplyB[0]).toInt(), resultUser.toDouble())

        db.addResultInDB(resultNew)

    }
}