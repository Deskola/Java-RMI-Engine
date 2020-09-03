<%-- 
    Document   : home
    Created on : Sep 1, 2020, 8:29:39 PM
    Author     : #USER
--%>

<%@page import="java.util.List"%>
<%@page import="Vegi.VegiClass"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vegetable Service Engine</title>
        <script type="text/javascript">
            var counter = 1;
            var limit = 10;
            function addInput(divName){
                if(counter === limit){
                    alert("You have reached the limit of adding"+ counter+"input");
                }else{
                    var newdiv = document.createElement('div');
                    newdiv.innerHTML = "<tr><td><select name='veginame[]'>\n\
                    <option value='Kale'>Kale</option>\n\
                    <option value='Cabbage'>Cabbage</option>\n\
                    <option value='Spinach'>Spinach</option>\n\
                    <option value='Cucumber'>Cucumber</option>\n\
                    <option value='Carrot'>Carrot</option>\n\
                    <option value='Broccoli'>Broccoli</option>\n\
                    <option value='Asparagus'>Asparagus</option>\n\
                    <option value='Chokos'>Chokos</option>\n\
                    <option value='Celery'>Celery</option>\n\
                    </select></td><td><input type='text' name='quantity[]' placeholder='Quantity'></td>\n\
                     <td><input type='button' value='(+)' onclick='addInput('dynamic')'></td><tr>";
                    document.getElementById(divName).appendChild(newdiv);
                    counter++; 
                }
            }
        </script>
    </head>
    <body>
        <h1>Vegetable Service</h1>
        <h3 style="color: red">
             <%
            if(request.getParameter("msg") != null){
                if(request.getParameter("msg").equals("insert")){
                    out.println("Inserted Successfully");
                }
                else if(request.getParameter("msg").equals("update")){
                   out.println("Update Successfully"); 
                }
                else if(request.getParameter("msg").equals("delete")){
                    out.println("Deleted Successsfully");
                }
            }
             %>
        </h3>
        <h3>
            Employee name: <%= request.getAttribute("username")%>
        </h3>
        <hr>
        <div>
            <div style="float: left;">
                <h4>Add New vegetables</h4>
                <form method="POST" action="VegiController">
                    <table>
                        <tr>
                            <td>Vegetable Name</td>
                            <td><input type="text" name="vegi_name"></td>                    
                        </tr>                 
                        <tr>
                            <td>Vegetable Price</td>
                            <td><input type="text" name="vegi_price"></td>                    
                        </tr> 
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                
                        <tr>                    
                            <td><input type="submit" name="save" value="Save"></td>                    
                            <td><input type="submit" name="update" value="Update"></td>
                            <td><input type="submit" name="display" value="Display"></td>
                        </tr>              
                
                    </table>
                </form>
                <br>
            </div>
            <div style="float: left;margin-left: 300px;">
                <h4>Calculation</h4>
                <form action="VegiController" method="POST">
                    <table border='1'>
                        <tr>
                            <th style="padding-left: 0px; padding-right: 2.5px;"><label>Vegetable Name</label></th>
                            <th style="padding-left: 50px; padding-right: 2.5px;"><label>Quantity</label></th>
                            <th style="padding-left: 25px; padding-right: 2.5px;"><label>Action</label></th>
                        </tr>
                    </table>
                    <div id="dynamicInput">
                        <table>
                            <tr style="margin-bottom: 500px;">
                                <td>
                                    <select name="veginame[]">
                                        <option value="Kale">Kale</option>
                                        <option value="Cabbage">Cabbage</option>
                                        <option value="Spinach">Spinach</option>
                                        <option value="Cucumber">Cucumber</option>
                                        <option value="Carrot">Carrot</option>
                                        <option value="Broccoli">Broccoli</option>
                                        <option value="Asparagus">Asparagus</option>
                                        <option value="Chokos">Chokos</option>
                                        <option value="Celery">Celery</option>
                                        <option value="Cauliflower">Cauliflower</option>
                                        <option value="Eggplant">Eggplant</option>
                                        <option value="Leeks">Leeks</option>
                                        <option value="Lettuce">Lettuce</option>
                                        <option value="Melon">Melon</option>
                                        <option value="Onion">Onion</option>
                                    </select>
                                </td>
                                <td>
                                   <input type="text" name="quantity[]" placeholder="Quantity"> 
                                </td>
                                <td>
                                    <input type="button" value="(+)" onclick="addInput('dynamicInput')">
                                </td>
                            </tr>
                        </table>
                    </div><br>
                    <table>
                        <tr>
                           <td><input type="submit" name="calculate" value="Total Price"></td> 
                        </tr>
                        
                    </table><br>
                    <table>
                        <tr>
                            <td><label>Paid Amount: </label></td>
                            <td><input type="text" name="paid_amount" placeholder="Paid Amount"></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="username" value="<%= request.getAttribute("username")%>" hidden=""></td>                            
                            <td><input type="submit" name="print" value="Print Receipt"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div style="float: right;">
                <h4>Delete the Vegetables</h4>
                <form action="VegiController" method="POST">
                    <input type="text" name="delname" placeholder="Enter Name">
                    <input type="submit" name="delete" value="DELETE">
                </form> 
            </div>
            <div>
                               
            </div>
        </div>
                           
      

    </body>
</html>
