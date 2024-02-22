package fan.core;

import fan.core.text.StringUtil;

import java.lang.management.ManagementFactory;

/**
 * Process ID singleton class
 *
 * @author Fan
 * @since 2024/2/21 10:14
 */
public class PID {

    private PID() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final int PROCESS_ID = getProcessId();

    /**
     * To obtain the current process ID, first retrieve the process name, read the ID value before the '@' symbol.
     * If it doesn't exist, then read the hash value of the process name.
     *
     * @return {@link int}
     * @author Fan
     * @since 2024/2/21 10:16
     */
    private static int getProcessId() {
        final String processName = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtil.isBlank(processName)) {
            return 0;
        }

        final int index = processName.indexOf('@');
        return index > 0 ? Integer.parseInt(processName.substring(0, index)) : processName.hashCode();
    }
}