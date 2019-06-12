package cn.leo.pi.sensor

import cn.leo.pi.gpio.GPIO
import cn.leo.pi.gpio.PinUtil
import cn.leo.pi.utils.CoroutineUtil
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent
import com.pi4j.io.gpio.event.GpioPinListenerDigital
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * 超声波测距
 * 数值单位 毫米 mm
 */

class UltrasonicSensor(trigPin: Int, echoPin: Int) : BaseSensor {
    private val trig = GPIO.instance.provisionDigitalOutputPin(PinUtil.getPin(trigPin))
    private val echo = GPIO.instance.provisionDigitalInputPin(PinUtil.getPin(echoPin))

    fun listen(listener: (Double) -> Unit) {
        CoroutineUtil.io {
            while (isActive) {
                trig.high()
                delay(1)
                trig.low()
                val time1 = System.currentTimeMillis()
                echo.addListener(object : GpioPinListenerDigital {
                    override fun handleGpioPinDigitalStateChangeEvent(
                            state: GpioPinDigitalStateChangeEvent?) {
                        if (state?.state == PinState.HIGH) {
                            val time2 = System.currentTimeMillis()
                            val distance = (time2 - time1) * 343.0 / 2.0
                            listener(Math.round(distance).toDouble())
                        }
                    }
                })
                delay(200)
                echo.removeAllListeners()
            }
        }
    }
}