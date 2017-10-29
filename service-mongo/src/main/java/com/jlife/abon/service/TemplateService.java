package com.jlife.abon.service;

import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
public interface TemplateService {
    
    String createFromTemplate(String name, Map<String, Object> map);
}
