package fan.core.collection;

import java.util.Collection;

/**
 * {@link Collection} utility class
 *
 * @author Fan
 * @since 2024/2/20 14:04
 */
@SuppressWarnings("unused")
public class CollectionUtil {

    private CollectionUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Check whether the {@link Collection} is empty
     *
     * @param collection {@link Collection}
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 14:07
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * Check whether the {@link Collection} is not empty
     *
     * @param collection {@link Collection}
     * @return {@link boolean}
     * @author Fan
     * @since 2024/2/20 14:07
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}