package com.example.coroutines.customize.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.example.coroutines.R
import com.example.coroutines.databinding.CustomChartLayoutBinding
import com.example.coroutines.model.Result
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.custom_chart_layout.view.*
import kotlin.math.max

/**
 * Created by NhiNguyen on 4/13/2020.
 */

class CustomLineChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : BaseCustomView(context, attrs, defStyle), OnChartValueSelectedListener {

    lateinit var binding: CustomChartLayoutBinding

    override fun initViewDataBinging(inflater: LayoutInflater): ViewDataBinding {
        binding = CustomChartLayoutBinding.inflate(inflater, this, true)
        return binding
    }

    fun setData(data: Pair<MutableList<Float>, MutableList<String>>) {
        initChart(lineChart, data)
        /*Set market default at end of chasrt*/
//        setHighLightPosition(data.size - 1)
    }

    fun setHighLightPosition(position: Int) {
        lineChart.highlightValue(position.toFloat(), 0)
        lineChart.notifyDataSetChanged()
    }

    private fun initChart(chart: LineChart, data: Pair<MutableList<Float>, MutableList<String>>) {
        run {
            chart.setBackgroundColor(Color.WHITE)
            chart.description.isEnabled = false
            chart.setTouchEnabled(true)

            chart.setOnChartValueSelectedListener(this)
            chart.setDrawGridBackground(false)

            val mv = CustomMarkerView(context)
            mv.chartView = chart
            chart.marker = mv
            chart.setDrawMarkers(true)
            chart.onRtlPropertiesChanged(View.LAYOUT_DIRECTION_RTL)

            chart.isDragEnabled = false
            chart.setPinchZoom(false)
            chart.isDoubleTapToZoomEnabled = false
            chart.setScaleEnabled(false)
        }

        chart.axisRight.isEnabled = false
        chart.axisLeft.isEnabled = true
        chart.xAxis.isEnabled = true
        chart.xAxis.setCenterAxisLabels(true)
        chart.xAxis.position = XAxisPosition.BOTTOM
        chart.xAxis.setLabelCount(12, true)
        chart.axisLeft.axisMinimum = -5f
        chart.axisLeft.axisMaximum = data.first.maxOf { it } + 5f
        setData(chart, data)
        chart.animateX(1000)
        // get the legend (only possible after setting data)
        val l = chart.legend
        // draw legend entries as lines
        l.form = Legend.LegendForm.LINE
    }

    private fun setData(chart: LineChart, data: Pair<MutableList<Float>, MutableList<String>>) {
        chart.setBackgroundColor(
            ResourcesCompat.getColor(
                context.resources,
                R.color.white, null
            )
        )
        chart.setVisibleXRangeMinimum(7f)
        val values = java.util.ArrayList<Entry>()

        data.first.forEachIndexed { i, fl ->
            values.add(
                Entry(
                    i.toFloat(),
                    fl,
                    ContextCompat.getDrawable(context, when {
                        i > 0 && fl < data.first[i - 1] -> {
                            R.drawable.ic_circle_red
                        }
                        else -> {
                            R.drawable.ic_circle_blue
                        }
                    }),
                    data.second[i]
                )
            )
        }
        val set1: LineDataSet

        if (chart.data != null && chart.data!!.dataSetCount > 0) {
            set1 = chart.data!!.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data?.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, null)
            set1.setDrawIcons(true)
            set1.color = Color.parseColor("#7470EF")
            set1.setDrawCircles(false)
            set1.lineWidth = 2f
            set1.setDrawCircleHole(false)
            set1.setDrawValues(false)
            set1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            set1.setDrawHighlightIndicators(false)
            // set the filled area
            set1.setDrawFilled(false)
            val dataSets = java.util.ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets
            // set data
            chart.data = LineData(dataSets)
        }
    }

    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }
}