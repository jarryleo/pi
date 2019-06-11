package cn.leo.pi.utils

import java.io.FileInputStream
import java.util.*

object PropertiesUtil {
    var p = Properties().apply { load(FileInputStream("Setting.properties")) }
    val port = p.getProperty("port").toInt()
}