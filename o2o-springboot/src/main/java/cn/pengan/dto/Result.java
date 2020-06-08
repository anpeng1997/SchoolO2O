package cn.pengan.dto;

public class Result<T> {
    private boolean success;
    private T data;
    private String errorInfo;
    private int errorCode;

    public Result() {
    }

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String errorInfo) {
        this.success = success;
        this.errorInfo = errorInfo;
    }

    public Result(boolean success, String errorInfo, int errorCode) {
        this.success = success;
        this.errorInfo = errorInfo;
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
