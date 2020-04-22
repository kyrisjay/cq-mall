package club.banyuan.mgt.common;

public enum FailReason {

    UMS_ADMIN_USER_NOT_VALID("用户名或密码错误"),
    UMS_ADMIN_USER_NOT_EXIST("用户不存在"),
    UMS_ADMIN_ROLE_EMPTY("角色列表为空"),
    UMS_ADMIN_ROLE_NOT_EXIST("角色不存在"),
    UMS_ROLE_NAME_DUPLICATE("角色名冲突")
    ;



    private final String message;

     FailReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
