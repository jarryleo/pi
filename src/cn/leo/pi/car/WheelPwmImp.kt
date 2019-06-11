package cn.leo.pi.car

import cn.leo.pi.car.dim.Wheel
import cn.leo.pi.gpio.GPIO
import com.pi4j.io.gpio.Pin
import com.pi4j.io.gpio.PinState

class WheelPwmImp(pin1: Pin, pin2: Pin) : Wheel {

    private val pin1Pwm = GPIO.instance.provisionPwmOutputPin(pin1)
    private val pin2Pwm = GPIO.instance.provisionPwmOutputPin(pin2)
    private val pin1Dig = GPIO.instance.provisionDigitalOutputPin(pin1,PinState.LOW)
    private val pin2Dig = GPIO.instance.provisionDigitalOutputPin(pin2,PinState.LOW)

    override fun idle() {
        pin1Dig.low()
        pin2Dig.low()
    }

    override fun forward(speed: Int) {
        if (speed <0){
            pin1Dig.high()
        }else {
            pin1Pwm.setPwmRange(speed)
        }
        pin2Dig.low()
    }

    override fun backward(speed: Int) {
        pin1Dig.low()
        if (speed <0){
            pin2Dig.high()
        }else {
            pin2Pwm.setPwmRange(speed)
        }
    }

    override fun brake() {
        pin1Dig.high()
        pin2Dig.high()
    }

}