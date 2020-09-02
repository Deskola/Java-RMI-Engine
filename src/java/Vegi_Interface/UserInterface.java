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
 */
public interface UserInterface extends Remote {
    
    public void getConnection()throws RemoteException;
    public void registerUser(User users)throws RemoteException;
    public boolean login(User users)throws RemoteException;
    
}
