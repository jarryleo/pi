package cn.leo.pi

import cn.leo.pi.msg.BaseMsg
import cn.leo.pi.msg.MsgType
import cn.leo.pi.mycar.Command
import cn.leo.pi.mycar.MyCar
import cn.leo.pi.mycar.PwmCommand
import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.*
import com.google.gson.Gson
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    //延时5秒等待系统启动完成
    logI("系统启动中，请稍候。。。")
    delay(5000)
    val sender = UdpFrame.getSender(PropertiesUtil.port)
    val listener = UdpFrame.getListener()
    listener.subscribe(PropertiesUtil.port){ data, host ->
        val json = String(data, Charsets.UTF_8)
        try {
            val msg = JsonUtil.fromJson(json, BaseMsg::class.java)
            when {
                msg.type == MsgType.TYPE_CAR -> //小车执行普通指令（前后左右转弯）
                    MyCar.executeCommand(object :BaseMsg<Command>(){}.fromJson(json).data!!)
                msg.type == MsgType.TYPE_PWM_COMMAND -> //小车执行精细指令（每个轮子独立控制）特技玩法
                    MyCar.executePWM(object :BaseMsg<PwmCommand>(){}.fromJson(json).data!!)
                msg.type == MsgType.TYPE_SHUTDOWN -> {
                    //关闭服务
                    listener.closePort(PropertiesUtil.port)
                    this.cancel()
                    System.exit(0)
                }
                msg.type == MsgType.TYPE_POWEROFF -> //关闭树莓派
                    PythonUtil.powerOff()
            }
            if (msg.type != MsgType.TYPE_BROADCAST) {
                logD("$host :$json")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    logI("小车控制系统启动完成")
    //每秒广播自身位置
    while (isActive){
        delay(1000)
        val msg = BaseMsg<String>(type = MsgType.TYPE_BROADCAST)
        val json = JsonUtil.toJson(msg)
        sender.sendBroadcast(json.toByteArray(Charsets.UTF_8))
    }
}