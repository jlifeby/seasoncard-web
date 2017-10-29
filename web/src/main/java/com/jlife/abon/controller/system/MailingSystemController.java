//package com.jlife.abon.controller.system;
//
//import com.jlife.abon.controller.application.BaseController;
//import com.jlife.abon.repository.EventRepository;
//import com.jlife.abon.service.EventService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * @author Khralovich Dzmitry
// */
//@Controller
//@RequestMapping("/system")
//// todo security
//public class MailingSystemController extends BaseController {
//
//    private final static Logger logger = LoggerFactory.getLogger(MailingSystemController.class);
//
//    @Autowired
//    public EventRepository eventRepository;
//
//    @Autowired
//    public EventService eventService;
//
//    @RequestMapping(value = "/mailing", method = RequestMethod.GET)
//    public String getMailing(ModelMap model) {
//        return "system-mailing";
//    }
//
//    @RequestMapping(value = "/mailing/{mailId}", method = RequestMethod.GET)
//    public String getMail(@PathVariable("mailId") String mailId, ModelMap model) {
//
//        model.put("events", eventService.getActualEvents(0, 100));
//
//        if ("new".equals(mailId)){
//            model.put("mail", "system-mail");
//        } else {
//            model.put("mail", "system-mail");
//        }
//        return "system-mail";
//    }
//
//
//}
