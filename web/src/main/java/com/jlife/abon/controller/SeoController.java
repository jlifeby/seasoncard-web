package com.jlife.abon.controller;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.utility.XmlUrl;
import com.jlife.abon.utility.XmlUrlSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This controller is used for configuring search engine indexing
 *
 * @author Dzmitry Misiuk
 * @author Khralovich Dzmitry
 */
@Controller
public class SeoController extends BaseController {

    private enum RobotsTxt {
        ALLOW("User-agent: *\n" +
                "Disallow: /api/\n" +
                "Disallow: /company/\n" +
                "Disallow: /admin/\n" +
                "Disallow: /user/\n" +
                "Host:"),
        DISALLOW("User-agent: *\n" +
                "Disallow: /\n" +
                "Host:");

        private String body;

        RobotsTxt(String body) {
            this.body = body;
        }

        public String getBody() {
            return body;
        }
    }

    @Value("${site.allow.index}")
    private boolean isAllowedToIndex;

    private final static Logger logger = LoggerFactory.getLogger(SeoController.class);

    @RequestMapping(value = "/robots.txt", method = RequestMethod.GET)
    public void getRobots(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        response.setContentType("text/plain");
        try {
            out = response.getWriter();
            RobotsTxt robotsTxt = isAllowedToIndex ? RobotsTxt.ALLOW : RobotsTxt.DISALLOW;
            out.write(robotsTxt.getBody());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

    @RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET)
    @ResponseBody
    public XmlUrlSet main() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        create(xmlUrlSet, "", XmlUrl.Priority.HIGH);
        create(xmlUrlSet, "/products", XmlUrl.Priority.HIGH);
        create(xmlUrlSet, "/about", XmlUrl.Priority.HIGH);
        create(xmlUrlSet, "/features", XmlUrl.Priority.HIGH);
        // for loop to generate all the links by querying against database
        return xmlUrlSet;
    }

    private void create(XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority) {
        xmlUrlSet.addUrl(new XmlUrl("https://seasoncard.by" + link, priority));
    }

}
