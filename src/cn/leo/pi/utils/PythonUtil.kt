package cn.leo.pi.utils

object PythonUtil {
    fun exePy(path:String){
        Runtime.getRuntime().exec("python $path")
    }
}