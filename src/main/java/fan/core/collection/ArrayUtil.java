package fan.core.collection;

/**
 * Array utility class
 *
 * @author Fan
 * @since 2024/2/20 11:30
 */
@SuppressWarnings("unused")
public class ArrayUtil {

    private ArrayUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Check whether the array is empty
     *
     * @param array Array
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 11:37
     */
    public static <T> boolean isEmpty(T[] array) {
        return null == array || 0 == array.length;
    }

    /**
     * Check whether the array is not empty
     *
     * @param array Array
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 11:45
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * Create an array by values
     *
     * @param values Values
     * @return {@link T[]}
     * @author Fan
     * @since 2024/2/20 11:45
     */
    @SafeVarargs
    public static <T> T[] array(T... values) {
        return values;
    }
}