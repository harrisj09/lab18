package com.example.lab18

import android.content.res.XmlResourceParser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.children


class MainActivity : AppCompatActivity() {

    var runtimeTotal = 0
    val myList = arrayListOf<ShortFilm>()  //bunch of horse objects

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spTitle1 = findViewById<Spinner>(R.id.spTitle1)
        val tvFestivalRuntime = findViewById<TextView>(R.id.tvFestivalRuntime)

        // Movie info
        val tvDirector = findViewById<TextView>(R.id.tvDirector)
        val tvProducer = findViewById<TextView>(R.id.tvProducer)
        val tvRuntime = findViewById<TextView>(R.id.tvRuntime)
        val tvWriter = findViewById<TextView>(R.id.tvWriter)
        val tvYear = findViewById<TextView>(R.id.tvYear)
        val tvCountry = findViewById<TextView>(R.id.tvCountry)
        val ivPoster = findViewById<ImageView>(R.id.ivPoster)

        val filmTitles = ArrayList<String>()

        val parser = resources.getXml(R.xml.shorts)
        var eventType = -1
        var myIndex = -1

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                val tagName = parser.name
                when (tagName) {
                    "element" -> {
                        myList.add(ShortFilm())
                        myIndex++
                    }
                    "country" -> {
                        parser.next()
                        myList[myIndex].country = parser.text
                        parser.next()
                    }
                    "director" -> {
                        parser.next()
                        myList[myIndex].director = parser.text
                        parser.next()
                    }
                    "image" -> {
                        parser.next()
                        myList[myIndex].image = parser.text
                        parser.next()
                    }
                    "producer" -> {
                        parser.next()
                        myList[myIndex].producer = parser.text
                        parser.next()
                    }
                    "runtime" -> {
                        parser.next()
                        myList[myIndex].runtime = parser.text.toInt()
                        parser.next()
                    }
                    "title" -> {
                        parser.next()
                        myList[myIndex].title = parser.text
                        filmTitles.add(parser.text)
                        parser.next()
                    }
                    "winner" -> {
                        parser.next()
                        myList[myIndex].winner = parser.text
                        parser.next()
                    }
                    "writer" -> {
                        parser.next()
                        myList[myIndex].writer = parser.text
                        parser.next()
                    }
                    "year" -> {
                        parser.next()
                        myList[myIndex].year = parser.text
                        parser.next()
                    }
                }
            }
            eventType = parser.next()
        }

        val aaFilm = ArrayAdapter(this, android.R.layout.simple_spinner_item, filmTitles)
        spTitle1.adapter = aaFilm

        spTitle1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                tvDirector.text = myList[i].director
                tvProducer.text = myList[i].producer
                tvRuntime.text = myList[i].runtime.toString()
                tvWriter.text = myList[i].writer
                tvYear.text = myList[i].year
                tvCountry.text = myList[i].country

                val resourceId = resources.getIdentifier("${myList[i].image}", "drawable", packageName)
                ivPoster.setBackgroundResource(resourceId)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val ll_shorts = findViewById<LinearLayout>(R.id.ll_shorts)
        val cols = ll_shorts.children
        val col_num = ll_shorts.childCount
        var id_base = 0

        var myCheckBoxListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                runtimeTotal += myList.get(buttonView.id).runtime
            } else {
                runtimeTotal -= myList.get(buttonView.id).runtime
            }
            tvFestivalRuntime.text = runtimeTotal.toString()
        }


        for (i in 0 until filmTitles.size) {
            val cbMovie = CheckBox(this)
            cbMovie.setText(filmTitles[i])
            val current_column = ll_shorts.getChildAt(i%col_num) as LinearLayout
            current_column?.addView(cbMovie)
            cbMovie.id = id_base
            id_base++
            cbMovie.setOnCheckedChangeListener(myCheckBoxListener)
        }
    }
}