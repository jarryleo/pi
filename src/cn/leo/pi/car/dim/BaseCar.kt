package cn.leo.pi.car.dim

interface BaseCar {
    /**
     * 向前
     */
    fun forward(speed:Int = 0)
    /**
     * 向后
     */
    fun backward(speed:Int = 0)
    /**
     * 左转弯
     */
    fun turnLeft(speed:Int = 0)
    /**
     * 右转弯
     */
    fun turnRight(speed:Int = 0)
    /**
     * 待机
     */
    fun idle()

}