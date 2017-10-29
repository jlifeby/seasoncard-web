package com.jlife.abon.service.impl;

import com.jlife.abon.entity.SingleAttendance;
import com.jlife.abon.entity.Trainer;
import com.jlife.abon.repository.EntityRepository;
import com.jlife.abon.service.AbstractService;
import com.jlife.abon.service.SingleAttendanceService;
import com.jlife.abon.service.TrainerService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Default implementation of single attendance service.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class SingleAttendanceServiceImpl extends AbstractService implements SingleAttendanceService {

    @Autowired
    private TrainerService trainerService;

    @Override
    public SingleAttendance markAttended(String companyId, double price, String comment) {
        SingleAttendance singleAttendance = new SingleAttendance();
        singleAttendance.setPrice(price);
        singleAttendance.setComment(comment);
        singleAttendance.setCompanyId(companyId);
        return singleAttendanceRepository.save(singleAttendance);
    }

    @Override
    public SingleAttendance markAttended(String companyId, double price, String trainerId, String comment) {
        // todo DM verify
        SingleAttendance singleAttendance = new SingleAttendance();
        singleAttendance.setPrice(price);
        singleAttendance.setTrainerId(trainerId);
        singleAttendance.setComment(comment);
        singleAttendance.setCompanyId(companyId);
        return singleAttendanceRepository.save(singleAttendance);
    }

    @Override
    public List<SingleAttendance> findSingleAttendancesBetween(String companyId, DateTime startDate, DateTime endDate) {
        return singleAttendanceRepository.findAllSingleAttendancesForCompanyWithVisitDateBetween(companyId, startDate, endDate);
    }

    @Override
    public long countSingleAttendancesBetween(String companyId, DateTime startDate, DateTime endDate) {
        return singleAttendanceRepository.countSingleAttendancesForCompanyWithVisitDateBetween(companyId, startDate, endDate);
    }

    @Override
    public SingleAttendance markAttended(String companyId, SingleAttendance sourceAttendance) {
        Assert.notNull(sourceAttendance.getPrice());
        Assert.state(sourceAttendance.getPrice() >= 0, "Price should be positive");
        SingleAttendance singleAttendance = new SingleAttendance();
        singleAttendance.setPrice(sourceAttendance.getPrice());
        singleAttendance.setComment(sourceAttendance.getComment());
        singleAttendance.setCompanyId(companyId);
        if (sourceAttendance.getTrainerId() != null) {
            Trainer trainer = trainerService.getActiveOneWithOwnerCompanyId(sourceAttendance.getTrainerId(), companyId);
            singleAttendance.setTrainerId(trainer.getId());
        }
        return singleAttendanceRepository.save(singleAttendance);
    }


    @Override
    public EntityRepository<SingleAttendance> getBaseRepository() {
        return singleAttendanceRepository;
    }

    @Override
    public String getBaseEntityName() {
        return SingleAttendance.class.getSimpleName();
    }
}
