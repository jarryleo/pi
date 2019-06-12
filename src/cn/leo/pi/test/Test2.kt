package cn.leo.pi.test

import cn.leo.pi.msg.BaseMsg
import cn.leo.pi.msg.MsgType
import cn.leo.pi.mycar.Command
import cn.leo.pi.utils.JsonUtil

fun main(args: Array<String>) {
    val a = BaseMsg<Command>()
    a.type = MsgType.TYPE_CAR
    a.data= Command(0,1)
    val json = JsonUtil.toJson(a)
    println(json)

    val b = object :BaseMsg<Command>(){}.fromJson(json)
    println(b.data?.speed)
}