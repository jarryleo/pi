package cn.leo.pi.sensor

import cn.leo.pi.gpio.GPIO
import com.pi4j.io.gpio.Pin

class SettingGear(pin1: Pin) {
    val gearPin = GPIO.instance.provisionSoftPwmOutputPin(pin1, 50)
    val a = 10
    val b = 2
    fun setDirection(direction: Int) {
        val duty = a / 180 * direction + b
        gearPin.pwm = direction
    }
}