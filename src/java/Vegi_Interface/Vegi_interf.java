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
 */
public interface Vegi_interf extends Remote {
    
    public void getConnection() throws RemoteException;
    public void addVegetable(VegiClass vegis) throws RemoteException;
    public List<VegiClass> getVegetableInfo() throws RemoteException;
    public int totalPrice(VegiClass vegis) throws RemoteException;
    public void updateVegetable(VegiClass vegis) throws RemoteException;
    public void deleteVegetable(VegiClass vegis) throws RemoteException;
    //public String getUsername(User users)throws RemoteException;
}
