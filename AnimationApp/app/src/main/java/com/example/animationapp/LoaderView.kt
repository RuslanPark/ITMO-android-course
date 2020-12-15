package com.example.animationapp

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.hypot
import kotlin.math.min

class LoaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var circleRadius: Float
    private var rectLongSide: Float
    private var rectShortSide: Float
    private val rectHypot: Float
    private val rectF = RectF()

    private val leftMargin: Float
    private val topMargin: Float
    private val desiredWidth: Float
    private val desiredHeight: Float

    private var animationDelay: Long
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var animVal = 0f
    private var animValVar = 0f

    init {
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.LoaderView, defStyleAttr, 0
        )
        try {
            rectShortSide = dp(a.getFloat(R.styleable.LoaderView_rectShortSide, 15f))
            rectLongSide = dp(a.getFloat(R.styleable.LoaderView_rectLongSide, 70f))
            circleRadius = dp(a.getFloat(R.styleable.LoaderView_circleRadiusValue, 2.5f))
            rectHypot = hypot(rectLongSide, rectShortSide)
            leftMargin = dp(2f)
            topMargin = dp(16f)
            desiredWidth = 2 * leftMargin + rectHypot
            desiredHeight = 2 * topMargin + rectHypot

            animationDelay = a.getInt(R.styleable.LoaderView_animationDelay, 1000).toLong()
            paint.color = a.getColor(R.styleable.LoaderView_color, Color.BLUE)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getSize(widthMeasureSpec, desiredWidth.toInt()),
            getSize(heightMeasureSpec, desiredHeight.toInt())
        )
    }


    private fun drawRect(canvas: Canvas, rotationX: Float, rotationY: Float, rectF: RectF) {
        val save = canvas.save()
        canvas.rotate(animVal, rotationX, rotationY)
        canvas.drawRoundRect(
            rectF, circleRadius, circleRadius, paint
        )
        canvas.restoreToCount(save)
    }

    private var toggle = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (animValVar == 0f) {
            toggle = true
        }
        if (animValVar == 9f) {
            animValVar += 1f
            toggle = false
        }

        if (toggle) {
            animValVar += 1f
        } else {
            animValVar -= 1f
        }
        animVal += animValVar
        animVal %= 180f

        //Log.d("onDraw","onDraw" + animVal + " " + animValVar);

        val horTop = topMargin + (rectLongSide - rectShortSide) / 2
        rectF.set(leftMargin, horTop, leftMargin + rectLongSide, horTop + rectShortSide)
        drawRect(
            canvas, leftMargin + rectLongSide / 2, horTop + rectShortSide / 2, rectF
        )
        val verLeft = leftMargin + (rectLongSide - rectShortSide) / 2
        rectF.set(verLeft, topMargin, verLeft + rectShortSide, topMargin + rectLongSide)
        drawRect(
            canvas, verLeft + rectShortSide / 2, topMargin + rectLongSide / 2, rectF
        )

        if (animVal == 90f) {
            postDelayed(this::invalidate, animationDelay)
        } else {
            invalidate()
        }

    }

    private fun getSize(measureSpec: Int, desired: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        return when (mode) {
            MeasureSpec.AT_MOST -> min(size, desired)
            MeasureSpec.EXACTLY -> size
            MeasureSpec.UNSPECIFIED -> desired
            else -> desired
        }
    }

    private fun dp(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }
}