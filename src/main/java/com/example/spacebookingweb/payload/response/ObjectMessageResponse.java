package com.example.spacebookingweb.payload.response;

public class ObjectMessageResponse<T> extends MessageResponse {
    private T object;
    public ObjectMessageResponse(String message, T object) {
        super(message);
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
