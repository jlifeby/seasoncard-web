package com.jlife.abon.api;

import com.jlife.abon.error.ApiErrorCode;

/**
 * @author Dzmitry Misiuk
 */
public class ApiResponse {

    private boolean success;

    private ApiErrorCode apiErrorCode;

    private String msg;

    private String localizedMsg;

    private String arguments[];

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLocalizedMsg() {
        return localizedMsg;
    }

    public void setLocalizedMsg(String localizedMsg) {
        this.localizedMsg = localizedMsg;
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

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
