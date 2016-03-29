<%-- 
    Document   : viewCharges
    Created on : Jan 28, 2013, 7:42:32 PM
    Author     : hackmel
--%>


<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
    $(function() {
        $( "#start" ).datepicker();
        $( "#end" ).datepicker();
        $( "#tab" ).tabs();
    });
</script>


<div class="form">

	<div id="tab" class="tab">
		<ul>
			<li><a href="#tabs-1"> Search Bills </a></li>

		</ul>


		<div id="tabs-1" align="center">

			<form action="viewBills.do" method="GET">



				<table>

					<tr>
						<td>Start Date:</td>

						<td><input type="text" name="start" id="start"
							class="text_box" /></td>

					</tr>


					<tr>
						<td>End Date:</td>

						<td><input type="text" name="end" id="end" class="text_box" />
							<input type="submit" name="search" value="Search" class="submit" />

						</td>
					</tr>

				</table>
				<div>&nbsp;</div>

				<table class="tbl">

					<tr>


						<th>Description</th>

						<th>From</th>

						<th>To</th>

					</tr>






					<c:forEach varStatus="status" items="${billList}" var="item">


						<c:choose>
							<c:when test='${(status.index)%2 eq 0}'>
								<tr class="even-rowtd">
							</c:when>

							<c:otherwise>
								<tr class="odd-rowtd">
							</c:otherwise>

						</c:choose>



						<td><a href="viewMonthlyCharges.do?id=${item.id}">${item.description}
						</a></td>

						<td><a href="viewMonthlyCharges.do?id=${item.id}"><fmt:formatDate
									pattern="MMM-dd-yyyy" value="${item.startDate}" /> </a></td>

						<td><a href="viewMonthlyCharges.do?id=${item.id}"><a
								href="editBillForm.do?id=${item.id}"><fmt:formatDate
										pattern="MMM-dd-yyyy" value="${item.endDate}" /> </a></td>
						</tr>
					</c:forEach>







				</table>

			</form>

			<div>&nbsp;</div>

			<div>

				<table>
					<tr>
						<c:if test="${firstPage==false}">
							<th><a
								href="${pageContext.request.contextPath}/paginateBillList.do?page=previous"
								name="previous" class="button">Previous</a></th>
						</c:if>
						<c:if test="${lastPage==false}">
							<th><a
								href="${pageContext.request.contextPath}/paginateBillList.do?page=next"
								name="next" class="button">Next</a></th>
						</c:if>

					</tr>

				</table>





			</div>

		</div>