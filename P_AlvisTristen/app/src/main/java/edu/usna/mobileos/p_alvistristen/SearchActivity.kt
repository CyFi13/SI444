package edu.usna.mobileos.p_alvistristen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ScrollView

class SearchActivity : AppCompatActivity() {
    lateinit var scrollView: ScrollView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}