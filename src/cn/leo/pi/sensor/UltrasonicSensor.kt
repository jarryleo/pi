package cn.leo.pi.sensor

import cn.leo.pi.gpio.GPIO
import cn.leo.pi.gpio.PinUtil
import cn.leo.pi.utils.CoroutineUtil
import cn.leo.pi.utils.logD
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import com.pi4j.io.gpio.event.GpioPinListenerDigital
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * 超声波测距
 * 数值单位 毫米 mm
 */

class UltrasonicSensor(trigPin: Int, echoPin: Int) : BaseSensor {
    private val trig = GPIO.instance.provisionDigitalOutputPin(PinUtil.getPin(trigPin))
    private val echo = GPIO.instance.provisionDigitalInputPin(
                    PinUtil.getPin(echoPin),
                    PinPullResistance.PULL_DOWN)
    var delayTime:Long by Delegates.vetoable(1000L){
        _, _, newValue ->
        newValue in 10L..5000L
    }

    var distance :Double = Double.MAX_VALUE

    fun listen(listener: (Double) -> Unit) {
        logD("初始化超声波测距传感器")
        echo.setShutdownOptions(true)
        CoroutineUtil.io {
            var time1 = 0L
            var time2 = 0L
            echo.addListener(GpioPinListenerDigital { state ->
                if (state?.state == PinState.HIGH) {
                    time2 = System.currentTimeMillis()
                    distance = (time2 - time1) * 343.0 / 2.0
                    listener(Math.round(distance).toDouble())
                    logD("time1 = $time1 ,time2= $time2")
                }
            })
            while (isActive) {
                trig.pulse(1)
                //理论上设置间隔是10微妙，但是java只能设置毫秒级别
                time1 = System.currentTimeMillis()
                logD("发射一次超声波")
                delay(delayTime)
            }
        }
    }
}