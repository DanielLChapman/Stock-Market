/*
 * Author: David Gerne
 * Final Java Project
 * Date: 11-16-2013
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import edu.Beans.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author School
 */
@WebServlet(urlPatterns = {"/LRservlet"})
public class LRservlet extends HttpServlet {

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
    
    @Resource(name = "jdbc/FP1DB")
    DataSource dataSource;
    
    String stocks[] = {"VZ", "BAC", "ATT", "GE", "EEM", "IYF", "PGJ", "PJJ", "DAN", "HIG", "PPL", "T", "DG", "GOOG", "BKBA", "JQR"};
   private boolean checkUserName(String userName)throws SQLException
    {
        boolean cUserName = false;
        
        Connection connection = dataSource.getConnection();
        String selectSQL = "select USERNAME from PEOPLE where USERNAME =?";
        PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
        selectStatement.setString(1,userName);
        ResultSet resultSet = selectStatement.executeQuery();
        
        if(resultSet.next())
        {
            cUserName = true;
        }
        else
        {
           cUserName = false;
        }
        resultSet.close();
        selectStatement.close();
        connection.close();
        return cUserName;
    }
    
    
    
    
    private boolean checkLogin(String login, String password)throws SQLException
    {
        boolean cLogin = false;
        
        Connection connection = dataSource.getConnection();
        String selectSQL = "select USERNAME from PEOPLE where USERNAME =? and PASS =? ";
        PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
        selectStatement.setString(1,login);
        selectStatement.setString(2, password);
        ResultSet resultSet = selectStatement.executeQuery();
        String id = "";
        if(resultSet.next())
        {
            id = resultSet.getString(1);
            cLogin = true;
            
            
        }
        else
        {
            
           cLogin = false;
        }
        resultSet.close();
        selectStatement.close();
        connection.close();
        return cLogin;
    }
    
    private boolean regUser (String rUserName, String rPassword) throws SQLException
    {
        boolean regDone = false;
        
        Connection connection = dataSource.getConnection();
        String insertSQL ="insert INTO PEOPLE (USERNAME, PASS) VALUES (?,?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
        insertStatement.setString(1, rUserName);
        insertStatement.setString(2, rPassword);
        
        int result = insertStatement.executeUpdate();
        
        if(result == 1)
        {
           regDone = true; 
        }
        else
        {
           regDone = false;
        }
        
        return regDone;
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        Connection connection = dataSource.getConnection();
        try 
        {   
            String task = request.getParameter("task");
         
            if(task.equals("3")) //Checks if login is successful.
            {
                String login = request.getParameter("UserName");
                String password = request.getParameter("Password");
            
                if(checkLogin(login, password))
                {
                    String id = login;
                    personBean person = new personBean();
                    ArrayList<stockBean> stockArrayList = new ArrayList<stockBean>();
                    person.setUsername(id);
                     double money = 0.0;
                    String sql = "select MONEY from PEOPLE where USERNAME='" + id + "'";   
                     PreparedStatement voteSelect = connection.prepareStatement(sql);
                    ResultSet results = voteSelect.executeQuery();
                    results.next();
                    money = results.getDouble(1);
                    person.setMoney(money);
                    for (int i = 0; i < stocks.length; i++) {
                    //Select query to retrieve the num of votes of Pop.
                    sql = "select " + stocks[i] +" from PEOPLE where USERNAME='" + id+"'";
                    //prepares the SQL and protects it.
                    voteSelect = connection.prepareStatement(sql);
                    //Executes the query and stores the value in a resultset
                    results = voteSelect.executeQuery();
                    int amountStock = 0;
                    
                    results.next();
                    amountStock = (results.getInt(1));
                    stockBean sb = new stockBean();
                    sb.setstockName(stocks[i]);
                    sb.setAmount(amountStock);
                    stockArrayList.add(sb);
                    //Redundancy but it gets popId out of the block.
                    //closes everything out
                    }
                    person.setStock(stockArrayList);
                    results.close();
                    voteSelect.close();
                    HttpSession session = request.getSession();
                    session.setAttribute("beans", person);
                    request.setAttribute("beans",person);
                    request.getRequestDispatcher("Display.xhtml").forward(request,response);
                }
                else
                {
                    request.getRequestDispatcher("badlogin.html").forward(request,response);
                }
            }
            else if(task.equals("2")) //Registers a New user.
            {
                PrintWriter out = response.getWriter();
                String login = request.getParameter("RegUserName");
                String password = request.getParameter("RegPassword");
                
                
                if(regUser(login, password))
                {
                    out.println("Registration Successful");
                }
                else
                {
                    out.println("Registration Failed");
                }    
                out.close();
            }
            else //Checking the username with the database (using ajax)
            {
                String uName = request.getParameter("userName");
                        PrintWriter out = response.getWriter();
                if(checkUserName(uName))
                {
                    out.print("bad");
                }
                else
                {
                    out.print("good");
                }
                out.close();
            }
        } 
        catch(Exception e)
        {
                    PrintWriter out = response.getWriter();
            out.println("Exception occurred: "+e.getMessage());
            e.printStackTrace();
            out.close();
        }
        finally 
        {  

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LRservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LRservlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
