package cn.leo.pi.gpio

import com.pi4j.io.gpio.GpioPinPwmOutput

class GpioLab {
    var mPwm: GpioPinPwmOutput? = null
    fun getGpio(index:Int){
        if (mPwm != null){
        }
        val pin = PinUtil.getPin(index)
        val pwm = GPIO.instance.provisionSoftPwmOutputPin(pin)

    }
}