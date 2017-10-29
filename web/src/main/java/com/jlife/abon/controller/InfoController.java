package com.jlife.abon.controller;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.enumeration.Role;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author Khralovich Dzmitry
 */
@Controller
public class InfoController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("classpath:docs/card_guide.pdf")
    private Resource templateCardGuide;

    @Value("classpath:docs/seasoncard_public_offer.docx")
    private Resource templateOffer;

    @RequestMapping("/card-design")
    public String getCardDesign(ModelMap model) {
        return "card-design";
    }

    @RequestMapping("/card-design/card_guide.pdf")
    public void printCardDesign(HttpServletResponse response) {
        response.addHeader("Content-Disposition", "inline");
        response.setContentType("application/pdf");
        try {
            OutputStream outStream = response.getOutputStream();
            IOUtils.copy(templateCardGuide.getInputStream(), outStream );
//            outStream.write(outArray);
//            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/user-agreement")
    public String getUserAgreement(ModelMap model) {
        return "user-agreement";
    }

    @RequestMapping(value = "/public-offer.pdf", method = RequestMethod.GET)
    public void getCompanyOffer(HttpServletResponse response) {
        try {
            //String fileName = "seasoncard_public_offer.pdf";
            //response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";inline");
            response.addHeader("Content-Disposition", "inline");
            response.setContentType("application/pdf");

            XWPFDocument workbook = new XWPFDocument(templateOffer.getInputStream());

            String contractID;
            String contractDate;
            String pattern = "«dd» MMMM yyyy г.";

            if(hasRole(Role.ROLE_COMPANY)){
                CompanyData company = getApplication().getCurrentCompany();
                contractID = String.valueOf(company.getContractId());
                DateTime date;
                if(company.getContractDate() != null){
                    date = company.getContractDate();
                } else {
                    date = new DateTime(DateTimeZone.forID("Europe/Minsk"));
                }
                contractDate = date.toString(pattern, new Locale("ru", "RU"));
            } else {
                contractID = " ___";
                contractDate = "«   » ______________ 2016 г.";
            }
            for (XWPFParagraph p : workbook.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("ContractIdValue")) {
                            text = text.replace("ContractIdValue", contractID);
                            r.setText(text, 0);
                        }
                        if (text != null && text.contains("ContractDateValue")) {
                            text = text.replace("ContractDateValue", contractDate);
                            r.setText(text, 0);
                        }
                    }
                }
            }
            PdfOptions options = PdfOptions.create();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(workbook, byteArrayOutputStream, options);
            workbook.write(byteArrayOutputStream);
            byte[] outArray = byteArrayOutputStream.toByteArray();
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasRole(Role role) {
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role.name());
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }
}
