package com.jlife.abon.repository;

import com.jlife.abon.entity.Abonnement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Dzmitry Misiuk
 */
public interface AbonnementRepository extends EntityRepository<Abonnement> {

    List<Abonnement> findByCardId(String cardId);

    List<Abonnement> findByCardIdAndActive(String cardId, boolean active);

    List<Abonnement> findByCardIdAndActiveAndProductIdIn(String cardId, boolean active, List<String> productIds);

    List<Abonnement> findByProductIdIn(List<String> productIds);

    List<Abonnement> findByProductId(String productId);

    Abonnement findOneByIdAndCardId(String abonnementId, String userId);

    List<Abonnement> findByCardIdAndProductId(String cardId, String productId);

    List<Abonnement> findByCardIdAndProductIdIn(String cardId, List<String> productIds);

    List<Abonnement> findByProductIdAndPurchaseDateBetween(String productId, DateTime startDate, DateTime endDate);


    List<Abonnement> findByPurchaseDateBetweenAndProductIdIn(DateTime startDate, DateTime endDate, String... productIds);

    @Query(value = "{ 'product.companyId' : ?0, 'attendances': {  '$elemMatch' : {'visitDate' : {'$gte': ?1, '$lte': ?2 }}}}")
    List<Abonnement> getAllAbonnementsForCompanyWithAttendancesBetween(String companyId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'product.companyId' : ?0, 'attendances': {  '$elemMatch' : {'visitDate' : {'$gte': ?1, '$lte': ?2 }}}}", count = true)
    int countAllAbonnementsForCompanyWithAttendancesBetween(String companyId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'product.companyId' : ?0, 'purchaseDate' : {'$gte': ?1, '$lte': ?2 }}")
    Stream<Abonnement> findAllAbonnementsForCompanyWithPurchaseDateBetween(String companyId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'product.companyId' : ?0, 'purchaseDate' : {'$gte': ?1, '$lte': ?2 }}", count = true)
    int countAllAbonnementsForCompanyWithPurchaseDateBetween(String companyId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'product.companyId' : ?0, '$or': [{'purchaseDate' : {'$gte': ?1, '$lte': ?2 }}, {'attendances': {'$elemMatch': { 'visitDate' : {'$gte': ?1, '$lte': ?2 }}}}]}",
            fields = "{cardId: 1, _id:  1 }")
    Stream<Abonnement> countUniqueAbonWithAtLeastOneAttendancesOrPurchasedByCompanyBetween(String companyId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'product.companyId' : ?0, 'trainerId': ?1, 'purchaseDate' : {'$gte': ?2, '$lte': ?3 }}")
    Stream<Abonnement> findAllAbonnementsForCompanyIdAndTrainerIdWithPurchaseDateBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'product.companyId' : ?0, 'attendances': { '$elemMatch': {'trainerId': ?1, 'visitDate' : {'$gte': ?2, '$lte': ?3 }}}}")
    Stream<Abonnement> findAllAbonnementsForCompanyIdAndTrainerIdWithVisitDateBetween(String companyId, String trainerId, DateTime startDate, DateTime endDate);

    @Query(value = "{ 'attendances' : { '$elemMatch': {'visitDate' : {'$gte': ?0, '$lte': ?1 }}}}")
    List<Abonnement> getAllAbonnementsWithAttendancesBetween(DateTime startDate, DateTime endDate);

    @NotNull
    List<Abonnement> findByPurchaseDateBetween(@NotNull DateTime startDate, @NotNull DateTime endDate);

    @NotNull
    @Query(value = "{ 'product.companyId' : ?0, '$or': [{'purchaseDate' : {'$gte': ?1, '$lte': ?2 }}, {'attendances': {'$elemMatch': { 'visitDate' : {'$gte': ?1, '$lte': ?2 }}}}]}")
    List<Abonnement> findAbonsWithAtLeastOneAttendanceOrPurchasedByCompanyBetween(@Nullable String companyId, @Nullable DateTime startDate, @Nullable DateTime endDate);
}
