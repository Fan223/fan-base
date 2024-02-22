package fan.core;

import fan.core.util.IdUtil;

/**
 * Snowflake ID class
 *
 * @author Fan
 * @since 2024/2/20 16:58
 */
public class Snowflake {

    /**
     * Data center ID occupies 5 bits
     */
    private static final long DATA_CENTER_ID_BITS = 5;

    /**
     * Data center ID occupies 5 bits, with a maximum value of 31.
     * <ul>
     *     <li> -1 << 5 = -32, Original code is: 1111111111111111111111111111111111111111111111111111111111100000 </li>
     *     <li> After negation, it is: 0000000000000000000000000000000000000000000000000000000000011111 </li>
     * </ul>
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    /**
     * Machine ID occupies 5 bits
     */
    private static final long WORKER_ID_BITS = 5;

    /**
     * Machine ID occupies 5 bits, with a maximum value of 31.
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * Sequence number occupies 12 bits
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * Sequence mask (the lowest 12 bits are 1, the higher bits are all 0),
     * mainly used for bitwise AND with the incremented sequence number.
     * If the value is 0, it means the incremented sequence number has exceeded 4095. <br />
     * 序列掩码(最低 12 位为 1, 高位都为 0), 主要用于与自增后的序列号进行位与, 如果值为 0, 则代表自增后的序列号超过了 4095
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * The latest sequence number within the same millisecond, with a maximum value of (2^12 - 1 = 4095).
     */
    private long sequence = 0L;

    /**
     * Number of bits the WORKER_ID needs to be left-shifted by 12
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * Number of bits the DATA_CENTER_ID needs to be left-shifted by 12+5
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * Number of bits the TIMESTAMP needs to be left-shifted by 12+5+5
     */
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /**
     * Initial time
     */
    private static final long INITIAL_EPOCH = 1678068570258L;

    /**
     * Record the last used millisecond timestamp, primarily used for determining if it's the same millisecond,
     * and for detecting server clock rollback. <br />
     * 记录最后使用的毫秒时间戳, 主要用于判断是否同一毫秒, 以及用于服务器时钟回拨判断
     */
    private long lastTimestamp = -1L;

    private final long dataCenterId;

    private final long workerId;

    public Snowflake() {
        this(IdUtil.getWorkerId(IdUtil.getDataCenterId(MAX_DATA_CENTER_ID), MAX_WORKER_ID));
    }

    public Snowflake(long workerId) {
        this(IdUtil.getDataCenterId(MAX_DATA_CENTER_ID), workerId);
    }

    public Snowflake(long dataCenterId, long workerId) {
        this.workerId = Assert.checkBetween(workerId, 0, MAX_WORKER_ID);
        this.dataCenterId = Assert.checkBetween(dataCenterId, 0, MAX_DATA_CENTER_ID);
    }

    /**
     * Generate the nextId using the Snowflake algorithm, synchronized is used here for synchronization.
     *
     * @return {@link long}
     * @author Fan
     * @since 2024/2/21 11:48
     */
    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException(String.format("可能出现服务器时钟回拨问题, 请检查服务器时间. " +
                    "当前服务器时间戳: %d, 上一次使用时间戳: %d", currentTimestamp, lastTimestamp));
        }

        if (currentTimestamp == lastTimestamp) {
            // 序列号每次加 1, 然后和序列掩码进行位于
            final long seq = (sequence + 1) & SEQUENCE_MASK;
            // 为 0 则表示序列号大于 4095, 当前毫秒使用的序列号已达到最大个数, 使用新的时间戳
            if (seq == 0) {
                currentTimestamp = tilNextMillis(lastTimestamp);
            }
            this.sequence = seq;
        } else {
            // 不在同一毫秒内, 则序列号重新从 0 开始
            sequence = 0L;
        }

        // 记录最后一次使用的毫秒时间戳
        lastTimestamp = currentTimestamp;

        // 核心算法, 将不同部分的数值移动到指定的位置, 然后进行位或操作
        return ((currentTimestamp - INITIAL_EPOCH) << TIMESTAMP_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * Waiting in a loop for the next time
     *
     * @param lastTimestamp Last recorded time
     * @return {@link long}
     * @author Fan
     * @since 2024/2/21 11:49
     */
    private long tilNextMillis(long lastTimestamp) {
        long currentTimestamp = System.currentTimeMillis();

        // Loop until the operating system timestamp changes
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = System.currentTimeMillis();
        }

        if (currentTimestamp < lastTimestamp) {
            // If the current timestamp is smaller than the last used timestamp,
            // it indicates that the operating system time has regressed.
            throw new IllegalStateException(String.format("可能出现服务器时钟回拨问题, 请检查服务器时间. " +
                    "当前服务器时间戳: %d, 上一次使用时间戳: %d", currentTimestamp, lastTimestamp));
        }

        return currentTimestamp;
    }
}