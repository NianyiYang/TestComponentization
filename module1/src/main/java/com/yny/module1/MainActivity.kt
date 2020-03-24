package com.yny.module1

import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.yny.module1.view.AnimationSurfaceView
import com.yny.testcomponentization.R
import java.util.*
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val handler = Handler()

    private val timeService = ScheduledThreadPoolExecutor(1)

    private val scroller: NestedScrollView by lazy { findViewById<NestedScrollView>(R.id.nsv_scroller) }
    private val container: LinearLayout by lazy { findViewById<LinearLayout>(R.id.ll_container) }

    private val asvList = mutableListOf<AnimationSurfaceView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module1_activity_main)

        for (i in 0 until 50) {

            val asv = AnimationSurfaceView(this)
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50)

            asvList.add(asv)
            container.addView(asv, params)
        }

        scroller.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY == oldScrollY) {

            }
        }
    }

    override fun onStart() {
        super.onStart()
        timeService.scheduleAtFixedRate(mTimerTask, 1000, 2000, TimeUnit.MILLISECONDS)
    }

    override fun onStop() {
        super.onStop()
        timeService.shutdown()
    }

    private val mTimerTask = object : TimerTask() {
        override fun run() {
            handler.post {
                for (view in asvList) {
                    view.setPercentWithAnimator(Math.random().toFloat())
                }
            }
        }
    }
}