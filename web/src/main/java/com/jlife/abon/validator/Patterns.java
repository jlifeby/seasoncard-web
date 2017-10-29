package com.jlife.abon.validator;

/**
 * @author Dzmitry Misiuk
 */
public class Patterns {
    public static final String PHONE = "((^375(25|29|33|44|17)[\\d]{7}$)|(^(7)\\d{3}[\\d]{7,10}))$";
    public static final String EMAIL = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+";
}
