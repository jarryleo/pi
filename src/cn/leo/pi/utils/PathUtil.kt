package cn.leo.pi.utils

import java.net.URLDecoder

object PathUtil {
    fun getPath():String{
        var path = this.javaClass.protectionDomain.codeSource.location.path
        path = URLDecoder.decode(path, "UTF-8")
        val firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1
        val lastIndex = path.lastIndexOf("/") + 1
        return path.substring(firstIndex, lastIndex)
    }
}