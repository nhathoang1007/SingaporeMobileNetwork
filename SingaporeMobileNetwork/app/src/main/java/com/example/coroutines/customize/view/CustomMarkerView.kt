package com.example.coroutines.customize.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat.getColor
import com.example.coroutines.R
import com.example.coroutines.extensions.dp
import com.example.coroutines.extensions.getDefault
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import kotlinx.android.synthetic.main.custom_marker_chart.view.*

/**
 * Created by NhiNguyen on 4/13/2020.
 */

class CustomMarkerView(context: Context) : MarkerView(context, R.layout.custom_marker_chart) {
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val label = e?.label.getDefault()
        val value = String.format("%.06f", e?.y)
        val span = SpannableStringBuilder("$label\n$value")
        span.setSpan(StyleSpan(Typeface.BOLD), 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(RelativeSizeSpan(1.2f), 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val color = ColorStateList.valueOf(Color.parseColor(if (e?.isDecreaseValue.getDefault()) "#FF5E5E" else "#7470EF"))
        tvValue?.apply {
            text = span
            backgroundTintList = color
        }
        bottom_view?.backgroundTintList = color
        super.refreshContent(e, highlight)
    }

    override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
        val posx = posX - lnValue.width / 2
        var posy = posY - lnValue.height + 6.dp
        if (posy < 0) posy = 0f

        // translate to the correct position and draw
        canvas?.translate(posx, posy)
        draw(canvas)
        canvas?.translate(-posx, -posy)
    }
}