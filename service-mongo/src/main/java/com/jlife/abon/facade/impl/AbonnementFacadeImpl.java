package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.ConsolidatedAbonnementData;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Attendance;
import com.jlife.abon.entity.ConsolidatedAbonnement;
import com.jlife.abon.entity.Product;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.facade.AbonnementFacade;
import com.jlife.abon.facade.MarkingAttendedFacade;
import com.jlife.abon.facade.ProductFacade;
import com.jlife.abon.repository.AbonnementRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Component
public class AbonnementFacadeImpl extends AbstractFacade implements AbonnementFacade {

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private ProductFacade productFacade;

    @Override
    public List<AbonnementData> getActiveAbonnementsForUser(String userId) {
        List<Abonnement> activeAbonnementsForUser = abonnementService.getActiveAbonnementsForUser(userId);
        List<AbonnementData> abonnementDatas = dataMapper.toAbonnementDataList(activeAbonnementsForUser);
        for (AbonnementData abonnement : abonnementDatas) {
            String productId = abonnement.getProductId();
            ProductData product = productFacade.getProductWithCompany(productId);
            if (abonnement.getProduct() == null) {
                abonnement.setProduct(product);
            }
            abonnement.getProduct().setCompany(product.getCompany());
        }
        return abonnementDatas;
    }

    /**
     * Marks attended
     *
     * @param abonnementId
     * @param companyId
     * @return
     * @deprecated please use {@link MarkingAttendedFacade#markAttended(String, com.jlife.abon.dto.AttendanceData)} instead
     */
    @Override
    @Deprecated
    public AbonnementData markAttended(String abonnementId, String companyId, int markUnits) {
        Abonnement abonnement = markingAttendedService.markAttendedAsCompany(abonnementId, companyId, markUnits);
        return dataMapper.toAbonnementData(abonnement);
    }

    /**
     * Marks attended
     *
     * @param abonnementId
     * @param companyId
     * @param trainerId
     * @return
     * @deprecated please use {@link MarkingAttendedFacade#markAttended(String, com.jlife.abon.dto.AttendanceData)} instead
     */
    @Override
    @Deprecated
    public AbonnementData markAttended(String abonnementId, String companyId, int markUnits, String trainerId) {
        Attendance attendance = new Attendance();
        attendance.setMarkUnits(markUnits);
        attendance.setTrainerId(trainerId);
        attendance.setAbonnementId(abonnementId);
        Abonnement abonnement = markingAttendedService.markAttendedAsCompany(companyId, attendance);
        return dataMapper.toAbonnementData(abonnement);
    }

    // todo move checking to service


    @Override
    public AbonnementData getAbonnementByCompany(String abonnementId, String companyId) {
        Abonnement abonnement = abonnementRepository.findOne(abonnementId);
        if (abonnement == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ABONNEMENT_NOT_FOUND,
                    abonnementId);
        }
        Product product = productService.getProduct(abonnement.getProductId());
        if (!companyId.equals(product.getCompanyId())) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_THIS_ABONNEMENT,
                    companyId, abonnementId);
        }
        // todo remove setting
        if (abonnement.getProduct() == null) {
            abonnement.setProduct(product);
        }
        populateTrainer(abonnement);
        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public AbonnementData getAbonnementByUserCardId(String abonnementId, String cardId) {
        Abonnement abonnement = abonnementRepository.findOne(abonnementId);
        if (abonnement == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ABONNEMENT_NOT_FOUND,
                    abonnementId);
        }
        if (!cardId.equals(abonnement.getCardId())) {
            throw new NotAllowedException(
                    ApiErrorCode.USER_IS_NOT_OWNER_OF_THIS_ABONNEMENT,
                    cardId, abonnementId);
        }

        Product product = productService.getProduct(abonnement.getProductId());

        if (abonnement.getProduct() == null) {
            abonnement.setProduct(product);
        }
        populateCompany(abonnement.getProduct());

        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public AbonnementData getAbonnementByUserCard(String abonnementId, String cardId) {
        Abonnement abonnement = abonnementRepository.findOneByIdAndCardId(abonnementId, cardId);
        if (abonnement == null) {
            throw new ResourceNotFoundException(ApiErrorCode.ABONNEMENT_NOT_FOUND,
                    abonnementId);
        }
        if (abonnement.getProduct() == null) {
            Product product = productService.getProduct(abonnement.getProductId());
            abonnement.setProduct(product);
        }
        populateCompany(abonnement.getProduct());
        populateTrainer(abonnement);
        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public ConsolidatedAbonnementData getConsolidatedAbonnement(String userId, String productId) {
        ConsolidatedAbonnement consolidateAbonnement = abonnementService.getConsolidateAbonnement(userId, productId);
        populateProductWithCompany(consolidateAbonnement);
        return dataMapper.toConsolidatedAbonnementData(consolidateAbonnement);
    }

    @Override
    public List<AbonnementData> getLastAbonnementsForUser(String userId) {
        List<Abonnement> lastAbonnements = abonnementService.getLastAbonnementForEachProduct(userId);
        populateProducts(lastAbonnements);
        return dataMapper.toAbonnementDataList(lastAbonnements);
    }

    @Override
    public AbonnementData changeEndDate(String abonnementId, DateTime newEndDate, String comment, String companyId) {
        Abonnement abonnement = abonnementService.changeEndDate(abonnementId, newEndDate, comment, companyId);
        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public AbonnementData getAbonnementById(String abonnementId, String companyId) {
        Abonnement abonnement = abonnementService.getAbonnementAsCompany(abonnementId, companyId);
        populateTrainer(abonnement);
        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public AbonnementData addComment(String abonnementId, String comment, String companyId) {
        Abonnement abonnement = abonnementService.addComment(abonnementId, comment, companyId);
        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public ConsolidatedAbonnementData getConsolidatedAbonnementAsCompany(String userId, String productId, String companyId) {
        ConsolidatedAbonnement consolidateAbonnement = abonnementService.getConsolidateAbonnementAsCompany(userId, productId, companyId);
        populateProductWithCompany(consolidateAbonnement);
        return dataMapper.toConsolidatedAbonnementData(consolidateAbonnement);
    }

    @Override
    public List<AbonnementData> findAll() {
        List<Abonnement> abonnements = abonnementRepository.findAll();
        return dataMapper.toAbonnementDataList(abonnements);
    }

    @Override
    public AbonnementData findOne(String abonnementId) {
        Abonnement abonnement = abonnementRepository.findOne(abonnementId);
        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public AbonnementData save(AbonnementData rowAbonnement) {
        Abonnement savedAbonnement = abonnementRepository.save(dataMapper.fromAbonnementData(rowAbonnement));
        return dataMapper.toAbonnementData(savedAbonnement);
    }
}
