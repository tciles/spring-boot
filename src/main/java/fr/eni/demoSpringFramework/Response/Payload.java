package fr.eni.demoSpringFramework.Response;

import org.springframework.http.HttpStatus;

public class Payload<T> {

    private final T data;

    private String status = "OK";

    private HttpStatus httpStatus = HttpStatus.OK;

    public Payload(T data) {
        this.data = data;
    }

    public Payload(T data, String status) {
        this.data = data;
        this.status = status;
    }

    public Payload(T data, String status, HttpStatus httpStatus) {
        this.data = data;
        this.status = status;
        this.httpStatus = httpStatus;
    }

    public T getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public static <T> Payload<T> create(T data) {
        return new Payload<>(data);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public static <T> Payload<T> create(T data, String status) {
        return new Payload<>(data, status);
    }

    public static <T> Payload<T> create(T data, String status, HttpStatus httpStatus) {
        return new Payload<>(data, status, httpStatus);
    }
}
