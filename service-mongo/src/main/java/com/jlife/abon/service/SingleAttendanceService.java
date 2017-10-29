package com.jlife.abon.service;

import com.jlife.abon.entity.SingleAttendance;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Service to marking single attendance.
 *
 * @author Dzmitry Misiuk
 */
public interface SingleAttendanceService extends EntityAbstractService<SingleAttendance> {

    /**
     * Marks not-register user as attended.
     *
     * @param companyId
     * @param price
     * @param comment
     * @return
     */
    SingleAttendance markAttended(String companyId, double price, String comment);

    /**
     * Marks not-register user as attended.
     *
     * @param companyId
     * @param price
     * @param comment
     * @param trainerId
     * @return
     */
    SingleAttendance markAttended(String companyId, double price, String trainerId, String comment);

    /**
     * Returns list of single attendances for visit date between startDate and endDate.
     *
     * @param companyId
     * @param startDate
     * @param endDate
     * @return
     */
    List<SingleAttendance> findSingleAttendancesBetween(String companyId, DateTime startDate, DateTime endDate);

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
     * Marks based on single Attendance
     *
     * @param companyId
     * @param sourceSingleAttendance
     * @return
     */
    SingleAttendance markAttended(String companyId, SingleAttendance sourceSingleAttendance);
}
