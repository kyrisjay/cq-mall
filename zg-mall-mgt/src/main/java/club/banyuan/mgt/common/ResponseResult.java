package club.banyuan.mgt.common;

import cn.hutool.json.JSONUtil;

import static club.banyuan.mgt.common.ResponseCode.FAILED;
import static club.banyuan.mgt.common.ResponseCode.REQUEST_FAIL;

public class ResponseResult {

    /**
     * code : 200
     * message : 操作成功
     * data : {}
     */

    private int code;
    private String message;
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(ResponseCode code) {
        this(code.getCode(), code.getMessage(), "");
    }

    public ResponseResult(ResponseCode code, Object data) {
        this(code.getCode(), code.getMessage(), data);
    }

    public static ResponseResult unauthorized() {
        return new ResponseResult(ResponseCode.UNAUTHORIZED);
    }

    public static ResponseResult badRequest(String message) {
        return new ResponseResult(REQUEST_FAIL, message);
    }

    public static ResponseResult failed(String message) {
        return new ResponseResult(FAILED, message);
    }

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

    public static ResponseResult success(Object data) {
        return new ResponseResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                data);
    }

    public static ResponseResult forbidden() {
        return new ResponseResult(ResponseCode.FORBIDDEN);
    }

    public static ResponseResult forbidden(Object data) {
        return new ResponseResult(ResponseCode.FORBIDDEN, data);
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}