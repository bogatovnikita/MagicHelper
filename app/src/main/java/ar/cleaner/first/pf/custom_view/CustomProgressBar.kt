package ar.cleaner.first.pf.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import ar.cleaner.first.pf.R
import kotlin.properties.Delegates

class CustomProgressBar(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attributesSet, defStyleAttr, defStyleRes) {

    private var progressColor by Delegates.notNull<Int>()
    private var indicatorColor by Delegates.notNull<Int>()
    private var shadowColor by Delegates.notNull<Int>()
    var progressPercent: Float = 0f
        set(value) {
            field = if (value > 100 && value < 0) {
                return
            } else {
                value
            }
            invalidate()
        }

    private lateinit var progressPaint: Paint
    private lateinit var indicatorPaint: Paint
    private lateinit var shadowPaint: Paint

    constructor(context: Context, attributesSet: AttributeSet?, defStyleAttr: Int) : this(
        context, attributesSet, defStyleAttr,
        R.style.DefaultCustomProgressBarStyle
    )


    constructor(context: Context, attributesSet: AttributeSet?) : this(context, attributesSet, 0)
    constructor(context: Context) : this(context, null)

    init {
        initAttributes(attributesSet, defStyleAttr, defStyleRes)
        initPaints()
        if (isInEditMode) {
            progressPercent = 50f
        }
    }

    private fun initAttributes(attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributesSet,
            R.styleable.ProgressBar,
            defStyleAttr,
            defStyleRes
        )
        progressColor = typedArray.getColor(
            R.styleable.ProgressBar_progress_color,
            DEFAULT_PROGRESS_COLOR
        )
        indicatorColor = typedArray.getColor(
            R.styleable.ProgressBar_indicator_color,
            DEFAULT_INDICATOR_COLOR
        )
        shadowColor =
            typedArray.getColor(R.styleable.ProgressBar_shadow_color, DEFAULT_SHADOW_COLOR)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            widthMeasureSpec + (paddingLeft + paddingRight),
            heightMeasureSpec + (paddingTop + paddingBottom)
        )
    }

    private fun initPaints() {
        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint.color = progressColor
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeCap = Paint.Cap.ROUND
        progressPaint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics)

        indicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        indicatorPaint.color = indicatorColor
        indicatorPaint.style = Paint.Style.STROKE
        indicatorPaint.strokeCap = Paint.Cap.ROUND
        indicatorPaint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, resources.displayMetrics)

        shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        shadowPaint.color = shadowColor
        shadowPaint.style = Paint.Style.STROKE
        shadowPaint.strokeCap = Paint.Cap.ROUND
        shadowPaint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawProgress(canvas)
        drawIndicator(canvas)
//        drawShadow(canvas)
    }

    private fun drawShadow(canvas: Canvas) {
        canvas.drawArc(
            3f,
            3f,
            width.toFloat() - 3f,
            height.toFloat() - 3f,
            135f,
            270f,
            false,
            shadowPaint
        )
    }

    private fun drawIndicator(canvas: Canvas) {
        canvas.drawArc(
            30f,
            30f,
            width.toFloat() - 30f,
            height.toFloat() - 30f,
            135f,
            progressPercent * 2.7f,
            false,
            indicatorPaint
        )
    }

    private fun drawProgress(canvas: Canvas) {
        canvas.drawArc(
            30f,
            30f,
            width.toFloat() - 30f,
            height.toFloat() - 30f,
            135f,
            270f,
            false,
            progressPaint
        )
    }

    companion object {
        const val DEFAULT_PROGRESS_COLOR = Color.WHITE
        const val DEFAULT_INDICATOR_COLOR = Color.GREEN
        const val DEFAULT_SHADOW_COLOR = Color.BLACK
    }
}