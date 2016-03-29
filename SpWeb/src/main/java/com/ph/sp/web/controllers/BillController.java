/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ph.sp.web.controllers;

import com.ph.sp.domain.Bill;
import com.ph.sp.domain.BillCharges;
import com.ph.sp.domain.Charges;
import com.ph.sp.domain.Tenant;
import com.ph.sp.domain.TenantBillDuration;
import com.ph.sp.domain.TenantCharges;

import com.ph.sp.service.inf.IBillTransactionService;
import com.ph.sp.service.inf.IChargesService;
import com.ph.sp.service.inf.ITenantService;
import com.ph.sp.web.forms.BillForm;
import com.ph.sp.web.forms.BillFormValidator;
import com.ph.sp.web.forms.TenantBillDurationForm;
import com.ph.sp.web.forms.TenantBillFormValidator;
import com.ph.sp.web.forms.TenantChargesListDisplay;
import com.ph.sp.web.forms.TenantPresence;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author hackmel
 */
@Controller
public class BillController {

	@Autowired
	private IBillTransactionService billTransactionService;
	@Autowired
	private IChargesService chargesService;
	@Autowired
	private ITenantService tenantService;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM/dd/yyyy");
	@Autowired
	private BillFormValidator billFormValidator;
	@Autowired
	private TenantBillFormValidator tenantBillFormValidator;

