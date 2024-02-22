package fan.core.map;

import fan.core.builder.Builder;

import java.util.Map;

/**
 * {@link Map} builder class
 *
 * @author Fan
 * @since 2024/2/20 14:25
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class MapBuilder<K, V> implements Builder<Map<K, V>> {

    private final Map<K, V> map;

    public MapBuilder() {
        this(false);
    }

    public MapBuilder(boolean isLinked) {
        this(MapUtil.hashMap(isLinked));
    }

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    /**
     * Add a key-value pair to the {@link Map}
     *
     * @param key   Key
     * @param value Value
     * @return {@link MapBuilder}
     * @author Fan
     * @since 2024/2/20 14:41
     */
    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    /**
     * Add a key-value pair to the {@link Map} when a condition is met
     *
     * @param condition Condition
     * @param key       Key
     * @param value     Value
     * @return {@link MapBuilder}
     * @author Fan
     * @since 2024/2/20 14:47
     */
    public MapBuilder<K, V> put(boolean condition, K key, V value) {
        if (condition) {
            put(key, value);
        }
        return this;
    }

    /**
     * Add a {@link Map} to the {@link Map}
     *
     * @param map {@link Map}
     * @return {@link MapBuilder}
     * @author Fan
     * @since 2024/2/20 14:50
     */
    public MapBuilder<K, V> putAll(Map<K, V> map) {
        this.map.putAll(map);
        return this;
    }

    @Override
    public Map<K, V> build() {
        return map;
    }
}