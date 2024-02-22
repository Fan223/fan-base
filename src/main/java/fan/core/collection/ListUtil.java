package fan.core.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * {@link List} utility class
 *
 * @author Fan
 * @since 2024/2/20 11:19
 */
@SuppressWarnings("unused")
public class ListUtil {

    private ListUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Create a new ArrayList
     *
     * @return {@link List<T>}
     * @author Fan
     * @since 2024/2/20 11:24
     */
    public static <T> List<T> list() {
        return list(false);
    }

    /**
     * Create a new LinkedList/ArrayList
     *
     * @param isLinked IsLinked
     * @return {@link List<T>}
     * @author Fan
     * @since 2024/2/20 11:25
     */
    public static <T> List<T> list(boolean isLinked) {
        return isLinked ? new LinkedList<>() : new ArrayList<>();
    }

    /**
     * Create a new ArrayList by values
     *
     * @param values Values
     * @return {@link List<T>}
     * @author Fan
     * @since 2024/2/20 11:52
     */
    @SafeVarargs
    public static <T> List<T> list(T... values) {
        return list(false, values);
    }

    /**
     * Create a new LinkedList/ArrayList by values
     *
     * @param isLinked IsLinked
     * @param values   Values
     * @return {@link List<T>}
     * @author Fan
     * @since 2024/2/20 11:55
     */
    @SafeVarargs
    public static <T> List<T> list(boolean isLinked, T... values) {
        if (ArrayUtil.isEmpty(values)) {
            return list(isLinked);
        }

        final List<T> list = isLinked ? new LinkedList<>() : new ArrayList<>(values.length);
        Collections.addAll(list, values);
        return list;
    }

    /**
     * Cast List data of Object type to List type
     *
     * @param clazz  Data type
     * @param object List data of Object type
     * @return {@link List<T>}
     * @author Fan
     * @since 2024/2/20 13:39
     */
    public static <T> List<T> castToList(Class<T> clazz, Object object) {
        final List<T> list = list(false);

        if (object instanceof List<?>) {
            for (Object obj : (List<?>) object) {
                list.add(clazz.cast(obj));
            }
        }

        return list;
    }
}