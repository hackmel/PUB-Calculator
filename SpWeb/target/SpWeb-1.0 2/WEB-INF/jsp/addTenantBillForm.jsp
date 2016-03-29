<%-- 
    Document   : addTenantChargesForm
    Created on : Feb 10, 2013, 4:22:12 PM
    Author     : hackmel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
            
    function displayDuration(){
        document.forms[0].action="processTenant.do?displayTenantDuration=1";
        document.forms[0].submit();
                
    }
            
    $(function() {
        $( "#startDate" ).datepicker();
        $( "#endDate" ).datepicker();
        $( "#tab" ).tabs();
        
        
         
        $( "#selectall" ).change(function(){
            var selected=$(":checkbox");
             
             
            if($( "#selectall" ).is(":checked")){
                 
                $.each(selected, function() {
                     
                    this.checked=true;
                            
                });
            }else{
                $.each(selected, function() {
                    this.checked=false;   
                });
            }
        });
        
      
    });
</script>


<div id="tab" class="tab">
    <ul>
        <li>
            <a href="#tabs-1" > Select Tenant </a>
        </li>

    </ul>


    <div id="tabs-1" align="center">

        <div>&nbsp;</div>
        <div>&nbsp;</div>
        <form:form action="processTenant.do" modelAttribute="tenantBillDurationForm" method="POST">



            <div>
                <table>
                    <tr>

                        <td>

                            Select Tenant:

                        </td>
                        <td>

                            <form:select path="tenantId" onchange="displayDuration();">
                                <form:option value="NONE" label="--- Select ---"/>
                                <form:options items="${tenantList}"  itemValue="id" itemLabel="firstName" />
                            </form:select> 

                        </td>

                    </tr>

                    <tr>


                        <td colspan="2"> 
                            &nbsp;
                        </td>
                    </tr>
                    <tr>


                        <td colspan="2"> 
                            &nbsp;
                            &nbsp;
                            &nbsp;
                            <c:if test="${errorMsg==null}">
                                Please choose the date when the selected tenant is present
                            </c:if>

                            <c:if test="${errorMsg!=null}">
                                ${errorMsg}
                            </c:if>


                        </td>
                    </tr>

                </table>  
            </div>

            <div>&nbsp;</div>
            <div>
                <table class="tbl">

                    <thead>
                        <tr>
                            <th>
                                <input type="checkbox" id="selectall" />
                                Select
                            </th>

                            <th>
                                Date
                            </th>


                        </tr>

                    </thead>

                    <c:forEach varStatus="status" items="${dateList}" var="item">


                        <c:choose>
                            <c:when test='${(status.index)%2 eq 0}'>
                                <tr class="even-rowtd">

                                </c:when>

                                <c:otherwise>
                                <tr class="odd-rowtd">
                                </c:otherwise>

                            </c:choose>
                            <td>

                                <c:choose >
                                    <c:when test="${item.checked}">
                                        <input type="checkbox" value=<fmt:formatDate pattern="MM/dd/yyyy" 
                                                        value="${item.date}" /> name="id" checked >
                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" value=<fmt:formatDate pattern="MM/dd/yyyy" 
                                                        value="${item.date}" /> name="id" >
                                    </c:otherwise>

                                </c:choose>


                            </td>

                            <td>
                                <fmt:formatDate pattern="dd-MMM-yyyy" 
                                                value="${item.date}" />  


                            </td>


                        </tr>
                    </c:forEach>


                </table>

                <div>&nbsp;</div>


                <div> 
                    <input type="submit" name="back" value="Back"class="submit"/>
                    <input type="submit" name="add" value="Add Tenant"class="submit"/>
                </div>

            </form:form> 



        </div>




    </div>


