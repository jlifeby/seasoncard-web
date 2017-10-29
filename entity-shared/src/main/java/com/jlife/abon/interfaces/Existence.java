package com.jlife.abon.interfaces;

/**
 * @author Khralovich Dzmitry.
 */
public interface Existence {

    public static final String STATUS = "status";

    public static final String WAITING_STATUS = "waiting";
    public static final String PROCESSING_STATUS = "processing";
    public static final String ACTIVE_STATUS = "active";
    public static final String DEACTIVATED_STATUS = "deactivated";
    public static final String MODERATION_STATUS = "moderation";

    public String getStatus();

    public void setStatus(String status);

}
