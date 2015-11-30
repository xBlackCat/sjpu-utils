package org.xblackcat.sjpu.util.function;

/**
 * Exception for covering checked exceptions from functional *Ex interfaces by default used in unchecked() method of the interfaces.
 *
 * @author xBlackCat
 */
public class CoveringException extends RuntimeException {
    public CoveringException() {
    }

    public CoveringException(String message) {
        super(message);
    }

    public CoveringException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoveringException(Throwable cause) {
        super(cause);
    }

    public CoveringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
