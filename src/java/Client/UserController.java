/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Vegi.User;
import Vegi_Interface.UserInterface;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author #USER
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try{
                //if register button is pressed
                if(request.getParameter("register") != null){
                    //Connect to tha server using ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //bind the interface with the server name while also creating the instance of the UserInterface(i.e uint)
                    UserInterface uint = (UserInterface)reg.lookup("Server2");
                    
                    //get the parameters(username,email...) from index.jsp and store them in local variable(name,emil,password,role)
                    String username = request.getParameter("username");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String role = request.getParameter("role");
                    
                    //create obj of UserClass(user)
                    User user = new User();
                    //store the above localvariables(username,email,password,role) into the user object using set methods
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setRole(role);
                    
                    //pass the obj user into the register function
                    uint.registerUser(user);
                    //return a response message if the action(i.e registerUser) is successful
                    response.sendRedirect("index.jsp?msg=newuser");
                
                //if the login button is pressed
                }else if(request.getParameter("login") != null){
                    //Connect to tha server using ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //bind the interface with the server name while also creating the instance of the UserInterface(i.e uint)
                    UserInterface uint = (UserInterface)reg.lookup("Server2");
                    
                    //get the parameters(username,password) from index.jsp and store them in local variable(username,password)
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    
                    //create obj of UserClass(user)
                    User user = new User();
                    //store the above localvariables(username,password) into the user object using set methods
                    user.setUsername(username);
                    user.setPassword(password);
                    
                    //creat a local variable(i.e result) of type boolean to store the value returned from login function
                    boolean result = uint.login(user);
                    //if the result is TRUE
                    if(result){
                        
                        //store the username variable and forward it to the home.jsp page
                        request.setAttribute("username", username);                        
                        request.getRequestDispatcher("home.jsp").forward(request, response);
                        
                          
                    }else{
                        //if the result if FALSE
                        //display error message
                        response.sendRedirect("index.jsp?msg=errlogin");
                    }
                }
                
            }catch(Exception ex){
                //printing error
                ex.printStackTrace();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
