package cn.leo.pi.utils

import java.text.SimpleDateFormat
import java.util.*

fun Any.logD(msg: String, tag: String = this.javaClass.simpleName) {
    println("$time/D($tag):$msg")
}

fun Any.logI(msg: String, tag: String = this.javaClass.simpleName) {
    println("$time/I($tag):$msg")
}

val time = System.currentTimeMillis().toDate("MM-dd HH:mm:ss.SSS")

//转换成指定日志格式
fun Long.toDate(format: String): String = SimpleDateFormat(format, Locale.getDefault()).format(Date(this))
