/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.Beans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author RustyPeach
 */
public class stockBean implements Serializable{
    
    @Id
    @GeneratedValue
    private String stockName;
    private int amount;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public stockBean() {
    }


    public String getstockName() {
        return stockName;
    }


    public void setstockName(String id) {
        this.stockName = id;
    }
    
    public int getAmount() {
        return amount;
    }


    public void setAmount(int money) {
        this.amount = money;
    }

}
