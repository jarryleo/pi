package cn.leo.pi.car

import cn.leo.pi.car.dim.SimpleCar
import cn.leo.pi.car.dim.Wheel

/**
 * 四轮驱动控制
 */
class Car4WheelImpl(private val wheelLF: Wheel,//左前轮
                    private val wheelRF: Wheel,//右前轮
                    private val wheelLB: Wheel,//左后轮
                    private val wheelRB: Wheel //右后轮
                    ): SimpleCar {
    override fun forward(speed: Int) {
        wheelLF.forward(speed)
        wheelRF.forward(speed)
        wheelLB.forward(speed)
        wheelRB.forward(speed)
    }

    override fun backward(speed: Int) {
        wheelLF.backward(speed)
        wheelRF.backward(speed)
        wheelLB.backward(speed)
        wheelRB.backward(speed)
    }

    override fun left(speed: Int) {
        wheelLF.forward(speed)
        wheelRF.backward(speed)
        wheelLB.backward(speed)
        wheelRB.forward(speed)
    }

    override fun right(speed: Int) {
        wheelLF.backward(speed)
        wheelRF.forward(speed)
        wheelLB.forward(speed)
        wheelRB.backward(speed)
    }

    override fun turnLeft(speed: Int) {
        wheelLF.backward(speed)
        wheelRF.forward(speed)
        wheelLB.backward(speed)
        wheelRB.forward(speed)
    }

    override fun turnRight(speed: Int) {
        wheelLF.forward(speed)
        wheelRF.backward(speed)
        wheelLB.forward(speed)
        wheelRB.backward(speed)
    }

    override fun brake() {
        wheelLF.brake()
        wheelRF.brake()
        wheelLB.brake()
        wheelRB.brake()
    }

    override fun idle() {
        wheelLF.idle()
        wheelRF.idle()
        wheelLB.idle()
        wheelRB.idle()
    }
}