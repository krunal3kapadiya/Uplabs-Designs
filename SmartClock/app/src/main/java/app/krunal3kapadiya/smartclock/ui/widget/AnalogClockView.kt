package app.krunal3kapadiya.smartclock.ui.widget


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * created custom clockview as per the design
 */

class AnalogClockView : View {

    private var padding = 0
    private val numeralSpacing = 0
    private var handTruncation = 0
    private var hourHandTruncation = 0
    private var radius = 0
    private var paint: Paint? = null
    private var isInit = false
    var view_height = 0
    var view_width = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun initClock() {
        this.view_height = height
        this.view_width = width
        padding = numeralSpacing + 50
        val min = height.coerceAtMost(width)
        radius = min / 2 - padding
        handTruncation = min / 20
        hourHandTruncation = min / 7
        paint = Paint()
        isInit = true
    }

    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            initClock()
        }
        drawCircle(canvas)
        drawHands(canvas)
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHand(
        canvas: Canvas,
        loc: Double,
        isHour: Boolean
    ) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius =
            if (isHour) radius - handTruncation - hourHandTruncation else radius - handTruncation
        canvas.drawLine(
            width / 2.toFloat(), height / 2.toFloat(),
            (width / 2 + cos(angle) * handRadius).toFloat(),
            (height / 2 + sin(angle) * handRadius).toFloat(),
            paint!!
        )
    }

    private fun drawHands(canvas: Canvas) {
        val c = Calendar.getInstance()
        var hour = c[Calendar.HOUR_OF_DAY].toFloat()
        hour = if (hour > 12) hour - 12 else hour
        drawHand(canvas, (hour + c[Calendar.MINUTE] / 60) * 5f.toDouble(), true)
        drawHand(canvas, c[Calendar.MINUTE].toDouble(), false)
    }

    private fun drawCircle(canvas: Canvas) {
        paint!!.reset()
        paint!!.color = ContextCompat.getColor(this.context, android.R.color.white)
        paint!!.strokeWidth = 5f
        paint!!.style = Paint.Style.STROKE
        paint!!.isAntiAlias = true
        canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            radius + padding - 10.toFloat(),
            paint!!
        )
    }
}