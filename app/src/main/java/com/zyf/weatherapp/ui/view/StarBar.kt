package com.zyf.weatherapp.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.zyf.weatherapp.R

/**
 * Created by zyf on 2018/8/28.
 */
class StarBar : View {

    private var starDistance = 0 //星星间距
    private var starCount = 5  //星星个数
    private var starSize: Int = 0     //星星高度大小，星星一般正方形，宽度等于高度

    private var starFullBitmap: Bitmap? = null //亮星星
    private var starEmptyDrawable: Drawable? = null //暗星星
    private var paint: Paint? = null//绘制星星画笔

    var integerMark = false//是否整数评分

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    /**
     * 定义星星点击的监听接口
     */
    lateinit var onStarChangeListener: (mark: Float) -> Unit

    /**
     * 设置显示的星星的分数
     *
     * @return starMark
     */
    var starMark = 0.0f
        set(mark) {
            field = if (integerMark) {
                Math.ceil(mark.toDouble()).toInt().toFloat()
            } else {
                Math.round(mark * 10) * 1.0f / 10
            }
            if (::onStarChangeListener.isInitialized){
                onStarChangeListener.invoke(this.starMark)
            }
            invalidate()
        }

    /**
     * 初始化UI组件
     *
     * @param context
     * @param attrs
     */
    private fun init(context: Context, attrs: AttributeSet) {
        isClickable = true
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.StarBar)
        this.starDistance = mTypedArray.getDimension(R.styleable.StarBar_starDistance, 0f).toInt()
        this.starSize = mTypedArray.getDimension(R.styleable.StarBar_starSize, 20f).toInt()
        this.starCount = mTypedArray.getInteger(R.styleable.StarBar_starCount, 5)
        this.starEmptyDrawable = mTypedArray.getDrawable(R.styleable.StarBar_starEmpty)
        this.starFullBitmap = drawableToBitmap(mTypedArray.getDrawable(R.styleable.StarBar_starFull))
        mTypedArray.recycle()

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.shader = BitmapShader(starFullBitmap!!, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(starSize * starCount + starDistance * (starCount - 1), starSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (starFullBitmap == null || starEmptyDrawable == null) {
            return
        }
        for (i in 0 until starCount) {
            starEmptyDrawable!!.setBounds((starDistance + starSize) * i, 0, (starDistance + starSize) * i + starSize, starSize)
            starEmptyDrawable!!.draw(canvas)
        }
        if (this.starMark > 1) {
            canvas.drawRect(0f, 0f, starSize.toFloat(), starSize.toFloat(), paint!!)
            if (this.starMark - this.starMark.toInt() == 0f) {
                var i = 1
                while (i < this.starMark) {
                    canvas.translate((starDistance + starSize).toFloat(), 0f)
                    canvas.drawRect(0f, 0f, starSize.toFloat(), starSize.toFloat(), paint!!)
                    i++
                }
            } else {
                var i = 1
                while (i < this.starMark - 1) {
                    canvas.translate((starDistance + starSize).toFloat(), 0f)
                    canvas.drawRect(0f, 0f, starSize.toFloat(), starSize.toFloat(), paint!!)
                    i++
                }
                canvas.translate((starDistance + starSize).toFloat(), 0f)
                canvas.drawRect(0f, 0f, starSize * (Math.round((this.starMark - this.starMark.toInt()) * 10) * 1.0f / 10), starSize.toFloat(), paint!!)
            }
        } else {
            canvas.drawRect(0f, 0f, starSize * this.starMark, starSize.toFloat(), paint!!)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x.toInt()
        if (x < 0) x = 0
        if (x > measuredWidth) x = measuredWidth
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                starMark = x * 1.0f / (measuredWidth * 1.0f / starCount)
            }
            MotionEvent.ACTION_MOVE -> {
                starMark = x * 1.0f / (measuredWidth * 1.0f / starCount)
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        invalidate()
        return super.onTouchEvent(event)
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) return null
        val bitmap = Bitmap.createBitmap(starSize, starSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        with(drawable) {
            setBounds(0, 0, starSize, starSize)
            draw(canvas)
        }
        return bitmap
    }
}
