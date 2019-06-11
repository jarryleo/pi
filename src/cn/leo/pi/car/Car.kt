package cn.leo.pi.car

interface Car {
    /**
     * 向前
     */
    fun forward(speed:Int = 0)
    /**
     * 向后
     */
    fun backward(speed:Int = 0)
    /**
     * 左平移
     */
    fun left(speed:Int = 0)
    /**
     * 右平移
     */
    fun right(speed:Int = 0)
    /**
     * 左转弯
     */
    fun turnLeft(speed:Int = 0)
    /**
     * 右转弯
     */
    fun turnRight(speed:Int = 0)
    /**
     * 刹车
     */
    fun brake()
    /**
     * 待机
     */
    fun idle()
}