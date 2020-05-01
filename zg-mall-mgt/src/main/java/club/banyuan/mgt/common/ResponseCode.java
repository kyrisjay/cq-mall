package club.banyuan.mgt.common;

public enum ResponseCode {
    SUCCESS(200, "操作成功"),
    REQUEST_FAIL(400, "操作失败"),
    FORBIDDEN(401, "用户身份验证失败"),
    UNAUTHORIZED(403, "用户未授权"),
    FAILED(500, "服务器内部错误");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
