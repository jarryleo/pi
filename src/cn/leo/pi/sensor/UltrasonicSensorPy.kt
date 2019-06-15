package cn.leo.pi.sensor

import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.CoroutineUtil
import cn.leo.pi.utils.PathUtil
import cn.leo.pi.utils.PythonUtil
import cn.leo.pi.utils.logD
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import java.io.File

/**
 * 超声波测距
 * 数值单位 毫米 mm
 */

class UltrasonicSensorPy(trigPin: Int, echoPin: Int,
                         private val sendPort:Int,
                         private val receivePort:Int) : BaseSensor {
    //多少秒发送一次,浮点值，可以是小数
    var sensorDelay = 0.2f
    var distance = 1000f

    init {
        //启动python脚本，开启超声波测距
        val path = "${PathUtil.getPath()}ultrasonic_sensor.py"
//        val path = "C:/Users/lingluo/Desktop/Pipy/udp/ultrasonic_sensor.py"
        if(File(path).exists()){
            PythonUtil.exePy(path,
                    "127.0.0.1",
                    sendPort.toString(),
                    receivePort.toString(),
                    trigPin.toString(),
                    echoPin.toString())
            logD("python超声波传感器启动")
        }else{
            logD("$path 文件不存在")
        }
    }
    //监听python发送过来的超声波距离
    fun listen(listener: (Float) -> Unit) = CoroutineUtil.io{
        UdpFrame.getListener().subscribe(sendPort){
            data, _ ->
            distance = data.toString(Charsets.UTF_8).toFloat()
            listener(distance)
            //logD(data.toString(Charsets.UTF_8))
        }

        //控制超声波采样频率，单位秒
        val sender = UdpFrame.getSender("127.0.0.1",receivePort)
        while (isActive) {
            delay((sensorDelay*1000).toLong())
            sender.send(sensorDelay.toString().toByteArray(Charsets.UTF_8))
        }
    }
}