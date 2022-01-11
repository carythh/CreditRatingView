package com.example.creditratingview

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mCreditRatingView = findViewById<CreditRatingView>(R.id.mCreditRatingView)
        mCreditRatingView.refreshData(
            listOf("0分", "60分", "70分", "80分", "90分", "100分"),
            listOf("D", "C", "B", "A","S") ,
            listOf(0.3f, 0.175f, 0.175f, 0.175f, 0.175f))
    }

}