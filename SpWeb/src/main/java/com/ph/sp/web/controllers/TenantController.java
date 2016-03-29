/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.controllers;

import java.io.IOException;
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
import com.ph.sp.domain.Tenant;
import com.ph.sp.service.inf.ITenantService;
import com.ph.sp.web.forms.TenantForm;
import com.ph.sp.web.forms.TenantFormValidator;
import com.ph.sp.web.forms.ValidationResponse;

/**
 * 
 * @author hackmel
 */
@Controller
public class TenantController {

	@Autowired
	private ITenantService tenantService;
	@Autowired
	private TenantFormValidator tenantFormValidator;

	@RequestMapping(method = RequestMethod.POST, value = "addTenant")
	public @ResponseBody
	ValidationResponse createTenant(HttpServletRequest request,
			@ModelAttribute TenantForm tenantForm, BindingResult result) {
		ValidationResponse validationResponse = new ValidationResponse();

		try {

			String input = request.getParameter("request");

			tenantForm = new ObjectMapper().readValue(input, TenantForm.class);

			tenantFormValidator.validate(tenantForm, result);

			if (result.hasErrors()) {
				validationResponse.setStatus("FIELD_VALIDATION_FAILED");
				validationResponse.setErrorMessageList(result.getFieldErrors());
				return validationResponse;
			}

			if (tenantService.findTenantByName(tenantForm.getLastName(),
					tenantForm.getFirstName()).size() > 0) {
				validationResponse.setStatus("OTHER_VALIDATION_FAILED");
				validationResponse.getModelAttribute().put("error",
						"Last Name and First Name already exist");

				return validationResponse;
			}

			Tenant tenant = new Tenant();
			tenant.setFirstName(tenantForm.getFirstName());
			tenant.setLastName(tenantForm.getLastName());
			tenant.setMiddleName(tenantForm.getMiddleName());
			tenant.setPhoneNo(tenantForm.getPhoneNo());

			tenant.setVisitor(tenantForm.isVisitor());
			tenant.setEmail(tenantForm.getEmail());
			

			tenantService.create(tenant);

			List<Tenant> tenantList = tenantService.getAllTenant();
			validationResponse.getModelAttribute()
					.put("tenantList", tenantList);

			validationResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			Logger.getLogger(TenantController.class.getName()).log(
					Level.SEVERE, null, ex);

		}
		return validationResponse;

	}

	@RequestMapping(method = RequestMethod.POST, value = "deleteTenants")
	public String deleteTenants(Model model, HttpServletRequest request) {

		if (request.getParameterValues("id") == null) {
			model.addAttribute("message",
					"There is no selected record/s to delete.");
		} else {
			String ids[] = request.getParameterValues("id");

			for (String id : ids) {
				tenantService.delete(new Long(id));
			}
			model.addAttribute("message", "Record/s successfully deleted");
		}

		List<Tenant> result = tenantService.getAllTenant();
		PagedListHolder tenantList = new PagedListHolder(result);
		tenantList.setPageSize(12);
		request.getSession().setAttribute("tenantList", tenantList);
		model.addAttribute("tenantList", tenantList.getPageList());
		model.addAttribute("firstPage", tenantList.isFirstPage());
		model.addAttribute("lastPage", tenantList.isLastPage());

		return "viewTenants";

	}

	@RequestMapping("/editTenant")
	public @ResponseBody
	ValidationResponse editTenant(HttpServletRequest request,
			@ModelAttribute TenantForm tenantForm, BindingResult result) {
		ValidationResponse validationResponse = new ValidationResponse();

		try {

			String input = request.getParameter("request");

			tenantForm = new ObjectMapper().readValue(input, TenantForm.class);

			tenantFormValidator.validate(tenantForm, result);

			if (result.hasErrors()) {
				validationResponse.setStatus("FIELD_VALIDATION_FAILED");
				validationResponse.setErrorMessageList(result.getFieldErrors());
				return validationResponse;
			}

			Tenant tenant = new Tenant();

			tenant.setId(tenantForm.getId());
			tenant.setFirstName(tenantForm.getFirstName());
			tenant.setLastName(tenantForm.getLastName());
			tenant.setMiddleName(tenantForm.getMiddleName());
			tenant.setPhoneNo(tenantForm.getPhoneNo());
			tenant.setVisitor(tenantForm.isVisitor());
			tenant.setEmail(tenantForm.getEmail());

			tenantService.edit(tenant);

			List<Tenant> tenantList = tenantService.getAllTenant();
			validationResponse.getModelAttribute()
					.put("tenantList", tenantList);

			validationResponse.setStatus("SUCCESS");

		} catch (IOException ex) {
			Logger.getLogger(TenantController.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return validationResponse;

	}

	@RequestMapping("/displayTenants")
	public String displayTenants(Model model,
			@ModelAttribute TenantForm tenantForm, HttpServletRequest request) {
		String id = request.getParameter("id");

		Tenant tenant = tenantService.findTenant(new Long(id));

		List<Tenant> tenantList = tenantService.getAllTenant();

		model.addAttribute("tenantList", tenantList);

		tenantForm.setId(tenant.getId());
		tenantForm.setFirstName(tenant.getFirstName());
		tenantForm.setLastName(tenant.getLastName());
		tenantForm.setMiddleName(tenant.getMiddleName());
		tenantForm.setPhoneNo(tenant.getPhoneNo());
		tenantForm.setVisitor(String.valueOf(tenant.isVisitor()));
		tenantForm.setEmail(tenant.getEmail());

		return "editTenantForm";

	}

	@RequestMapping(method = RequestMethod.GET, value = "paginateTenantList")
	public String paginateTenantList(Model model, HttpServletRequest request) {

		String page = request.getParameter("page");
		PagedListHolder tenantList = (PagedListHolder) request.getSession()
				.getAttribute("tenantList");
		if (tenantList == null) {

			model.addAttribute("tenantList", new ArrayList<Tenant>());
			return "viewTenants";
		}
		if ("next".equals(page)) {
			tenantList.nextPage();
		} else if ("previous".equals(page)) {
			tenantList.previousPage();
		}
		model.addAttribute("tenantList", tenantList.getPageList());
		model.addAttribute("firstPage", tenantList.isFirstPage());
		model.addAttribute("lastPage", tenantList.isLastPage());

		return "viewTenants";

	}
}
