package cn.leo.pi.udp;

/**
 * @author : Jarry Leo
 * @date : 2019/1/25 13:42
 */
public interface OnDataArrivedListener {
    void onDataArrived(byte[] data, String host);
}
