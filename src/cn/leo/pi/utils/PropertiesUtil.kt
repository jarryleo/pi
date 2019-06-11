package cn.leo.pi.utils

import java.io.FileInputStream
import java.util.*

object PropertiesUtil {
    var p = Properties().apply { load(FileInputStream("Setting.properties")) }
    val port = p.getProperty("port").toInt()
    val pinLF1 = p.getProperty("pinLF1").toInt()
    val pinLF2 = p.getProperty("pinLF2").toInt()
    val pinRF1 = p.getProperty("pinRF1").toInt()
    val pinRF2 = p.getProperty("pinRF2").toInt()
    val pinLB1 = p.getProperty("pinLB1").toInt()
    val pinLB2 = p.getProperty("pinLB2").toInt()
    val pinRB1 = p.getProperty("pinRB1").toInt()
    val pinRB2 = p.getProperty("pinRB2").toInt()
}