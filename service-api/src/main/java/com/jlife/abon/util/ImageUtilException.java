package com.jlife.abon.util;

import java.io.IOException;

/**
 * @author Khralovich Dzmitry
 */
public class ImageUtilException extends IOException {

    public ImageUtilException(String message) {
        super(message);
    }

    public ImageUtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
