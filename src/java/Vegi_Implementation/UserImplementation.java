/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vegi_Implementation;

import Vegi.User;
import Vegi_Interface.UserInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author #USER
 */
public class UserImplementation extends UnicastRemoteObject implements UserInterface {
    Connection conn;
    public UserImplementation()throws RemoteException{
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
    public void registerUser(User users) throws RemoteException {
       try{
           String query = "INSERT INTO user(username,email,password,role) VALUES(?,?,?,?)";
           PreparedStatement stmt = conn.prepareStatement(query);
           stmt.setString(1, users.getUsername());
           stmt.setString(2, users.getEmail());
           stmt.setString(3, users.getPassword());
           stmt.setString(4, users.getRole());
           
           stmt.executeUpdate();
       }catch(SQLException ex){
           Logger.getLogger(UserImplementation.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    @Override
    public boolean login(User users) throws RemoteException {
        boolean exist = false;
        try {
            String query = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, users.getUsername());
            stmt.setString(2, users.getPassword());
            
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                exist = true;
//                name = res.getString("username");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exist;
    }
    
}
