/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vegi_Implementation;

import Vegi.User;
import Vegi.VegiClass;
import Vegi_Interface.Vegi_interf;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author #USER
 */
public class Vegi_implem extends UnicastRemoteObject implements Vegi_interf {
    Connection conn;
    public Vegi_implem() throws RemoteException{
        super();
        getConnection();
    }

    @Override
    public void getConnection() throws RemoteException {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/VegiEngine", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Vegi_implem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Vegi_implem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addVegetable(VegiClass vegis) throws RemoteException {
        try {
            String query = "INSERT INTO vegi_info(vegi_name,vegi_price) VALUES(?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, vegis.getVegi_name());            
            stmt.setDouble(2, vegis.getVegi_price());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Vegi_implem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<VegiClass> getVegetableInfo() throws RemoteException {
        List<VegiClass> list = new ArrayList<>();
        try {
            
            String query = "SELECT * FROM vegi_info";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet res = stmt.executeQuery();
            //Extract data from result set
            while(res.next()){
                //Retrive by column name
                String name = res.getString("vegi_name");                
                int price = res.getInt("vegi_price");
                
                //Setting the values
                VegiClass vegis = new VegiClass();
                vegis.setVegi_name(name);
                vegis.setVegi_price(price);
                list.add(vegis);
            }
        }catch(SQLException ex){
            Logger.getLogger(Vegi_implem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    @Override
    public int totalPrice(VegiClass vegis) throws RemoteException {
        int price = 0;
        try{
            String query = "SELECT vegi_price FROM vegi_info WHERE vegi_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,vegis.getVegi_name());
            ResultSet res = stmt.executeQuery();
           while(res.next()){
                price = res.getInt("vegi_price");
                
                vegis.setVegi_price(price);
                
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return price;
    }

    @Override
    public void updateVegetable(VegiClass vegis) throws RemoteException {
        try{
            String query = "UPDATE vegi_info SET vegi_price = ? WHERE vegi_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, vegis.getVegi_price());
            stmt.setString(2, vegis.getVegi_name());
            
            stmt.executeUpdate();            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVegetable(VegiClass vegis) throws RemoteException {
        try{
            String query = "DELETE FROM vegi_info WHERE vegi_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, vegis.getVegi_name());
            
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

//    @Override
//    public String getUsername(User users) throws RemoteException {
//        try {
//            String query = "SELECT username FROM user username=? AND password=?";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setString(1, users.getUsername());
//        } catch (SQLException ex) {
//            Logger.getLogger(Vegi_implem.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    
}
