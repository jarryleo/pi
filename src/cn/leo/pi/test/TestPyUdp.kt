package cn.leo.pi.test

import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.PythonUtil
import cn.leo.pi.utils.logD
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    PythonUtil.exePy("C:/Users/lingluo/Desktop/Pipy/udp/sensor.py","127.0.0.1","25535")

    UdpFrame.getListener().subscribe(25535){
        data, host ->
        logD(data.toString(Charsets.UTF_8))
    }

    val sender = UdpFrame.getSender("127.0.0.1",25536)
    while (isActive) {
        delay(10000)
        sender.send("1".toByteArray(Charsets.UTF_8))
    }
}