package com.jlife.abon.facade;

import com.jlife.abon.dto.SingleAttendanceData;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Facade to provide marking attended for single attendance.
 *
 * @author Dzmitry Misiuk
 */
public interface SingleAttendanceFacade {

    /**
     * Marks not-register user attended as company with purchased value.
     *
     * @param companyId
     * @param price
     * @param comment
     * @return
     * @deprecated please use {@link #markSingleAttendance(String, SingleAttendanceData)} instead
     */
    @Deprecated
    SingleAttendanceData markSingleAttendance(String companyId, double price, String comment);

    /**
     * Marks not-register user attended as company with purchased value.
     *
     * @param companyId
     * @param price
     * @param trainerId
     * @param comment
     * @return
     * @deprecated please use {@link #markSingleAttendance(String, SingleAttendanceData)} instead
     */
    @Deprecated
    SingleAttendanceData markSingleAttendance(String companyId, double price, String trainerId, String comment);

    /**
     * Returns list of single attendances for visit date between startDate and endDate.
     *
     * @param companyId
     * @param startDate
     * @param endDate
     * @return
     */
    List<SingleAttendanceData> findSingleAttendancesBetween(String companyId, DateTime startDate, DateTime endDate);

    /**
     * Count single attendances for visit date between startDate end endDate
     *
     * @param companyId
     * @param startDate
     * @param endDate
     * @return
     */
    long countSingleAttendancesBetween(String companyId, DateTime startDate, DateTime endDate);

    /**
     * Mark based on single Attendance
     *
     * @param companyId
     * @param sourceSingleAttendance
     * @return
     */
    SingleAttendanceData markSingleAttendance(String companyId, SingleAttendanceData sourceSingleAttendance);

    SingleAttendanceData getOne(String id);
}
