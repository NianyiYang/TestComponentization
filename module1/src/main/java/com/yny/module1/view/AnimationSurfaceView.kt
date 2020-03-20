package com.yny.module1.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.animation.AccelerateDecelerateInterpolator
import android.animation.ObjectAnimator

class AnimationSurfaceView : SurfaceView, SurfaceHolder.Callback, Runnable {

    private var drawThread: Thread? = null

    var percent: Float = 0F

    constructor(context: Context) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        drawThread = Thread(this)
        holder.addCallback(this)
    }

    fun setPercentWithAnimator(newPercent: Float) {
        val animator =
            ObjectAnimator.ofFloat(this, "percent", percent, newPercent)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        drawThread = null
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        drawThread?.start()
    }

    override fun run() {
        val thread = Thread.currentThread()
        while (drawThread == thread) {

            val paint = Paint()


            val canvas = holder.lockCanvas()

            try {

                val midX = (measuredWidth * percent).toInt()

                paint.color = Color.parseColor("#000000")
                canvas?.drawRect(
                    Rect(0, 0, midX, measuredHeight),
                    paint
                )
                paint.color = Color.parseColor("#FF0000")
                canvas?.drawRect(
                    Rect(midX, 0, measuredWidth, measuredHeight),
                    paint
                )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                canvas?.let {
                    holder.unlockCanvasAndPost(it)
                }
            }
        }
    }
}