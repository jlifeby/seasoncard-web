package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.ClientGroupData;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Product;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.facade.CardFacade;
import com.jlife.abon.facade.ClientGroupFacade;
import com.jlife.abon.repository.AbonnementRepository;
import com.jlife.abon.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dzmitry Misiuk
 */
// todo refactor
@Service
public class ClientGroupFacadeImpl extends AbstractFacade implements ClientGroupFacade {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private CardFacade cardFacade;

    @Override
    public List<ClientGroupData> getClientsGroupsByCompany(String companyId) {
        List<ClientGroupData> clientGroups = new ArrayList<>();
        List<Product> products = productRepository.findByCompanyId(companyId);
        List<ProductData> productDataList = dataMapper.toProductDataList(products);
        for (ProductData product : productDataList) {
            ClientGroupData clientGroup = new ClientGroupData();
            clientGroup.setProductId(product.getId());
            clientGroup.setProduct(product);
            List<Abonnement> abonnements = abonnementRepository.findByProductId(product.getId());
            Set<String> memberCardIds = new HashSet<>();
            for (Abonnement abonnement : abonnements) {
                if (abonnement.checkIsActual()) {
                    memberCardIds.add(abonnement.getCardId());
                }
            }
            clientGroup.setMemberCount(memberCardIds.size());
            clientGroups.add(clientGroup);
        }
        return clientGroups;
    }

    @Override
    public ClientGroupData getClientGroupByCompanyAndProduct(String companyId, String productId) {
        Product product = productRepository.findOne(productId);
        if (product == null) {
            throw new ResourceNotFoundException(ApiErrorCode.PRODUCT_NOT_FOUND, productId);
        }
        if (!companyId.equals(product.getCompanyId())) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_PRODUCT,
                    companyId,
                    productId);
        }
        List<Abonnement> abonnements = abonnementRepository.findByProductId(product.getId());
        Set<String> memberCardIds = new HashSet<>();
        for (Abonnement abonnement : abonnements) {
            if (abonnement.checkIsActual()) {
                memberCardIds.add(abonnement.getCardId());
            }
        }
        List<CardData> memberCards = cardFacade.getCardsWithPopulatedUsers(memberCardIds, companyId);
        ClientGroupData clientGroup = new ClientGroupData();
        clientGroup.setProduct(dataMapper.toProductData(product));
        clientGroup.setProductId(productId);
        clientGroup.setMembers(memberCards);
        clientGroup.setMemberCount(memberCards.size());
        return clientGroup;
    }
}
