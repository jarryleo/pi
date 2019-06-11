package cn.leo.pi.gpio

import com.pi4j.io.gpio.RaspiPin

/**
 * 根据板子类型获取针脚
 */
object PinUtil {
    fun getPin(index: Int) = RaspiPin.getPinByAddress(index)
}