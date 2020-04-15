package club.banyuan.demo.authentication.common;

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
}
