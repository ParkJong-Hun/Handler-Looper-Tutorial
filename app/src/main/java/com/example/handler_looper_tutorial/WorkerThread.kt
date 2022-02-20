package com.example.handler_looper_tutorial

import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.handler_looper_tutorial.Contract.Companion.COUNT_DATA_KEY
import com.example.handler_looper_tutorial.Contract.Companion.MSG_CODE

//Handler를 이용하여 run을 실행할 때 아래와 같은 내용을 하는 커스텀 스레드 정의
class WorkerThread(private val handler: Handler): Thread() {
    override fun run() {
        for(i in 0..100) {
            val bundle = Bundle()
            bundle.putInt(COUNT_DATA_KEY, i)

            val msg = Message()
            msg.what = MSG_CODE
            msg.data = bundle

            handler.sendMessage(msg)
            sleep(100)
        }
    }
}