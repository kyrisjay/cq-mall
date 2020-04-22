package club.banyuan.mgt.common;

public class RequestFailException extends RuntimeException {
    public RequestFailException() {
    }

    public RequestFailException(FailReason failReason) {
        this(failReason.getMessage());
    }

    public RequestFailException(String message) {
        super(message);
    }

    public RequestFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestFailException(Throwable cause) {
        super(cause);
    }

    public RequestFailException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
