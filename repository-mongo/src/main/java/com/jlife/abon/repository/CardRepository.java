package com.jlife.abon.repository;

import com.jlife.abon.entity.Card;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public interface CardRepository extends EntityRepository<Card> {

    Card findOneByCardUUID(long cardUUID);

    Card findOneByUserId(String userId);

    List<Card> findByInitializingCompany(String initializingCompany);

    Card findOneByNfcUUID(String nfcUUID);

    Card findOneByCardUUIDAndFree(long cardUUID, boolean free);

    List<Card> findByDeliveredToCompany(String companyId);

    Card findOneByDeliveredToCompanyAndFreeAndNfcUUIDNull(String companyId, boolean free);

    List<Card> findByCardUUIDBetweenAndFreeAndDeliveredToCompanyNullAndNfcUUIDNotNull(long fromCard, long toCard, boolean free);

    List<Card> findByFreeAndDeliveredToCompanyNullAndNfcUUIDNull(boolean free, Pageable pageable);

    List<Card> findByCardUUIDBetweenAndDeliveredToCompanyAndFreeAndNfcUUIDNotNull(long fromCard, long toCard, String companyId, boolean free);

    List<Card> findByCardUUIDBetweenAndDeliveredToCompanyAndFreeAndNfcUUIDNull(long fromCard, long toCard, String companyId, boolean free);

    long countByFreeAndDeliveredToCompanyNullAndNfcUUIDNull(boolean free);

    /**
     * Returns virtual(w/o nfc )cards for company
     */
    long countByDeliveredToCompanyAndNfcUUIDNull(String companyId);

    /**
     * Returns physical(with nfc )cards for company
     */
    long countByDeliveredToCompanyAndNfcUUIDNotNull(String companyId);


    /**
     * Returns virtual(w/o nfc )cards for company
     *
     * @param free free or not
     */
    long countByFreeAndDeliveredToCompanyAndNfcUUIDNull(boolean free, String companyId);

    /**
     * Returns physical(with nfc )cards for company
     *
     * @param free free or not
     */
    long countByFreeAndDeliveredToCompanyAndNfcUUIDNotNull(boolean free, String companyId);

    Card findOneByFreeAndNfcUUIDNullAndDeliveredToCompanyNull(boolean free);
}