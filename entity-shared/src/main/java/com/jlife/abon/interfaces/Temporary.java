package com.jlife.abon.interfaces;

import java.util.Date;

/**
 * @author Khralovich Dzmitry.
 */
public interface Temporary {

    public static final String FINISH_DATE = "finishDate";

    public Date getStartDate();

    public void setStartDate(Date startDate);

    public Date getFinishDate();

    public void setFinishDate(Date finishDate);

}
