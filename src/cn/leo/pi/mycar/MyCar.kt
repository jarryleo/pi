package cn.leo.pi.mycar

import cn.leo.pi.car.Car4WheelImpl
import cn.leo.pi.car.WheelPwmImp
import cn.leo.pi.car.dim.Wheel
import cn.leo.pi.gpio.PinUtil
import cn.leo.pi.utils.PropertiesUtil

object MyCar {
    val wheelLF: Wheel = WheelPwmImp(
            PinUtil.getPin(PropertiesUtil.pinLF1),
            PinUtil.getPin(PropertiesUtil.pinLF2))
    val wheelRF: Wheel = WheelPwmImp(
            PinUtil.getPin(PropertiesUtil.pinRF1),
            PinUtil.getPin(PropertiesUtil.pinRF2))
    val wheelLB: Wheel = WheelPwmImp(
            PinUtil.getPin(PropertiesUtil.pinLB1),
            PinUtil.getPin(PropertiesUtil.pinLB2))
    val wheelRB: Wheel = WheelPwmImp(
            PinUtil.getPin(PropertiesUtil.pinRB1),
            PinUtil.getPin(PropertiesUtil.pinRB2))

    val car = Car4WheelImpl(wheelLF, wheelRF, wheelLB, wheelRB)

    //小车执行指令
    fun executeCommand(command: Command){
        when(command.command){
            CommandType.IDLE -> car.idle()
            CommandType.FORWARD -> car.forward(command.speed)
            CommandType.BACKWARD -> car.backward(command.speed)
            CommandType.LEFT -> car.left(command.speed)
            CommandType.RIGHT -> car.right(command.speed)
            CommandType.TURN_LEFT -> car.turnLeft(command.speed)
            CommandType.TURN_RIGHT -> car.turnRight(command.speed)
            CommandType.BRAKE -> car.brake()
        }
    }
}