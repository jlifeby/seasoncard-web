package com.jlife.abon.error;

/**
 * @author Dzmitry Misiuk
 */
public class BadRequestException extends AbonRuntimeException {

    public BadRequestException(ApiErrorCode apiErrorCode, String... arguments) {
        super(apiErrorCode, arguments);
    }
}
