package fan.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic logging class
 *
 * @author Fan
 * @since 2024/2/21 17:03
 */
@SuppressWarnings("unused")
public class LogUtil {

    private LogUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    private static final String LOGGER_VARIABLE = "{} - Situated at: {}";

    /**
     * Print info log
     *
     * @param infoMsg Info message
     * @author Fan
     * @since 2024/2/21 17:06
     */
    public static void info(String infoMsg) {
        logger.info(infoMsg);
    }

    /**
     * Print info log
     *
     * @param clazz   Class
     * @param infoMsg Info message
     * @author Fan
     * @since 2024/2/21 17:07
     */
    public static void info(Class<?> clazz, String infoMsg) {
        logger.info(LOGGER_VARIABLE, infoMsg, clazz.getName());
    }

    /**
     * Print warn log
     *
     * @param warnMsg Warn message
     * @author Fan
     * @since 2024/2/21 17:08
     */
    public static void warn(String warnMsg) {
        logger.warn(warnMsg);
    }

    /**
     * Print warn log
     *
     * @param clazz   Class
     * @param warnMsg Warn message
     * @author Fan
     * @since 2024/2/21 17:09
     */
    public static void warn(Class<?> clazz, String warnMsg) {
        logger.warn(LOGGER_VARIABLE, warnMsg, clazz.getName());
    }

    /**
     * Print error log
     *
     * @param errorMsg Error message
     * @author Fan
     * @since 2024/2/21 17:09
     */
    public static void error(String errorMsg) {
        logger.error(errorMsg);
    }

    /**
     * Print error log
     *
     * @param clazz    Class
     * @param errorMsg Error message
     * @author Fan
     * @since 2024/2/21 17:10
     */
    public static void error(Class<?> clazz, String errorMsg) {
        logger.error(LOGGER_VARIABLE, errorMsg, clazz.getName());
    }
}