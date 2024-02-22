package fan.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Network utility class
 *
 * @author Fan
 * @since 2024/2/20 17:25
 */
@SuppressWarnings("unused")
public class NetUtil {

    private NetUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Retrieve local host
     *
     * @return {@link InetAddress}
     * @author Fan
     * @since 2024/2/20 17:26
     */
    public static InetAddress getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    /**
     * Retrieve local host address
     *
     * @return {@link String}
     * @author Fan
     * @since 2024/2/20 17:28
     */
    public static String getLocalHostAddress() throws UnknownHostException {
        return getLocalHost().getHostAddress();
    }

    /**
     * Retrieve the local host hardware (MAC) address array using local host
     *
     * @return {@link byte[]}
     * @author Fan
     * @since 2024/2/20 17:29
     */
    public static byte[] getLocalHardwareAddress() throws UnknownHostException, SocketException {
        return getLocalHardwareAddress(getLocalHost());
    }

    /**
     * Retrieve the local host hardware (MAC) address array using {@link InetAddress}
     *
     * @param inetAddress {@link InetAddress}
     * @return {@link byte[]}
     * @author Fan
     * @since 2024/2/20 17:29
     */
    public static byte[] getLocalHardwareAddress(InetAddress inetAddress) throws SocketException {
        // 通过 InetAddress 获取网卡, 然后获取硬件地址
        return NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
    }

    /**
     * Retrieve the local host hardware (MAC) address {@link String}
     *
     * @return {@link String}
     * @author Fan
     * @since 2024/2/21 9:59
     */
    public static String getLocalHardwareAddressStr() throws SocketException, UnknownHostException {
        byte[] mac = getLocalHardwareAddress();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < mac.length; i++) {
            if (0 != i) {
                builder.append("-");
            }

            /*
             * 字节转换为整数. 0xFF 表示 16 进制, 转换为十进制即为 255, 二进制表示为: 0000 0000 0000 0000 0000 0000 1111 1111
             * 当 byte 要转化为 int 的时候, 负数高的 24 位必然会补1, 这样, 其二进制补码已经不一致了
             * & 0xFF 可以将高的 24 位置为 0, 低 8 位保持原样, 保证二进制数据的一致性
             */
            String str = Integer.toHexString(mac[i] & 0xFF);
            if (1 == str.length()) {
                builder.append("0").append(str);
            } else {
                builder.append(str);
            }
        }

        return builder.toString();
    }
}