package cn.leo.pi.test

import cn.leo.pi.sensor.UltrasonicSensorPy
import cn.leo.pi.utils.PropertiesUtil
import cn.leo.pi.utils.logD
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    //超声波测距防正面撞击
    val ultrasonicSensorPy = UltrasonicSensorPy(
            PropertiesUtil.pinTrig,
            PropertiesUtil.pinEcho,
            25567,
            25568).apply {
        val l = listen {
            logD("当前距离：$it")
        }
        l.join()
    }
}