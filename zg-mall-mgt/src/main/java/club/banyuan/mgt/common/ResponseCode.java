package club.banyuan.mgt.common;

public enum ResponseCode {
    SUCCESS(200, "操作成功"),
    FORBIDDEN(401, "用户身份验证失败"),
    UNAUTHORIZED(403, "用户未授权");
    ;

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
