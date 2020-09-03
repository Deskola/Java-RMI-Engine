/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vegi_Interface;

import Vegi.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author #USER
 * 
 * UserInterface declares the functions that are to be performed 
 * on the User class
 */
public interface UserInterface extends Remote {
    //method to connect to mysql database
    public void getConnection()throws RemoteException;
    //method to register/create new user 
    public void registerUser(User users)throws RemoteException;
    //method to login existing user of the system
    public boolean login(User users)throws RemoteException;
    
}
