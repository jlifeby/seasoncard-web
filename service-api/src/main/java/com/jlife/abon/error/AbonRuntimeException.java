package com.jlife.abon.error;

import com.jlife.abon.api.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Dzmitry Misiuk
 */
public class AbonRuntimeException extends RuntimeException {


    private final static Logger LOG = LoggerFactory.getLogger(AbonRuntimeException.class);

    private ApiErrorCode apiErrorCode;
    private String[] arguments;

    public AbonRuntimeException(ApiErrorCode apiErrorCode, String... arguments) {
        super(String.format(apiErrorCode.getPattern(), arguments));
        this.apiErrorCode = apiErrorCode;
        this.arguments = arguments;
    }

    public AbonRuntimeException() {
    }

    public ApiErrorCode getApiErrorCode() {
        return apiErrorCode;
    }

    public void setApiErrorCode(ApiErrorCode apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public ApiResponse toApiResponse() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMsg(this.getMessage());
        apiResponse.setApiErrorCode(this.getApiErrorCode());
        apiResponse.setArguments(this.getArguments());
        return apiResponse;
    }

    public String getPrettyString() {
        return String.format(apiErrorCode.getPattern(), arguments);
    }

    public String getPrettyString(MessageSource messageSource) {
        String key = "api.error." + apiErrorCode.name();
        try {
            return messageSource.getMessage(key, arguments, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            LOG.error("There is not message for key={}", key);
            return this.getPrettyString();
        }
    }
}
