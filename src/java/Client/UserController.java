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
                if(request.getParameter("register") != null){
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    UserInterface uint = (UserInterface)reg.lookup("Server2");
                    
                    String username = request.getParameter("username");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String role = request.getParameter("role");
                    
                    User user = new User();
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setRole(role);
                    
                    uint.registerUser(user);
                    response.sendRedirect("index.jsp?msg=newuser");
                    
                }else if(request.getParameter("login") != null){
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    UserInterface uint = (UserInterface)reg.lookup("Server2");
                    
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    
                    boolean result = uint.login(user);
                    if(result){
                        
                        request.setAttribute("username", username);
                        //request.getSession(true).setAttribute("username", username);
                        request.getRequestDispatcher("home.jsp").forward(request, response);
                        
                          //response.sendRedirect(request.getContextPath()+"/home.jsp");
                    }else{
                        response.sendRedirect("index.jsp?msg=errlogin");
                    }
                }
                
            }catch(Exception ex){
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
