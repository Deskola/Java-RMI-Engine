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
            //Creat registry that create a port to which remote clients can connect to the server
            Registry reg = LocateRegistry.createRegistry(5000);
            //creat instances/objects of implementation classes(i.e impl and uimplem)
            Vegi_implem impl = new Vegi_implem();
            UserImplementation uimplem = new UserImplementation();
            //rebind the implementation classes(i.e impl, unimplem) with the server name
            reg.rebind("Server", impl);
            reg.rebind("Server2", uimplem);
            //print in the console that the server is started
            System.out.println("Server started...");
        }catch(Exception e){
            //print errors in the console
            e.printStackTrace();
        }
    }
    
}
