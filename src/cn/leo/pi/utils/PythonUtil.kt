package cn.leo.pi.utils

object PythonUtil {
    /**
     * java 启动 python 脚本并传参
     */
    fun exePy(path:String,vararg arg:String){
        val cmd = mutableListOf("python",path)
        cmd.addAll(arg.toList())
        Runtime.getRuntime().exec(cmd.toTypedArray())
    }

    /**
     * 关闭树莓派
     */
    fun powerOff(){
        Runtime.getRuntime().exec("sudo poweroff")
    }
}