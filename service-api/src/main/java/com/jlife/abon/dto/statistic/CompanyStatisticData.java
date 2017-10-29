package com.jlife.abon.dto.statistic;

import com.jlife.abon.dto.BaseData;
import com.jlife.abon.dto.TariffData;

/**
 * It's object that has statistic data about company
 *
 * @author Dzmitry Misiuk
 */
public class CompanyStatisticData extends BaseData {

    private String name;
    private int contractId;
    private TariffData currentTariff;

    private int countOfProducts;
    private int countOfClients;
    private long countOfVirtualCards;
    private long countOfFreeVirtualCards;
    private long countOfPhysicalCards;
    private long countOfFreePhysicalCards;

    private long countOfActiveClients;

    public CompanyStatisticData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public TariffData getCurrentTariff() {
        return currentTariff;
    }

    public void setCurrentTariff(TariffData currentTariff) {
        this.currentTariff = currentTariff;
    }

    public int getCountOfProducts() {
        return countOfProducts;
    }

    public void setCountOfProducts(int countOfProducts) {
        this.countOfProducts = countOfProducts;
    }

    public int getCountOfClients() {
        return countOfClients;
    }

    public void setCountOfClients(int countOfClients) {
        this.countOfClients = countOfClients;
    }

    public long getCountOfVirtualCards() {
        return countOfVirtualCards;
    }

    public void setCountOfVirtualCards(long countOfVirtualCards) {
        this.countOfVirtualCards = countOfVirtualCards;
    }

    public long getCountOfFreeVirtualCards() {
        return countOfFreeVirtualCards;
    }

    public void setCountOfFreeVirtualCards(long countOfFreeVirtualCards) {
        this.countOfFreeVirtualCards = countOfFreeVirtualCards;
    }

    public long getCountOfPhysicalCards() {
        return countOfPhysicalCards;
    }

    public void setCountOfPhysicalCards(long countOfPhysicalCards) {
        this.countOfPhysicalCards = countOfPhysicalCards;
    }

    public long getCountOfFreePhysicalCards() {
        return countOfFreePhysicalCards;
    }

    public void setCountOfFreePhysicalCards(long countOfFreePhysicalCards) {
        this.countOfFreePhysicalCards = countOfFreePhysicalCards;
    }

    public long getCountOfActiveClients() {
        return countOfActiveClients;
    }

    public void setCountOfActiveClients(long countOfActiveClients) {
        this.countOfActiveClients = countOfActiveClients;
    }
}
