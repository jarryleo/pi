package cn.leo.pi.test

import cn.leo.pi.msg.BaseMsg
import cn.leo.pi.msg.MsgType
import cn.leo.pi.mycar.Command
import cn.leo.pi.mycar.MyCar
import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.PropertiesUtil
import cn.leo.pi.utils.logD
import cn.leo.pi.utils.logI
import com.alibaba.fastjson.JSON
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import java.util.*

fun main(args: Array<String>) = runBlocking {
    var remoteHost = ""
    var sender = UdpFrame.getSender(PropertiesUtil.port)
    val listener = UdpFrame.getListener()
    listener.subscribe(PropertiesUtil.port){ data, host ->
        val json = String(data, Charsets.UTF_8)
        try {
            val msg = JSON.parseObject(json, BaseMsg::class.java)
            if (msg.type == MsgType.TYPE_BORAD_CAST) {
                if (remoteHost !=host) {
                    remoteHost = host
                    sender = UdpFrame.getSender(remoteHost, PropertiesUtil.port)
                    logD("remote : $host")
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    logI("远程控制端启动")
    val scanner = Scanner(System.`in`)
    while (isActive){
        val text = scanner.next()
        sender.send(text.toByteArray(Charsets.UTF_8))
    }
}