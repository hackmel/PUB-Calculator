<%-- 
    Document   : addBillForm
    Created on : Feb 3, 2013, 1:24:41 PM
    Author     : hackmel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

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
			<li><a href="#tabs-1"> Update Bill </a></li>

		</ul>


		<div id="tabs-1" align="center">

			<form:form method="post" action="editBill.do"
				modelAttribute="billForm">
				<table>
					<form:hidden path="id" />
					<tr>

						<td><label>Description</label></td>
						<td><form:input path="description" cssClass="text_box" /></td>
						<td><form:errors path="description" /></td>
					</tr>

					<tr>
						<td colspan="3">&nbsp;</td>

					</tr>

					<tr>
						<td><label>Start Date</label></td>
						<td><form:input path="startDate" cssClass="text_box" /></td>
						<td><form:errors path="endDate" /></td>
					</tr>

					<tr>
						<td colspan="3">&nbsp;</td>

					</tr>

					<tr>
						<td><label>End Date</label></td>
						<td><form:input path="endDate" cssClass="text_box" /></td>
						<td><form:errors path="endDate" /></td>
					</tr>

					<tr>
						<td colspan="3">&nbsp;</td>

					</tr>
					<tr>
						<td colspan="2"><input type="submit" class="submit"
							value="Next" /></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</div>