/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.Beans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author RustyPeach
 */
@ManagedBean
@SessionScoped
public class personBean implements Serializable{
    
    @Id
    @GeneratedValue
     String stockList[] = {"VZ", "BAC", "ATT", "GE", "EEM", "IYF", "PGJ", "PJJ", "DAN", "HIG", "PPL", "T", "DG", "GOOG", "BKBA", "JQR"};
    private String id;
    private double money;
    private String stockName="ATT";
    private int amountStock=10;
    private ArrayList<stockBean> stocks;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public personBean() {
    }


    public String getUsername() {
        return id;
    }


    public void setUsername(String id) {
        this.id = id;
    }
    public String getstockName() {
        return stockName;
    }


    public void setstockName(String stockName) {
        this.stockName = stockName;
    }
    public int getamountStock() {
        return amountStock;
    }


    public void setamountStock(int amountStock) {
        this.amountStock = amountStock;
    }
    public double getMoney() {
        return money;
    }


    public void setMoney(Double money) {
        this.money = money;
    }
    
    public int getStock(String name) {
        int stockAmount = 0;
        if (stocks.isEmpty()) {
            return stockAmount;
        }
        else {
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getstockName().compareTo(name)==0) {
                stockAmount = stocks.get(i).getAmount();
            }
        }
        return stockAmount;
        }
    }
    public ArrayList<stockBean> getStocks() {
        return stocks;
    }

    public void setStock(ArrayList<stockBean> stocks) {
        this.stocks = stocks;
    }
    
    public boolean enoughStock(String name, int amountStock) {
        int amount = 0;
        if (stocks.isEmpty()) {
            return false;
        }
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getstockName().compareTo(name)==0) {
                amount = stocks.get(i).getAmount();
            }
        }
        if (amount >= amountStock)
            return true;
        else
        return false;
    }
    
    public boolean enoughMoney(double cost) {
        if (cost < money) 
            return true;
        else
        return false;
    }
    public String viewStocks() {
        String returnString = "";
        for(int i = 0; i < stockList.length; i++) {
            if (getStock(stockList[i]) != 0) {
                returnString = returnString +" "+ stockList[i] +" "+ getStock(stockList[i]);
            }
        }
        return returnString;
    }
}
