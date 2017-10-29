package com.jlife.abon.facade;

import com.jlife.abon.dto.WidgetSettingData;

/**
 * Copyright © 2017 JLife. All rights reserved.
 *
 * @author Dzmitry Khralovich
 */
public interface WidgetSettingFacade {

    WidgetSettingData getWidgetSetting(String widgetSettingId);

    WidgetSettingData getWidgetSettingForCompany(String companyId);

    WidgetSettingData create(WidgetSettingData widgetSetting, String companyId);

    WidgetSettingData update(String widgetSettingId, WidgetSettingData widgetSetting, String companyId);

}
