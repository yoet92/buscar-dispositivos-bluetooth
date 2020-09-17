package com.yt.bluej.models;

public class ResponseModel<T> {
    private boolean error;
    private T response;
    private String message;

    public ResponseModel() {

    }

    public ResponseModel(T response) {
        this.response = response;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void putError(String message) {
        this.error = true;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "hasError=" + error +
                ", response=" + response +
                ", message='" + message + '\'' +
                '}';
    }
}
