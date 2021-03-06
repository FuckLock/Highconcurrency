package com.bao.miaosha.result;

public class Result<T> {
    private int code;
    private String msg;
    private  T data;

    public Result(){

    }

    public Result(T data, CodeMsg codeMsg) {
        this.data = data;
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public Result(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(CodeMsg codeMsg) {
        if (codeMsg == null){
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 成功方法
    public static <T> Result<T> success(T data){
        return new Result<T>(data, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
    }

    public static <T> Result<T> success(T data, CodeMsg codeMsg){
        return new Result<T>(data, codeMsg);
    }

    public static <T> Result<T> success(T data, String msg){
        return new Result<T>(data, ResponseCode.SUCCESS.getCode(), msg);
    }

    // 失败方法
    public static <T> Result<T> error(){
        return new Result<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg());
    }

    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    public static <T> Result<T> error(String msg){
        return new Result<T>(ResponseCode.ERROR.getCode(), msg);
    }

    // get set方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
