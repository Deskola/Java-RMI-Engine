/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vegi;

import java.io.Serializable;

/**
 *
 * @author #USER
 */
public class VegiClass implements Serializable {
    
    int id;
    double vegi_price;
    String vegi_name;
    
    public VegiClass(){
        int id = 0;
        double vegi_price = 0.0;
        String vegi_name = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVegi_price() {
        return vegi_price;
    }

    public void setVegi_price(double vegi_price) {
        this.vegi_price = vegi_price;
    }

    public String getVegi_name() {
        return vegi_name;
    }

    public void setVegi_name(String vegi_name) {
        this.vegi_name = vegi_name;
    }
    
    
    
}
