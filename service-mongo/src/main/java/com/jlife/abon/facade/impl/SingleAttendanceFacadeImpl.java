package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.SingleAttendanceData;
import com.jlife.abon.entity.SingleAttendance;
import com.jlife.abon.facade.SingleAttendanceFacade;
import com.jlife.abon.service.SingleAttendanceService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Default implementation of single attendance facade.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class SingleAttendanceFacadeImpl extends AbstractFacade implements SingleAttendanceFacade {

    @Autowired
    private SingleAttendanceService singleAttendanceService;

    @Override
    public SingleAttendanceData markSingleAttendance(String companyId, double price, String comment) {
        SingleAttendanceData singleAttendance = new SingleAttendanceData();
        singleAttendance.setPrice(price);
        singleAttendance.setComment(comment);
        return this.markSingleAttendance(companyId, singleAttendance);
    }

    @Override
    public SingleAttendanceData markSingleAttendance(String companyId, double price, String trainerId, String comment) {
        SingleAttendanceData singleAttendance = new SingleAttendanceData();
        singleAttendance.setPrice(price);
        singleAttendance.setComment(comment);
        singleAttendance.setTrainerId(trainerId);
        return this.markSingleAttendance(companyId, singleAttendance);
    }

    @Override
    public List<SingleAttendanceData> findSingleAttendancesBetween(String companyId, DateTime startDate, DateTime endDate) {
        List<SingleAttendance> singleAttendancesBetween = singleAttendanceService.findSingleAttendancesBetween(companyId, startDate, endDate);
        return dataMapper.toSingleAttendanceDataList(singleAttendancesBetween);
    }

    @Override
    public long countSingleAttendancesBetween(String companyId, DateTime startDate, DateTime endDate) {
        return singleAttendanceService.countSingleAttendancesBetween(companyId, startDate, endDate);
    }

    @Override
    public SingleAttendanceData markSingleAttendance(String companyId, SingleAttendanceData sourceSingleAttendance) {
        SingleAttendance singleAttendance = singleAttendanceService.markAttended(companyId, dataMapper.fromSingleAttendanceData(sourceSingleAttendance));
        return dataMapper.toSingleAttendanceData(singleAttendance);
    }

    @Override
    public SingleAttendanceData getOne(String id) {
        SingleAttendance one = singleAttendanceService.getOne(id);
        return dataMapper.toSingleAttendanceData(one);
    }
}
