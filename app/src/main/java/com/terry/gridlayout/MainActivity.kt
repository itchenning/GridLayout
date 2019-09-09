package com.terry.gridlayout

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() , SeekBar.OnSeekBarChangeListener {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listenActions()
    }

    private fun listenActions() {
        heightSeekbar.setOnSeekBarChangeListener(this)
        countSeekbar.setOnSeekBarChangeListener(this)
        divSeekbar.setOnSeekBarChangeListener(this)
        colSeekbar.setOnSeekBarChangeListener(this)
    }

    private fun getHeight() : Int {
        var height = heightSeekbar.progress * 5
        height = Math.max(10 , height)
        return height
    }

    private fun getCount() : Int {
        var count = countSeekbar.progress
        count = Math.max(2 , count)
        return count
    }

    private fun getDiv() : Int {
        return divSeekbar.progress
    }

    private fun getCol() : Int {
        var col = colSeekbar.progress / 10
        col = Math.max(2 , col)
        return col
    }

    private fun resetViews() {
        val height = getHeight()
        val count = getCount()
        val div = getDiv()
        val col = getCol()
        gridlayout.removeAllViews()
        val params = ViewGroup.LayoutParams(- 2 , - 2)
        params.height = height
        for (index in 0 .. count) {
            val layout = FrameLayout(this)
            val tv = TextView(this)
            val subParams = FrameLayout.LayoutParams(- 1 , - 1)
            subParams.gravity = Gravity.CENTER
            tv.layoutParams = subParams
            tv.setTextColor(Color.WHITE)
            tv.setText("ITEM$index")
            layout.addView(tv)
            layout.setBackgroundColor(Color.parseColor("#3866FE"))
            layout.layoutParams = params
            gridlayout.addView(layout)
        }
        gridlayout.setDividerHeight(div)
        gridlayout.setColumns(col)
    }

    override fun onProgressChanged(seekBar : SeekBar? , progress : Int , fromUser : Boolean) {
        resetViews()
    }

    override fun onStartTrackingTouch(seekBar : SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar : SeekBar?) {

    }
}
