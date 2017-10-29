package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.WidgetSettingData;
import com.jlife.abon.entity.WidgetSetting;
import com.jlife.abon.facade.WidgetSettingFacade;
import com.jlife.abon.repository.WidgetSettingRepository;
import com.jlife.abon.service.WidgetSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Dzmitry Misiuk
 */
@Component
public class WidgetSettingFacadeImpl extends AbstractFacade implements WidgetSettingFacade {

    @Autowired
    private WidgetSettingService widgetSettingService;

    @Autowired
    private WidgetSettingRepository widgetSettingRepository;


    @Override
    public WidgetSettingData getWidgetSetting(String widgetSettingId) {
        WidgetSetting widgetSetting = widgetSettingService.getWidgetSetting(widgetSettingId);
        return dataMapper.toWidgetSettingData(widgetSetting);
    }

    @Override
    public WidgetSettingData getWidgetSettingForCompany(String companyId) {
        WidgetSetting widgetSetting = widgetSettingService.getWidgetSettingByCompanyId(companyId);
        return dataMapper.toWidgetSettingData(widgetSetting);
    }

    @Override
    public WidgetSettingData create(WidgetSettingData widgetSetting, String companyId) {
        widgetSetting.setCompanyId(companyId);
        WidgetSetting newWidgetSetting = widgetSettingRepository.save(dataMapper.fromWidgetSettingData(widgetSetting));
        return dataMapper.toWidgetSettingData(newWidgetSetting);
    }

    @Override
    public WidgetSettingData update(String widgetSettingId, WidgetSettingData widgetSetting, String companyId) {
        WidgetSetting existedWidgetSetting = widgetSettingService.getWidgetSetting(widgetSettingId);

        existedWidgetSetting.setTitle(widgetSetting.getTitle());
        existedWidgetSetting.setDescription(widgetSetting.getDescription());
        existedWidgetSetting.setShowHeader(widgetSetting.isShowHeader());
        existedWidgetSetting.setLogo(widgetSetting.getLogo());
        existedWidgetSetting.setShowButton(widgetSetting.isShowButton());
        existedWidgetSetting.setPrimaryPalette(widgetSetting.getPrimaryPalette());
        existedWidgetSetting.setButtonText(widgetSetting.getButtonText());
        existedWidgetSetting.setButtonPosition(widgetSetting.getButtonPosition());
        existedWidgetSetting.setButtonColor(widgetSetting.getButtonColor());
        existedWidgetSetting.setButtonAnimation(widgetSetting.isButtonAnimation());
        existedWidgetSetting.setFormPosition(widgetSetting.getFormPosition());
        existedWidgetSetting.setNotificationEmail(widgetSetting.getNotificationEmail());
        existedWidgetSetting.setNewClientEmailNotification(widgetSetting.isNewClientEmailNotification());

        WidgetSetting updatedWidgetSetting = widgetSettingRepository.save(existedWidgetSetting);
        return dataMapper.toWidgetSettingData(updatedWidgetSetting);
    }

}
