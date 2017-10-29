package com.jlife.abon.repository;

import com.jlife.abon.entity.WidgetSetting;

/**
 * @author Dzmitry Khralovich
 */
public interface WidgetSettingRepository extends EntityRepository<WidgetSetting> {

    WidgetSetting findByCompanyId(String companyId);

}
