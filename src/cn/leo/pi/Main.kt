package cn.leo.pi

import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.PropertiesUtil
import cn.leo.pi.utils.logD
import cn.leo.pi.utils.logI
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val sender = UdpFrame.getSender(PropertiesUtil.port)
    val listener = UdpFrame.getListener()
    listener.subscribe(PropertiesUtil.port){ data, host ->
        logD("$host :${String(data,Charsets.UTF_8)}")
    }
    logI("小车控制系统启动完成")
    while (isActive){
        delay(1000)
        sender.send("这里是测试发来信息".toByteArray(Charsets.UTF_8))
    }
}