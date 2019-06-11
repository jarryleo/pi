package cn.leo.pi

import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin

fun main(args: Array<String>) {
    val gpio = GpioFactory.getInstance()
    val pin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_01, "test", 0)
        pin.setPwmRange(1)

    val pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "test1", PinState.HIGH)
        pin1.low()
        pin1.blink(120)
        pin1.toggle()

}