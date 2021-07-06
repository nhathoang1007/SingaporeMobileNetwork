package com.example.koin.customize.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Path.FillType
import android.util.AttributeSet
import android.view.View
import com.example.koin.R
import com.example.koin.extensions.dp
import com.example.koin.utils.EnumCompanion

@SuppressLint("Recycle")
class JsShapeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var mSize: Int = 0
    private var mShape: Shape = Shape.RECT
    private var mShapeRadius: Int = 16.dp
    private var mFillColor: Int = Color.GRAY
    private var mStrokeColor: Int? = null
    private var mStrokeWidth: Int = 0.dp
    private var mShadowColor: Int? = null
    private var mShadowSize: Int = 12.dp
    private var mShadowPosition: ShadowPosition = ShadowPosition.BOTTOM

    init {
        kotlin.runCatching {
            context.obtainStyledAttributes(attrs, R.styleable.JsShapeView).apply {
                /*fill shape config*/
                mShape = Shape.getByValue(getInt(R.styleable.JsShapeView_js_shape, 0), Shape.RECT)
                mFillColor = getColor(R.styleable.JsShapeView_js_fill_color, Color.WHITE)
                /*Stroke config*/
                if (hasValue(R.styleable.JsShapeView_js_stroke_color)) {
                    mStrokeColor = getColor(R.styleable.JsShapeView_js_stroke_color, Color.WHITE)
                }
                mShapeRadius = getDimensionPixelSize(R.styleable.JsShapeView_js_shape_radius, 16.dp)
                mStrokeWidth = getDimensionPixelSize(R.styleable.JsShapeView_js_stroke_width, 0.dp)
                /*Shadow config*/
                if (hasValue(R.styleable.JsShapeView_js_shadow_color)) {
                    mShadowColor = getColor(R.styleable.JsShapeView_js_shadow_color, Color.WHITE)
                }
                mShadowPosition = ShadowPosition.getByValue(
                    getInt(R.styleable.JsShapeView_js_shadow_gravity, 0),
                    ShadowPosition.BOTTOM
                )
                mShadowSize = getDimensionPixelSize(R.styleable.JsShapeView_js_shadow_size, 12.dp)

                this.recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mSize = measuredWidth.coerceAtMost(measuredHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paintFill = Paint().apply {
            isAntiAlias = true
            color = mFillColor
            if (mShadowColor != null) {
                setShadowLayer(
                    mShadowSize.toFloat(),
                    mShadowPosition.x,
                    mShadowPosition.y,
                    mShadowColor!!
                )
            }
        }
        val paintStroke = Paint().apply {
            isAntiAlias = true
            color = mStrokeColor ?: Color.TRANSPARENT
            strokeWidth = mStrokeWidth.toFloat()
            style = Paint.Style.STROKE
        }
        val centerOfCanvas = Point(width / 2, height / 2)
        val margin = if (mShadowColor != null) {
            16
        } else {
            0
        }.dp.toFloat()
        when (mShape) {
            Shape.CIRCLE -> {
                val radius = (mSize / 2).toFloat() - margin
                canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, radius, paintFill)
                canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, radius, paintStroke)
            }
            Shape.RECT, Shape.SQUARE -> {
                val rect = if (mShape == Shape.RECT) {
                    RectF(margin, margin, width - margin, height - margin)
                } else {
                    RectF(
                        centerOfCanvas.x - (mSize / 2 - margin),
                        centerOfCanvas.y - (mSize / 2 - margin),
                        centerOfCanvas.x + (mSize / 2 - margin),
                        centerOfCanvas.y + (mSize / 2 - margin)
                    )
                }
                canvas?.drawRoundRect(
                    rect,
                    mShapeRadius.toFloat(),
                    mShapeRadius.toFloat(),
                    paintFill
                )
                canvas?.drawRoundRect(
                    rect,
                    mShapeRadius.toFloat(),
                    mShapeRadius.toFloat(),
                    paintStroke
                )
            }
            Shape.TRIANGLE -> {
                val a = Point(width / 2, margin.toInt())
                val b = Point(width / 2 - mSize / 2, height - margin.toInt())
                val c = Point(width / 2 + mSize / 2, height - margin.toInt())

                val path = Path()
                path.fillType = FillType.EVEN_ODD
                path.moveTo(a.x.toFloat(), a.y.toFloat())
                path.lineTo(b.x.toFloat(), b.y.toFloat())
                path.moveTo(b.x.toFloat() - 4, b.y.toFloat())
                path.lineTo(c.x.toFloat(), c.y.toFloat())
                path.moveTo(c.x.toFloat() - 4, c.y.toFloat())
                path.lineTo(a.x.toFloat(), a.y.toFloat() + 4)
                path.close()

                canvas?.drawPath(path, paintFill)
                canvas?.drawPath(path, paintStroke)
            }
        }
    }

    enum class Shape(val style: Int) {
        CIRCLE(0),
        RECT(1),
        SQUARE(2),
        TRIANGLE(3);

        companion object : EnumCompanion<Int, Shape>(values().associateBy(Shape::style))
    }

    enum class ShadowPosition(val gravity: Int, val x: Float, val y: Float) {
        TOP(0, -4f, -4f),
        START(1, -4f, 0f),
        END(2, 4f, 0f),
        BOTTOM(3, 4f, 4f);

        companion object : EnumCompanion<Int, ShadowPosition>(
            values().associateBy(ShadowPosition::gravity)
        )
    }
}