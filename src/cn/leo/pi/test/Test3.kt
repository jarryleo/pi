package cn.leo.pi.test

import cn.leo.pi.utils.PathUtil
import com.pi4j.component.lcd.impl.I2CLcdDisplay

fun main() {
    println(PathUtil.getPath())
    I2CLcdDisplay(2,16,
            1,0x27,
            0,
            90,16,
            1,
            1,1,1,1).write(0,0,"hello world !")
}
