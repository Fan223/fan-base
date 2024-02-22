package fan.core.builder;

/**
 * Builder pattern interface
 *
 * @author Fan
 * @since 2024/2/20 14:09
 */
@SuppressWarnings("unused")
public interface Builder<T> {

    /**
     * Build method
     *
     * @return {@link T}
     * @author Fan
     * @since 2024/2/20 14:12
     */
    T build();
}