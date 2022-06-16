package com.example.appweather.service;

import com.example.appweather.response.BaseResponse;
import org.springframework.http.HttpStatus;

public interface BaseService {

    default BaseResponse SUCCESS_ADDED(Object o){
        String className = o.getClass().getSimpleName();
        BaseResponse response = new BaseResponse(className + " adding success", true, 200);
        response.setData(o);
        return response;
    }
    default BaseResponse EXIST(Object o){
        String className = o.getClass().getSimpleName();
        BaseResponse response = new BaseResponse(className + " exist", true, 409);
        response.setData(o);
        return response;
    }
    default BaseResponse DONT_EXIST(Object o){
        String className = o.getClass().getSimpleName();
        BaseResponse response = new BaseResponse(className + " dont exist", true, 409);
        response.setData(o);
        return response;
    }
    default BaseResponse SPECIAL(Object o,String message,int statusCode,boolean success){
        String className = o.getClass().getSimpleName();
        BaseResponse response = new BaseResponse(message, success, statusCode);
        response.setData(o);
        return response;
    }
}
