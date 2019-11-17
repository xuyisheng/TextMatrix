package com.xys.drawtextindeep

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class AlignText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TEXT = "中文，AGagj,12 9"
    }

    private var drawText = TEXT

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var baseLineY: Float = 0F

    private val fontSize: Float = 50F

    private val textPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            textSize = fontSize
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
        baseLineY = viewHeight * 0.6F
    }

    override fun onDraw(canvas: Canvas?) {
        val centerWidth = (width / 2).toFloat()
        canvas?.drawColor(Color.CYAN)
        canvas?.drawLine(centerWidth, 0F, centerWidth, height.toFloat(), textPaint)
        textPaint.textAlign = Paint.Align.LEFT
        canvas?.drawText(drawText, centerWidth, baseLineY * 0.5F, textPaint)
        textPaint.textAlign = Paint.Align.CENTER
        canvas?.drawText(drawText, centerWidth, baseLineY, textPaint)
        textPaint.textAlign = Paint.Align.RIGHT
        canvas?.drawText(drawText, centerWidth, baseLineY * 1.5F, textPaint)
    }
}