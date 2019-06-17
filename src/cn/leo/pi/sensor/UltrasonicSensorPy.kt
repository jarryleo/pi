package cn.leo.pi.sensor

import cn.leo.pi.udp.UdpFrame
import cn.leo.pi.utils.CoroutineUtil
import cn.leo.pi.utils.PathUtil
import cn.leo.pi.utils.PythonUtil
import cn.leo.pi.utils.logD
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.io.File

/**
 * 超声波测距
 * 数值单位 毫米 mm
 */

class UltrasonicSensorPy(private val trigPin: Int,
                         private val echoPin: Int,
                         private val sendPort: Int,
                         private val receivePort: Int) : BaseSensor {
    //多少秒发送一次,浮点值，可以是小数
    val defaultDelay = 0.2f
    var sensorDelay = defaultDelay
    var distance = 1000f
    private var isStart = false

    fun startPython() = CoroutineUtil.io {
        //启动python脚本，开启超声波测距
        if (isStart){
            sensorDelay = 0f
            delay(1100)
            if (!isStart) {
                return@io
            }
        }
        val path = "${PathUtil.getPath()}ultrasonic_sensor.py"
//        val path = "C:/work/javaCode/pi/ultrasonic_sensor.py"
        if (File(path).exists()) {
            PythonUtil.exePy(path,
                    "127.0.0.1",
                    sendPort.toString(),
                    receivePort.toString(),
                    trigPin.toString(),
                    echoPin.toString())
            sensorDelay = defaultDelay
            isStart = true
            logD("python超声波传感器启动")
        } else {
            logD("$path 文件不存在")
        }
    }

    fun stop() {
        sensorDelay = 0f
        isStart = false
        distance = 1000f
        logD("python超声波传感器关闭")
    }

    //监听python发送过来的超声波距离
    fun listen(listener: (Float) -> Unit) = CoroutineUtil.io {
        UdpFrame.getListener().subscribe(sendPort) { data, _ ->
            if (isStart) {
                distance = data.toString(Charsets.UTF_8).toFloat()
                listener(distance)
                //logD(data.toString(Charsets.UTF_8))
            }
        }

        //控制超声波采样频率，单位秒
        val sender = UdpFrame.getSender("127.0.0.1", receivePort)
        while (isActive) {
            sender.send(sensorDelay.toString().toByteArray(Charsets.UTF_8))
            if (sensorDelay > 0f && isStart) {
                delay((sensorDelay * 1000).toLong())
            } else {
                delay(1000)
            }
        }

    }
}