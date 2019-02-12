package com.example.administrator.doctorClient.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class OrderScheduleView:View{

    private val leftTextPaint = TextPaint()
    private val redBarPaint = Paint()
    private val grayBarPaint = Paint()
    private val centerTextPaint = TextPaint()
    private val rightTextPaint = TextPaint()
    private val whiteCirclePaint = Paint()
    private var state = -1
    private var timeList= ArrayList<String>()

    constructor(context: Context):super(context){

    }

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        initPaint()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (state>-1){
            startDraw(canvas!!)
        }
    }

    fun init(state:Int,timeList:List<String>){
        post {
            this.state = state
            this.timeList.clear()
            this.timeList.addAll(timeList)
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width,width)
    }

    fun initPaint(){
        leftTextPaint.color = Color.BLACK
        leftTextPaint.textAlign = Paint.Align.RIGHT
        leftTextPaint.textSize = dpToPx(16)
        centerTextPaint.textSize = dpToPx(14)
        centerTextPaint.color = Color.GRAY
        rightTextPaint.textSize = dpToPx(12)
        rightTextPaint.color = Color.GRAY
        redBarPaint.style = Paint.Style.FILL
        redBarPaint.strokeWidth = dpToPx(8)
        redBarPaint.color = Color.RED
        grayBarPaint.style = Paint.Style.FILL
        grayBarPaint.color = Color.GRAY
        grayBarPaint.strokeWidth = dpToPx(8)
        whiteCirclePaint.color = Color.WHITE
        whiteCirclePaint.style = Paint.Style.FILL
    }

    private fun startDraw(canvas: Canvas){
        val width = measuredWidth
        val height = measuredHeight
        val beginY = height/10f
        val oneY = height/5f
        val leftStartX = width/7f
        val lText = arrayOf("提交","同意","开始","结束","评价")
        drawText(canvas,leftStartX,beginY,oneY,leftTextPaint,lText)
        val centerText = arrayOf("订单已提交","等待医生同意","预约时间已到","治疗结束","订单已完成")
        val centerStartX = width/5f
        drawText(canvas,centerStartX,beginY,oneY,centerTextPaint,centerText)
        val rightStartX = width/1.5f
        drawText(canvas,rightStartX,beginY,oneY,rightTextPaint,timeList.toTypedArray())
        val barStartX = (leftStartX+centerStartX)/2f
        drawBar(canvas,barStartX,beginY,oneY)
    }

    private fun drawText(canvas: Canvas,beginX:Float,beginY:Float,oneY:Float,paint: TextPaint,array: Array<String>){
        var startY = beginY
        var count = 0
        for (text in array){
            canvas.drawText(text,beginX,startY,paint)
            startY+=oneY
            count++
            if (count>4){
                return
            }
        }
    }

    private fun drawBar(canvas: Canvas,beginX: Float,beginY: Float,oneY: Float){
        var startY = beginY-dpToPx(4)
        val y = startY + oneY*state
        canvas.drawLine(beginX,startY,beginX,y,redBarPaint)
        canvas.drawLine(beginX,y,beginX,startY+oneY*4,grayBarPaint)
        val radius = dpToPx(5)
        var outPaint = redBarPaint
        for (i in 0 until 5){
            if (i == state){
                outPaint= grayBarPaint
            }
            canvas.drawCircle(beginX,startY+i*oneY,radius+3,outPaint)
            canvas.drawCircle(beginX,startY+i*oneY,radius,whiteCirclePaint)
        }
    }

    private fun dpToPx(dp:Int):Float{
        val density = resources.displayMetrics.density
        return dp * density
    }
}