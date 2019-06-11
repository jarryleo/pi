package cn.leo.pi.gpio

import com.pi4j.io.gpio.Pin
import com.pi4j.io.gpio.RaspiPin
import com.pi4j.system.SystemInfo

/**
 * 根据板子类型获取针脚
 */
object PinUtil {
    private val allPins: Array<out Pin> = RaspiPin.allPins(SystemInfo.BoardType.RaspberryPi_3B_Plus)
    fun getPin(index: Int) = allPins[index]
}