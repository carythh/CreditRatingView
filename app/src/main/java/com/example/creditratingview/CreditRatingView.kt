package com.example.creditratingview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author tanhuohui
 * @desc 信用等级
 */

class CreditRatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    private val mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRatingValuePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRatingNamePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDivideLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mRatingValueList = listOf<String>()
    private var mRatingNameList = listOf<String>()
    private var mWidthList = mutableListOf<Float>()
    private var mWidthRetaList = listOf<Float>()
    private var mLineWidth= 22f
    private var mDivideLineWidth= 2f
    private var mDivideLineHeight= 12f
    private val margin = 20 //左右margin

    init {
        mLinePaint.apply {
            strokeWidth = mLineWidth
            strokeCap = Paint.Cap.ROUND
        }
        mRatingValuePaint.apply {
            color = Color.parseColor("#878787")
            textSize =sp2px(getContext(),12f)
        }
        mRatingNamePaint.apply {
            color = Color.parseColor("#323232")
            textSize =sp2px(getContext(),15f)
        }
        mDivideLinePaint.apply {
            strokeWidth = mDivideLineWidth
            color =Color.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        mRatingValueList = listOf("0分", "60分", "70分", "80分", "90分", "100分") //刻度值
        mRatingNameList= listOf("D", "C", "B", "A","S") //等级名称
        mWidthRetaList= listOf(0.3f, 0.175f, 0.175f, 0.175f, 0.175f)//分段占比
    }
    fun refreshData(mRatingValues : List<String>,mRatingNames : List<String>,mWidthRetaList: List<Float> ){
        this.mRatingValueList =mRatingValues
        this.mRatingNameList= mRatingNames
        this.mWidthRetaList =mWidthRetaList
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        suggestedMinimumWidth
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(mRatingValueList.isEmpty()){
            return  //空数据
        }
        var mWidth=width-margin*2 //除去边距
        canvas?.let {
            var totalmWidthList =0f
            for ((index, mValue) in mRatingValueList.withIndex()) {
                //每个等级百分占比
                var mRatingValueWidth =  0f
                if(index!=0){
                    mRatingValueWidth =  mWidthRetaList[index-1]*(mWidth)
                    mWidthList.add(mRatingValueWidth)
                }
                totalmWidthList += mRatingValueWidth
                //线的下面画刻度
                if(index==0){
                    canvas.drawText(
                        mValue,
                        margin +0f,
                        (height / 2f + 50),
                        mRatingValuePaint
                    )
                }else if(index==(mRatingValueList.size-1)){
                    canvas.drawText(
                        mValue,
                        mWidth - getTextWidth(mValue)*2+margin,
                        (height / 2f + 50),
                        mRatingValuePaint
                    )
                    //线的上面画等级名称，画在每段的中间
                    var mRatingNameWidth=mRatingValueWidth/2f
                    if(mWidthList.size>1){
                        mRatingNameWidth=totalmWidthList-mRatingValueWidth+mRatingValueWidth/2f
                    }
                    canvas.drawText(
                        mRatingNameList[index - 1],
                        margin +mRatingNameWidth- getTextWidth(mRatingNameList[index - 1]),
                        height / 2f - 30,
                        mRatingNamePaint
                    )
                }else{
                    canvas.drawText(
                        mValue,
                        margin +totalmWidthList - getTextWidth(mValue),
                        (height / 2f + 50),
                        mRatingValuePaint
                    )
                    //线的上面画等级名称，画在每段的中间
                    var mRatingNameWidth=mRatingValueWidth/2f
                    if(mWidthList.size>1){
                        mRatingNameWidth=totalmWidthList-mRatingValueWidth+mRatingValueWidth/2f
                    }
                    canvas.drawText(
                        mRatingNameList[index - 1],
                        margin +mRatingNameWidth- getTextWidth(mRatingNameList[index - 1]),
                        height / 2f - 30,
                        mRatingNamePaint
                    )
                }

            }
            //画线
            val mLinearGradient = LinearGradient(
                0f,
                0f,
                totalmWidthList,
                0f,
                Color.parseColor("#FFD09D"),
                Color.parseColor("#F7562E"),
                Shader.TileMode.CLAMP
            )
            mLinePaint.shader = mLinearGradient
            canvas.drawLine(
                margin +0f,
                (height / 2f),
                margin+totalmWidthList,
                (height / 2f),
                mLinePaint
            )
        }
        canvas?.let {
            var totalmWidthList =0f
            for ((index, _) in mRatingValueList.withIndex()) {
                //每个等级百分占比
                var mRatingValueWidth =  0f
                if(index!=0){
                    mRatingValueWidth =  mWidthRetaList[index-1]*(width)
                    mWidthList.add(mRatingValueWidth)
                }
                totalmWidthList += mRatingValueWidth
                if(index>0&&index<(mRatingValueList.size-1)){
                    //画每段等级的间隔线
                    canvas.drawLine(
                        margin +totalmWidthList - mDivideLineWidth/2,
                        height / 2f-mDivideLineHeight/2,
                        margin+totalmWidthList - mDivideLineWidth/2,
                        height/2f+mDivideLineHeight/2,
                        mDivideLinePaint
                    )
                }
            }
        }


    }
    private fun getTextWidth(text: String?): Float {
        return mRatingValuePaint.measureText(text) / 2
    }
    fun sp2px(context: Context, spValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f)
    }

}