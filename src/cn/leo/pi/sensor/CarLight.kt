package cn.leo.pi.sensor

import cn.leo.pi.gpio.GPIO
import cn.leo.pi.utils.CoroutineUtil
import com.pi4j.io.gpio.Pin

class CarLight(pin: Pin) {
    val lightPin = GPIO.instance.provisionDigitalOutputPin(pin)

    fun lightTrigger(isOn:Boolean) = CoroutineUtil.io {
        if(isOn){
            lightPin.low()
        }else{
            lightPin.high()
        }
    }
}