	@RequestMapping(method = RequestMethod.POST, value = "addBill")
	public String createBill(@ModelAttribute BillForm billForm,
			BindingResult result, HttpServletRequest request) {
		try {

			billFormValidator.validate(billForm, result);

			if (result.hasErrors()) {
				return "addBillForm";
			}

			Bill bill = new Bill();
			bill.setDescription(billForm.getDescription());
			bill.setStartDate(dateFormat.parse(billForm.getStartDate()));
			bill.setEndDate(dateFormat.parse(billForm.getEndDate()));

			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(bill.getStartDate());
			c2.setTime(bill.getEndDate());

			Long diff = (c2.getTimeInMillis() - c1.getTimeInMillis())
					/ (60 * 60 * 24 * 1000);

			bill.setNumberOfDays(diff + 1);

			billTransactionService.createBill(bill);

			request.getSession().setAttribute("BILL", bill);

		} catch (ParseException ex) {
			Logger.getLogger(BillController.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return "viewBillCharges";

	}

	@RequestMapping(method = RequestMethod.POST, value = "editBill")
	public String editBill(@ModelAttribute BillForm billForm,
			BindingResult result, Model model, HttpServletRequest request) {
		try {

			billFormValidator.validate(billForm, result);

			if (result.hasErrors()) {
				return "editBillForm";
			}

			Bill bill = billTransactionService.findBill(billForm.getId());
			bill.setDescription(billForm.getDescription());
			bill.setStartDate(dateFormat.parse(billForm.getStartDate()));
			bill.setEndDate(dateFormat.parse(billForm.getEndDate()));

			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(bill.getStartDate());
			c2.setTime(bill.getEndDate());

			Long diff = (c2.getTimeInMillis() - c1.getTimeInMillis())
					/ (60 * 60 * 24 * 1000);
			bill.setNumberOfDays(diff + 1);

			billTransactionService.editBill(bill);

			List<BillCharges> billChargesList = billTransactionService
					.getAllBillChargesByBillId(bill.getId());
			model.addAttribute("billChargesList", billChargesList);

			request.getSession().setAttribute("BILL", bill);

		} catch (ParseException ex) {
			Logger.getLogger(BillController.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return "viewBillCharges";

	}

	@RequestMapping(method = RequestMethod.GET, value = "editBillForm")
	public String displayBill(@ModelAttribute BillForm billForm,
			HttpServletRequest request) {

		Bill bill = null;

		if (request.getParameter("id") != null) {
			bill = billTransactionService.findBill(new Long(request
					.getParameter("id")));
			request.getSession().setAttribute("BILL", bill);

		} else {

			bill = (Bill) request.getSession().getAttribute("BILL");
		}

		billForm.setId(bill.getId());
		billForm.setDescription(bill.getDescription());
		billForm.setStartDate(dateFormat.format(bill.getStartDate()));
		billForm.setEndDate(dateFormat.format(bill.getEndDate()));

		return "editBillForm";

	}

	@RequestMapping(method = RequestMethod.GET, value = "displayBillCharges")
	public String displayBillCharges(Model model, HttpServletRequest request) {
		Bill bill = (Bill) request.getSession().getAttribute("BILL");

		List<BillCharges> billChargesList = billTransactionService
				.getAllBillChargesByBillId(bill.getId());
		model.addAttribute("billChargesList", billChargesList);

		return "viewBillCharges";

	}

	@RequestMapping(method = RequestMethod.POST, value = "addBillCharges")
	public String addBillCharges(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Bill bill = (Bill) request.getSession().getAttribute("BILL");
		List<BillCharges> billChargesList = new ArrayList<BillCharges>();

		if (request.getParameter("add") != null) {

			if (request.getParameterValues("index") == null) {
				model.addAttribute("message",
						"There is no selected record/s to delete.");
				billChargesList = billTransactionService
						.getAllBillChargesByBillId(bill.getId());
				model.addAttribute("billChargesList", billChargesList);
			} else {

				String index[] = request.getParameterValues("index");
				String ids[] = request.getParameterValues("id");
				String actualCharges[] = request
						.getParameterValues("actualCharges");

				BigDecimal totalCharges = new BigDecimal("0.00");
				BigDecimal totalSubChargesCharges = new BigDecimal("0.00");

				for (String i : index) {
					String id = ids[new Integer(i)];
					String actualCharge = actualCharges[new Integer(i)];

					Charges charges = chargesService.findCharges(new Long(id));

					BillCharges billCharges = new BillCharges();

					try {

						billCharges.setActualCharges(new BigDecimal(
								actualCharge));
						billCharges.setBill(bill);
						billCharges.setCharges(charges);

						billChargesList.add(billCharges);

					} catch (NumberFormatException e) {
						model.addAttribute("message",
								"Invalid number format has been encountered.");
						billChargesList = billTransactionService
								.getAllBillChargesByBillId(bill.getId());
						model.addAttribute("billChargesList", billChargesList);
						return "viewBillCharges";
					}

				}

				billTransactionService.createBillChages(billChargesList);

				billChargesList = billTransactionService
						.getAllBillChargesByBillId(bill.getId());

				for (BillCharges b : billChargesList) {
					if (b.getCharges().isSubCharges()) {
						totalSubChargesCharges = totalSubChargesCharges.add(b
								.getActualCharges());
					} else {
						totalCharges = totalCharges.add(b.getActualCharges());
					}
				}

				bill.setTotalCharges(totalCharges);
				bill.setTotalSubCharges(totalSubChargesCharges);

				billTransactionService.editBill(bill);

				model.addAttribute("billChargesList", billChargesList);
			}

		} else {
			try {
				response.sendRedirect("displayBillCharges.do");
				return null;
			} catch (IOException ex) {
				Logger.getLogger(BillController.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}

		return "viewBillCharges";

	}

	public void deleteBillCharges(HttpServletRequest request) {
		String ids[] = request.getParameterValues("id");
		List<Long> billChargesIdList = new ArrayList<Long>();

		Bill bill = (Bill) request.getSession().getAttribute("BILL");
		List<BillCharges> billChargesList = new ArrayList<BillCharges>();

		BigDecimal totalCharges = new BigDecimal("0.00");
		BigDecimal totalSubChargesCharges = new BigDecimal("0.00");

		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			billChargesIdList.add(new Long(id));

		}

		billTransactionService.deleteBillChages(billChargesIdList);

		billChargesList = billTransactionService.getAllBillChargesByBillId(bill
				.getId());

		for (BillCharges b : billChargesList) {
			if (b.getCharges().isSubCharges()) {
				totalSubChargesCharges = totalSubChargesCharges.add(b
						.getActualCharges());
			} else {
				totalCharges = totalCharges.add(b.getActualCharges());
			}
		}

		bill.setTotalCharges(totalCharges);
		bill.setTotalSubCharges(totalSubChargesCharges);

		billTransactionService.editBill(bill);

	}

	@RequestMapping(method = RequestMethod.GET, value = "submitBillChargesForm")
	public String submitBillChargesForm(Model model,
			HttpServletRequest request, HttpServletResponse response) {

		String page = "";

		if (request.getParameter("add") != null) {
			List<Charges> chargesList = chargesService.getAllCharges();
			model.addAttribute("chargesList", chargesList);
			page = "addBillCharges";
		} else if (request.getParameter("delete") != null) {

			if (request.getParameterValues("id") == null) {
				model.addAttribute("message",
						"There is no selected record/s to delete.");
			} else {
				deleteBillCharges(request);
				model.addAttribute("message", "Record/s succesfully deleted");
			}

			Bill bill = (Bill) request.getSession().getAttribute("BILL");

			List<BillCharges> billChargesList = billTransactionService
					.getAllBillChargesByBillId(bill.getId());
			model.addAttribute("billChargesList", billChargesList);

			page = "viewBillCharges";

		} else if (request.getParameter("back") != null) {
			try {
				response.sendRedirect("editBillForm.do");
				return null;
			} catch (IOException ex) {
				Logger.getLogger(BillController.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		} else if (request.getParameter("next") != null) {
			try {
				response.sendRedirect("viewTenantCharges.do");
				return null;
			} catch (IOException ex) {
				Logger.getLogger(BillController.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}

		return page;

	}

	@RequestMapping(method = RequestMethod.GET, value = "viewTenantCharges")
	public String viewTenantCharges(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Bill bill = (Bill) request.getSession().getAttribute("BILL");

		Set<Tenant> tenantList = new HashSet<Tenant>();

		List<TenantCharges> tenantChargesList = billTransactionService
				.viewTenantCharges(bill.getId());

		model.addAttribute("tenantList", tenantChargesList);

		return "viewTenantCharges";

	}

	@RequestMapping(method = RequestMethod.GET, value = "calculateMonthlyCharges")
	public String calculateMonthlyCharges(Model model,
			HttpServletRequest request, HttpServletResponse response) {

		if (request.getParameter("id") != null) {

			String billId = request.getParameter("id");
			Bill billParam = billTransactionService.findBill(new Long(billId));

			request.getSession().setAttribute("BILL", billParam);

		}

		Bill bill = (Bill) request.getSession().getAttribute("BILL");

		Set<TenantChargesListDisplay> tenantChrgList = new HashSet<TenantChargesListDisplay>();

		List<TenantCharges> tenantChargesList = billTransactionService
				.viewTenantCharges(bill.getId());

		for (TenantCharges tCharges : tenantChargesList) {
			tCharges.setPub(new BigDecimal(0));
			billTransactionService.editTenantBill(tCharges);
		}

		billTransactionService.consolidate(bill);

		tenantChargesList = billTransactionService.viewTenantCharges(bill
				.getId());

		List<TenantCharges> cloneTenantChargesList = new ArrayList(
				tenantChargesList);

		for (TenantCharges t : cloneTenantChargesList) {
			if (!t.getTenant().isVisitor()) {
				List<TenantCharges> vistorChargesList = billTransactionService
						.getAllTenantVisitorChargesByBillIdAndTenantId(t);
				TenantChargesListDisplay tenantChargesListDisplay = new TenantChargesListDisplay();

				tenantChargesListDisplay.setMainTenantCharges(t);
				tenantChargesListDisplay.setVisitorCharges(vistorChargesList);
				tenantChargesListDisplay.setTotalPub(t.getPub());
				tenantChargesListDisplay.setManualCharges(t.getManualCharges());

				for (TenantCharges v : vistorChargesList) {

					if (tenantChargesListDisplay.getTotalPub() != null) {

						if (v.getPub() != null) {
							tenantChargesListDisplay
									.setTotalPub(tenantChargesListDisplay
											.getTotalPub().add(v.getPub()));
						}

						if (v.getManualCharges() != null
								&& v.getManualCharges().doubleValue() > 0) {
							tenantChargesListDisplay
									.setTotalPub(tenantChargesListDisplay
											.getTotalPub().add(
													v.getManualCharges()));
						}

					} else {
						if (v.getPub() != null) {
							tenantChargesListDisplay.setTotalPub(v.getPub());
						}

						if (v.getManualCharges() != null
								&& v.getManualCharges().doubleValue() > 0) {
							tenantChargesListDisplay
									.setTotalPub(tenantChargesListDisplay
											.getTotalPub().add(
													v.getManualCharges()));
						}
					}

				}

				BigDecimal totalPub = new BigDecimal(tenantChargesListDisplay
						.getTotalPub().setScale(4, RoundingMode.HALF_EVEN)
						.toString());

				tenantChargesListDisplay.setTotalPub(new BigDecimal(totalPub
						.toString()));
				tenantChrgList.add(tenantChargesListDisplay);

			}

		}

		model.addAttribute("bill", bill);
		model.addAttribute("tenantChrgList", tenantChrgList);

		return "monthlyCharges";

	}

	@RequestMapping(method = RequestMethod.GET, value = "viewMonthlyCharges")
	public String viewMonthlyCharges(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (request.getParameter("id") != null) {

			String billId = request.getParameter("id");
			Bill billParam = billTransactionService.findBill(new Long(billId));

			request.getSession().setAttribute("BILL", billParam);

		}

		Bill bill = (Bill) request.getSession().getAttribute("BILL");

		Set<TenantChargesListDisplay> tenantChrgList = new HashSet<TenantChargesListDisplay>();

		List<TenantCharges> tenantChargesList = billTransactionService
				.viewTenantCharges(bill.getId());

		List<TenantCharges> cloneTenantChargesList = new ArrayList<TenantCharges>(
				tenantChargesList);

		for (TenantCharges t : cloneTenantChargesList) {
			if (t.getVisitorOf() == null) {
				List<TenantCharges> vistorChargesList = billTransactionService
						.getAllTenantVisitorChargesByBillIdAndTenantId(t);
				TenantChargesListDisplay tenantChargesListDisplay = new TenantChargesListDisplay();

				tenantChargesListDisplay.setMainTenantCharges(t);
				tenantChargesListDisplay.setVisitorCharges(vistorChargesList);
				tenantChargesListDisplay.setTotalPub(t.getPub());
				tenantChargesListDisplay.setManualCharges(t.getManualCharges());
				for (TenantCharges v : vistorChargesList) {

					if (tenantChargesListDisplay.getTotalPub() != null) {

						if (v.getPub() != null) {
							tenantChargesListDisplay
									.setTotalPub(tenantChargesListDisplay
											.getTotalPub().add(v.getPub()));
						}

						if (v.getManualCharges() != null
								&& v.getManualCharges().doubleValue() > 0) {
							tenantChargesListDisplay
									.setTotalPub(tenantChargesListDisplay
											.getTotalPub().add(
													v.getManualCharges()));
						}

					} else {
						if (v.getPub() != null) {
							tenantChargesListDisplay.setTotalPub(v.getPub());
						}

						if (v.getManualCharges() != null
								&& v.getManualCharges().doubleValue() > 0) {
							tenantChargesListDisplay
									.setTotalPub(tenantChargesListDisplay
											.getTotalPub().add(
													v.getManualCharges()));
						}
					}

				}

				BigDecimal totalPub = new BigDecimal(tenantChargesListDisplay
						.getTotalPub().setScale(4, RoundingMode.HALF_EVEN)
						.toString());

				tenantChargesListDisplay.setTotalPub(new BigDecimal(totalPub
						.toString()));
				tenantChrgList.add(tenantChargesListDisplay);

			}

		}

		model.addAttribute("bill", bill);
		model.addAttribute("tenantChrgList", tenantChrgList);

		return "monthlyCharges";

	}

	@RequestMapping(method = RequestMethod.GET, value = "processTenantCharges")
	public String processTenantCharges(
			@ModelAttribute TenantBillDurationForm tenantBillDurationForm,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String page = "";
		Bill bill = (Bill) request.getSession().getAttribute("BILL");
		List<Tenant> tenantList = new ArrayList<Tenant>();

		try {

			if (request.getParameter("addTenant") != null) {
				tenantList = tenantService.getAllTenant();

				model.addAttribute("tenantList", tenantList);

				page = "addTenantBillForm";
			} else if (request.getParameter("deleteTenant") != null) {

				if (request.getParameterValues("id") == null) {
					model.addAttribute("message",
							"There is no selected record/s to delete.");

				} else {
					String[] ids = request.getParameterValues("id");
					for (String id : ids) {
						
						TenantCharges tenantCharges= billTransactionService.findTenantCharges(new Long(id));
						List<TenantBillDuration> tenantBillDurations= billTransactionService.findTenantBillDuration(tenantCharges.getTenant().getId(), bill.getId());
						
						for(TenantBillDuration tenantBillDuration: tenantBillDurations){
							billTransactionService.deleteTenantBillDuration(tenantBillDuration.getId());
						}
						
						billTransactionService.deleteTenantBill(new Long(id));
			
					}
					model.addAttribute("message",
							"Record/s successfully deleted");
				}

				return viewTenantCharges(model, request, response);
			} else if (request.getParameter("back") != null) {

				response.sendRedirect("displayBillCharges.do");
				return null;

			} else if (request.getParameter("next") != null) {

				response.sendRedirect("calculateMonthlyCharges.do");

				return null;
			}

		} catch (IOException ex) {
			Logger.getLogger(BillController.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return page;
	}

	@RequestMapping(method = RequestMethod.POST, value = "processTenant")
	public String processTenant(
			@ModelAttribute TenantBillDurationForm tenantBillDurationForm,
			BindingResult result, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("Bill Controller");
		String page = "";
		Bill bill = (Bill) request.getSession().getAttribute("BILL");

		try {
			if (request.getParameter("add") != null) {

				if (tenantBillDurationForm.getCharges() == null
						|| tenantBillDurationForm.getCharges().trim()
								.equals("")) {
					tenantBillDurationForm.setCharges("0.00");

				}

				List<Tenant> tenantList = tenantService.getAllTenant();

				if (tenantBillDurationForm.getTenantId() == null) {
					model.addAttribute("errorMsg",
							"Please select a tenant from the list");
					model.addAttribute("tenantList", tenantList);

					return "addTenantBillForm";

				} else {

					Long selectedItemsCount = new Long(0);

					if (request.getParameterValues("id") != null) {
						selectedItemsCount = new Long(
								request.getParameterValues("id").length);
					}

					TenantCharges tenantCharges = new TenantCharges();
					Tenant tenant = tenantService
							.findTenant(tenantBillDurationForm.getTenantId());

					if (billTransactionService.findTenantCharges(
							tenant.getId(), bill.getId()) == null) {
						tenantCharges.setTenant(tenant);
						tenantCharges.setBill(bill);

						if (selectedItemsCount == 0
								&& (tenantBillDurationForm.getCharges() == null
										|| tenantBillDurationForm.getCharges()
												.equals("0.00") || tenantBillDurationForm
										.getCharges().equals(""))) {

							model.addAttribute("errorMsg",
									"Please select atleast 1 date or enter the charges manually");
							model.addAttribute("tenantList", tenantList);
							displayDurationOfTenant(model,
									tenantBillDurationForm,
									String.valueOf(tenantBillDurationForm
											.getTenantId()), bill);

							return "addTenantBillForm";

						} else {

							tenantCharges.setNoOfDaysStayed(selectedItemsCount);
							if (tenant.isVisitor()) {

								if (tenantBillDurationForm.getVisitorOf() != null) {

									Tenant visitorOf = tenantService
											.findTenant(tenantBillDurationForm
													.getVisitorOf());

									if (visitorOf != null) {
										tenantCharges.setVisitorOf(visitorOf);
									} else {
										model.addAttribute("errorMsg",
												"Please select from [vistor of:] dropdown");
										model.addAttribute("tenantList",
												tenantList);
										displayDurationOfTenant(
												model,
												tenantBillDurationForm,
												String.valueOf(tenantBillDurationForm
														.getTenantId()), bill);

										return "addTenantBillForm";

									}

								} else {
									model.addAttribute("errorMsg",
											"Please select from [vistor of:] dropdown");
									model.addAttribute("tenantList", tenantList);
									displayDurationOfTenant(
											model,
											tenantBillDurationForm,
											String.valueOf(tenantBillDurationForm
													.getTenantId()), bill);
									return "addTenantBillForm";

								}

							}

						}

						try {
							tenantCharges.setManualCharges(new BigDecimal(
									tenantBillDurationForm.getCharges()));
						} catch (NumberFormatException e) {
							model.addAttribute("errorMsg",
									"Invalid number entered, please try again");
							model.addAttribute("tenantList", tenantList);
							displayDurationOfTenant(model,
									tenantBillDurationForm,
									String.valueOf(tenantBillDurationForm
											.getTenantId()), bill);

							return "addTenantBillForm";

						}

						billTransactionService.createTenantBill(tenantCharges);

					} else {
						tenantCharges = billTransactionService
								.findTenantCharges(tenant.getId(), bill.getId());

						if (selectedItemsCount == 0
								&& (tenantBillDurationForm.getCharges() == null
										|| tenantBillDurationForm.getCharges()
												.equals("0.00") || tenantBillDurationForm
										.getCharges().equals(""))) {

							model.addAttribute("errorMsg",
									"Please select atleast 1 date or enter the charges manually");
							model.addAttribute("tenantList", tenantList);
							displayDurationOfTenant(model,
									tenantBillDurationForm,
									String.valueOf(tenantBillDurationForm
											.getTenantId()), bill);

							return "addTenantBillForm";

						} else {
							tenantCharges.setNoOfDaysStayed(selectedItemsCount);
							if (tenant.isVisitor()) {
								if (tenantBillDurationForm.getVisitorOf() != null) {

									Tenant visitorOf = tenantService
											.findTenant(tenantBillDurationForm
													.getVisitorOf());

									if (visitorOf != null) {
										tenantCharges.setVisitorOf(visitorOf);
									} else {
										model.addAttribute("errorMsg",
												"Please select from vistor of dropdown");
										model.addAttribute("tenantList",
												tenantList);
										displayDurationOfTenant(
												model,
												tenantBillDurationForm,
												String.valueOf(tenantBillDurationForm
														.getTenantId()), bill);
										return "addTenantBillForm";

									}

								} else {
									model.addAttribute("errorMsg",
											"Please select from vistor of dropdown");
									model.addAttribute("tenantList", tenantList);
									displayDurationOfTenant(
											model,
											tenantBillDurationForm,
											String.valueOf(tenantBillDurationForm
													.getTenantId()), bill);
									return "addTenantBillForm";

								}

							}

						}

						try {
							tenantCharges.setManualCharges(new BigDecimal(
									tenantBillDurationForm.getCharges()));
						} catch (NumberFormatException e) {
							model.addAttribute("errorMsg",
									"Invalid number entered, please try again");
							model.addAttribute("tenantList", tenantList);
							displayDurationOfTenant(model,
									tenantBillDurationForm,
									String.valueOf(tenantBillDurationForm
											.getTenantId()), bill);

							return "addTenantBillForm";

						}

						billTransactionService.editTenantBill(tenantCharges);
					}

					System.out
							.println("Bill Controller: request.getParameterValues(id) != null==>"
									+ request.getParameterValues("id") != null);

					if (request.getParameterValues("id") != null) {

						List<TenantBillDuration> billDurations = billTransactionService
								.findTenantBillDuration(tenant.getId(),
										bill.getId());
						for (TenantBillDuration tBillDuration : billDurations) {
							billTransactionService
									.deleteTenantBillDuration(tBillDuration
											.getId());
						}

						if (!tenant.isVisitor()) {
							String[] dateValues = request
									.getParameterValues("id");
							Calendar billDate = Calendar.getInstance();
							billDate.setTime(bill.getStartDate());

							Map selectedDates = new HashMap<Date, Date>();
							for (String date : dateValues) {
								selectedDates.put(dateFormat.parse(date),
										dateFormat.parse(date));

								System.out.println(selectedDates
										.containsKey(dateFormat.parse(date)));
							}

							System.out
									.println("Bill Controller: bill.getNumberOfDays() != null==>"
											+ bill.getNumberOfDays());

							for (int i = 0; i < bill.getNumberOfDays(); i++) {

								System.out
										.println(billDate.getTime() + "==A==");

								System.out
										.println("selectedDates.containsKey(billDate.getTime())"
												+ selectedDates
														.containsKey(billDate
																.getTime()));
								if (!selectedDates.containsKey(billDate
										.getTime())) {
									TenantBillDuration t = new TenantBillDuration();

									t.setBill(bill);
									t.setTenant(tenant);
									t.setPresent(false);

									t.setDate(billDate.getTime());

									System.out.println(billDate.getTime());
									billTransactionService
											.createTenantBillDuration(t);
								}

								billDate.add(Calendar.DATE, 1);
							}

						} else {
							String[] dateValues = request
									.getParameterValues("id");
							for (String date : dateValues) {

								TenantBillDuration t = new TenantBillDuration();

								t.setBill(bill);
								t.setTenant(tenant);
								t.setPresent(true);

								t.setDate(dateFormat.parse(date));

								billTransactionService
										.createTenantBillDuration(t);

							}
						}

					}

					List<TenantBillDuration> tenantBillDurationList = billTransactionService
							.findTenantBillDuration(tenant.getId(),
									bill.getId());
					model.addAttribute("tenantBillDurationList",
							tenantBillDurationList);

					model.addAttribute("tenantList", tenantList);

					tenantBillDurationForm.setTenantId(tenant.getId());

					displayDurationOfTenant(model, tenantBillDurationForm,
							String.valueOf(tenant.getId()), bill);

					response.sendRedirect("viewTenantCharges.do");
					return null;

				}

			} else if (request.getParameter("back") != null) {
				try {
					response.sendRedirect("viewTenantCharges.do");
					return null;
				} catch (IOException ex) {
					Logger.getLogger(BillController.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			} else if (request.getParameter("next") != null) {
			} else if (request.getParameter("displayTenantDuration") != null) {
				if (request.getParameter("tenantId") == null
						|| request.getParameter("tenantId").equals("NONE")) {
					model.addAttribute("tenantList",
							tenantService.getAllTenant());
				} else {
					String tenantId = request.getParameter("tenantId");
					displayDurationOfTenant(model, tenantBillDurationForm,
							tenantId, bill);
				}

				page = "addTenantBillForm";

			}

		} catch (IOException ex) {
			Logger.getLogger(BillController.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (ParseException ex) {
			Logger.getLogger(BillController.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return page;
	}

	private void displayDurationOfTenant(Model model,
			TenantBillDurationForm tenantBillDurationForm, String tenantId,
			Bill bill) {
		Map<Date, TenantBillDuration> durationMap = new HashMap<Date, TenantBillDuration>();
		List<TenantPresence> dateList = new ArrayList<TenantPresence>();

		Tenant tenant = tenantService.findTenant(tenantBillDurationForm
				.getTenantId());

		List<Tenant> tenantList = tenantService.getAllTenant();

		List<TenantBillDuration> billdurationList = billTransactionService
				.findTenantBillDuration(new Long(tenantId),
						new Long(bill.getId()));

		for (TenantBillDuration duration : billdurationList) {
			durationMap.put(duration.getDate(), duration);
		}

		Calendar startDate = Calendar.getInstance();

		startDate.setTime(bill.getStartDate());

		for (int i = 0; i < bill.getNumberOfDays(); i++) {

			TenantPresence tenantPresence = new TenantPresence();

			tenantPresence.setBill(bill);
			tenantPresence.setDate(startDate.getTime());
			tenantPresence.setTenant(tenant);

			if (durationMap.containsKey(startDate.getTime())) {
				tenantPresence.setChecked(true);
			} else {
				tenantPresence.setChecked(false);
			}

			dateList.add(tenantPresence);
			startDate.add(Calendar.DATE, 1);
		}

		tenantBillDurationForm.setTenantId(new Long(tenantId));
		model.addAttribute("tenantList", tenantList);
		model.addAttribute("dateList", dateList);
		model.addAttribute("isVisitor", tenant.isVisitor());
	}

	@RequestMapping(method = RequestMethod.GET, value = "viewBills")
	public String viewBills(@ModelAttribute BillForm billForm,
			BindingResult result, HttpServletRequest request, Model model) {
		if (request.getParameter("start") != null
				&& request.getParameter("end") != null) {
			try {
				Date start = dateFormat.parse(request.getParameter("start"));
				Date end = dateFormat.parse(request.getParameter("end"));

				List<Bill> bills = billTransactionService.findBillStartDate(
						start, end);

				PagedListHolder billList = new PagedListHolder(bills);
				billList.setPageSize(12);
				request.getSession().setAttribute("billList", billList);
				model.addAttribute("billList", billList.getPageList());
				model.addAttribute("firstPage", billList.isFirstPage());
				model.addAttribute("lastPage", billList.isLastPage());

			} catch (ParseException ex) {
				Logger.getLogger(BillController.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		} else {
			model.addAttribute("billList", new ArrayList<Bill>());
		}
		return "viewBills";

	}

	@RequestMapping(method = RequestMethod.GET, value = "paginateBillList")
	public String paginateBillList(Model model, HttpServletRequest request) {

		String page = request.getParameter("page");
		PagedListHolder billList = (PagedListHolder) request.getSession()
				.getAttribute("billList");
		if (billList == null) {

			model.addAttribute("billList", new ArrayList<Bill>());
			return "viewBills";
		}
		if ("next".equals(page)) {
			billList.nextPage();
		} else if ("previous".equals(page)) {
			billList.previousPage();
		}
		model.addAttribute("billList", billList.getPageList());
		model.addAttribute("firstPage", billList.isFirstPage());
		model.addAttribute("lastPage", billList.isLastPage());

		return "viewBills";

	}
}
