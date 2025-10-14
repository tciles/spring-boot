package fr.eni.demoSpringFramework.Response;

public class Payload<T> {

    private final T data;

    private String status = "OK";

    public Payload(T data) {
        this.data = data;
    }

    public Payload(T data, String status) {
        this.data = data;
        this.status = status;
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

    public static <T> Payload<T> create(T data, String status) {
        return new Payload<>(data, status);
    }
}
