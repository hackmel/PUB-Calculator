<%-- 
    Document   : addTenant
    Created on : Jan 29, 2013, 8:08:41 PM
    Author     : hackmel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />

<script>
            
    $(document).ready(function() {
                
                
        if($("#visitor").is(":checked")){
            $("#visitorOf").removeAttr('disabled'); 
        }else{
            $("#visitorOf").attr("disabled", "disabled");
        }
                
        $("#visitor").change(function(e) {
            if(!this.checked) {
                $("#visitorOf").val("Please select..");
                $("#visitorOf").attr("disabled", "disabled");
            }else{
                $("#visitorOf").val("Please select..");
                $("#visitorOf").removeAttr('disabled'); 
            }
                    
                    
        });
                
                
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
                url: '${pageContext.request.contextPath}/addTenant.do',
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
                        $("#dialog").dialog();
                       
                    }else{
                        
                        var result= response.modelAttribute['tenantList'];
                        
                        
                        var options = $("#visitorOf");
                        
                        options.empty();
                      
                        options.append($("<option />").val("").text("Please select.."));
                        $.each(result, function() {
                            options.append($("<option />").val(this.id).text(this.firstName));
                        });
                        
                        
                        
                        
                        
                        
                        $("#dialog").text("Tenant succesfully created");
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
                        
                        $("#firstName").val("");
                        $("#lastName").val("");
                        $("#middleName").val("");
                        $("#email").val("");
                        $("#phoneNo").val("");
                        $("#visitor").attr('checked', false);
                        $("#visitorOf").val("Please select..");
                        $("#visitorOf").attr("disabled", "disabled");
                        
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
            <li>
                <a href="#tabs-1" > Create Tenant </a>
            </li>

        </ul>


        <div id="tabs-1"> 

            <form method="post">

                <div>

                    &nbsp;&nbsp;

                </div>

                <div align="center">


                    <table>
                        <tr>
                            <td align="right"><label>First Name:</label></td>
                            <td><input type="text" id="firstName" name="firstName" class="text_box" type="text"/></td> 
                            <td><span id="firstName-error"></span></td> 

                        </tr>
                        <tr>
                            <td colspan="3" > &nbsp;</td>

                        </tr>
                        <tr>
                            <td align="right"><label>Last Name:</label></td>
                            <td><input type="text" id="lastName" name="lastName" class="text_box"/></td> 
                            <td><span id="lastName-error">&nbsp;</span></td>

                        </tr>

                        <tr>
                            <td colspan="3" > &nbsp;</td>

                        </tr>

                        <tr>
                            <td align="right"><label>Middle Name:</label></td>
                            <td><input type="text" id="middleName" name="middleName" class="text_box"/></td> 
                            <td><span>&nbsp;</span></td>

                        </tr>

                        <tr>
                            <td colspan="3" > &nbsp;</td>

                        </tr>

                        <tr>
                            <td align="right"><label>Phone#:</label></td>
                            <td><input type="text" id="phoneNo" name="phoneNo" class="text_box"/></td> 
                            <td><span>&nbsp;</span></td>

                        </tr>

                        <tr>
                            <td colspan="3" > &nbsp;</td>

                        </tr>

                        <tr>
                            <td align="right"><label>Email:</label></td>
                            <td><input type="text" id="email" name="email" class="text_box"/></td> 
                            <td><span>&nbsp;</span></td>

                        </tr>
                        <tr>
                            <td colspan="3" > &nbsp;</td>

                        </tr>
                        <tr>
                            <td align="right"><label>Visitor:</label></td>
                            <td><input type="checkbox" name="visitor" id="visitor" class="text_box"/></td> 
                            <td><span>&nbsp;</span></td>

                        </tr>
                        <tr>
                            <td colspan="3" > &nbsp;</td>

                        </tr>
                        <tr>

                            <td align="right"><label>Visitor of:</label></td>
                            <td><select name="visitorOf" id="visitorOf" class="text_box">
                                    <option value="Please select.." >Please select.. </option>
                                    <c:forEach items="${tenantList}" var="item">
                                        <option value="${item.id}" > ${item.firstName} </option>

                                    </c:forEach>
                                </select></td> 
                            <td><span>&nbsp;</span></td>

                        </tr>
                        <tr>
                            <td colspan="3" > &nbsp;</td>

                        </tr>
                        <tr>
                            <td colspan="3">
                                <input type="submit" class="submit" id="submit" value="Save"/>
                            </td>
                        </tr>
                    </table>

                </div>

            </form>

        </div>

    </div>

    <div>

        &nbsp;&nbsp;

    </div>
    <div id="dialog" title="Message"></div>

</div>

