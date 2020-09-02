/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Vegi.User;
import Vegi.VegiClass;
import Vegi_Interface.UserInterface;
import Vegi_Interface.Vegi_interf;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author #USER
 */
@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {
    String[] names,quantities;
    double[] costs;
    double total = 0.0;
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

            if(request.getParameter("save") !=null){
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    Vegi_interf implem = (Vegi_interf)reg.lookup("Server");
//                    out.println("We are here");
                    String name = request.getParameter("vegi_name");                  
                    int price = Integer.parseInt(request.getParameter("vegi_price"));
                    
                    VegiClass info = new VegiClass();
                    info.setVegi_name(name);
                    info.setVegi_price(price);
                    
                    implem.addVegetable(info);
                    response.sendRedirect("home.jsp?msg=insert");
                    
                    
                    
   //If display button is clicked                 
                }else if(request.getParameter("display") != null){
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    
                    List<VegiClass> list = (List)infc.getVegetableInfo();
                    out.println("<table border='1'>");
                     out.println("<tr><th>Vegetable Name</th><th>Vegetable Price</th></tr>");
                     
                     for(VegiClass inf: list){
                         out.println("<tr>");
                         out.println("<td>" + inf.getVegi_name() + "</td>");                        
                         out.println("<td>" + inf.getVegi_price() + "</td>");
                         out.println("<td>"
                                 + "<form action='Controller' method='POST'>"
                                 + "<input type='submit' name='delete' value='Delete'</td>"
                                 + "</form>");
                         out.println("</tr>");                   
                    
                         
                     }                     
                    out.println("</table>");
                    
//If the calculate button is clicked                    
                }else if(request.getParameter("calculate") !=null){
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    
                    names = request.getParameterValues("veginame[]");
                    quantities = request.getParameterValues("quantity[]");
                    
                    double final_price  = 0.0;
                    
                    out.println("<table border='1'>");
                    out.println("<tr><th align='center'>Vegetable Service Receipt</th></tr>");
                    out.println("</table>");
                    for(int i=0;i<names.length;i++){
                        VegiClass info = new VegiClass();
                        info.setVegi_name(names[i]);
                        final_price = Integer.parseInt(quantities[i]) * infc.totalPrice(info);
                        total +=final_price;
                       out.println("<table border='1'>");                        
                        out.println("<tr><th>Vegetable Name</th><th>Total Amount</th></tr>");
                        out.println("<tr>");
                        out.println("<td>" +info.getVegi_name() + "</td>");                        
                        out.println("<td>" + final_price + "</td>");                            
                        out.println("</tr>");
                       out.println("</table>");
                    } 
                    out.println("<table border='1'>");
                    out.println("<tr>");
                    out.println("<td>Total Amount</td>");
                        out.println("<td>" + total+ "</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        
                }
                else if(request.getParameter("print") !=null){
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    
//                    names = request.getParameterValues("veginame[]");
//                    quantities = request.getParameterValues("quantity[]");
                    String username = request.getParameter("username");
                    String paid_amount = request.getParameter("paid_amount");                     
                    
                    double final_price = total;                    
                    
//                    out.println("</table>");
//                    for(int i=0;i<names.length;i++){
//                        VegiClass info = new VegiClass();
//                        info.setVegi_name(names[i]);
//                        total = Integer.parseInt(quantities[i]) * infc.totalPrice(info);
//                        final_price +=total;
//                       out.println("<table border='1'>");                        
//                        out.println("<tr><th>Vegetable Name</th><th>Total Amount</th></tr>");
//                        out.println("<tr>");
//                        out.println("<td>" +info.getVegi_name() + "</td>");                        
//                        out.println("<td>" + total + "</td>");                            
//                        out.println("</tr>");
//                       out.println("</table>");
//                    } 
                        double change_due = Double.parseDouble(paid_amount)-final_price;
                        
                        out.println("<table border='1'>");
                        out.println("<tr><th align='center'>Vegetable Service Receipt</th></tr>");
                        out.println("<tr><th>Net Price</th><th>"+final_price+"</th></tr>");
                        out.println("<tr><th>Amount Given</th><th>"+paid_amount+"</th></tr>");
                        out.println("<tr><th>Change due</th><th>"+change_due+"</th></tr>");
                        out.println("<tr><th>Served By</th><th>"+username+"</th></tr>");
                        out.println("</table>");
                        //System.out.println("Here");
                        total = 0.0;
                    
                    
                }else if(request.getParameter("update") != null){
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                   
                    String name = request.getParameter("vegi_name");                  
                    int price = Integer.parseInt(request.getParameter("vegi_price"));
                    
                    VegiClass info = new VegiClass();
                    info.setVegi_name(name);
                    info.setVegi_price(price);
                    
                    infc.updateVegetable(info);
                    response.sendRedirect("home.jsp?msg=update");
                    
                    

                }else if(request.getParameter("delete") != null){                            
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    String name = request.getParameter("delname");
                            
                    VegiClass info = new VegiClass();
                    info.setVegi_name(name);
                            
                    infc.deleteVegetable(info);
                    response.sendRedirect("home.jsp?msg=delete");
                }
                
               
               
           }catch(Exception e){
               e.printStackTrace();
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
//        try {
//            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
//            Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
//            
//            List<VegiClass> list = (List)infc.getVegetableInfo();
//            request.setAttribute("vegies", list);
//            request.getRequestDispatcher("home.jsp").forward(request, response);
//        } catch (RemoteException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NotBoundException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
//        try {
//            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
//            Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
//            
//            List<VegiClass> list = (List)infc.getVegetableInfo();
//            request.setAttribute("vegies", list);
//            request.getRequestDispatcher("home.jsp").forward(request, response);
//        } catch (RemoteException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NotBoundException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
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
