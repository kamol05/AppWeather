package com.example.appweather.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponse {
    private String message;
    private boolean success;
    private int statusCode;
    private Object data;

    public BaseResponse(String message, boolean success, int statusCode) {
        this.message = message;
        this.success = success;
        this.statusCode = statusCode;
    }

    public BaseResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
