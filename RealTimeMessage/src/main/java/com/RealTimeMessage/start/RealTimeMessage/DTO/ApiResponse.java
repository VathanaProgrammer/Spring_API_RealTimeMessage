package com.RealTimeMessage.start.RealTimeMessage.DTO;

// src/main/java/com/yourapp/dto/ApiResponse.java
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    // getters + setters …
    public boolean getSuccess(){
        return this.success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public T getData(){
        return this.data;
    }

    public void setData(T data){
        this.data = data;
    }
}
