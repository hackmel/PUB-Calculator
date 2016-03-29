<%-- 
    Document   : monthlyCharges
    Created on : Apr 2, 2013, 6:09:42 PM
    Author     : hackmel
--%>
<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
    $(function() {
        $( "#startDate" ).datepicker();
        $( "#endDate" ).datepicker();
        $( "#tab" ).tabs();
    });
</script>


<div class="form">

	<div id="tab" class="tab">
		<ul>
			<li><a href="#tabs-1"> Bill Information </a></li>

		</ul>


		<div id="tabs-1" align="center">

			<table border="0" width="100%">



				<tr>
					<td>Description:</td>

					<td colspan="3">${bill.description}</td>




				</tr>


				<tr>

					<td>Duration</td>

					<td><fmt:formatDate pattern="MMM-dd-yyyy"
							value="${bill.startDate}" /> To <fmt:formatDate
							pattern="MMM-dd-yyyy" value="${bill.endDate}" /></td>

					<td>Days Covered:</td>

					<td>${bill.numberOfDays}</td>



				</tr>

				<tr>
					<td>Total PUB:</td>

					<td>${bill.totalCharges}</td>

					<td>Sub-Charges:</td>

					<td>${bill.totalSubCharges}</td>
				<tr>
					<td>PUB/Day</td>

					<td>${bill.totalCharges/bill.numberOfDays }</td>
					
					<td>Total</td>
					<td>${bill.totalCharges+bill.totalSubCharges}</td>

				</tr>
				</tr>




			</table>
			<br />
			<hr>



			&nbsp;

			<table class="tbl">
				<thead>
					<tr>

						<th>Tenant</th>
						<th>No Of Days</th>
                        <th>Manual-Charges</th>
						<th>PUB</th>
						<th>Sub-Charges</th>
						<th>Total</th>
					</tr>

				</thead>



				<c:forEach items="${tenantChrgList}" var="item">
					<tr>
						<td>${item.mainTenantCharges.tenant.firstName}</td>

						<td>${item.mainTenantCharges.noOfDaysStayed}</td>

                        <td>${item.manualCharges}</td>
                        
						<td>${item.totalPub}</td>

						<td>${item.mainTenantCharges.charges}</td>

						<td>${item.totalPub + item.mainTenantCharges.charges + item.manualCharges}</td>
					</tr>


					<c:choose>
						<c:when test="${fn:length(item.visitorCharges) gt 0}">

							<tr>
								<td>&nbsp;</td>
								<td colspan="5">

									<table class="tbl-2">

										<tr>
											<th>Visitor</th>

											<th>Days Stayed</th>
											
											 <th>Manual-Charges</th>

											<th>Pub</th>

										</tr>


										<c:forEach items="${item.visitorCharges}" var="visitorItem">
											<tr>
												<td>${visitorItem.tenant.firstName}</td>

												<td>${visitorItem.noOfDaysStayed}</td>

                                                <td>${visitorItem.manualCharges}</td>
                                                
												<td>${visitorItem.pub}</td>

											</tr>

										</c:forEach>
										<tr>
											<td colspan="4"><hr></td>

										</tr>
										<tr>
											<td colspan="4">&nbsp;</td>

										</tr>
									</table>


								</td>



							</tr>

						</c:when>


					</c:choose>




				</c:forEach>




			</table>

			<hr>
			<div>&nbsp</div>

			<table>
				<tr>
					<th><a href="editBillForm.do" class="submit"> Edit Bill</a></th>

					<th><a href="javascript:window.print();" class="submit">
							Print</a></th>
				</tr>
			</table>

		</div>
	</div>