<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
            <title><tiles:insertAttribute name="title" ignore="true" /></title>
            
    </head>
    
    <center>
        <body>
       
            
            <table width="50%">
            <tr>
                <td  colspan="2" class="header">
                    <tiles:insertAttribute name="header" />
                </td>
            </tr>
            <tr>
                <td class="menu">
                    <tiles:insertAttribute name="menu" />
                </td>
                <td class="body" >
                    <tiles:insertAttribute name="body" />
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td colspan="2" class="footer">
                    <tiles:insertAttribute name="footer" />
                </td>
            </tr>
        </table>
                
                
                  
                
    </body>
    </center>
   
</html>
