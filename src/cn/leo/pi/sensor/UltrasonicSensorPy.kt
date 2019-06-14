package cn.leo.pi.sensor

import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.PythonUtil
import cn.leo.pi.utils.logD
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

/**
 * 超声波测距
 * 数值单位 毫米 mm
 */

class UltrasonicSensorPy(trigPin: Int, echoPin: Int,
                         private val sendPort:Int,
                         private val receivePort:Int) : BaseSensor {
    var sensorDelay = 1

    init {
        //启动python脚本，开启超声波测距
        PythonUtil.exePy("C:/Users/lingluo/Desktop/Pipy/udp/sensor.py","127.0.0.1","25535")
    }

    fun listen() = runBlocking{
        UdpFrame.getListener().subscribe(receivePort){
            data, host ->
            logD(data.toString(Charsets.UTF_8))
        }

        val sender = UdpFrame.getSender("127.0.0.1",sendPort)
        while (isActive) {
            delay(1000)
            sender.send(sensorDelay.toString().toByteArray(Charsets.UTF_8))
        }
    }
}