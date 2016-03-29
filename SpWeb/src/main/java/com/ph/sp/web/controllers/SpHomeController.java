/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ph.sp.domain.Charges;
import com.ph.sp.domain.Tenant;
import com.ph.sp.service.inf.IChargesService;
import com.ph.sp.service.inf.ITenantService;
import com.ph.sp.web.forms.BillForm;
import com.ph.sp.web.forms.ChargesForm;

/**
 *
 * @author hackmel
 */
@Controller
public class SpHomeController {

    @Autowired
    private IChargesService chargesService;
    @Autowired
    private ITenantService tenantService;

    @RequestMapping("/index")
    public String indexHandler(Model model,HttpServletResponse response) {
        try {
            
            Logger.getLogger("Here");
            response.sendRedirect("viewBills.do");
            
            
        } catch (IOException ex) {
            Logger.getLogger(SpHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @RequestMapping("/addChargesForm")
    public String addChargesDisplay(@ModelAttribute("chargesForm") ChargesForm chargesForm) {
        chargesForm.setPrice("0.00");
        return "addChargesForm";

    }

    @RequestMapping("/viewCharges")
    public String viewChargesDisplay(Model model,HttpServletRequest request) {
        List<Charges> result = chargesService.getAllCharges();
        
        
        PagedListHolder chargesList = new PagedListHolder(result);
        chargesList.setPageSize(12);
        request.getSession().setAttribute("chargesList", chargesList);
        model.addAttribute("chargesList", chargesList.getPageList());
        model.addAttribute("firstPage", chargesList.isFirstPage());
        model.addAttribute("lastPage", chargesList.isLastPage());
        
       
        return "viewCharges";


    }

    @RequestMapping(value = "/addTenantForm")
    public String addTenantForm(Model model) {

        List<Tenant> tenantList = tenantService.getAllTenant();

        model.addAttribute("tenantList", tenantList);
        return "addTenantForm";

    }

    @RequestMapping("/viewTenants")
    public String viewTenantDisplay(Model model, HttpServletRequest request) {
        List<Tenant> result = tenantService.getAllTenant();

        PagedListHolder tenantList = new PagedListHolder(result);
        tenantList.setPageSize(12);
        request.getSession().setAttribute("tenantList", tenantList);
        model.addAttribute("tenantList", tenantList.getPageList());
        model.addAttribute("firstPage", tenantList.isFirstPage());
        model.addAttribute("lastPage", tenantList.isLastPage());
        

        return "viewTenants";


    }

    @RequestMapping("/addBillForm")
    public String addBillDisplay(@ModelAttribute("billForm") BillForm BillForm) {
        return "addBillForm";

    }
}
