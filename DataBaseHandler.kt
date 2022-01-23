package com.example.myapplication111

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.AccessControlContext
import android.content.ContentValues
import android.widget.Toast
import android.database.Cursor

val DATABASE_NAME = "DB_Guess6"
val TABLE_NAME = "Guess2"
val COL_ID = "id"
val COL_QUESTION = "question"
val COL_ANSWER = "answer"
val COL_SPORT_ID = "id_sport"
val COL_QUESTION_RU = "questionRU"

val TABLE_NAME_1 = "Result2"
val COL_ID_1 = "id"
val COL_QUESTION_1 = "questionCount"
val COL_PERCENT = "percent"

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_1")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_QUESTION + " varchar(250), " +
                COL_ANSWER + " INTEGER, " +
                COL_SPORT_ID + " INTEGER, " +
                COL_QUESTION_RU + " varchar(250))"

        val createTable_1 = "CREATE TABLE " + TABLE_NAME_1 + " ( " + COL_ID_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_QUESTION_1 + " INTEGER, " +
                COL_PERCENT + " REAL )"

        db?.execSQL(createTable)
        db?.execSQL(createTable_1)
        //addQuestionInDB()

    }

    fun addResultInDB (result: Result) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COL_QUESTION_1, result.questionCount)
        cv.put(COL_PERCENT, result.percent)

        db.insert(TABLE_NAME_1,null,cv)
    }

     fun addQuestionInDB () {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COL_QUESTION,
            "Was the 2010 FIFA World Cup held in Brazil ?")
        cv.put(COL_ANSWER, 0)
        cv.put(COL_SPORT_ID, 1)
        cv.put(COL_QUESTION_RU, "В 2010 году ЧМ по футболу проходил в Бразилии ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "Pavel Bure played for Toronto ?")
        cv.put(COL_ANSWER, 0)
        cv.put(COL_SPORT_ID, 2)
        cv.put(COL_QUESTION_RU, "Павел Буре играл в Торонто ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "Chelsea have never won the Champions League ?")
        cv.put(COL_ANSWER, 0)
        cv.put(COL_SPORT_ID, 1)
        cv.put(COL_QUESTION_RU, "Челси никогда не выигрывал ЛЧ ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "Toronto Raptors won the NBA in 2019 ?")
        cv.put(COL_ANSWER, 1)
        cv.put(COL_SPORT_ID, 3)
        cv.put(COL_QUESTION_RU, "Торонто Рэпторс - победитель НБА-2019 ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "Jaromir Jagr won the Stanley Cup twice ?")
        cv.put(COL_ANSWER, 1)
        cv.put(COL_SPORT_ID, 2)
        cv.put(COL_QUESTION_RU, "Яромир Ягр дважды выигывал кубок Стэнли ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
                    "Sir Alex Ferguson joined Manchester United in 1986 ?")
        cv.put(COL_ANSWER, 1)
        cv.put(COL_SPORT_ID, 1)
        cv.put(COL_QUESTION_RU, "Сэр Алекс Фергюссон возглавил МЮ в 1986 году ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "There are 2 teams from New York playing in the NBA ?")
        cv.put(COL_ANSWER, 1)
        cv.put(COL_SPORT_ID, 3)
        cv.put(COL_QUESTION_RU, "В НБА выступают 2 команды из Нью-Йорка ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "Artemiy Panarin was selected in the 2010 draft by Blackhawks ?")
        cv.put(COL_ANSWER, 0)
        cv.put(COL_SPORT_ID, 2)
        cv.put(COL_QUESTION_RU, "Артемий Панарин был задрафтован Чикаго в 2010 году ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "The winner of the hockey tournament in Nagano in 1998 was the Czechoslovakia national team ?")
        cv.put(COL_ANSWER, 1)
        cv.put(COL_SPORT_ID, 2)
        cv.put(COL_QUESTION_RU, "Чехословакия победила на хоккейном турнире в Нагано-98 ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "The NBA consists of 6 divisions ?")
        cv.put(COL_ANSWER, 1)
        cv.put(COL_SPORT_ID, 3)
        cv.put(COL_QUESTION_RU, "НБА состоит из 6 дивизионов ?")
        db.insert(TABLE_NAME,null,cv)

        cv.clear()
        cv.put(COL_QUESTION,
            "Is a team from Missouri playing in the NHL ?")
        cv.put(COL_ANSWER, 1)
        cv.put(COL_SPORT_ID, 2)
        cv.put(COL_QUESTION_RU, "В НХЛ выступает команда из штата Миссури ?")
        db.insert(TABLE_NAME,null,cv)

    }

    fun getQuestions(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        var cursor : Cursor? = null
        if(db != null){
            cursor = db.rawQuery(query,null)
        }
        return cursor
    }

    fun getResults(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME_1"
        val db = this.readableDatabase

        var cursor : Cursor? = null
        if(db != null){
            cursor = db.rawQuery(query,null)
        }
        return cursor
    }
}