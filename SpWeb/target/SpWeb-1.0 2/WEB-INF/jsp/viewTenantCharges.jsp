<%-- 
    Document   : viewCharges
    Created on : Jan 28, 2013, 7:42:32 PM
    Author     : hackmel
--%>


<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
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
            <li>
                <a href="#tabs-1" > Select Tenant </a>
            </li>

        </ul>


        <div id="tabs-1" align="center"> 

            <form action="processTenantCharges.do">



                <table class="tbl">
                    <thead>
                        <tr>

                            <th>
                                Select

                            </th>
                            <th>
                                Last Name

                            </th>

                            <th>
                                First Name

                            </th>


                            <th>
                                No of days Stayed

                            </th>

                        </tr>

                    </thead>




                    <c:forEach varStatus="status" items="${tenantList}" var="item">

                        <c:choose>
                            <c:when test='${(status.index)%2 eq 0}'>
                                <tr class="even-rowtd">

                                </c:when>

                                <c:otherwise>
                                <tr class="odd-rowtd">
                                </c:otherwise>

                            </c:choose>
                            <td>
                                <input type="checkbox" value="${item.id}" name="id" cssClass="text_box">
                            </td>

                            <td>
                                ${item.tenant.lastName}
                            </td>

                            <td>
                                ${item.tenant.firstName} 
                            </td>
                            <td>
                                ${item.noOfDaysStayed}
                            </td>
                        </tr>
                    </c:forEach>




                    

                </table>
                
                
                <div>
                    &nbsp;
                </div>   
                
                <div>
                    <table>
                        <tr>

                        <td colspan="2">
                            <input  type="submit" value="Back" name="back" class="submit">
                        </td>
                        <td>
                            <input  type="submit" value="Add Tenant" name="addTenant" class="submit">
                        </td>
                        <td>
                            <input  type="submit" value="Delete Tenant" name="deleteTenant" class="submit">
                        </td>
                        <td>
                            <input  type="submit" value="Next" name="next" class="submit">
                        </td>


                    </tr>

                        
                    </table>
                    
                </div>

            </form>

        </div>
    </div>
</div>

 <div id="dialog" title="Message">${message}</div>