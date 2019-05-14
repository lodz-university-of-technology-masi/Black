package pl.masi.exception;

public class MasiException extends RuntimeException {
    public MasiException() {
    }

    public MasiException(String message, Throwable cause) {
        super(message, cause);
    }

    public MasiException(Throwable cause) {
        super(cause);
    }

    public MasiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MasiException(String message) {
        super(message);
    }
}
