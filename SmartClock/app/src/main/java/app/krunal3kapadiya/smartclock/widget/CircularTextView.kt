package app.krunal3kapadiya.smartclock.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * @author krunal kapadiya
 * @link https://krunal3kapadiya.app
 * @date 14,April,2019
 */

class CircularTextView : AppCompatTextView {
    private var strokeWidth = 0
    var strokeColor = 0
    var solidColors = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun draw(canvas: Canvas?) {
        val circlePaint = Paint()
        circlePaint.color = solidColor
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG

        val strokePaint = Paint()
        strokePaint.color = strokeColor
        strokePaint.flags = Paint.ANTI_ALIAS_FLAG

        val h = this.height
        val w = this.width

        val diameter = if (h > w) h else w
        val radius = diameter / 2

        this.height = diameter
        this.width = diameter

        canvas!!.drawCircle(
            (diameter / 2).toFloat(),
            (diameter / 2).toFloat(),
            radius.toFloat(), strokePaint
        )

        canvas.drawCircle(
            (diameter / 2).toFloat(),
            (diameter / 2).toFloat(),
            (radius - strokeWidth).toFloat(), circlePaint
        )

        super.draw(canvas)
    }

    fun setStrokeWidth(dp: Int) {
        val scale = context.resources.displayMetrics.density
        strokeWidth = dp * scale.toInt()
    }

    fun setStrokeColor(color: String?) {
        strokeColor = Color.parseColor(color)
    }

    fun setSolidColor(color: String?) {
        solidColors = Color.parseColor(color)
    }
}