<%-- 
    Document   : addBillCharges
    Created on : Feb 3, 2013, 2:59:26 PM
    Author     : hackmel
--%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
                <a href="#tabs-1" > Select Charges </a>
            </li>

        </ul>


        <div id="tabs-1" align="center"> 

            <form action="submitBillChargesForm.do">



                <table class="tbl">
                    <thead>
                        <tr>

                            <th>
                               Select

                            </th>
                            <th>
                                Charges Description

                            </th>
                            <th colspan="2">
                                Actual Charge amount

                            </th>
                        </tr>

                    </thead>




                    <c:forEach varStatus="status" items="${billChargesList}" var="item">
                        <c:choose>
                            <c:when test='${(status.index)%2 eq 0}'>
                                <tr class="even-rowtd">

                             </c:when>

                             <c:otherwise>
                                <tr class="odd-rowtd">
                             </c:otherwise>

                          </c:choose>
                            <td>
                                <input type="checkbox" value="${item.id}" name="id" class="text_box">
                            </td>

                            <td>
                                ${item.charges.description}
                            </td>

                            <td colspan="2">
                                ${item.actualCharges}
                            </td>
                        </tr>
                    </c:forEach>



                </table>
                <div>
                    &nbsp;

                </div>

                <div align="center">
                    <table>
                        <tr>

                            <td>
                                <input  type="submit" value="Back" name="back" class="submit">
                            </td>

                            <td>
                                <input  type="submit" value="Delete" name="delete" class="submit">
                            </td>

                            <td>
                                <input  type="submit" value="Add Charges" name="add"class="submit">
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
