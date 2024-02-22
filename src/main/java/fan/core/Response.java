package fan.core;

/**
 * Unified response result return class, applying the Builder pattern.
 *
 * @author Fan
 * @since 2024/2/21 14:59
 */
@SuppressWarnings("unused")
public class Response<T> {

    private int code;

    private String message;

    private T data;

    /**
     * Return a Builder object, passing in data to specify the generics.
     *
     * @param data Data
     * @return {@link Builder<T>}
     * @author Fan
     * @since 2024/2/21 15:06
     */
    private static <T> Builder<T> builder(T data) {
        return new Builder<>(data);
    }

    private static class Builder<T> {

        private final Response<T> response = new Response<>();

        public Builder(T data) {
            response.data = data;
        }

        private Builder<T> code(int code) {
            response.code = code;
            return this;
        }

        private Builder<T> message(String message) {
            response.message = message;
            return this;
        }

        private Response<T> build() {
            return response;
        }
    }

    /**
     * Successful response with default code and message, along with specified data.
     *
     * @param data Response data
     * @return {@link Response<T>}
     * @author Fan
     * @since 2024/2/21 15:14
     */
    public static <T> Response<T> success(T data) {
        return Response.builder(data).code(200).message("Successful").build();
    }

    /**
     * Successful response with default code, specified message, and data.
     *
     * @param message Response message
     * @param data    Response data
     * @return {@link Response<T>}
     * @author Fan
     * @since 2024/2/21 15:11
     */
    public static <T> Response<T> success(String message, T data) {
        return Response.builder(data).code(200).message(message).build();
    }

    /**
     * Successful response with specified code, message and data.
     *
     * @param code    Response code
     * @param message Response message
     * @param data    Response data
     * @return {@link Response<T>}
     * @author Fan
     * @since 2024/2/21 15:07
     */
    public static <T> Response<T> success(int code, String message, T data) {
        return Response.builder(data).code(code).message(message).build();
    }

    /**
     * Failed response with default code and message, along with specified data.
     *
     * @param data Response data
     * @return {@link Response<T>}
     * @author Fan
     * @since 2024/2/21 15:34
     */
    public static <T> Response<T> fail(T data) {
        return Response.builder(data).code(500).message("Failed").build();
    }

    /**
     * Failed response with default code, specified message, and data.
     *
     * @param message Response message
     * @param data    Response data
     * @return {@link Response<T>}
     * @author Fan
     * @since 2024/2/21 15:33
     */
    public static <T> Response<T> fail(String message, T data) {
        return Response.builder(data).code(500).message(message).build();
    }

    /**
     * Failed response with specified code, message and data.
     *
     * @param code    Response code
     * @param message Response message
     * @param data    Response data
     * @return {@link Response<T>}
     * @author Fan
     * @since 2024/2/21 15:29
     */
    public static <T> Response<T> fail(int code, String message, T data) {
        return Response.builder(data).code(code).message(message).build();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}