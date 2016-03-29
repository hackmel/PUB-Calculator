<%-- 
    Document   : viewCharges
    Created on : Jan 28, 2013, 7:42:32 PM
    Author     : hackmel
--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
    $(function() {
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
			<li><a href="#tabs-1"> Search Charges </a></li>

		</ul>


		<div id="tabs-1" align="center">

			<form action="deleteCharges.do" method="POST">



				<table class="tbl">
					<thead>
						<tr>

							<th width="10px">Select</th>
							<th>Charges Description</th>

						</tr>

					</thead>




					<c:forEach varStatus="status" items="${chargesList}" var="item">

						<c:choose>
							<c:when test='${(status.index)%2 eq 0}'>
								<tr class="even-rowtd">
							</c:when>

							<c:otherwise>
								<tr class="odd-rowtd">
							</c:otherwise>

						</c:choose>

						<td align="center"><input type="checkbox" name="id"
							value="${item.id}" cssClass="text_box"></td>

						<td><a href="displayCharges.do?id=${item.id}">${item.description}
						</a></td>
						</tr>
					</c:forEach>


				</table>

				<div>&nbsp;</div>

				<div>


					<table class="">
						<tr>
							<c:if test="${firstPage==false}">
								<th><a
									href="${pageContext.request.contextPath}/paginateChargesList.do?page=previous"
									name="previous" class="button">Previous</a></th>
							</c:if>
							<c:if test="${lastPage==false}">
								<th><a
									href="${pageContext.request.contextPath}/paginateChargesList.do?page=next"
									name="next" class="button">Next</a></th>
							</c:if>

						</tr>

					</table>




				</div>

				<div>&nbsp;</div>

				<div>
					<input type="submit" name="delete" value="Delete" class="submit">
				</div>



			</form>

			<div id="dialog" title="Message">${message}</div>
		</div>