package club.banyuan.mgt.common;

public enum FailReason {

    UMS_ADMIN_USER_NOT_VALID("用户名或密码错误"),
    UMS_ADMIN_USER_NOT_EXIST("用户不存在"),
    UMS_ADMIN_ROLE_EMPTY("角色列表为空");



    private final String message;

     FailReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
