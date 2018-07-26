package com.textclipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_longclick.apply {
            setOnLongClickListener {
                showPopupWindow()

                if(it is TextView) {
                    val clickText = it.text.toString()
                    Log.i("Warren", it.text.toString())
                }
                false
            }
        }
    }

    fun showPopupWindow() {
        val view = LayoutInflater.from(this).inflate(R.layout.popupwindow_layout,null)
        popupWindow?.dismiss()
        popupWindow = PopupWindow(this)
        popupWindow?.let {
            it.contentView = view
            it.width = ViewGroup.LayoutParams.WRAP_CONTENT
            it.height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, android.R.color.transparent)))
            it.isOutsideTouchable = true
            it.isFocusable = true
            view.findViewById<TextView>(R.id.tv_copy)?.apply {
                setOnClickListener {
                    val copyText = tv_longclick.text.toString()
                    val cmb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cmb.primaryClip = ClipData.newPlainText(null, copyText)
                    Toast.makeText(this@MainActivity, "Copy to Clipboard.", Toast.LENGTH_SHORT).show()
                    popupWindow?.dismiss()
                }
            }
            it.showAsDropDown(tv_longclick)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        popupWindow?.dismiss()
    }
}
