package com.jlife.abon.repository;

import com.jlife.abon.entity.SingleAttendance;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Dzmitry Misiuk
 */
public interface SingleAttendanceRepository extends EntityRepository<SingleAttendance> {

    @Query(value = "{ 'companyId' : ?0, 'visitDate' : {'$gte': ?1, '$lte': ?2 }}")
    List<SingleAttendance> findAllSingleAttendancesForCompanyWithVisitDateBetween(String companyId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'companyId' : ?0, 'trainerId': ?1, 'visitDate' : {'$gte': ?2, '$lte': ?3 }}")
    Stream<SingleAttendance> findAllSingleAttendancesForCompanyIdAndTrainerIdWithVisitDateBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'companyId' : ?0, 'visitDate' : {'$gte': ?1, '$lte': ?2 }}", count = true)
    int countSingleAttendancesForCompanyWithVisitDateBetween(String companyId, DateTime startDate, DateTime endDate);
}
