package com.jlife.abon.facade;

import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.PriceData;
import com.jlife.abon.dto.TariffData;
import com.jlife.abon.enumeration.Country;
import com.jlife.abon.enumeration.Currency;
import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Kuzma
 */
public class TariffFacadeTest extends ServiceBaseTestCase {
    @Test
    public void test_dosNotHasPreviousTariffInList_whenCurrentIsNotNUll() {
        DateTime dt = DateTime.now().plusMonths(1);
        CompanyData company = tariffFacade
                .setCurrentTariff(DEFAULT_COMPANY_ID, TARIFF_FREE_ID, 0.0, DateTime.now(), dt, true, "descrption", true);
        TariffData currentTariff = company.getCurrentTariff();
        assertNotNull(currentTariff);
        assertEquals(TARIFF_FREE_ID, currentTariff.getId());
        assertTrue(company.getPreviousTariffs().isEmpty());
    }

    @Test
    public void test_hasPreviousTariffInList_whenCurrentWasNotNull() {
        DateTime dt = DateTime.now().plusMonths(1);
        tariffFacade.setCurrentTariff(DEFAULT_COMPANY_ID, TARIFF_FREE_ID, 0.0, DateTime.now(), dt, true, "descrption", true);

        CompanyData company = tariffFacade
                .setCurrentTariff(DEFAULT_COMPANY_ID, TARIFF_PAID_ID, 224.1, DateTime.now(), dt, true, "descrption", false);
        TariffData currentTariff = company.getCurrentTariff();
        assertNotNull(currentTariff);
        assertEquals(TARIFF_PAID_ID, currentTariff.getId());
        List<TariffData> previousTariffs = company.getPreviousTariffs();
        assertFalse(previousTariffs.isEmpty());
        assertEquals(TARIFF_FREE_ID, previousTariffs.get(0).getId());
    }

    @Test
    public void test_checkTariffFields_whenSetCurrent() {
        DateTime now = DateTime.now();
        DateTime nowPlusMonth = now.plusMonths(1);
        String description = "description";
        CompanyData company = tariffFacade
                .setCurrentTariff(DEFAULT_COMPANY_ID, TARIFF_FREE_ID, 0.0, now, nowPlusMonth, true, description, true);
        TariffData currentTariff = company.getCurrentTariff();
        assertNotNull(currentTariff);
        assertEquals(TARIFF_FREE_ID, currentTariff.getId());
        assertEquals(now, currentTariff.getStartDate());
        assertEquals(nowPlusMonth, currentTariff.getEndDate());
        assertEquals(0.0, currentTariff.getTotalPrice(), 0.01);
        assertEquals(description, company.getCurrentTariff().getComment());
        assertTrue(currentTariff.isFree());
    }

    @Test
    public void test_storedMaxClients_whenCreating() {
        TariffData tariff = new TariffData();
        tariff.setMaxClients(10);
        TariffData storedTariff = tariffFacade.createTariff(tariff);

        TariffData gotTariff = tariffFacade.getTariff(storedTariff.getId());
        assertTrue(gotTariff.getMaxClients() == 10);
    }

    @Test
    public void test_updatedMaxClients_whenUpdating() {
        TariffData tariff = tariffFacade.getTariff(TARIFF_FREE_ID);
        int maxClientsInitial = tariff.getMaxClients();

        int maxClientsUpdated = maxClientsInitial + 1;
        tariff.setMaxClients(maxClientsUpdated);

        tariffFacade.updateTariff(tariff);

        TariffData updatedTariff = tariffFacade.getTariff(TARIFF_FREE_ID);
        assertTrue(maxClientsUpdated == updatedTariff.getMaxClients());
    }

    @Test
    public void test_storedActive_whenCreating() {
        TariffData tariff = new TariffData();
        tariff.setActive(true);
        TariffData storedTariff = tariffFacade.createTariff(tariff);

        TariffData gotTariff = tariffFacade.getTariff(storedTariff.getId());
        assertTrue(gotTariff.isActive());
    }

    @Test
    public void test_updatedActive_whenUpdating() {
        TariffData tariff = tariffFacade.getTariff(TARIFF_FREE_ID);
        boolean initialActive = tariff.isActive();

        boolean updatedActive = !initialActive;
        tariff.setActive(updatedActive);

        tariffFacade.updateTariff(tariff);

        TariffData updatedTariff = tariffFacade.getTariff(TARIFF_FREE_ID);
        assertTrue(updatedActive == updatedTariff.isActive());
    }

    @Test
    public void test_getActiveTariffs_whenHaveNotActive() {
        List<TariffData> availableTariffs = tariffFacade.getAllTariffs();
        List<TariffData> activeTariffs = tariffFacade.getActiveTariffs();
        assertThat(availableTariffs).isNotNull().hasSize(2);
        assertThat(activeTariffs).isNotNull().hasSize(2);

        TariffData tariff = tariffFacade.getTariff(TARIFF_PAID_ID);
        tariff.setActive(false);
        tariffFacade.updateTariff(tariff);

        availableTariffs = tariffFacade.getAllTariffs();
        activeTariffs = tariffFacade.getActiveTariffs();
        assertThat(availableTariffs).isNotNull().hasSize(2);
        assertThat(activeTariffs).isNotNull().hasSize(1);

    }

    @Test
    public void test_storedLocalizedMonthPrice_whenCreating() {
        TariffData tariff = new TariffData();
        HashMap<Country, PriceData> localizedMonthPrice = new HashMap<>();
        localizedMonthPrice.put(Country.BELARUS, new PriceData(Currency.BYN, BigDecimal.TEN));
        tariff.setLocalizedMonthPrice(localizedMonthPrice);
        TariffData storedTariff = tariffFacade.createTariff(tariff);

        TariffData gotTariff = tariffFacade.getTariff(storedTariff.getId());
        assertThat(gotTariff.getLocalizedMonthPrice()).isNotNull().hasSize(1);
        PriceData priceData = gotTariff.getMonthPrice(Country.BELARUS);
        assertThat(priceData.getCurrency()).isEqualTo(Currency.BYN);
        assertThat(priceData.getValue()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void test_updatedLocalizedPrice_whenUpdating() {
        TariffData tariff = new TariffData();
        HashMap<Country, PriceData> localizedMonthPrice = new HashMap<>();
        localizedMonthPrice.put(Country.BELARUS, new PriceData(Currency.BYN, BigDecimal.TEN));
        tariff.setLocalizedMonthPrice(localizedMonthPrice);
        TariffData storedTariff = tariffFacade.createTariff(tariff);

        TariffData gotTariff = tariffFacade.getTariff(storedTariff.getId());
        Map<Country, PriceData> localizedMonthPriceCreated = gotTariff.getLocalizedMonthPrice();
        localizedMonthPriceCreated.put(Country.RUSSIA, new PriceData(Currency.RUB, BigDecimal.ONE));


        tariffFacade.updateTariff(gotTariff);

        TariffData updatedTariff = tariffFacade.getTariff(storedTariff.getId());
        Map<Country, PriceData> updatedMonthPrices = updatedTariff.getLocalizedMonthPrice();
        assertThat(updatedMonthPrices).isNotNull().hasSize(2);
        PriceData monthPrice = updatedTariff.getMonthPrice(Country.RUSSIA);
        assertThat(monthPrice.getValue()).isEqualTo(BigDecimal.ONE);
        assertThat(monthPrice.getCurrency()).isEqualTo(Currency.RUB);
    }

}
