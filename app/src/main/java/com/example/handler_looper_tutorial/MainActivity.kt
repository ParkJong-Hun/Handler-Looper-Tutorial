package com.example.handler_looper_tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        val handler = CustomHandler(textView)
        val thread = WorkerThread(handler)
        thread.start()
    }
}

/*Handler/Looper
안드로이드 시스템은 기본적으로 하나의 메인 스레드만을 가지고 이 스레드에서 작업을 하는데, UI 관련 작업은
반드시 메인 스레드에서 처리를 해야하고 시간이 오래 걸릴 수 있는 작업이나 데이터베이스 작업들은 별도의
스레드에서 작업을 해야한다.
멀티 스레드 환경에서 작업해야하는데, 서로 다른 스레드간 통신을 하기 위해 고려해야할 부분이 많아짐.
이 때 안드로이드에서 스레드간 통신을 도와주는 도구.
최근에는 Handler 생성 중 암시적으로 Looper를 선택하면 작업이 자동으로 손실, 충돌 또는 경쟁 조건이
발생하는 버그가 발생할 수 있어서, 명시적으로 Looper를 지정해 생성한다.
 */

/*Handler
안드로이드에서 사용할 수 있는 스레드 통신 방법은 여러가지가 있는데, 대표적인 방법 중 하나가 Handler를 통해
Message를 전달하는 방법. Handler를 생성하면 호출한 스레드의 Message Queue와 Looper에
자동으로 연결.
 */

/*핸들러를 통한 스레드간 통신
1. 핸들러의 sendMessage()를 통해 Message(작업)을 전달해 핸들러는 Message Queue에 차례대로 넣음.
2. Looper가 Message Queue로부터 하나씩 Message를 뽑아 핸들러로 전달.
3. Looper로부터 전달받은 Message는 handleMessage()를 통해 작업을 함.
 */

/*핸들러 주요 메서드
obtainMessage(): 핸들러 자신으로 지정된 Message 객체를 반환.
sendMessage(msg: Message): Message Queue에 Message 만들어 전달.
post(runnable: Runnable): Message Queue애 Runnable 전달.
sendEmptyMessage(what: Int): Message Queue에 빈 Message를 만들어 전달.
removeMessage(what: Int): 전달한 Message를 Message Queue에서 삭제.
handleMessage(msg: Message): Looper를 통해 Message Queue에서 꺼낸 Message나 Runnable 처리.
 */

/*Message
스레드 통신에서 Handler에 데이터를 보내기 위한 클래스. 데이터를 객체에 담아 Handler로 보내면
해당 객체는 Handler를 통해 Message Queue에 쌓임.

what: 종류를 식별하기 위한 코드
arg1: 전달되는 정수 값 1
arg2: 전달되는 정수 값 2
obj: 전달되는 객체
 */

/*Message Queue
Message 객체를 Queue 형태로 관리하는 자료 구조.
호출 순서는 sendMessage() -> handleMessage()
 */

/*Looper
Thread당 하나씩 밖에 가질 수 없고, Message Queue가 비어 있는 동안 아무 행동도 하지 않고,
Message가 들어오면 해당 Message를 꺼내 적절한 Handler에 전달. 기본적으로 새로 생성한 Thread
는 Looper를 가지지 않고, Looper.prepare() 메서드를 호출해야 생성됨.
무한히 실행되는 Message Loop를 통해 Queue에 Message가 들어오는지 감시해 들어온 Message를
처리할 Handler를 찾아 handleMessage()를 호출.
 */

/*루퍼 주요 메서드
prepare(): 루퍼를 생성.
loop(): 무한히 루프를 돌며 Message Queue에 쌓인 Message나 Runnable 객체를 핸들러에 전달.
quit(): 루프 종료.
 */

/*Handler Thread
메인 스레드는 기본적으로 루퍼가 생성되어 있지만, 일반적으로 새로 생성된 스레드는 루퍼를 가지고 있지
않는다. 따라서 Handler(Looper.getMainLooper())로 루퍼를 가져오거나, 메시지를 받기 위해
Looper.prepare() 메서드와 Looper.loop() 메서드를 통해 Looper를 생성해 메시지를 받아야 한다.
이를 쉽게 사용하기 위해 HandlerThread는 Thread를 상속해 자동으로 Looper.prepare(), Looper.loop()
를 실행해 개별 Looper를 가지는 내부에서 무한루프를 도는 백그라운드 스레드를 의미한다.
 */