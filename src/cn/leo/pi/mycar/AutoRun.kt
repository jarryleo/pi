package cn.leo.pi.mycar

import cn.leo.pi.car.Car4WheelImpl
import cn.leo.pi.utils.CoroutineUtil
import cn.leo.pi.utils.logD
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

object AutoRun {
    @Volatile
    var lastDistance = 0f
    var turnTimes = 0 //转向次数
    private var job: Job? = null
    fun stop(){
        job?.cancel()
    }
    fun run(car: Car4WheelImpl) {
        stop()
        job = CoroutineUtil.io {
            while (isActive) {
                logD("当前距离：$lastDistance cm")
                if (lastDistance < 50) {
                    logD("前方有障碍物，转向")
                    car.brake()
                    delay(500)
                    //左转
                    car.left(50)
                    turnTimes ++
                    delay(500)
                    car.idle()
                    //10次转向没找到出路停车
                    if(turnTimes > 10){
                        cancel()
                    }
                } else {
                    turnTimes = 0
                    car.forward(50)
                }
            }
            car.idle()
        }
    }
}