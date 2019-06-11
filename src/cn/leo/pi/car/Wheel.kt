package cn.leo.pi.car

/**
 * 单车轮控制接口
 * 无法调速的话speed可以忽略
 */
interface Wheel {
    /**
     * 向前
     */
    fun forward(speed:Int = -1)
    /**
     * 向后
     */
    fun backward(speed:Int = -1)
    /**
     * 刹车
     */
    fun brake()
    /**
     * 待机
     */
    fun idle()
}