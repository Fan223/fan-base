package fan.core.text;

/**
 * {@link String} utility class
 *
 * @author Fan
 * @since 2024/2/20 15:36
 */
@SuppressWarnings("unused")
public class StringUtil {

    private StringUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Check whether the {@link CharSequence} is empty
     *
     * @param cs {@link CharSequence}
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 16:20
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.isEmpty();
    }

    /**
     * Check whether the {@link CharSequence} is not empty
     *
     * @param cs {@link CharSequence}
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 16:25
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * Check whether the {@link CharSequence} is blank
     *
     * @param cs {@link CharSequence}
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 16:39
     */
    public static boolean isBlank(CharSequence cs) {
        final int length;
        if (null == cs || 0 == (length = cs.length())) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (CharUtil.isNotBlank(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether the {@link CharSequence} is not blank
     *
     * @param cs {@link CharSequence}
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 16:55
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }
}