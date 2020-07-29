package com.onlin.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultApi {

    private boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private ResultApi(){}

    public static ResultApi ok() {
        ResultApi r = new ResultApi();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //失败静态方法
    public static ResultApi error() {
        ResultApi r = new ResultApi();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public ResultApi success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultApi message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultApi code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultApi data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResultApi data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
