package com.jlife.abon.service;

import com.jlife.abon.entity.WidgetSetting;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.WidgetSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dzmitry Khralovich
 */
@Service
public class WidgetSettingService extends AbstractService implements EntityAbstractService<WidgetSetting> {

    @Autowired
    private WidgetSettingRepository widgetSettingRepository;

    public WidgetSetting getWidgetSetting(String widgetSettingId) {
        WidgetSetting widgetSetting = widgetSettingRepository.findOne(widgetSettingId);
        if (widgetSetting == null) {
            throw new ResourceNotFoundException(ApiErrorCode.WIDGET_SETTING_NOT_FOUND, widgetSettingId);
        }
        return widgetSetting;
    }

    public WidgetSetting getWidgetSettingByCompanyId(String companyId) {
        return widgetSettingRepository.findByCompanyId(companyId);
    }

}
