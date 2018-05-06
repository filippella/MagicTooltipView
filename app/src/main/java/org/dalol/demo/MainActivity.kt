package org.dalol.demo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.dalol.magictooltipview.MagicTooltipView
import org.dalol.magictooltipview.TooltipPopup

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View?) {
        val popup = TooltipPopup(this)
        val tooltipView = MagicTooltipView(this)
        tooltipView.setTooltipBgColor(Color.parseColor("#7e4add"))
        tooltipView.setText("Filippo tooltip message", 14f, false)
        popup.setView(tooltipView)
        popup.setFocusable(true)
        popup.show(v)
    }
}
