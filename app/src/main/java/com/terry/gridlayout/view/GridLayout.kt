package com.terry.gridlayout.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TableLayout
import com.relax.sleepmelody.utils.DeviceUtils
import com.terry.gridlayout.R


/**
 * Author: Terry
 * Date: 2019-09-09 11:53
 * Comment:
 */
class GridLayout(context : Context , attrs : AttributeSet) : ViewGroup(context , attrs) {
    private val TAG = GridLayout::class.java.simpleName
    private var mColumns = 2
    private var mDividerHeight = 1

    init {
        val attributes = context.obtainStyledAttributes(attrs , R.styleable.GridLayout)
        mColumns = attributes.getInt(R.styleable.GridLayout_columns , 3)
        mDividerHeight = attributes.getDimension(R.styleable.GridLayout_divider_height , 1F).toInt()
        attributes.recycle()
    }

    fun setDividerHeight(dividerHeight : Int) {
        this.mDividerHeight = dividerHeight
        requestLayout()
    }

    fun setColumns(cols : Int) {
        this.mColumns = cols
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec : Int , heightMeasureSpec : Int) {
        val height = getChildAt(0).layoutParams.height
        val rows = if (childCount % mColumns == 0) childCount / mColumns else childCount / mColumns + 1
        val width = measureWidthExactly()
        setMeasuredDimension(width , rows * height + mDividerHeight * (rows - 1))
    }

    private fun measureWidthExactly() : Int {
        val params = layoutParams
        val sw = DeviceUtils.getScreenWidth(context)
        when (params) {
            is LinearLayout.LayoutParams -> return sw - params.leftMargin - params.rightMargin
            is RelativeLayout.LayoutParams -> return sw - params.leftMargin - params.rightMargin
            is FrameLayout.LayoutParams -> return sw - params.leftMargin - params.rightMargin
            is TableLayout.LayoutParams -> return sw - params.leftMargin - params.rightMargin
            is MarginLayoutParams -> return sw - params.leftMargin - params.rightMargin
        }
        return sw
    }

    override fun onLayout(changed : Boolean , l : Int , t : Int , r : Int , b : Int) {
        val rows = if (childCount % mColumns == 0) childCount / mColumns else childCount / mColumns + 1
        var child : View
        var index : Int
        val itemWidth = (width - mDividerHeight * (mColumns - 1)) / mColumns
        val itemHeight = getChildAt(0).layoutParams.height
        for (row in 0 until rows) {
            for (col in 0 until mColumns) {
                index = (row) * mColumns + (col)
                if (index > childCount - 1) {
                    break
                }
                child = getChildAt(index)
                if (isFirstRow(row , rows)) {
                    layoutFirstRow(child , itemWidth , itemHeight , col)
                } else if (isLastRow(row , rows)) {
                    layoutLastRow(child , itemWidth , itemHeight , row , col)
                } else {
                    layout(child , itemWidth , itemHeight , row , col)
                }
            }
        }
    }

    private fun layoutLastRow(child : View , itemWidth : Int , itemHeight : Int , row : Int , col : Int) {
        val top = itemHeight * (row) + mDividerHeight * (row)
        val bottom = height
        if (isFirstCol(col)) {
            child.layout(0 , top , itemWidth , bottom)
        } else if (isLastCol(col)) {
            child.layout(itemWidth * (col) + mDividerHeight * (col) , top , width , bottom)
        } else {
            child.layout(itemWidth * (col) + mDividerHeight * (col) , top , itemWidth + itemWidth * (col) + mDividerHeight * (col) , bottom)
        }
    }

    private fun layout(child : View , itemWidth : Int , itemHeight : Int , row : Int , col : Int) {
        val top = itemHeight * (row) + mDividerHeight * (row)
        if (isFirstCol(col)) {
            child.layout(0 , top , itemWidth , top + itemHeight)
        } else if (isLastCol(col)) {
            child.layout(itemWidth * (col) + mDividerHeight * (col) , top , width , top + itemHeight)
        } else {
            child.layout(itemWidth * (col) + mDividerHeight * (col) , top , itemWidth + itemWidth * (col) + mDividerHeight * (col) , top + itemHeight)
        }
    }

    private fun layoutFirstRow(child : View , itemWidth : Int , itemHeight : Int , col : Int) {
        if (isFirstCol(col)) {
            child.layout(0 , 0 , itemWidth , itemHeight)
        } else if (isLastCol(col)) {
            child.layout(itemWidth * (col) + mDividerHeight * (col) , 0 , width , itemHeight)
        } else {
            child.layout(itemWidth * (col) + mDividerHeight * (col) , 0 , itemWidth + itemWidth * (col) + mDividerHeight * (col) , itemHeight)
        }
    }

    private fun isFirstCol(col : Int) : Boolean {
        return col == 0
    }

    private fun isLastCol(col : Int) : Boolean {
        return col == mColumns - 1
    }

    private fun isFirstRow(row : Int , rows : Int) : Boolean {
        return row == 0
    }

    private fun isLastRow(row : Int , rows : Int) : Boolean {
        return row == rows - 1
    }
}