package com.linkinstars.autocoding.model;

/**
 * web公共返回类型
 * @author LinkinStar
 */
public class CommonResponse {
    /** 1：成功，-1：失败 **/
    private int code;
    /** 返回信息 **/
    private String message;
    /** 返回数据 **/
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public CommonResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public static CommonResponse fail(String message) {
        return new CommonResponse(-1, message, null);
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
