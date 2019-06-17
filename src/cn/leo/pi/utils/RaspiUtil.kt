package cn.leo.pi.utils

object RaspiUtil {
    /**
     * java 启动 python 脚本并传参
     */
    fun exePy(path: String, vararg arg: String) {
        val cmd = mutableListOf("python", path)
        cmd.addAll(arg.toList())
        Runtime.getRuntime().exec(cmd.toTypedArray())
    }

    /**
     * 执行linux指令
     */
    fun executeCmd(cmd: String){
        Runtime.getRuntime().exec(cmd)
    }
}