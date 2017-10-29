package com.jlife.abon.facade;

import com.jlife.abon.dto.ClientData;
import com.jlife.abon.entity.Client;
import com.jlife.abon.report.Report;
import com.jlife.abon.report.ReportPeriod;
import com.jlife.abon.repository.ClientRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class ClientBirthdayFacadeTest extends ServiceBaseTestCase {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientBirthdayFacade clientBirthdayFacade;

    @Autowired
    ReportFacade reportFacade;

    private final Integer currentMonth = DateTime.now().getMonthOfYear();
    private final int clientsTestSetCount = 31;

    @Test
    public void test_clientsCreated_whenPrepareIsCalled() {
        this.prepareClients();
        assertEquals(clientsTestSetCount + 1, clientRepository.count());
    }

    @Test
    public void test_singleClientIsReturned_whenSearchingClientsWhoHaveBirthdayToday() {
        final int feb29Adjustment = prepareClients();
        final DateTime now = DateTime.now();
        final int expected = calculateExpected(feb29Adjustment, now);

        List<ClientData> clientsWithNearestBirthday = clientBirthdayFacade.findClientsWithNearestBirthday(DEFAULT_COMPANY_ID, 0);
        assertThat(clientsWithNearestBirthday).hasSize(expected);
    }

    @Test
    public void test_numberOfClients_whenSearchingClientsWhoHaveBirthdayWithinAPeriod() {
        prepareClients();
        final DateTimeZone tz = DateTimeZone.forID("Europe/Minsk");
        final int startDay = 11;
        final int endDay = 15;
        DateTime startDate = new DateTime(tz).withMonthOfYear(currentMonth).withDayOfMonth(startDay).withTimeAtStartOfDay();
        DateTime endDate = new DateTime(tz).withMonthOfYear(currentMonth).withDayOfMonth(endDay).withTimeAtStartOfDay();
        int expected = Days.daysBetween(startDate, endDate).getDays() + 1;

        List<ClientData> clientsWithNearestBirthday = clientBirthdayFacade.findClientsWithinDates(DEFAULT_COMPANY_ID, startDate, endDate);
        assertEquals(expected, clientsWithNearestBirthday.size());
    }

    @Test
    public void test_oneClientWithBirthdayInCurrentMonth_whenNowIsAfterBirthDay() {
        clientRepository.deleteAll();
        Client client = new Client();
        client.setCompanyId(DEFAULT_COMPANY_ID);
        client.setId("200");
        client.setUserId("200");
        DateTime clientBirthday = new DateTime(1987, currentMonth, 1, 0, 0, 0, DateTimeZone.UTC);
        client.setBirthday(clientBirthday);
        clientRepository.save(client);

        DateTime startDate = new DateTime(DateTimeZone.UTC).withDayOfMonth(1).withTimeAtStartOfDay();
        DateTime endDate = startDate.plusMonths(1).minusSeconds(1);
        List<ClientData> clientDatas = clientBirthdayFacade.findClientsWithinDates(DEFAULT_COMPANY_ID, startDate, endDate);
        assertThat(clientDatas).isNotEmpty().hasSize(1);
        assertThat(clientDatas.get(0).getBirthday().withZone(DateTimeZone.UTC)).isEqualTo(clientBirthday);
    }

    @Test
    public void test_birthdayReportIsCreated_whenCalledWithTwoDates() {
        prepareClients();
        final DateTimeZone tz = DateTimeZone.forID("Europe/Minsk");
        final int startDay = 11;
        final int endDay = 15;
        final DateTime now = new DateTime(tz);
        final DateTime startDate = now.withMonthOfYear(currentMonth).withDayOfMonth(startDay);
        final DateTime endDate = now.withMonthOfYear(currentMonth).withDayOfMonth(endDay);

        final Integer expected = Days.daysBetween(startDate, endDate).getDays() + 1;
        final Report reportData = reportFacade.generateReportForBirthday(DEFAULT_COMPANY_ID, new ReportPeriod(startDate, endDate).normalize());
        assertThat(reportData.getEntries()).isNotEmpty().hasSize(expected);
    }

    @Test
    public void test_birthdayReportIsCreated_whenCalledForToday() {
        final int feb29Adjustment = prepareClients();
        final DateTimeZone tz = DateTimeZone.forID("Europe/Minsk");
        final DateTime now = new DateTime(tz);
        final int expected = calculateExpected(feb29Adjustment, now);

        final Report reportData = reportFacade.generateReportForBirthday(DEFAULT_COMPANY_ID, new ReportPeriod(now, now).normalize());
        assertThat(reportData.getEntries()).hasSize(expected);
    }

    private int calculateExpected(int feb29Adjustment, DateTime now) {
        int expected = 1;
        if (is28Feb(now)) {
            expected += feb29Adjustment;
        } else if (is29Feb(now)) {
            expected = feb29Adjustment;
        }
        return expected;
    }

    // returns number of clients who has birthday on the 29th of February
    private int prepareClients() {
        clientRepository.deleteAll();
        int numberOfClientsWith29FebBirthday = 0;
        for (int i = clientsTestSetCount; i >= 0; i--) {
            final Client client = new Client();
            client.setCompanyId(DEFAULT_COMPANY_ID);
            client.setId(String.valueOf(i));
            client.setUserId(String.valueOf(i));
            if (i > 0) {
                final int year = (int) (Math.random() * 50 + 1970);
                final DateTime birthday = createBirthday(year, i - 1);
                if (is29Feb(birthday)) {
                    ++numberOfClientsWith29FebBirthday;
                }
                client.setBirthday(birthday);
            }
            clientRepository.save(client);
        }
        return numberOfClientsWith29FebBirthday;
    }

    private DateTime createBirthday(int year, int daysOffset) {
        DateTime baseDate = new DateTime(year, currentMonth, 1, 0, 0, 0, DateTimeZone.UTC);
        return baseDate.plusDays(daysOffset);
    }

    private boolean is28Feb(DateTime date) {
        return date.getMonthOfYear() == 2 && date.getDayOfMonth() == 28;
    }

    private boolean is29Feb(DateTime date) {
        return date.getMonthOfYear() == 2 && date.getDayOfMonth() == 29;
    }
}
