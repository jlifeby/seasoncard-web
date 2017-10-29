package com.jlife.abon.error;

/**
 * @author Dzmitry Misiuk
 */
public class ResourceNotFoundException extends AbonRuntimeException {

    public ResourceNotFoundException(ApiErrorCode apiErrorCode, String... arguments) {
        super(apiErrorCode, arguments);
    }

    public ResourceNotFoundException() {
    }


}
