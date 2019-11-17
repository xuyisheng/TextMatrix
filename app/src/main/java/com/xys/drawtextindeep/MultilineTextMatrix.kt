package com.xys.drawtextindeep

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.text.*
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View


class MultilineTextMatrix @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TEXT =
            "The Canvas class holds the draw calls. " +
                    "To draw something, you need 4 basic components: " +
                    "A Bitmap to hold the pixels, a Canvas to host the draw calls " +
                    "(writing into the bitmap), a drawing primitive (e.g. Rect, Path, text, Bitmap)," +
                    " and a paint (to describe the colors and styles for the drawing)."
    }

    private val drawText = TEXT

    private val textPaint by lazy {
        TextPaint().apply {
            color = Color.BLACK
            isAntiAlias = true
            textSize = 30F
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(Color.CYAN)
        val spannable = SpannableString(drawText)
        spannable.setSpan(RelativeSizeSpan(2f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val staticLayout = StaticLayout(
            spannable, textPaint, width, Layout.Alignment.ALIGN_NORMAL,
            1F, 0F, true
        )
        val width = staticLayout.getLineWidth(0)
        val height = staticLayout.height
        Log.d("xys", "line width $width height $height")
        staticLayout.draw(canvas)
    }
}