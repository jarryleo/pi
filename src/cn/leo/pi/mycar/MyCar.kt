package cn.leo.pi.mycar

import cn.leo.pi.car.Car4WheelImpl
import cn.leo.pi.car.WheelPwmImp
import cn.leo.pi.gpio.PinUtil
import cn.leo.pi.sensor.SettingGear
import cn.leo.pi.sensor.UltrasonicSensorPy
import cn.leo.pi.utils.CoroutineUtil
import cn.leo.pi.utils.PropertiesUtil
import cn.leo.pi.utils.logD
import kotlinx.coroutines.delay

object MyCar {
    val wheelLF = WheelPwmImp(
            PinUtil.getPin(PropertiesUtil.pinLF1),
            PinUtil.getPin(PropertiesUtil.pinLF2))
    val wheelRF = WheelPwmImp(
            PinUtil.getPin(PropertiesUtil.pinRF1),
            PinUtil.getPin(PropertiesUtil.pinRF2))
    val wheelLB = WheelPwmImp(
            PinUtil.getPin(PropertiesUtil.pinLB1),
            PinUtil.getPin(PropertiesUtil.pinLB2))
    val wheelRB = WheelPwmImp(
            PinUtil.getPin(PropertiesUtil.pinRB1),
            PinUtil.getPin(PropertiesUtil.pinRB2))

    val car = Car4WheelImpl(wheelLF, wheelRF, wheelLB, wheelRB)

    //摄像头舵机
    val gear = SettingGear(PinUtil.getPin(PropertiesUtil.pinGear))

    private var lastDistance = 0f
    //超声波测距
    val ultrasonicSensorPy = UltrasonicSensorPy(
            PropertiesUtil.pinTrig,
            PropertiesUtil.pinEcho,
            25565,
            25566).apply {
        listen {
            logD("当前距离：$it cm")
            if (it < 80) {
                if (it < lastDistance) {
                    if (it < 50) {
                        car.backward((100 - it).toInt())
                        CoroutineUtil.io {
                            delay(200)
                            car.idle()
                        }
                    } else {
                        car.brake()
                    }
                    logD("前方有障碍物，刹车系统启动")
                }
            }
            lastDistance = it
        }
    }

    fun setUltrasonic(start: Boolean) {
        if (start) {
            ultrasonicSensorPy.startPython()
        } else {
            ultrasonicSensorPy.stop()
        }
    }

    fun settingGear(direction:Int){
        gear.setDirection(direction)
    }


    //小车执行指令
    fun executeCommand(command: Command) {
        //logD(command.toString())
        when (command.command) {
            CommandType.IDLE -> car.idle()
            CommandType.FORWARD -> {
                if (ultrasonicSensorPy.distance > 30) {
                    car.forward(command.speed)
                }
            }
            CommandType.BACKWARD -> car.backward(command.speed)
            CommandType.LEFT -> car.left(command.speed)
            CommandType.RIGHT -> car.right(command.speed)
            CommandType.TURN_LEFT -> car.turnLeft(command.speed)
            CommandType.TURN_RIGHT -> car.turnRight(command.speed)
            CommandType.BRAKE -> car.brake()
        }
    }

    //小车精细控制
    fun executePWM(command: PwmCommand) {
        if (command.pwmArray.size != 8) {
            logD("指令长度不正确：${command.pwmArray.size}")
            return
        }
        command.pwmArray.forEachIndexed { index, pwm ->
            when (index) {
                0 -> wheelLF.pin1Pwm.pwm = pwm
                1 -> wheelLF.pin2Pwm.pwm = pwm
                2 -> wheelRF.pin1Pwm.pwm = pwm
                3 -> wheelRF.pin2Pwm.pwm = pwm
                4 -> wheelLB.pin1Pwm.pwm = pwm
                5 -> wheelLB.pin2Pwm.pwm = pwm
                6 -> wheelRB.pin1Pwm.pwm = pwm
                7 -> wheelRB.pin2Pwm.pwm = pwm
            }
        }
    }

}