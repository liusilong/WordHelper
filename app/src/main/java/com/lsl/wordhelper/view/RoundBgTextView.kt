package com.lsl.wordhelper.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import com.lsl.wordhelper.util.loge
import java.util.*

/**
 * Created by liusilong on 2018/1/19.
 * version:1.0
 * Describe: 圆形背景的 TextView
 */
class RoundBgTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {
    private val tag = "RoundBgTextView"
    private val arrColor = arrayOf("#FA9510", "#F50057", "#D24843",
            "#3AC3C1", "#3AA532", "#FF6F00", "#FF5722")
    private var paint: Paint = getPaint()
    private var colorIndex = 0

    init {
        colorIndex = Random().nextInt(arrColor.size)
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.parseColor(arrColor[colorIndex])
        loge(tag, paint.color.toString())
        canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), width / 2.toFloat(), paint)
        paint.color = Color.WHITE
        super.onDraw(canvas)
    }


}