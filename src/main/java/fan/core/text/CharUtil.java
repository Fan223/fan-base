package fan.core.text;

/**
 * Char utility class
 *
 * @author Fan
 * @since 2024/2/20 16:40
 */
public class CharUtil {

    private CharUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Check whether the char is blank
     *
     * @param ch int
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 16:41
     */
    public static boolean isBlank(int ch) {
        return Character.isWhitespace(ch)
                || Character.isSpaceChar(ch)
                || ch == '⠀'
                || ch == 'ㅤ'
                || ch == '\ufeff'
                || ch == '\u202a'
                || ch == '\u0000'
                || ch == '\u180e';
    }

    /**
     * Check whether the char is not blank
     *
     * @param ch int
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 16:43
     */
    public static boolean isNotBlank(int ch) {
        return !isBlank(ch);
    }
}