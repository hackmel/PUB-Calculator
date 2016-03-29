<%-- 
    Document   : addBillCharges
    Created on : Feb 3, 2013, 2:59:26 PM
    Author     : hackmel
--%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


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
        
        if($("#dialog").text()!=""){
            $("#dialog").dialog({
                bgiframe: true,
                autoOpen: true,
                resizable: false,
                minHeight:250,
                width: 400,
                height: 200,  
                modal: true,
                buttons: { "Ok": function() { $(this).dialog("close"); } } 
            });
        }
    });
</script>


<div class="form">

	<div id="tab" class="tab">
		<ul>
			<li><a href="#tabs-1"> Add Charges </a></li>

		</ul>


		<div id="tabs-1" align="center">

			<form action="addBillCharges.do" method="POST">



				<table class="tbl">
					<thead>
						<tr>

							<th>Select</th>
							<th>Charges Description</th>

							<th>Actual Charge Amount</th>

							<th>Charge Type</th>

						</tr>

					</thead>


					<c:set var="index" value="0" scope="page" />

					<c:forEach varStatus="status" items="${chargesList}" var="item">

						<c:choose>
							<c:when test='${(status.index)%2 eq 0}'>
								<tr class="even-rowtd">
							</c:when>

							<c:otherwise>
								<tr class="odd-rowtd">
							</c:otherwise>

						</c:choose>

						<td align="center"><input type="checkbox" name="index"
							value="${index}"> <input type="hidden" name="id"
							value="${item.id}"></td>


						<td>${item.description}</td>


						<td><input type="text" name="actualCharges"
							value="${item.price}" cssClass="text_box"></td>

						<td><c:choose>
								<c:when test='${item.subCharges}'>
                                        Sub-Charge
                                    </c:when>

								<c:otherwise>

								</c:otherwise>

							</c:choose></td>
						</tr>

						<c:set var="index" value="${index + 1}" scope="page" />
					</c:forEach>

				</table>


				<div>&nbsp;</div>
				<div>

					<input type="submit" value="Back" name="back" class="submit" /> <input
						type="submit" value="Add" name="add" class="submit" />

				</div>
		</div>

	</div>
</div>

<div id="dialog" title="Message">${message}</div>
