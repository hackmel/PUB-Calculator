<%-- 
    Document   : addTenant
    Created on : Jan 29, 2013, 8:08:41 PM
    Author     : hackmel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />

<script>
            
    $(document).ready(function() {
                
                
       
        
        $( "#tab" ).tabs();
        
        
        
        $("#submit").click(function(e) {
            
            
             
            
            var formData=$("form").serialize( );
           
           
            var fVal=formData.split("\&");
            
           
            
            var json="{"
            
            for(j=0;j<fVal.length;j++){
                
                
                var field=fVal[j].split("\=");
                
                
                json=json +"\"" + field[0] + "\"" +":" +"\"" + field[1] + "\",";
                
                
                
            }
            
            json=json.substring(0,json.length-1);
            
            json=json +"}";
            
            
            
          
            $.ajax({
                url: '${pageContext.request.contextPath}/editTenant.do',
                type: 'POST',
                data: 'request=' + json,
                success: function (response) {
                    
                   
                    
                    if(response.status=='FIELD_VALIDATION_FAILED'){
                        for (var i = 0; i < response.errorMessageList.length; i++) {
                            var item = response.errorMessageList[i];
                            $("#" +item.field +"-error").text(item.defaultMessage);
                       
                        
                        }
                    }else if(response.status=='OTHER_VALIDATION_FAILED'){
                        
                        
                        var result= response.modelAttribute['error'];
                        $("#dialog").text(result);
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
                       
                    }else{
                        
                        
                       /* 
                        var result= response.modelAttribute['tenantList'];
                        
                      
                        var ovalue=$("#visitorOf").val();
                        var otext=$("#visitorOf option:selected").text();
                        var options = $("#visitorOf");
                        options.empty();
                      
                        options.append($("<option />").val(ovalue).text(otext));
                        $.each(result, function() {
                            options.append($("<option />").val(this.id).text(this.firstName));
                        });
                        
                        */
                        
                        
                        
                        
                        $("#dialog").text("Tenant succesfully updated");
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
                        
                        
                        $("#firstName-error").text("");
                        $("#lastName-error").text("");
                        
                        
                        
                    }
                    
                   
                    
                      
                   
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('An error has occured!! :-(' + jqXHR)
                    alert('An error has occured!! :-(' + textStatus)
                    alert('An error has occured!! :-(' + errorThrown)
                }
            })
 
            return false
                    
        });
               
    });
            
</script>

<div class="form">
	<div id="tab" class="tab">
		<ul>
			<li><a href="#tabs-1"> Update Tenant </a></li>

		</ul>


		<div id="tabs-1">

			<div align="center">
				<form:form method="post" modelAttribute="tenantForm">

					<div>&nbsp;&nbsp;</div>

					<table>
						<form:hidden path="id" />

						<tr>
							<td align="right"><label>First Name:</label></td>
							<td><form:input path="firstName" cssClass="text_box" /></td>
							<td><span id="firstName-error"></span></td>
						</tr>

						<tr>
							<td colspan="3">&nbsp;</td>

						</tr>

						<tr>
							<td align="right"><label>Last Name:</label></td>
							<td><form:input path="lastName" cssClass="text_box" /></td>
							<td><span id="lastName-error"></span></td>
						</tr>

						<tr>
							<td colspan="3">&nbsp;</td>

						</tr>

						<tr>
							<td align="right"><label>Middle Name:</label></td>
							<td><form:input path="middleName" cssClass="text_box" /></td>
							<td><span></span></td>
						</tr>
						<tr>
							<td colspan="3">&nbsp;</td>

						</tr>
						<tr>
							<td align="right"><label>Phone#:</label></td>
							<td><form:input path="phoneNo" cssClass="text_box" /></td>
							<td><span></span></td>
						</tr>

						<tr>
							<td colspan="3">&nbsp;</td>

						</tr>


						<tr>
							<td align="right"><label>Email:</label></td>
							<td><form:input path="email" cssClass="text_box" /></td>
							<td><span></span></td>
						</tr>


						<tr>
							<td colspan="3">&nbsp;</td>

						</tr>

						<tr>

							<td align="right"><label>Visitor:</label></td>
							<td><c:choose>
									<c:when test="${tenantForm.visitor}">
										<input type="checkbox" name="visitor" id="visitor"
											checked="checked" class="text_box" />
									</c:when>

									<c:otherwise>
										<input type="checkbox" name="visitor" id="visitor"
											class="text_box" />
									</c:otherwise>
								</c:choose></td>
							<td><span></span></td>
						</tr>

						<tr>
							<td colspan="3">&nbsp;</td>

						</tr>

						

						<tr>
							<td colspan="3">&nbsp;</td>

						</tr>

						<tr>
							<td colspan="2"><input type="submit" value="Save"
								id="submit" class="submit" /></td>
						</tr>
					</table>
				</form:form>

			</div>

		</div>

		<div id="dialog" title="Message"></div>
	</div>