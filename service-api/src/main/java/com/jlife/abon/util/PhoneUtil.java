package com.jlife.abon.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dzmitry Misiuk
 */
public class PhoneUtil {

    protected final static Logger LOG = LoggerFactory.getLogger(PhoneUtil.class);


    public static PhoneNumberUtil PHONE_UTIL = PhoneNumberUtil.getInstance();

    public static String formatPhone(String rowPhone) {
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = PHONE_UTIL.parse(rowPhone, "BY");
        } catch (NumberParseException e) {
            LOG.error("Error format phone: ", e);
        }
        String formattedPhone = rowPhone;
        if (phoneNumber != null) {
            formattedPhone = PHONE_UTIL.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        }

        //TODO Phone Util skipped
//        return formattedPhone;
        return rowPhone;
    }
}
