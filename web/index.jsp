<%-- 
    Document   : index
    Created on : Sep 1, 2020, 3:31:18 PM
    Author     : #USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vegetable Service Engine</title>
    </head>
    <body>
        <h1>Vegetable Service</h1>
        <br>
        <h3 style="color: red">
             <%
            if(request.getParameter("msg") != null){
                if(request.getParameter("msg").equals("newuser")){
                    out.println("User added Successfully");
                }else if(request.getParameter("msg").equals("errlogin")){
                    out.println("Login failed try again");
                }                
            }
             %>
        </h3>
        
        <div style="display: block; margin-left: 400px;margin-right: 500px;">
            <div style="float: right">
                <h3>User Registration</h3>
                <form action="UserController" method="POST">
                    <label>Username: </label><br>
                    <input type="text" name="username" placeholder="Username"><br><br>
                    <label>Email: </label><br>
                    <input type="email" name="email" placeholder="Email"><br><br>
                    <label>Password: </label><br>
                    <input type="password" name="password" placeholder="Password"><br><br>
                    <label>Role: </label><br>
                    <select name="role">
                        <option value="manager">Manager</option>
                        <option value="cashier">Cashier</option>
                        <option value="cook">Cook</option>
                    </select><br><br>
                    
                    <input type="submit" name="register" value="REGISTER">
                </form>
            </div>
            <div style="float: left;">
                <h3>Login</h3>
                <form action="UserController" method="POST">
                    <label>Username: </label><br>
                    <input type="text" name="username" placeholder="Username"><br><br>
                    <label>Password: </label><br>
                    <input type="password" name="password" placeholder="Password"><br><br>
                    
                    <input type="submit" name="login" value="LOGIN">
                </form>
            </div>
        </div>
        
        
    </body>
</html>
