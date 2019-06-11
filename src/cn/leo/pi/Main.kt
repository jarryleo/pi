package cn.leo.pi

import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.logD
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val sender = UdpFrame.getSender(25535)
    val listener = UdpFrame.getListener()
    listener.subscribe(25535){ data, host ->
        logD("$host :${String(data,Charsets.UTF_8)}")
    }
    while (isActive){
        delay(1000)
        sender.send("这里是测试发来信息".toByteArray(Charsets.UTF_8))
    }
}