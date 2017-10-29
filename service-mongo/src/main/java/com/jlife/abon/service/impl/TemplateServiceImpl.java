package com.jlife.abon.service.impl;

import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.ws.ServiceMode;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
@ServiceMode
public class TemplateServiceImpl implements TemplateService {


    @Value("${app.url}")
    private String appUrl;

    @Value("${image.path}")
    private String imagePath;

    private Configuration cfg;

    public TemplateServiceImpl() throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_21);
        cfg.setDefaultEncoding("UTF-8");
        String templateDirPath = null;
        try {
            templateDirPath = getClass().getClassLoader().getResource("templates").toURI().getSchemeSpecificPart();
        } catch (URISyntaxException e) {
            throw new AbonRuntimeException(ApiErrorCode.GENERAL_CODE, "Error initialize freemarker templates dir");
        }
        cfg.setDirectoryForTemplateLoading(new File(templateDirPath));
    }

    @Override
    public String createFromTemplate(String name, Map<String, Object> map) {
        try {
            Template template = cfg.getTemplate("email_" + name + ".ftl");
            Writer out = new StringWriter();

            map.put("site", appUrl);
            map.put("appUrl", appUrl);
            map.put("images_path", appUrl + imagePath);

            template.process(map, out);

            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
