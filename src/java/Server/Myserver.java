/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Vegi_Implementation.UserImplementation;
import Vegi_Implementation.Vegi_implem;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author #USER
 */
public class Myserver {
    
    public static void main(String args[]){
        try{
            Registry reg = LocateRegistry.createRegistry(5000);
            Vegi_implem impl = new Vegi_implem();
            UserImplementation uimplem = new UserImplementation();
            reg.rebind("Server", impl);
            reg.rebind("Server2", uimplem);
            System.out.println("Server started...");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
