package com.example.handler_looper_tutorial

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView
import com.example.handler_looper_tutorial.Contract.Companion.COUNT_DATA_KEY
import com.example.handler_looper_tutorial.Contract.Companion.MSG_CODE

//MSG_CODE를 검사해 맞으면 TextView 내용을 바꾸는 커스텀 핸들러
class CustomHandler(private val textView: TextView): Handler(Looper.myLooper()!!) {
    override fun handleMessage(msg: Message) {
        when(msg.what) {
            MSG_CODE -> textView.text = msg.data.getInt(COUNT_DATA_KEY).toString()
        }
    }
}