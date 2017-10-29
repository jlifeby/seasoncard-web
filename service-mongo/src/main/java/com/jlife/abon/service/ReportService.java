package com.jlife.abon.service;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;
import com.jlife.abon.dto.ClientData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Attendance;
import com.jlife.abon.entity.Card;
import com.jlife.abon.entity.Client;
import com.jlife.abon.entity.Product;
import com.jlife.abon.entity.SingleAttendance;
import com.jlife.abon.entity.Trainer;
import com.jlife.abon.entity.User;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.enumeration.ReportTrainerType;
import com.jlife.abon.facade.ClientBirthdayFacade;
import com.jlife.abon.report.Report;
import com.jlife.abon.report.ReportEntry;
import com.jlife.abon.report.ReportHeader;
import com.jlife.abon.report.ReportKey;
import com.jlife.abon.report.ReportPeriod;
import com.jlife.abon.report.ReportSummary;
import com.jlife.abon.repository.AbonnementRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class ReportService {

    private static DecimalFormat currencyDF = new DecimalFormat("#,###,##0.00");

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private ClientBirthdayFacade clientBirthdayFacade;

    @Autowired
    private SingleAttendanceService singleAttendanceService;

    @Autowired
    private TrainerStatisticService trainerStatisticService;


    public Report generateReportForAllAbonnements(String companyId, ReportPeriod reportPeriod) {
        Report report = new Report();
        report.setReportPeriod(reportPeriod);
        report.setReportName("Отчет по абонементам");
        List<Product> companyProducts = productService.getAllProductsForCompany(companyId);
        double totalPrice = 0;
        int totalSold = 0;
        int totalActualCount = 0;
        int currentNumber = 0;
        for (Product companyProduct : companyProducts) {
            currentNumber++;
            List<Abonnement> abonnementList = abonnementRepository
                    .findByProductIdAndPurchaseDateBetween(companyProduct.getId(), reportPeriod.getStartDate(), reportPeriod.getEndDate());
            int soldCount = abonnementList.size();
            totalSold = totalSold + soldCount;
            int actualCount = Collections2.filter(abonnementList, Abonnement::checkIsActual).size();
            totalActualCount += actualCount;
            int attendedCount = 0;
            int initialAttendances = 0;
            int canAttend = 0;
            double commononPrice = 0;


            for (Abonnement abonnement : abonnementList) {
                attendedCount = attendedCount + abonnement.getCountOfAttendances();
                initialAttendances = initialAttendances + abonnement.getInitialCountOfAttendances();
                totalPrice = totalPrice + abonnement.getProduct().getPrice();
                commononPrice = commononPrice + abonnement.getProduct().getPrice();
                canAttend = canAttend + abonnement.getAvailableCountOfAttendances();
            }

            String name = companyProduct.getName();
            ReportEntry reportEntry = new ReportEntry();
            reportEntry.addPair(ReportKey.ORDER_NUMBER, currentNumber);
            reportEntry.addPair(ReportKey.PRODUCT_NAME, name);
            reportEntry.addPair(ReportKey.COUNT_OF_SOLD, soldCount);
            reportEntry.addPair(ReportKey.COUNT_OF_ACTUAL, actualCount);
            reportEntry.addPair(ReportKey.ALL_INITIAL_ATTENDANCES, initialAttendances);
            if (companyProduct.getAbonnementType() == AbonnementType.BY_ATTENDANCE) {
                reportEntry.addPair(ReportKey.ALL_INITIAL_ATTENDANCES, initialAttendances);
                reportEntry.addPair(ReportKey.ALL_AVAILABLE_ATTENDANCES, canAttend);
            } else {
                reportEntry.addPair(ReportKey.ALL_INITIAL_ATTENDANCES, "-");
                reportEntry.addPair(ReportKey.ALL_AVAILABLE_ATTENDANCES, "-");
            }
            commononPrice = (double) Math.round(commononPrice * 100d) / 100d;
            reportEntry.addPair(ReportKey.PRODUCT_COMMON_PRICE, commononPrice);
            report.addEntry(reportEntry);
        }
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.addPair(ReportKey.ORDER_NUMBER, "№");
        reportHeader.addPair(ReportKey.PRODUCT_NAME, "Наименование абонемента");
        reportHeader.addPair(ReportKey.COUNT_OF_SOLD, "Кол-во проданных");
        reportHeader.addPair(ReportKey.COUNT_OF_ACTUAL, "Из них активных");
        reportHeader.addPair(ReportKey.ALL_INITIAL_ATTENDANCES, "Всего посещений");
        reportHeader.addPair(ReportKey.ALL_AVAILABLE_ATTENDANCES, "Осталось посещений");
        reportHeader.addPair(ReportKey.PRODUCT_COMMON_PRICE, "Общая стоимость, рублей");
        report.setReportHeader(reportHeader);

        ReportSummary reportSummary = new ReportSummary();
        reportSummary.addPair(ReportKey.ORDER_NUMBER, "");
        reportSummary.addPair(ReportKey.PRODUCT_NAME, "Итого");
        reportSummary.addPair(ReportKey.COUNT_OF_SOLD, totalSold);
        reportSummary.addPair(ReportKey.COUNT_OF_ACTUAL, totalActualCount);
        reportSummary.addPair(ReportKey.ALL_INITIAL_ATTENDANCES, "-");
        reportSummary.addPair(ReportKey.ALL_AVAILABLE_ATTENDANCES, "-");
        reportSummary.addPair(ReportKey.PRODUCT_COMMON_PRICE, totalPrice);
        report.setReportSummary(reportSummary);

        return report;
    }


    public Report generateReportForNewClients(String companyId, ReportPeriod reportPeriod) {

        // todo format date in another place
        Report report = new Report();
        report.setReportPeriod(reportPeriod);
        report.setReportName("Отчет по новым посетителям");
        List<Client> newClients = clientService.
                getNewClientsForCompanyBetweenDays(companyId, reportPeriod.getStartDate(), reportPeriod.getEndDate());
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.addPair(ReportKey.ORDER_NUMBER, "№");
        reportHeader.addPair(ReportKey.INITIALS, "ФИО");
        reportHeader.addPair(ReportKey.CARD_UUID, "Номер карты");
        reportHeader.addPair(ReportKey.PHONE, "Номер телефона");
        reportHeader.addPair(ReportKey.CREATED_DATE, "Дата добавления в систему");
        reportHeader.addPair(ReportKey.LAST_ATTENDANCE_DATE, "Дата последнего посещения");
        report.setReportHeader(reportHeader);
        int orderNumber = 0;
        for (Client newClient : newClients) {
            orderNumber++;
            ReportEntry reportEntry = new ReportEntry();
            reportEntry.addPair(ReportKey.ORDER_NUMBER, orderNumber);
            reportEntry.addPair(ReportKey.INITIALS, newClient.getLastName() + " " + newClient.getName());
            reportEntry.addPair(ReportKey.CARD_UUID, newClient.getCardUUID());
            reportEntry.addPair(ReportKey.PHONE, newClient.getPhone());
            reportEntry.addPair(ReportKey.CREATED_DATE, formatDateToUserLocale(newClient.getCreatedDate()));
            DateTime lastAttendanceDate = getLastAttendance(newClient);
            if (lastAttendanceDate != null) {
                reportEntry.addPair(ReportKey.LAST_ATTENDANCE_DATE, formatDateToUserLocale(lastAttendanceDate));
            } else {
                reportEntry.addPair(ReportKey.LAST_ATTENDANCE_DATE, "-");
            }
            report.addEntry(reportEntry);

        }

        return report;
    }


    public Report generateReportForAllSoldAbonnements(String companyId, ReportPeriod reportPeriod) {
        Report report = new Report();
        report.setReportPeriod(reportPeriod);
        report.setReportName("Отчет по проданным абонементам");
        // get user for eache abonnement
        // add this infromation to report
        List<Product> productList = productService.getAllProductsForCompany(companyId);
        Collection<String> productIds = Collections2.transform(productList, new Function<Product, String>() {
            @Override
            public String apply(Product input) {
                return input.getId();
            }
        });
        List<Abonnement> soldAbonnements = abonnementService.getAbonnementsForProductsSoldBetweenDays(productIds,
                reportPeriod.getStartDate(), reportPeriod.getEndDate());
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.addPair(ReportKey.ORDER_NUMBER, "№");
        reportHeader.addPair(ReportKey.INITIALS, "ФИО");
        reportHeader.addPair(ReportKey.PRODUCT_NAME, "Название абонемента");
        reportHeader.addPair(ReportKey.ALL_INITIAL_ATTENDANCES, "Кол-во занятий");
        reportHeader.addPair(ReportKey.START_DATE, "Дата начала");
        reportHeader.addPair(ReportKey.END_DATE, "Дата окончания");
        reportHeader.addPair(ReportKey.PURCHASE_DATE, "Дата покупки");
        reportHeader.addPair(ReportKey.PRODUCT_COMMON_PRICE, "Цена продажи");
        report.setReportHeader(reportHeader);
        int orderNumber = 0;
        for (Abonnement soldAbonnement : soldAbonnements) {
            orderNumber++;
            Card card = cardService.getCard(soldAbonnement.getCardId());
            User user = userService.getUserByCardUUD(card.getCardUUID());
            // todo optimize
            Client client = clientService.getClientByUserIdAndCompanyId(user.getId(), companyId);
            Product product = productService.getProduct(soldAbonnement.getProductId());
            ReportEntry reportEntry = new ReportEntry();
            reportEntry.addPair(ReportKey.ORDER_NUMBER, orderNumber);
            reportEntry.addPair(ReportKey.INITIALS, client.getLastName() + " " + client.getName());
            // todo analyze what product name we should use
            reportEntry.addPair(ReportKey.PRODUCT_NAME, product.getName());
            if (soldAbonnement.getType().equals(AbonnementType.BY_ATTENDANCE)) {
                reportEntry.addPair(ReportKey.ALL_INITIAL_ATTENDANCES, soldAbonnement.getInitialCountOfAttendances());
            } else {
                reportEntry.addPair(ReportKey.ALL_INITIAL_ATTENDANCES, "Неогр.");
            }
            reportEntry.addPair(ReportKey.START_DATE, formatDateToUserLocale(soldAbonnement.getStartDate()));
            reportEntry.addPair(ReportKey.END_DATE, formatDateToUserLocale(soldAbonnement.getEndDate()));
            reportEntry.addPair(ReportKey.PURCHASE_DATE, formatDateToUserLocale(soldAbonnement.getPurchaseDate()));
            reportEntry.addPair(ReportKey.PRODUCT_COMMON_PRICE, soldAbonnement.getProduct().getPrice());
            report.addEntry(reportEntry);
        }
        return report;
    }

    private DateTime getLastAttendance(Client newClient) {
        // todo optimize in future
        Card card = cardService.getClientCardByUUID(newClient.getCardUUID(), newClient.getCompanyId());

        List<Abonnement> abonnementList = abonnementService
                .getAllAbonnementsForCompanyAndCardId(newClient.getCompanyId(), card.getId());
        List<Attendance> allAttendaces = new ArrayList<>();
        for (Abonnement abonnement : abonnementList) {
            allAttendaces.addAll(abonnement.getAttendances());
        }
        if (allAttendaces.isEmpty()) {
            return null;
        } else if (abonnementList.size() == 1) {
            return allAttendaces.get(0).getVisitDate();
        }
        DateTime lastAttendanceDate = Collections.max(allAttendaces, new Ordering<Attendance>() {
            @Override
            public int compare(Attendance left, Attendance right) {

                return left.getVisitDate().compareTo(right.getVisitDate());
            }
        }).getVisitDate();
        return lastAttendanceDate;
    }

    private String formatDateToUserLocale(DateTime date) {
        if (date != null) {
            DateTime createdDateUserFormat = date.toDateTime(DateTimeZone.forID("Europe/Minsk"));
            return createdDateUserFormat.toString("dd.MM.yyyy");
        } else {
            return "";
        }
    }

    private String formatDateTimeToUserLocale(DateTime date) {
        DateTime createdDateUserFormat = date.toDateTime(DateTimeZone.forID("Europe/Minsk"));
        return createdDateUserFormat.toString("d.MM.yyyy HH:mm");
    }

    public Report generateReportForAllAttendances(String companyId, ReportPeriod reportPeriod) {
        // todo write aggregation in future
        Report report = new Report();
        report.setReportPeriod(reportPeriod);
        report.setReportName("Отчет по посещениям");
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.addPair(ReportKey.DATE_TIME, "Дата\\Время");
        for (int i = 7; i < 23; i++) {
            reportHeader.addPair(String.valueOf(i), String.valueOf(i));
        }
        reportHeader.addPair("23", "23-7");
        report.setReportHeader(reportHeader);
        List<Abonnement> abonnementList = abonnementRepository.getAllAbonnementsForCompanyWithAttendancesBetween(companyId,
                reportPeriod.getStartDate(), reportPeriod.getEndDate());

        LinkedHashMap<String, Map<String, Integer>> staticForAttendances = new LinkedHashMap<>();
        DateTime itDate = reportPeriod.getStartDate();
        do {
            String dateKey = formatDateToUserLocale(itDate);
            itDate = itDate.plusDays(1);
            staticForAttendances.put(dateKey, new HashMap<>());
        } while (itDate.isBefore(reportPeriod.getEndDate()));

        for (Abonnement abonnement : abonnementList) {
            for (Attendance attendance : abonnement.getAttendances()) {
                DateTime visitDate = attendance.getVisitDate();
                visitDate = visitDate.toDateTime(DateTimeZone.forID("Europe/Minsk"));
                if (visitDate.isAfter(reportPeriod.getStartDate()) && visitDate.isBefore(reportPeriod.getEndDate())) {
                    String dateKey = formatDateToUserLocale(visitDate);
                    Map<String, Integer> particularDayMap = staticForAttendances.get(dateKey);
                    int hourOfDay = visitDate.getHourOfDay();
                    String hourKey = String.valueOf(hourOfDay);
                    if (hourOfDay < 7 && hourOfDay >= 23) {
                        hourKey = "23";
                    }
                    int countOfAttendancesAtThisHour = particularDayMap.getOrDefault(hourKey, 0);
                    particularDayMap.put(hourKey, countOfAttendancesAtThisHour + 1);
                }
            }
        }
        Set<String> dateKeys = staticForAttendances.keySet();

        if (dateKeys.size() > 0) {
            Map<String, Integer> totalSummary = new HashMap<>();
            for (String dateKey : dateKeys) {
                Map<String, Integer> particularDayMap = staticForAttendances.get(dateKey);
                ReportEntry reportEntry = new ReportEntry();
                reportEntry.addPair(ReportKey.DATE_TIME, dateKey);
                for (int i = 7; i <= 23; i++) {
                    int countOfAttendancesAtThisHour = particularDayMap.getOrDefault(String.valueOf(i), 0);
                    String pairKey = String.valueOf(i);
                    reportEntry.addPair(pairKey, countOfAttendancesAtThisHour);
                    int prevSummaryValue = totalSummary.get(pairKey) != null ? totalSummary.get(pairKey) : 0;
                    totalSummary.put(pairKey, prevSummaryValue + countOfAttendancesAtThisHour);
                }
                report.addEntry(reportEntry);
            }
            ReportSummary reportSummary = new ReportSummary();
            reportSummary.addPair(ReportKey.DATE_TIME, "Итого");
            for (int i = 7; i <= 23; i++) {
                String pairKey = String.valueOf(i);
                reportSummary.addPair(pairKey, totalSummary.get(pairKey));
            }
            report.setReportSummary(reportSummary);
        }

        return report;
    }

    public Report generateReportForBirthday(String companyId, ReportPeriod reportPeriod) {

        Report report = new Report();
        // report period is set from original request to display it on ui/excel according to user time zone
        report.setReportPeriod(reportPeriod);
        report.setReportName("Отчет по Дням Рождения посетителей");

        // birthdays don't depend on time zones so we force UTC to exclude time zone factor in date comparison
        final ReportPeriod reportPeriodUTC = reportPeriod.forceUTC();
        List<ClientData> clients = clientBirthdayFacade.findClientsWithinDates(companyId, reportPeriodUTC.getStartDate(), reportPeriodUTC.getEndDate());
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.addPair(ReportKey.ORDER_NUMBER, "№");
        reportHeader.addPair(ReportKey.INITIALS, "ФИО");
        reportHeader.addPair(ReportKey.BIRTHDAY, "Дата рождения");
        reportHeader.addPair(ReportKey.AGE, "Возраст: года (месяцы)");
        reportHeader.addPair(ReportKey.PHONE, "Номер телефона");
        //reportHeader.addPair(ReportKey.EMAIL, "Email");
        report.setReportHeader(reportHeader);
        int orderNumber = 0;
        for (ClientData client : clients) {
            orderNumber++;
            ReportEntry reportEntry = new ReportEntry();
            reportEntry.addPair(ReportKey.ORDER_NUMBER, orderNumber);
            reportEntry.addPair(ReportKey.INITIALS, client.getLastName() + " " + client.getName());
            final DateTime clientBirthday = client.getBirthday();
            reportEntry.addPair(ReportKey.BIRTHDAY, formatDateToUserLocale(clientBirthday));

            if (clientBirthday != null) {
                LocalDate birthdayDate = LocalDate.of(clientBirthday.getYear(), clientBirthday.getMonthOfYear(),
                        clientBirthday.getDayOfMonth());
                Period period = Period.between(birthdayDate, LocalDate.now());
                reportEntry.addPair(ReportKey.AGE, period.getYears() + " (" + period.getMonths() + ")");
            } else {
                reportEntry.addPair(ReportKey.AGE, "");
            }

            reportEntry.addPair(ReportKey.PHONE, client.getPhone());

            //TODO DM Email ?
            //reportEntry.addPair(ReportKey.EMAIL, "????");
            report.addEntry(reportEntry);

        }
        return report;
    }

    public Report generateReportForSingleAttendance(String companyId, ReportPeriod reportPeriod) {

        Report report = new Report();
        report.setReportPeriod(reportPeriod);
        report.setReportName("Отчет по разовым посещениям");

        List<SingleAttendance> singleAttendances = singleAttendanceService.
                findSingleAttendancesBetween(companyId, reportPeriod.getStartDate(), reportPeriod.getEndDate());

        populateBodyForSingleAttendancesReport(report, reportPeriod, singleAttendances);

        return report;
    }

    private void populateBodyForSingleAttendancesReport(Report report, ReportPeriod reportPeriod, List<SingleAttendance> singleAttendances) {
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.addPair(ReportKey.DATE, "Дата");
        reportHeader.addPair(ReportKey.COUNT_SINGLE_ATTENDANCES, "Количество разовых посещений");
        reportHeader.addPair(ReportKey.SINGLE_ATTENDANCES_COMMON_PRICE, "Общая сумма за разовые посещения");
        report.setReportHeader(reportHeader);

        LinkedHashMap<String, Integer> staticForCount = new LinkedHashMap<>();
        LinkedHashMap<String, Double> staticForCost = new LinkedHashMap<>();
        for (SingleAttendance singleAttendance : singleAttendances) {
            String dateKey = formatDateToUserLocale(singleAttendance.getVisitDate());
            staticForCount.put(dateKey, staticForCount.getOrDefault(dateKey, 0) + 1);
            staticForCost.put(dateKey, staticForCost.getOrDefault(dateKey, (double) 0) + singleAttendance.getPrice());
        }
        Set<String> dateKeys = new LinkedHashSet<>();
        DateTime itDate = reportPeriod.getStartDate();
        do {
            String dateKey = formatDateToUserLocale(itDate);
            itDate = itDate.plusDays(1);
            dateKeys.add(dateKey);
        } while (itDate.isBefore(reportPeriod.getEndDate()));

        if (dateKeys.size() > 0) {
            int totalSold = 0;
            double totalActualPrice = 0;

            for (String dateKey : dateKeys) {
                ReportEntry reportEntry = new ReportEntry();
                reportEntry.addPair(ReportKey.DATE, dateKey);
                reportEntry.addPair(ReportKey.COUNT_SINGLE_ATTENDANCES, staticForCount.getOrDefault(dateKey, 0));
                reportEntry.addPair(ReportKey.SINGLE_ATTENDANCES_COMMON_PRICE, currencyDF.format(staticForCost.getOrDefault(dateKey, (double) 0)));
                report.addEntry(reportEntry);

                totalSold += staticForCount.getOrDefault(dateKey, 0);
                totalActualPrice += staticForCost.getOrDefault(dateKey, (double) 0);
            }
            ReportSummary reportSummary = new ReportSummary();
            reportSummary.addPair(ReportKey.DATE, "Итого");
            reportSummary.addPair(ReportKey.COUNT_SINGLE_ATTENDANCES, totalSold);
            reportSummary.addPair(ReportKey.SINGLE_ATTENDANCES_COMMON_PRICE, currencyDF.format(totalActualPrice));
            report.setReportSummary(reportSummary);
        }
    }

    public Report generateReportForTrainer(String companyId, ReportPeriod reportPeriod, String trainerId, ReportTrainerType type) {

        Report report = new Report();
        report.setReportPeriod(reportPeriod);
        Trainer trainer = trainerService.getTrainer(trainerId);
        report.setReportName(String.format("Отчет по сотруднику (%s): %s", trainer.getName() + " " + trainer.getLastName(), type.getDescription()));

        if (type.equals(ReportTrainerType.BY_SOLD_ABONNEMENTS)) {

            ReportHeader reportHeader = new ReportHeader();
            reportHeader.addPair(ReportKey.ORDER_NUMBER, "№");
            reportHeader.addPair(ReportKey.PRODUCT_NAME, "Наименование абонемента");
            //reportHeader.addPair(ReportKey.INITIALS, "ФИО посетителя");
            reportHeader.addPair(ReportKey.DATE, "Дата");
            //reportHeader.addPair(ReportKey.INITIAL_PRICE, "Полная стоимость");
            reportHeader.addPair(ReportKey.DISCOUNT, "Скидка");
            reportHeader.addPair(ReportKey.PRODUCT_COMMON_PRICE, "Стоимость (со скидкой)");
            report.setReportHeader(reportHeader);

            List<Abonnement> abonnements = trainerStatisticService.
                    findPurchasedAbonnementsForTrainerBetween(companyId, trainerId, reportPeriod.getStartDate(), reportPeriod.getEndDate())
                    .sorted(Comparator.comparing(Abonnement::getPurchaseDate))
                    .collect(Collectors.toList());

            double totalPrice = 0;

            int orderNumber = 0;
            for (Abonnement abonnement : abonnements) {
                orderNumber++;
                ReportEntry reportEntry = new ReportEntry();
                reportEntry.addPair(ReportKey.ORDER_NUMBER, orderNumber);
                reportEntry.addPair(ReportKey.PRODUCT_NAME, abonnement.getProduct().getName());
                //reportEntry.addPair(ReportKey.INITIALS, abonnement.get);
                reportEntry.addPair(ReportKey.DATE, formatDateTimeToUserLocale(abonnement.getPurchaseDate()));
                //reportEntry.addPair(ReportKey.INITIAL_PRICE, abonnement.getProduct().getPrice());
                reportEntry.addPair(ReportKey.DISCOUNT, abonnement.getAppliedPromotionValue());
                reportEntry.addPair(ReportKey.PRODUCT_COMMON_PRICE, abonnement.getProduct().getPrice());

                totalPrice += abonnement.getProduct().getPrice();
                report.addEntry(reportEntry);
            }

            ReportSummary reportSummary = new ReportSummary();
            reportSummary.addPair(ReportKey.ORDER_NUMBER, "");
            reportSummary.addPair(ReportKey.PRODUCT_NAME, "Итого");
            //reportEntry.addPair(ReportKey.INITIALS, abonnement.get);
            reportSummary.addPair(ReportKey.DATE, "-");
            //reportSummary.addPair(ReportKey.INITIAL_PRICE, "-");
            reportSummary.addPair(ReportKey.DISCOUNT, "-");
            reportSummary.addPair(ReportKey.PRODUCT_COMMON_PRICE, totalPrice);
            report.setReportSummary(reportSummary);

        } else if (type.equals(ReportTrainerType.BY_SINGLE_ATTENDANCES)) {

            List<SingleAttendance> singleAttendances = trainerStatisticService.
                    findSingleAttendancesForTrainerBetween(companyId, trainerId, reportPeriod.getStartDate(), reportPeriod.getEndDate())
                    .collect(Collectors.toList());

            populateBodyForSingleAttendancesReport(report, reportPeriod, singleAttendances);

        } else if (type.equals(ReportTrainerType.BY_ATTENDANCES)) {

            ReportHeader reportHeader = new ReportHeader();
            reportHeader.addPair(ReportKey.ORDER_NUMBER, "№");
            reportHeader.addPair(ReportKey.PRODUCT_NAME, "Наименование абонемента");
            reportHeader.addPair(ReportKey.COUNT_ATTENDANCES, "Количество посещений");
            report.setReportHeader(reportHeader);

            List<Abonnement> abonnements = trainerStatisticService.
                    findAbonnementsWithAttendancesForTrainer(companyId, trainerId, reportPeriod.getStartDate(), reportPeriod.getEndDate())
                    .collect(Collectors.toList());

            if (abonnements.size() > 0) {
                LinkedHashMap<String, Integer> staticForCount = new LinkedHashMap<>();
                for (Abonnement abonnement : abonnements) {
                    for (Attendance attendance : abonnement.getAttendances()) {
                        boolean isThisTrainer = trainerId.equals(attendance.getTrainerId());
                        boolean startDateBeforeVisitDate = reportPeriod.getStartDate().isBefore(attendance.getVisitDate());
                        boolean endDateAfterVisitDate = reportPeriod.getEndDate().isAfter(attendance.getVisitDate());
                        if (isThisTrainer && startDateBeforeVisitDate && endDateAfterVisitDate) {
                            staticForCount.put(abonnement.getProductId(), staticForCount.getOrDefault(abonnement.getProductId(), 0) + 1);
                        }
                    }
                }

                int totalCount = 0;
                int orderNumber = 0;

                for (Map.Entry<String, Integer> entry : staticForCount.entrySet()) {
                    orderNumber++;

                    ReportEntry reportEntry = new ReportEntry();
                    Product product = productService.getProduct(entry.getKey());
                    reportEntry.addPair(ReportKey.ORDER_NUMBER, orderNumber);
                    reportEntry.addPair(ReportKey.PRODUCT_NAME, product.getName());
                    reportEntry.addPair(ReportKey.COUNT_ATTENDANCES, entry.getValue());
                    report.addEntry(reportEntry);

                    totalCount += entry.getValue();
                }
                ReportSummary reportSummary = new ReportSummary();
                reportSummary.addPair(ReportKey.ORDER_NUMBER, "");
                reportSummary.addPair(ReportKey.PRODUCT_NAME, "Итого");
                reportSummary.addPair(ReportKey.COUNT_ATTENDANCES, totalCount);
                report.setReportSummary(reportSummary);
            }
        }

        return report;
    }


    public Report generateReportForAllClients(String companyId, ReportPeriod reportPeriod) {
        // todo format date in another place
        Report report = new Report();
        report.setReportPeriod(reportPeriod);
        report.setReportName("Отчет по всем клиентам(без учета даты регистрации)");
        Stream<Client> clients = clientService.
                getAllClientsForCompany(companyId);
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.addPair(ReportKey.ORDER_NUMBER, "№");
        reportHeader.addPair(ReportKey.CARD_UUID, "Номер карты");
        reportHeader.addPair(ReportKey.LAST_NAME, "Фамилия");
        reportHeader.addPair(ReportKey.NAME, "Имя");
        reportHeader.addPair(ReportKey.BIRTHDAY, "Дата Рождения");
        reportHeader.addPair(ReportKey.PHONE, "Номер телефона");
        reportHeader.addPair(ReportKey.EMAIL, "E-mail");
        reportHeader.addPair(ReportKey.INTERNAL_NUMBER, "Внутренний номер");
        reportHeader.addPair(ReportKey.CREATED_DATE, "Дата добавления в систему");
        reportHeader.addPair(ReportKey.LAST_ATTENDANCE_DATE, "Дата последнего посещения");
        report.setReportHeader(reportHeader);
        int orderNumber = 0;
        for (Client client : clients.collect(Collectors.toList())) {
            orderNumber++;
            ReportEntry reportEntry = new ReportEntry();
            reportEntry.addPair(ReportKey.ORDER_NUMBER, orderNumber);
            reportEntry.addPair(ReportKey.LAST_NAME, client.getLastName());
            reportEntry.addPair(ReportKey.NAME, client.getName());
            reportEntry.addPair(ReportKey.CARD_UUID, client.getCardUUID());
            reportEntry.addPair(ReportKey.PHONE, client.getPhone());
            reportEntry.addPair(ReportKey.CREATED_DATE, formatDateToUserLocale(client.getCreatedDate()));
            DateTime lastAttendanceDate = getLastAttendance(client);
            if (lastAttendanceDate != null) {
                reportEntry.addPair(ReportKey.LAST_ATTENDANCE_DATE, formatDateToUserLocale(lastAttendanceDate));
            } else {
                reportEntry.addPair(ReportKey.LAST_ATTENDANCE_DATE, "-");
            }
            DateTime birthday = client.getBirthday();
            if (birthday != null) {
                reportEntry.addPair(ReportKey.BIRTHDAY, formatDateToUserLocale(birthday));
            } else {
                reportEntry.addPair(ReportKey.BIRTHDAY, "-");
            }
            User user = userService.getUser(client.getUserId());
            if (user.getEmail() != null) {
                reportEntry.addPair(ReportKey.EMAIL, user.getEmail());
            } else {
                reportEntry.addPair(ReportKey.EMAIL, "-");
            }
            if (client.getInternalId() != null) {
                reportEntry.addPair(ReportKey.INTERNAL_NUMBER, client.getInternalId());
            } else {
                reportEntry.addPair(ReportKey.INTERNAL_NUMBER, "-");
            }
            report.addEntry(reportEntry);

        }

        return report;

    }
}
