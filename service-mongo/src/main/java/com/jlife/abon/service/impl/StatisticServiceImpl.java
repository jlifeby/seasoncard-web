package com.jlife.abon.service.impl;

import com.jlife.abon.dto.CurrentStatisticData;
import com.jlife.abon.dto.ProductStatData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Product;
import com.jlife.abon.entity.SingleAttendance;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.repository.AbonnementRepository;
import com.jlife.abon.service.AbstractService;
import com.jlife.abon.service.ProductService;
import com.jlife.abon.service.StatisticService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Dzmitry Misiuk
 */
//todo return all attendances in the same abonnemen
// for now is returned only one
@Service
public class StatisticServiceImpl extends AbstractService implements StatisticService {

    @Autowired
    private ProductService productService;

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Override public ProductStatData getProductStat(String productId, String companyId) {
        Product product = productService.getProduct(productId);
        if (!product.getCompanyId().equals(companyId)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_PRODUCT, companyId, productId);
        }

        ProductStatData productStat = new ProductStatData();
        productStat.setProduct(dataMapper.toProductData(product));
        List<Abonnement> abonnements = abonnementRepository.findByProductId(productId);
        for (Abonnement abonnement : abonnements) {
            if (abonnement.checkIsActual() || abonnement.checkIsFuture()) {
                productStat.setActive(productStat.getActive() + 1);
                if (product.getAbonnementType() == AbonnementType.BY_ATTENDANCE) {
                    productStat.setAvailableAttendances(productStat.getAvailableAttendances() + abonnement.getAvailableCountOfAttendances());
                }
            } else {
                productStat.setArchived(productStat.getArchived() + 1);
            }
            productStat.setAttended(productStat.getAttended() + abonnement.getAttendances().size());

        }
        productStat.setSold(abonnements.size());
        return productStat;
    }

    @Override public int getClientAttendedLastHour(String companyId) {
        return abonnementRepository.countAllAbonnementsForCompanyWithAttendancesBetween(companyId, DateTime.now().minusHours(1), DateTime.now());
    }

    @Override public int getClientAttendedToday(String companyId) {
        // todo different of company(For Russia)
        DateTime startDayTime = new DateTime(DateTimeZone.forID("Europe/Minsk")).withTimeAtStartOfDay();
        return abonnementRepository.countAllAbonnementsForCompanyWithAttendancesBetween(companyId, startDayTime, DateTime.now());
    }

    @Override public int countSingleAttendancesToday(String companyId) {
        // todo different of company(For Russia)
        DateTime startDayTime = new DateTime(DateTimeZone.forID("Europe/Minsk")).withTimeAtStartOfDay();
        return singleAttendanceRepository.countSingleAttendancesForCompanyWithVisitDateBetween(companyId, startDayTime, DateTime.now());
    }


    @Override public int countSingleAttendancesLastHour(String companyId) {
        // todo different of company(For Russia)
        DateTime startDayTime = new DateTime(DateTimeZone.forID("Europe/Minsk")).withTimeAtStartOfDay();
        return singleAttendanceRepository.countSingleAttendancesForCompanyWithVisitDateBetween(companyId, DateTime.now().minusHours(1), DateTime.now());
    }

    @Override public int getAbonnementSoldCountLastHour(String companyId) {
        return abonnementRepository.countAllAbonnementsForCompanyWithPurchaseDateBetween(companyId, DateTime.now().minusHours(1), DateTime.now());
    }

    @Override public int getAbonnementSoldCountToday(String companyId) {
        // todo different of company(For Russia)
        DateTime startDayTime = new DateTime(DateTimeZone.forID("Europe/Minsk")).withTimeAtStartOfDay();
        return abonnementRepository.countAllAbonnementsForCompanyWithPurchaseDateBetween(companyId, startDayTime, DateTime.now());
    }

    @Override public double getAbonnementSoldSumLastHour(String companyId) {
        Stream<Abonnement> abonnementStream = abonnementRepository
                .findAllAbonnementsForCompanyWithPurchaseDateBetween(companyId, DateTime.now().minusHours(1), DateTime.now());
        double sum = abonnementStream.mapToDouble(a -> a.getProduct().getPrice()).sum();
        return sum;
    }

    @Override public double getSingleAttendancesSoldSumLastHour(String companyId) {
        List<SingleAttendance> singleAttendances = singleAttendanceRepository
                .findAllSingleAttendancesForCompanyWithVisitDateBetween(companyId, DateTime.now().minusHours(1), DateTime.now());
        double sum = singleAttendances.stream().mapToDouble(a -> a.getPrice()).sum();
        return sum;
    }

    @Override public double getSingleAttendancesSoldSumToday(String companyId) {
        DateTime startDayTime = new DateTime(DateTimeZone.forID("Europe/Minsk")).withTimeAtStartOfDay();
        List<SingleAttendance> singleAttendances = singleAttendanceRepository
                .findAllSingleAttendancesForCompanyWithVisitDateBetween(companyId, startDayTime, DateTime.now());
        double sum = singleAttendances.stream().mapToDouble(a -> a.getPrice()).sum();
        return sum;
    }

    @Override public double getAbonnementSoldSumToday(String companyId) {
        // todo different of company(For Russia)
        DateTime startDayTime = new DateTime(DateTimeZone.forID("Europe/Minsk")).withTimeAtStartOfDay();
        Stream<Abonnement> abonnementStream = abonnementRepository
                .findAllAbonnementsForCompanyWithPurchaseDateBetween(companyId, startDayTime, DateTime.now());
        double sum = abonnementStream.mapToDouble(a -> a.getProduct().getPrice()).sum();
        return sum;
    }


    @Override public CurrentStatisticData getCurrentStatistic(String companyId) {
        CurrentStatisticData currentStatistic = new CurrentStatisticData();
        currentStatistic.setHourAttendances(getClientAttendedLastHour(companyId));
        currentStatistic.setHourAbonnements(getAbonnementSoldCountLastHour(companyId));
        currentStatistic.setHourAbonnementsSum(getAbonnementSoldSumLastHour(companyId));
        currentStatistic.setTodayAttendances(getClientAttendedToday(companyId));
        currentStatistic.setTodayAbonnements(getAbonnementSoldCountToday(companyId));
        currentStatistic.setTodayAbonnementsSum(getAbonnementSoldSumToday(companyId));
        currentStatistic.setHourSingleAttendances(countSingleAttendancesLastHour(companyId));
        currentStatistic.setHourSingleAttendancesSum(getSingleAttendancesSoldSumLastHour(companyId));
        currentStatistic.setTodaySingleAttendances(countSingleAttendancesToday(companyId));
        currentStatistic.setTodaySingleAttendancesSum(getSingleAttendancesSoldSumToday(companyId));
        return currentStatistic;
    }

}
