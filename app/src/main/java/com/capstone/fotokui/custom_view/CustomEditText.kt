package com.capstone.fotokui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.fotokui.R

class CustomEditText : AppCompatEditText {
//    private lateinit var enabledBackground: Drawable
//    private lateinit var disabledBackground: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setTextColor(currentTextColor)
        gravity = Gravity.CENTER
        textAlignment = TEXT_ALIGNMENT_TEXT_START
        setPadding(50,2,50,10)

    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        background = ContextCompat.getDrawable(context, R.drawable.custom_edit_text)
    }
}