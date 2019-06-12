package cn.leo.pi

import cn.leo.pi.msg.BaseMsg
import cn.leo.pi.msg.MsgType
import cn.leo.pi.mycar.Command
import cn.leo.pi.mycar.CommandType
import cn.leo.pi.mycar.MyCar
import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.PropertiesUtil
import cn.leo.pi.utils.logD
import cn.leo.pi.utils.logI
import com.alibaba.fastjson.JSON
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val sender = UdpFrame.getSender(PropertiesUtil.port)
    val listener = UdpFrame.getListener()
    listener.subscribe(PropertiesUtil.port){ data, host ->
        val json = String(data, Charsets.UTF_8)
        try {
            val msg = JSON.parseObject(json, BaseMsg::class.java)
            if (msg.type == MsgType.TYPE_CAR) {
                MyCar.executeCommand(msg.data as Command)
            }
            if (msg.type != MsgType.TYPE_BORAD_CAST) {
                logD("$host :$json")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    logI("小车控制系统启动完成")

    while (isActive){
        delay(1000)
        val msg = BaseMsg<String>(type = MsgType.TYPE_BORAD_CAST)
        val json = JSON.toJSONString(msg)
        sender.sendBroadcast(json.toByteArray(Charsets.UTF_8))
    }
}