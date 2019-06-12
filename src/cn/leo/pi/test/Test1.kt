package cn.leo.pi.test

import cn.leo.pi.msg.BaseMsg
import cn.leo.pi.msg.MsgType
import cn.leo.pi.mycar.Command
import cn.leo.pi.mycar.MyCar
import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.CoroutineUtil
import cn.leo.pi.utils.PropertiesUtil
import cn.leo.pi.utils.logD
import cn.leo.pi.utils.logI
import com.alibaba.fastjson.JSON
import kotlinx.coroutines.*
import java.util.*

fun main(args: Array<String>) = runBlocking {
    var remoteHost = ""
    var timeOut = 0
    var sender = UdpFrame.getSender(PropertiesUtil.port)
    val listener = UdpFrame.getListener()
    listener.subscribe(PropertiesUtil.port){ data, host ->
        val json = String(data, Charsets.UTF_8)
        try {
            val msg = JSON.parseObject(json, BaseMsg::class.java)
            if (msg.type == MsgType.TYPE_BORAD_CAST) {
                timeOut = 0
                if (remoteHost !=host) {
                    remoteHost = host
                    sender = UdpFrame.getSender(remoteHost, PropertiesUtil.port)
                    logI("成功连接到树莓派，ip地址: $host")
                }
            }else{
                logD("msg : $json")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    logI("远程控制端启动")
    //超时检测协程
    CoroutineUtil.io{
        while (isActive) {
            if (remoteHost != "") {
                delay(1000)
                timeOut++
                if (timeOut > 10) {
                    logI("树莓派断开连接")
                }
            }
        }
    }
    //命令输入协程
    val scanner = Scanner(System.`in`)
    while (isActive){
        val text = scanner.next()
        val msg = BaseMsg<String>(msg = text)
        val json = JSON.toJSONString(msg)
        sender.send(json.toByteArray(Charsets.UTF_8))
    }
}