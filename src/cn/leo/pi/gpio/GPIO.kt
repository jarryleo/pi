package cn.leo.pi.gpio

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory

object GPIO {
    private var gpio = GpioFactory.getInstance()
    val instance: GpioController
        get() = if(gpio.isShutdown){
        gpio = GpioFactory.getInstance()
        gpio
    }else{
        gpio
    }
}