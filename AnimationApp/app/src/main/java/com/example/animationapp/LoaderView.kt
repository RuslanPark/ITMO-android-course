package com.example.animationapp

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.PathInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

class LoaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var circleRadius = dp(2.5f)
    private val rectLongSide = dp(22f)
    private var rectShortSide = dp(6f)

    private val rectHypot = hypot(rectLongSide, rectShortSide)
    private val rectF = RectF()

    private val topMargin = dp(16f)
    private val leftMargin = dp(2f)

    private val desiredWidth = 2 * leftMargin + rectHypot
    private val desiredHeight = 2 * topMargin + rectHypot

    private var animationDelay: Long
    private var animationLength: Long
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFe1e3e6.toInt()
    }

    init {
        val a: TypedArray = context.obtainStyledAttributes(
            attrs, R.styleable.LoaderView, defStyleAttr, 0
        )
        try {
            rectShortSide = dp(a.getFloat(R.styleable.LoaderView_rectShortSide, 6f))
            circleRadius = dp(a.getFloat(R.styleable.LoaderView_circleRadiusValue, 2.5f))

            animationDelay = a.getInt(R.styleable.LoaderView_animationDelay, 1000).toLong()
            animationLength = a.getInt(R.styleable.LoaderView_animationLength, 300).toLong()
            paint.color = a.getColor(R.styleable.LoaderView_color, 0xFFe1e3e6.toInt())
        } finally {
            a.recycle()
        }
    }

    private var rectRotation: Float = 0f
        set(value) {
            field = value
            invalidate()
        }


    private var animator: Animator? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        animator?.cancel()
        animator = AnimatorSet().apply {
            val rectRotateAnimator = ValueAnimator.ofFloat(0.0F, 180F).apply {
                addUpdateListener {
                    rectRotation = it.animatedValue as Float
                    startDelay = 1000
                }
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE

                startDelay = animationDelay
                duration = animationLength
            }
            interpolator = PathInterpolator(0.25F, 0.1F, 0.25F, 1F)
            playTogether(rectRotateAnimator)
            start()
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
        canvas.rotate(rectRotation, rotationX, rotationY)
        canvas.drawRoundRect(
            rectF, circleRadius, circleRadius, paint
        )
        canvas.restoreToCount(save)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val horTop = topMargin + (rectLongSide - rectShortSide) / 2
        rectF.set(leftMargin, horTop, leftMargin + rectLongSide, horTop + rectShortSide)
        drawRect(
            canvas, leftMargin + rectLongSide / 2, horTop + rectShortSide / 2, rectF
        )

        val verLeft = leftMargin + (rectLongSide - rectShortSide) / 2
        rectF.set(
            verLeft, topMargin, verLeft + rectShortSide,
            topMargin + rectLongSide
        )
        drawRect(
            canvas, verLeft + rectShortSide / 2, topMargin + rectLongSide / 2, rectF
        )

    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        animator?.cancel()
        animator = null
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