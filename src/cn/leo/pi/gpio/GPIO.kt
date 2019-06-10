package cn.leo.pi.gpio

import com.pi4j.io.gpio.GpioFactory

object GPIO {
    private val gpio = GpioFactory.getInstance()
    val instance
    get() = gpio
}