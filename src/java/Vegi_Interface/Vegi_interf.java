/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vegi_Interface;

import Vegi.User;
import Vegi.VegiClass;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author #USER
 * Vegi_interf declares the functions that 
 * are to be performed on vegetable price.
 */
public interface Vegi_interf extends Remote {
    //method to connect to database
    public void getConnection() throws RemoteException;
    //method for adding items to the database
    public void addVegetable(VegiClass vegis) throws RemoteException;
    //method for getting List of items form the database
    public List<VegiClass> getVegetableInfo() throws RemoteException;
    //method for calculating total price
    public int totalPrice(VegiClass vegis) throws RemoteException;
    //method for updating individual item price in the database
    public void updateVegetable(VegiClass vegis) throws RemoteException;
    //method for deleting items form the database
    public void deleteVegetable(VegiClass vegis) throws RemoteException;
   
}
