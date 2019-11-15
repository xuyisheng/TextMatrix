package com.xys.drawtextindeep

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View


class TextMatrix @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TEXT = "中文，AGagj,12 9"
    }

    // 坐标格Size
    private val gridSize = 10
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    // 基线坐标
    private var baseLineX: Float = 0F
    private var baseLineY: Float = 0F
    private val fontSize: Float = 100F
    // top line
    private var topLineY: Float = 0F
    // bottom line
    private var bottomLineY: Float = 0F
    // ascent line
    private var ascentLineY: Float = 0F
    // descent line
    private var descentLineY: Float = 0F
    // center line
    private var centerLineY: Float = 0F
    private var lineRect = RectF()
    private var textRect = Rect()

    private val coordinatePaint by lazy {
        Paint().apply {
            color = Color.GRAY
            strokeWidth = 1F
        }
    }

    private val textPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            textSize = fontSize
        }
    }

    private val charPointPaint by lazy {
        Paint().apply {
            color = Color.YELLOW
            style = Paint.Style.FILL
        }
    }

    private val lineRectPaint by lazy {
        Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL
        }
    }

    private val textRectPaint by lazy {
        Paint().apply {
            color = Color.GREEN
            style = Paint.Style.FILL
        }
    }

    private val guidelinePaint by lazy {
        Paint().apply {
            color = Color.RED
            strokeWidth = 2F
            style = Paint.Style.FILL
        }
    }

    private val guideTextPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            textSize = 20F
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
        baseLineY = viewHeight * 0.6F
    }

    private var drawText = TEXT

    override fun onDraw(canvas: Canvas?) {
        width
        canvas?.drawColor(Color.CYAN)
        // 绘制坐标格
        drawGrids(canvas)
        // 绘制Baseline
        canvas?.drawLine(baseLineX, baseLineY, viewWidth.toFloat(), baseLineY, guidelinePaint)
        // 根据Baseline绘制LineRect
        drawLineRect(canvas)
        // 根据Baseline绘制TextRect
        drawTextRect(canvas)
        // 绘制四格线
        draw4Lines(canvas)
        // 绘制每个字符的Width
        drawTextWidth(canvas)

        canvas?.drawText(drawText, baseLineX, baseLineY, textPaint)
    }

    private fun drawTextWidth(canvas: Canvas?) {
        val widths = FloatArray(drawText.length)
        textPaint.getTextWidths(drawText, widths)
        var start = 0F
        canvas?.drawCircle(start, baseLineY, 4F, charPointPaint)
        widths.forEach {
            canvas?.drawCircle(start + it, baseLineY, 4F, charPointPaint)
            start += it
        }
    }

    private fun drawTextRect(canvas: Canvas?) {
        // getTextBounds的参照为baseline = 0得到的坐标，需要根据实际baseline位置进行修正
        textPaint.getTextBounds(drawText, 0, drawText.length, textRect)
        textRect.top = (baseLineY + textRect.top).toInt()
        textRect.bottom = (baseLineY + textRect.bottom).toInt()
        canvas?.drawRect(textRect, textRectPaint)
    }

    private fun draw4Lines(canvas: Canvas?) {
        val fontMetrics = textPaint.fontMetrics
        topLineY = baseLineY + fontMetrics.top
        ascentLineY = baseLineY + fontMetrics.ascent
        descentLineY = baseLineY + fontMetrics.descent
        bottomLineY = baseLineY + fontMetrics.bottom
        centerLineY = (bottomLineY - topLineY) / 2 + topLineY
        canvas?.drawLine(baseLineX, topLineY, viewWidth.toFloat(), topLineY, guidelinePaint)
        canvas?.drawText("top", viewWidth.toFloat() - 200, topLineY, guideTextPaint)
        canvas?.drawLine(baseLineX, ascentLineY, viewWidth.toFloat(), ascentLineY, guidelinePaint)
        canvas?.drawText("ascent", viewWidth.toFloat() - 100, ascentLineY, guideTextPaint)
        canvas?.drawLine(baseLineX, descentLineY, viewWidth.toFloat(), descentLineY, guidelinePaint)
        canvas?.drawText("descent", viewWidth.toFloat() - 200, descentLineY, guideTextPaint)
        canvas?.drawLine(baseLineX, bottomLineY, viewWidth.toFloat(), bottomLineY, guidelinePaint)
        canvas?.drawText("bottom", viewWidth.toFloat() - 100, bottomLineY, guideTextPaint)
        canvas?.drawLine(baseLineX, centerLineY, viewWidth.toFloat(), centerLineY, guidelinePaint)
        canvas?.drawText("center", viewWidth.toFloat() - 100, centerLineY, guideTextPaint)
    }

    private fun drawLineRect(canvas: Canvas?) {
        val fontMetrics = textPaint.fontMetrics
        topLineY = baseLineY + fontMetrics.top
        bottomLineY = baseLineY + fontMetrics.bottom
        lineRect.apply {
            top = topLineY
            left = baseLineX
            bottom = bottomLineY
            right = baseLineX + textPaint.measureText(drawText)
        }
        val textPaint2 =
            TextPaint().apply {
                color = Color.BLACK
                textSize = fontSize
            }
        Log.d(
            "xys",
            "measureText  ${textPaint.measureText(drawText)} getDesiredWidth  ${Layout.getDesiredWidth(
                drawText,
                textPaint2
            )} "
        )// measureText === getDesiredWidth === getTextWidths

        canvas?.drawRect(lineRect, lineRectPaint)
    }

    private fun drawGrids(canvas: Canvas?) {
        repeat(viewWidth / gridSize - 1) {
            val x = ((it + 1) * gridSize).toFloat()
            canvas?.drawLine(x, baseLineX, x, viewHeight.toFloat(), coordinatePaint)
        }
        repeat(viewHeight / gridSize - 1) {
            val y = ((it + 1) * gridSize).toFloat()
            canvas?.drawLine(baseLineX, y, viewWidth.toFloat(), y, coordinatePaint)
        }
    }
}