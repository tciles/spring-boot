package fr.eni.demoSpringFramework.Response;

import org.springframework.http.HttpStatus;

public class Payload<T> {

    private final T data;

    private String message = "";

    private HttpStatus httpStatus = HttpStatus.OK;

    public Payload(T data) {
        this.data = data;
    }

    public Payload(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public Payload(T data, String message, HttpStatus httpStatus) {
        this.data = data;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static <T> Payload<T> create(T data) {
        return new Payload<>(data);
    }

    public static <T> Payload<T> create(T data, String status) {
        return new Payload<>(data, status);
    }

    public static <T> Payload<T> create(T data, String status, HttpStatus httpStatus) {
        return new Payload<>(data, status, httpStatus);
    }
}
