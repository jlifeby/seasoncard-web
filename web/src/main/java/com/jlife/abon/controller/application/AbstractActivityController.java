package com.jlife.abon.controller.application;

import com.jlife.abon.dto.ClientActivityData;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.ui.ModelMap;

import java.util.List;

import static com.jlife.abon.facade.CompanyStatisticFacade.MINSK_TIME_ZONE;

/**
 * @author Khralovich Dzmitry
 */
public abstract class AbstractActivityController extends BaseController {

    @NotNull
    protected String getCompanyActivityPage(ModelMap model, String companyId) {
        DateTime startingOfPreviousMonth = new DateTime(MINSK_TIME_ZONE)
                .minusMonths(1)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();

        DateTime endOfPreviousMonth = new DateTime(MINSK_TIME_ZONE)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay()
                .minusMillis(1);

        model.put("company", companyFacade.getCompany(companyId));
        List<ClientActivityData> clientActivities = companyActivityFacade
                .getClientActivities(companyId, startingOfPreviousMonth, endOfPreviousMonth);
        model.put("clientActivities", clientActivities);
        model.put("startDate", startingOfPreviousMonth);
        model.put("endDate", endOfPreviousMonth);
        return "company-activity";
    }

}
