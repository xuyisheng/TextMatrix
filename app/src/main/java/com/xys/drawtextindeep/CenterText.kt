package com.xys.drawtextindeep

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View


class CenterText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TEXT = "中文，AGagj,12 9"
    }

    private var drawText = TEXT

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0

    private val fontSize: Float = 100F
    private val fontSize2: Float = 50F

    private val rect = Rect()

    private val textPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = fontSize
        }
    }

    private val textPaint2 by lazy {
        Paint().apply {
            color = Color.RED
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = fontSize2
        }
    }

    private val linePaint by lazy {
        Paint().apply {
            color = Color.RED
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        val centerWidth = (width / 2).toFloat()
        val centerHeight = (height / 2).toFloat()
        canvas?.drawColor(Color.CYAN)
        // 文字整体居中：画布高度的一半 - 文字总高度的一半(两种方式，取的有效文字区域不同)
//        val baseY = (height / 2 - (textPaint.descent() + textPaint.ascent()) / 2)
        val baseY = (height / 2 - (textPaint.fontMetrics.top + textPaint.fontMetrics.bottom) / 2)
        canvas?.drawText(drawText, centerWidth, baseY, textPaint)
        canvas?.drawLine(0F, centerHeight, width.toFloat(), centerHeight, textPaint)
        canvas?.drawLine(0F, baseY, width.toFloat(), baseY, linePaint)

        textPaint2.getTextBounds(drawText, 0, drawText.length, rect)
        val baseY2 = (height / 2 + (rect.height() - rect.bottom) / 2)
        canvas?.drawText(drawText, centerWidth, baseY2.toFloat(), textPaint2)
    }
}