/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.Stocks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import edu.Beans.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RustyPeach
 */
@WebServlet(name = "Stock", urlPatterns = {"/Stock"})
public class Stock extends HttpServlet {

    @Resource(name = "jdbc/FP1DB")
    //makes a datasource object
    private DataSource dataSource;
    String stocks[] = {"VZ", "BAC", "ATT", "GE", "EEM", "IYF", "PGJ", "PJJ", "DAN", "HIG", "PPL", "T", "DG", "GOOG", "BKBA", "JQR"};

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            personBean pb = new personBean();
            pb = (personBean) session.getAttribute("beans");
            System.out.println(pb.getUsername());
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String course = request.getParameter("objective");
        //UPDATE SCRIPT TO UPDATE STOCK PRICESresponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        personBean pb = new personBean();
        pb = (personBean) session.getAttribute("beans");
        try {
            if (course.compareTo("update") == 0) {
                try {
                    for (int i = 0; i < stocks.length; i++) {
                        //Select query to retrieve the num of votes of Pop.
                        Connection connection = dataSource.getConnection();
                        String sql = "select PRICE from " + stocks[i];
                        //prepares the SQL and protects it.
                        PreparedStatement voteSelect = connection.prepareStatement(sql);
                        //Executes the query and stores the value in a resultset
                        ResultSet results = voteSelect.executeQuery();
                        double num = 0;
                        while (results.next()) {
                            num = results.getDouble(1);
                        }
                        System.out.println(num);
                        //Redundancy but it gets popId out of the block.
                        //closes everything out
                        results.close();
                        voteSelect.close();

                        //Updates numvotes
                        Random rand = new Random();
                        int resultingRandom = rand.nextInt(20) + 1;
                        double percentage = (double) (rand.nextDouble() + 1) / 100;
                        String input = "";
                        if (resultingRandom > 10) {
                            double newResultPostive = num + num * percentage;
                            input = Double.toString(newResultPostive);
                        } else {
                            double newResultPostive = num - num * percentage;
                            input = Double.toString(newResultPostive);
                        }
                        sql = "INSERT INTO " + stocks[i] + " VALUES " + input;
                        PreparedStatement updateStatement = connection.prepareStatement(sql);
                        //executes statement
                        updateStatement.executeUpdate();
                    }
                } catch (Exception ie) {
                    //catches errors then prints them out.
                    System.out.println(ie);
                }
            } else if (course.compareTo("xml") == 0) {
                out.println("<STOCKS>");
                Random rand = new Random();
                int counter = 0;
                String[] stringS = {"stock1", "stock2", "stock3", "stock4", "stock5", "stock6", "stock7", "stock8"};
                while (stringS[7].compareTo("stock8") == 0) {
                    int newRandom = rand.nextInt(16);
                    String selected = stocks[newRandom];
                    if (stringS == null) {
                        stringS[counter] = selected;
                        counter++;
                    } else {
                        if (!contains(selected, stringS)) {
                            stringS[counter] = selected;
                            counter++;
                        }
                    }
                }
                for (int i = 0; i < stringS.length; i++) {
                    try {
                        Connection connection = dataSource.getConnection();
                        String sql = "select PRICE from " + stringS[i];
                        //prepares the SQL and protects it.
                        PreparedStatement voteSelect = connection.prepareStatement(sql);
                        //Executes the query and stores the value in a resultseta
                        ResultSet results = voteSelect.executeQuery();
                        double num = 0;
                        while (results.next()) {
                            num = results.getDouble(1);
                        }
                        out.println("<STOCK>");
                        out.println("<NAME>" + stringS[i] + "</NAME>");
                        out.println("<PRICE>" + new java.text.DecimalFormat("#.00").format(num) + "</PRICE>");
                        out.println("</STOCK>");
                        //Redundancy but it gets popId out of the block.
                        //closes everything out
                        results.close();
                        voteSelect.close();
                    } catch (Exception ie) {
                    }
                }
                out.println("</STOCKS>");
            } else if (course.compareTo("buy") == 0) {
                String searchStock = request.getParameter("stock");
                String id = pb.getUsername();
                String amount = request.getParameter("amount");
                if (contains(searchStock.toUpperCase(), stocks)) {
                    try {
                        Connection connection = dataSource.getConnection();
                        String sql = "select PRICE from " + searchStock.toUpperCase();
                        //prepares the SQL and protects it.
                        PreparedStatement voteSelect = connection.prepareStatement(sql);
                        //Executes the query and stores the value in a resultset
                        ResultSet results = voteSelect.executeQuery();
                        double num = 0;
                        while (results.next()) {
                            num = results.getDouble(1);
                        }
                        double finalPrice = num * Integer.parseInt(amount);
                        if (!pb.enoughMoney(finalPrice)) {
                            out.println("Not Enough Money");
                        } else {
                            double finalMoney = pb.getMoney() - finalPrice;
                            sql = "update PEOPLE set MONEY=" + finalMoney + "where USERNAME='" + id + "'";
                            voteSelect = connection.prepareStatement(sql);
                            voteSelect.executeUpdate();
                            sql = "insert into TRANSACTIONS (USERNAME,STOCK,PRICE) values ('" + id + "','Bought "+amount + " of " + searchStock + "'," + num + ")";
                            voteSelect = connection.prepareStatement(sql);
                            voteSelect.executeUpdate();
                            pb.setMoney(finalMoney);
                            ArrayList<stockBean> nSB = new ArrayList<>();
                            sql = "update PEOPLE set " + searchStock + "=" + searchStock + "+" + amount + "where USERNAME='" + id + "'";
                            voteSelect = connection.prepareStatement(sql);
                            voteSelect.executeUpdate();
                            nSB = updateSB(pb.getUsername());
                            pb.setStock(nSB);
                            out.print("<h2>BOUGHT</h2><br />\n"
                                    + "<h3>" + amount + " of Stock " + searchStock + " at a Price of<br/> " + new java.text.DecimalFormat("#.00").format(num) + " each</h3>");
                        }

                        results.close();
                        voteSelect.close();
                    } catch (Exception ie) {
                        System.out.println(ie);
                    }
                } else {
                    out.println("Stock doesnt exist");
                }

            } else if (course.compareTo("sell") == 0) {
                String searchStock = request.getParameter("stock");
                String id = pb.getUsername();
                String amount = request.getParameter("amount");
                if (contains(searchStock.toUpperCase(), stocks)) {
                    try {
                        Connection connection = dataSource.getConnection();
                        String sql = "select PRICE from " + searchStock.toUpperCase();
                        //prepares the SQL and protects it.
                        PreparedStatement voteSelect = connection.prepareStatement(sql);
                        //Executes the query and stores the value in a resultset
                        ResultSet results = voteSelect.executeQuery();
                        double num = 0;
                        while (results.next()) {
                            num = results.getDouble(1);
                        }
                        double finalPrice = num * Integer.parseInt(amount);
                        if (!pb.enoughStock(searchStock, Integer.parseInt(amount))) {
                            out.println("Not Enough Stock");
                        } else {
                            double finalMoney = pb.getMoney() + finalPrice;
                            sql = "update PEOPLE set MONEY=" + finalMoney + "where USERNAME='" + id + "'";
                            voteSelect = connection.prepareStatement(sql);
                            voteSelect.executeUpdate();
                            sql = "insert into TRANSACTIONS (USERNAME,STOCK,PRICE) values ('" + id + "','Sold "+amount + " of " + searchStock + "'," + num + ")";
                            voteSelect = connection.prepareStatement(sql);
                            voteSelect.executeUpdate();
                            pb.setMoney(finalMoney);
                            ArrayList<stockBean> nSB = new ArrayList<>();
                            sql = "update PEOPLE set " + searchStock + "=" + searchStock + "-" + amount + "where USERNAME='" + id + "'";
                            voteSelect = connection.prepareStatement(sql);
                            voteSelect.executeUpdate();
                            nSB = updateSB(pb.getUsername());
                            pb.setStock(nSB);
                            out.print("<h2>SOLD</h2><br />\n"
                                    + "<h3>" + amount + " of Stock " + searchStock + " at a Price of<br/> " + new java.text.DecimalFormat("#.00").format(num) + " each</h3>");
                        }

                        results.close();
                        voteSelect.close();
                    } catch (Exception ie) {
                        System.out.println(ie);
                    }
                } else {
                    out.println("Stock doesnt exist");
                }

            } else if (course.compareTo("search") == 0) {
                String searchStock = request.getParameter("stock");
                System.out.println("here");
                if (contains(searchStock.toUpperCase(), stocks)) {
                    try {
                        Connection connection = dataSource.getConnection();
                        String sql = "select PRICE from " + searchStock.toUpperCase();
                        //prepares the SQL and protects it.
                        PreparedStatement voteSelect = connection.prepareStatement(sql);
                        //Executes the query and stores the value in a resultset
                        ResultSet results = voteSelect.executeQuery();
                        double num = 0;
                        while (results.next()) {
                            num = results.getDouble(1);
                        }
                        out.println("<h1>" + searchStock + "</h1><br /><h3>Priced At: " + num);
                        System.out.println(num);
                        results.close();
                        voteSelect.close();
                    } catch (Exception ie) {
                        System.out.println(ie);
                    }
                } else {
                    out.println("Stock doesnt exist");
                }

            } else if (course.compareTo("history") == 0) {
                String id = pb.getUsername();
                String amount = request.getParameter("results");
                int amount2 = 0;
                if (amount.compareTo("viewAll")==0) {
                    amount2 = -1;
                }
                else {
                   amount2 = Integer.parseInt(amount);   
                }
                try {
                    Connection connection = dataSource.getConnection();
                    String sql = "select * from TRANSACTIONS where USERNAME='" + id +"'";
                    //prepares the SQL and protects it.
                    PreparedStatement voteSelect = connection.prepareStatement(sql);
                    //Executes the query and stores the value in a resultset
                    ResultSet results = voteSelect.executeQuery();
                    ArrayList<String> stock = new ArrayList<>();
                    ArrayList<Double> price = new ArrayList<>();
                    while (results.next()) {
                        stock.add(results.getString(2));
                        price.add(results.getDouble(3));
                    }
                    if (amount2 == -1) {
                       for (int i = stock.size()-1; i >= 0; i--) {
                        out.println("<h4>" + stock.get(i) + " at a price of " + price.get(i)+"</h4><br />");
                       }
                    }
                    else if (stock.size() > amount2) { 
                    for (int i = stock.size()-1; i >= (stock.size()-amount2); i--) {
                        out.println("<h4>" + stock.get(i) + " at a price of " + price.get(i)+"</h4><br />");
                    }
                    }
                    else {
                       for (int i = stock.size()-1; i >= 0; i--) {
                        out.println("<h4>" + stock.get(i) + " at a price of " + price.get(i)+"</h4><br />");
                    } 
                    }
                  
                    results.close();
                    voteSelect.close();
                } catch (Exception ie) {
                    System.out.println(ie);
                }
            }

        } finally {
            out.close();
        }
    }

    public boolean contains(String text, String[] arrayString) {
        for (int x = 0; x < arrayString.length; x++) {
            if (arrayString[x].compareTo(text) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private ArrayList<stockBean> updateSB(String id) throws SQLException {
        ArrayList<stockBean> nSB = new ArrayList<stockBean>();
        Connection connection = dataSource.getConnection();
        for (int i = 0; i < stocks.length; i++) {
            //Select query to retrieve the num of votes of Pop.
            String sql = "select " + stocks[i] + " from PEOPLE where USERNAME='" + id + "'";
            //prepares the SQL and protects it.
            PreparedStatement voteSelect = connection.prepareStatement(sql);
            //Executes the query and stores the value in a resultset
            ResultSet results = voteSelect.executeQuery();
            int amountStock = 0;

            results.next();
            amountStock = (results.getInt(1));
            stockBean sb = new stockBean();
            sb.setstockName(stocks[i]);
            sb.setAmount(amountStock);
            nSB.add(sb);
            //Redundancy but it gets popId out of the block.
            //closes everything out
        }
        return nSB;
    }
}
