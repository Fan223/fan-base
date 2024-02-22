package fan.core.util;

import fan.core.PID;
import fan.core.Snowflake;
import fan.net.NetUtil;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * ID utility class
 *
 * @author Fan
 * @since 2024/2/20 17:24
 */
@SuppressWarnings("unused")
public class IdUtil {

    private IdUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final Snowflake SNOWFLAKE = new Snowflake();

    /**
     * Retrieve data center ID. Data center ID depends on the local host hardware (MAC) address array.
     *
     * @param maxDataCenterId MaxDataCenterId
     * @return {@link long}
     * @author Fan
     * @since 2024/2/21 10:08
     */
    public static long getDataCenterId(long maxDataCenterId) {
        if (Long.MAX_VALUE == maxDataCenterId) {
            maxDataCenterId -= 1;
        }

        byte[] mac = null;
        try {
            mac = NetUtil.getLocalHardwareAddress();
        } catch (UnknownHostException | SocketException ignore) {
            // ignore
        }

        long id = 1L;
        if (maxDataCenterId > 0 && null != mac) {
            id = ((0xFF & (long) mac[mac.length - 2]) | (0x0000FF00 & (((long) mac[mac.length - 1]) << 8))) >> 6;
            id = id % (maxDataCenterId + 1);
        }

        return id;
    }

    /**
     * Retrieve machine ID, generate with data center ID and process ID.
     *
     * @param dataCenterId DataCenterId
     * @param maxWorkerId  MaxWorkerId
     * @return {@link long}
     * @author Fan
     * @since 2024/2/21 10:21
     */
    public static long getWorkerId(long dataCenterId, long maxWorkerId) {
        String workerId = String.valueOf(dataCenterId) + getProcessId();
        // MAC + PID 的 hashcode 获取 16 个低位
        return (workerId.hashCode() & 0xFFFF) % (maxWorkerId + 1);
    }

    /**
     * Retrieve process ID
     *
     * @return {@link int}
     * @author Fan
     * @since 2024/2/21 10:14
     */
    public static int getProcessId() {
        return PID.PROCESS_ID;
    }

    /**
     * Retrieve Snowflake ID
     *
     * @return {@link long}
     * @author Fan
     * @since 2024/2/21 14:24
     */
    public static long getSnowflakeId() {
        return SNOWFLAKE.nextId();
    }

    /**
     * Retrieve Snowflake ID {@link String}
     *
     * @return {@link String}
     * @author Fan
     * @since 2024/2/21 14:26
     */
    public static String getSnowflakeIdStr() {
        return String.valueOf(getSnowflakeId());
    }
}