package cn.leo.pi.car.dim

interface SimpleCar : BaseCar {
    /**
     * 左平移
     */
    fun left(speed:Int = 0)
    /**
     * 右平移
     */
    fun right(speed:Int = 0)
    /**
     * 刹车
     */
    fun brake()
}