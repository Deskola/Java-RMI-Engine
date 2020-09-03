/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Vegi.VegiClass;
import Vegi_Interface.Vegi_interf;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author #USER
 */
@WebServlet(name = "VegiController", urlPatterns = {"/VegiController"})
public class VegiController extends HttpServlet {
    String[] names,quantities;
    double[] costs;
    double total = 0.0;
    String delete = "delete";
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
            //if save button in home.jsp is clicked
            if(request.getParameter("save") !=null){
                    //Connection to server and bind with the server ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //creat object of Vegi_interface (i.e vinterface) and bind with the server name
                    Vegi_interf vinterface = (Vegi_interf)reg.lookup("Server");
                  
                    //get the parameters form home.jsp page 
                    String name = request.getParameter("vegi_name");                  
                    int price = Integer.parseInt(request.getParameter("vegi_price"));
                    
                    //Creat obj of VegiClass (i.e info)
                    VegiClass info = new VegiClass();
                    //Temporary store the parameters(veg_name,vegi_price) into the vegiclass object (info)
                    info.setVegi_name(name);
                    info.setVegi_price(price);
                    
                     //passing the VegiClass obj(info) into the addVegetable function from the interface(i.e infc)
                    vinterface.addVegetable(info); 
                    //dispaly message if addVegetable executes successfully.
                    response.sendRedirect("home.jsp?msg=insert");
                    
                    
                    
                //If display button in home.jsp page is clicked                 
                }else if(request.getParameter("display") != null){
                    //Connection to server and bind with the server ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //creat object of Vegi_interface (i.e infc) and bind with the server name
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    
                    //get the returned list from Vegi_implem class via Vegi_interf onject (i.e infc) and store
                    //in inside the list object vegi 
                    List<VegiClass> list = (List)infc.getVegetableInfo();
                    out.println("<table border='1'>");
                    out.println("<tr><th>Vegetable Name</th><th>Vegetable Price</th></tr>");
                    
                //loop through the list to get individual row(vegi_name and vegi_price)
                //display is a table format
               //+ "<input type='text' name='delname' value='"++"' hidden=''>" 
                list.stream().map((vegi) -> {
                    out.println("<tr>");
                    return vegi;
                }).map((vegi) -> {
                    out.println("<td>" + vegi.getVegi_name() + "</td>");
                    return vegi;
                }).map((vegi) -> {
                    out.println("<td>" + vegi.getVegi_price() + "</td>");
                    return vegi;
                }).map((vegi) -> {
                    out.println("<td>"
                            + "<form action='VegiController' method='POST'>"
                            + "<input type='text' name='delname' value='"+vegi.getVegi_name()+"' hidden=''>"                                                
                            + "<input type='submit' name='"+delete+"' value='Delete'></td>"
                            + "</form>");
                    return vegi;
                }).forEachOrdered((_item) -> {
                    out.println("</tr>");
                });
                    out.println("</table>");
                    
                //If the calculate button is clicked                    
                }else if(request.getParameter("calculate") !=null){
                    //Connection to server and bind with the server ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //creat object of Vegi_interface (i.e infc) and bind with the server name
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    
                    //get the parameters form home.jsp page 
                    names = request.getParameterValues("veginame[]");
                    quantities = request.getParameterValues("quantity[]");
                    
                    //create local variable final_price  
                    double final_price;
                    
                    out.println("<table border='1'>");
                    out.println("<tr><th align='center'>Quick Calculator</th></tr>");
                    out.println("</table>");
                    for(int i=0;i<names.length;i++){
                        //Creat obj of VegiClass (i.e info)
                        VegiClass info = new VegiClass();
                        //Temporary store the parameters(vegi_name,quantity) into the vegiclass object (info) using setters
                        info.setVegi_name(names[i]);
                        //final_price  stores the product(*) of quantity supplied and unit price of each item from the db                  
                        final_price = Integer.parseInt(quantities[i]) * infc.totalPrice(info);
                        //total store the sum of product of all items requested
                        total +=final_price;
                        //displaying the result in a table format
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
                    //Connection to server and bind with the server ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //creat object of Vegi_interface (i.e infc) and bind with the server name
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    
                    //get the parameters form home.jsp page 
                    String username = request.getParameter("username");
                    String paid_amount = request.getParameter("paid_amount"); 
                    
                    //create local variable final_price 
                    double final_price;                    
                    
                    out.println("<table border='1'>");
                    out.println("<tr><th align='center'>Vegetable Service Receipt</th></tr>");
                    out.println("</table>");
                    for(int i=0;i<names.length;i++){
                         //Creat obj of VegiClass (i.e info)
                        VegiClass info = new VegiClass();
                        //Temporary store the parameters(vegi_name,quantity) into the vegiclass object (info) using setters
                        info.setVegi_name(names[i]);
                        //final_price  stores the product(*) of quantity supplied and unit price of each item from the db 
                        final_price = Integer.parseInt(quantities[i]) * infc.totalPrice(info);                                                
                        //displaying the result in a table format
                       out.println("<table border='1'>");                        
                        out.println("<tr><th>Vegetable Name</th><th>Total Amount</th></tr>");
                        out.println("<tr>");
                        out.println("<td>" +info.getVegi_name() + "</td>");                        
                        out.println("<td>" + final_price + "</td>");                            
                        out.println("</tr>");
                       out.println("</table>");
                    } 
                    //crate a local variable change_due to store the difference between paid amount and total cost
                        double change_due = Double.parseDouble(paid_amount)-total;
                        //display results in table format
                        out.println("<table border='1'>");                        
                        out.println("<tr><th>Net Price</th><th>"+total+"</th></tr>");
                        out.println("<tr><th>Amount Given</th><th>"+paid_amount+"</th></tr>");
                        out.println("<tr><th>Change due</th><th>"+change_due+"</th></tr>");
                        out.println("<tr><th>Served By</th><th>"+username+"</th></tr>");
                        out.println("</table>");
                        //System.out.println("Here");
                        //reset total to 0 ready for new transaction.
                        total = 0.0;
                    
                    
                }else if(request.getParameter("update") != null){
                    //Connection to server and bind with the server ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //creat object of Vegi_interface (i.e infc) and bind with the server name
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                   
                    //get the parameters form home.jsp page 
                    String name = request.getParameter("vegi_name");                  
                    int price = Integer.parseInt(request.getParameter("vegi_price"));
                    
                     //Creat obj of VegiClass (i.e info)
                    VegiClass info = new VegiClass();
                     //Temporary store the parameters(vegi_name,vegi_price) into the vegiclass object (info) using setters
                    info.setVegi_name(name);
                    info.setVegi_price(price);
                    
                     //passing the VegiClass obj(info) into the updateVegetable function from the interface(i.e infc)
                    infc.updateVegetable(info);
                    //dispaly message if updateVegetable executes successfully.
                    response.sendRedirect("home.jsp?msg=update");
                    
                    

                }else if(request.getParameter("delete") != null){ 
                    //Connection to server and bind with the server ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //creat object of Vegi_interface (i.e infc) and bind with the server name
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    
                    //get the parameters form home.jsp page 
                    String name = request.getParameter("delname");
                    
                     //Creat obj of VegiClass (i.e info)
                    VegiClass info = new VegiClass();
                     //Temporary store the parameters(vegi_name,) into the vegiclass object (info) using setters
                    info.setVegi_name(name);
                    
                     //passing the VegiClass obj(info) into the deleteVegetable function from the interface(i.e infc)
                    infc.deleteVegetable(info);
                    //dispaly message if deleteVegetable executes successfully.
                    response.sendRedirect("home.jsp?msg=delete");
                    
                    
                }else if(request.getParameter(delete) != null){
                    //Connection to server and bind with the server ip and port
                    Registry reg = LocateRegistry.getRegistry("127.0.0.1", 5000);
                    //creat object of Vegi_interface (i.e infc) and bind with the server name
                    Vegi_interf infc = (Vegi_interf)reg.lookup("Server");
                    
                    //get the parameters form home.jsp page 
                    String name = request.getParameter("delname");
                    
                     //Creat obj of VegiClass (i.e info)
                    VegiClass info = new VegiClass();
                     //Temporary store the parameters(vegi_name,) into the vegiclass object (info) using setters
                    info.setVegi_name(name);
                    
                     //passing the VegiClass obj(info) into the deleteVegetable function from the interface(i.e infc)
                    infc.deleteVegetable(info);
                    //dispaly message if deleteVegetable executes successfully.
                    response.sendRedirect("home.jsp?msg=delete");
                    System.out.println("Delete");
                }
                
               
               
           }catch(IOException | NumberFormatException | NotBoundException e){
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
//            Logger.getLogger(VegiController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NotBoundException ex) {
//            Logger.getLogger(VegiController.class.getName()).log(Level.SEVERE, null, ex);
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
//            Logger.getLogger(VegiController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NotBoundException ex) {
//            Logger.getLogger(VegiController.class.getName()).log(Level.SEVERE, null, ex);
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
