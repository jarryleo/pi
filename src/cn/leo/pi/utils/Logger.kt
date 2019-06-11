package cn.leo.pi.utils

import java.text.SimpleDateFormat
import java.util.*

fun Any.logD(msg:String){
    println("$time/D(${this.javaClass.simpleName}):$msg")
}
fun Any.logI(msg:String){
    println("$time/I(${this.javaClass.simpleName}):$msg")
}
val time =System.currentTimeMillis().toDate("MM-dd HH:mm:ss.SSS")

//转换成指定日志格式
fun Long.toDate(format: String): String = SimpleDateFormat(format, Locale.getDefault()).format(Date(this))
