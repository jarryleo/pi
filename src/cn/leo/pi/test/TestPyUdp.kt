package cn.leo.pi.test

import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.PythonUtil
import cn.leo.pi.utils.logD
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    PythonUtil.exePy("python C:\\Users\\lingluo\\Desktop\\Pipy\\udp\\__init__.py")

    UdpFrame.getListener().subscribe(25535){
        data, host ->
        logD(data.toString(Charsets.UTF_8))
    }

    val sender = UdpFrame.getSender("127.0.0.1",25536)
    while (isActive) {
        sender.send("Hello Python".toByteArray(Charsets.UTF_8))
        delay(1000)
    }
}