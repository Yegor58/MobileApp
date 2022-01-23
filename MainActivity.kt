package com.example.myapplication111

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var tView:TextView
    private lateinit var seekB:SeekBar
    private lateinit var tInfo:TextView
    private lateinit var hButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tView = findViewById(R.id.headTextView)
        seekB = findViewById(R.id.seekBar)
        tInfo = findViewById(R.id.infoTextView)
        hButton = findViewById(R.id.headButton)

        val locale = this.getResources().getConfiguration().locale.getDisplayCountry()
        var headTxt:String = getString(R.string.headTextEn)
        var headBtn:String = getString(R.string.headButtonEn)


        if (locale == "Россия")
        {
            headTxt = getString(R.string.headText)
            headBtn = getString(R.string.headButton)

            tView.setText(headTxt)
            hButton.setText(headBtn)
        }


        hButton.setOnClickListener()
        {

            if (seekB.progress > 4 )
            {
                //Toast.makeText(this, "Ура", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("Number", seekB.progress.toString())
                startActivity(intent)
            }
            else
            {
                //Toast.makeText(this, "Что-то не так :(", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("Number", 5.toString())
                startActivity(intent)
            }
        }

        seekB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (locale == "Россия")
                    tInfo.setText(getString(R.string.firstHalf) + " " + seekB.progress + " " + getString(R.string.secondHalf))
                else
                    tInfo.setText(getString(R.string.firstHalfEn) + " " + seekB.progress + " " + getString(R.string.secondHalfEn))
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })
    }

}
