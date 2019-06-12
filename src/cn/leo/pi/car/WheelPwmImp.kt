package cn.leo.pi.car

import cn.leo.pi.car.dim.Wheel
import cn.leo.pi.gpio.GPIO
import com.pi4j.io.gpio.Pin

class WheelPwmImp(pin1: Pin, pin2: Pin) : Wheel {

    val pin1Pwm = GPIO.instance.provisionSoftPwmOutputPin(pin1)
    val pin2Pwm = GPIO.instance.provisionSoftPwmOutputPin(pin2)

    override fun idle() {
        pin1Pwm.pwm = 0
        pin2Pwm.pwm = 0
    }

    override fun forward(speed: Int) {
        if (speed < 0) {
            pin1Pwm.pwm = 100
        } else {
            pin1Pwm.pwm = speed
        }
        pin2Pwm.pwm = 0
    }

    override fun backward(speed: Int) {
        pin1Pwm.pwm = 0
        if (speed < 0) {
            pin2Pwm.pwm = 100
        } else {
            pin2Pwm.pwm = speed
        }
    }

    override fun brake() {
        pin1Pwm.pwm = 100
        pin2Pwm.pwm = 100
    }

}