package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.ClientGroupData;
import com.jlife.abon.facade.ClientGroupFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 * @author Aliaksei Pashkouski
 */
@Controller
@RequestMapping("/api")
public class ClientGroupRestController extends BaseController {

    @Autowired
    ClientGroupFacade clientGroupFacade;

    @RequestMapping(value = {"/company/clientGroups", "/company/clientgroups"}, method = RequestMethod.GET)
    @ResponseBody
    public List<ClientGroupData> getClientGroups() {
        return clientGroupFacade.getClientsGroupsByCompany(getSessionCompanyId());
    }

    @RequestMapping(value = {"/company/clientGroups/byProduct/{productId}", "/company/clientgroups/by-product/{productId}"},
            method = RequestMethod.GET)
    @ResponseBody
    public ClientGroupData getClientGroupByProductId(@PathVariable("productId") String productId) {
        return clientGroupFacade.getClientGroupByCompanyAndProduct(getSessionCompanyId(), productId);
    }
}
