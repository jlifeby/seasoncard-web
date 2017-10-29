package com.jlife.abon.error;

/**
 * @author Dzmitry Misiuk
 */
public class NotAllowedException extends AbonRuntimeException {

    public NotAllowedException(ApiErrorCode apiErrorCode, String... arguments) {
        super(apiErrorCode,arguments);
    }
}
