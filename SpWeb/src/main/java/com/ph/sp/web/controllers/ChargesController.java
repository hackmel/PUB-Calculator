/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ph.sp.domain.Charges;
import com.ph.sp.service.inf.IChargesService;
import com.ph.sp.web.forms.ChargesForm;
import com.ph.sp.web.forms.ChargesFormValidator;
import com.ph.sp.web.forms.ValidationResponse;

/**
 *
 * @author hackmel
 */
@Controller
public class ChargesController {

    @Autowired
    private IChargesService chargesService;
    @Autowired
    private ChargesFormValidator chargesFormValidator;

    @RequestMapping(method = RequestMethod.POST, value = "addCharges")
    public @ResponseBody
    ValidationResponse createCharges(HttpServletRequest request, @ModelAttribute ChargesForm chargesForm, BindingResult result, Model model) {
        ValidationResponse validationResponse = new ValidationResponse();

        try {


            String input = request.getParameter("request");

            chargesForm = new ObjectMapper().readValue(input, ChargesForm.class);


            chargesFormValidator.validate(chargesForm, result);

            if (result.hasErrors()) {
                validationResponse.setStatus("FIELD_VALIDATION_FAILED");
                validationResponse.setErrorMessageList(result.getFieldErrors());
                return validationResponse;
            }





            if (chargesService.findChargesByDescription(chargesForm.getDescription()).size() > 0) {
                validationResponse.setStatus("OTHER_VALIDATION_FAILED");
                validationResponse.getModelAttribute().put("error", "Record description already exist");

                return validationResponse;

            }

            Charges charges = new Charges();
            charges.setDescription(chargesForm.getDescription());
            charges.setPrice(new BigDecimal(chargesForm.getPrice()));
            charges.setSubCharges(chargesForm.isSubCharges());

            chargesService.create(charges);



            validationResponse.setStatus("SUCCESS");



        } catch (IOException ex) {
            Logger.getLogger(ChargesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validationResponse;


    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteCharges")
    public String deleteCharges(Model model, HttpServletRequest request) {

        if (request.getParameterValues("id") == null) {
            model.addAttribute("message", "There is no selected record/s to delete.");
        } else {
            String ids[] = request.getParameterValues("id");

            for (String id : ids) {

                Long chargeId = new Long(id);
                chargesService.delete(chargeId);
            }
            
            model.addAttribute("message", "Record/s successfully deleted");

        }


        List<Charges> result = chargesService.getAllCharges();
        PagedListHolder chargesList = new PagedListHolder(result);
        chargesList.setPageSize(12);
        request.getSession().setAttribute("chargesList", chargesList);
        model.addAttribute("chargesList", chargesList.getPageList());
        model.addAttribute("firstPage", chargesList.isFirstPage());
        model.addAttribute("lastPage", chargesList.isLastPage());

        

        return "viewCharges";


    }

    @RequestMapping("/displayCharges")
    public String displayCharges(@ModelAttribute ChargesForm chargesForm, HttpServletRequest request) {
        String id = request.getParameter("id");

        Charges charges = chargesService.findCharges(new Long(id));
        chargesForm.setId(charges.getId());
        chargesForm.setDescription(charges.getDescription());
        chargesForm.setPrice(String.valueOf(charges.getPrice()));
        chargesForm.setSubCharges(String.valueOf(charges.isSubCharges()));
        return "editChargesForm";


    }

    @RequestMapping("/editCharges")
    public @ResponseBody
    ValidationResponse editCharges(HttpServletRequest request, @ModelAttribute ChargesForm chargesForm, BindingResult result, Model model) {
        ValidationResponse validationResponse = new ValidationResponse();

        try {
            String input = request.getParameter("request");


            chargesForm = new ObjectMapper().readValue(input, ChargesForm.class);


            chargesFormValidator.validate(chargesForm, result);

            if (result.hasErrors()) {
                validationResponse.setStatus("FIELD_VALIDATION_FAILED");
                validationResponse.setErrorMessageList(result.getFieldErrors());
                return validationResponse;
            }


            Charges charges = new Charges();
            charges.setId(chargesForm.getId());
            charges.setDescription(chargesForm.getDescription());
            charges.setPrice(new BigDecimal(chargesForm.getPrice()));
            charges.setSubCharges(chargesForm.isSubCharges());

            chargesService.edit(charges);

            validationResponse.setStatus("SUCCESS");

            return validationResponse;
        } catch (IOException ex) {
            Logger.getLogger(ChargesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return validationResponse;

    }

    @RequestMapping(method = RequestMethod.GET, value = "paginateChargesList")
    public String paginateChargesList(Model model, HttpServletRequest request) {

        String page = request.getParameter("page");
        PagedListHolder chargesList = (PagedListHolder) request.getSession().getAttribute("chargesList");
        if (chargesList == null) {

            model.addAttribute("chargesList", new ArrayList<Charges>());
            return "viewCharges";
        }
        if ("next".equals(page)) {
            chargesList.nextPage();
        } else if ("previous".equals(page)) {
            chargesList.previousPage();
        }
        model.addAttribute("chargesList", chargesList.getPageList());
        model.addAttribute("firstPage", chargesList.isFirstPage());
        model.addAttribute("lastPage", chargesList.isLastPage());
        return "viewCharges";

    }
}
