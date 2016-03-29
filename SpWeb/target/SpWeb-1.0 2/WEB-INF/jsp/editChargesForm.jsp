<!DOCTYPE html>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
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
                url: '${pageContext.request.contextPath}/editCharges.do',
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
                        
                        
                        $("#dialog").text("Charges succesfully updated");
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
                        
                        
                        $("#description-error").text("");
                        $("#price-error").text("");
                        
                        
                        
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
                <a href="#tabs-1" > Update Charges </a>
            </li>

        </ul>


        <div id="tabs-1" align="center"> 



            <form:form method="post" action="editCharges.do" modelAttribute="chargesForm">

                <table>
                    <form:hidden path="id"/>
                    <tr>
                        <td><label>Description</label></td>
                        <td><form:input path="description" cssClass="text_box"/></td> 
                        <td><span id="description-error"></span></td> 
                    </tr>

                    <tr>
                        <td colspan="3" > &nbsp;</td>

                    </tr>

                    <tr>
                        <td><label>price</label></td>
                        <td><form:input path="price" cssClass="text_box"/></td>
                        <td><span id="price-error"></span></td> 
                    </tr>

                    <tr>
                        <td colspan="3" > &nbsp;</td>

                    </tr>

                    <tr>
                        <td><label>Sub Charges</label></td>
                        <td><c:choose>
                                <c:when test="${chargesForm.subCharges}">
                                    <input type="checkbox" id="subCharges" name="subCharges" checked="checked" class="text_box"/>
                                </c:when>

                                <c:otherwise>
                                    <input type="checkbox" id="subCharges" name="subCharges" class="text_box"/>
                                </c:otherwise>    
                            </c:choose> 
                        </td>
                        <td><span id="subCharges-error"></span></td> 
                    </tr>

                    <tr>
                        <td colspan="3" > &nbsp;</td>

                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" id="submit" value="Save"class="submit" />
                        </td>
                    </tr>
                </table>  
            </form:form>
        </div>

    </div>

</div>

 <div id="dialog" title="Message">${message}</div>