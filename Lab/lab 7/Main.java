

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;

        // Load a connection library between Java and the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("Error connecting to jdbc");
        }

        try {
            // Connect to the Dal database
            connection = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306/csci3901",
                    "**UserID**","**PASSWORD**");

            // Create a statement
            statement = connection.createStatement();

            System.out.println("Please Enter Order Number to generate receipt");
            Scanner input=new Scanner(System.in);
            int orderNo=input.nextInt();
            System.out.println("Your generated receipt is:\n");
            resultSet=statement.executeQuery("select * from orders where OrderID="+orderNo+";");
            while (resultSet.next()) {
                System.out.printf("Order Date: %s\nOrder Number: %s\n",
                        resultSet.getDate("OrderDate"),
                        resultSet.getInt("OrderID"));
            }

            ResultSet resultSet1=null;
            resultSet1=statement.executeQuery("select c.ContactName,c.Address from orders o INNER JOIN customers c ON o.CustomerID=c.CustomerID where o.OrderID="+orderNo+";");
            while (resultSet1.next()) {
                System.out.printf("Customer Name: %s\nCustomer Address: %s\n",
                        resultSet1.getString("ContactName"),
                        resultSet1.getString("Address"));
            }
            System.out.println("\nDescription of Items ordered:\n");
            System.out.println("Product Code\tQuantity");
            ResultSet resultSet2=null;
            float price=0;
            resultSet2=statement.executeQuery("select od.ProductID,od.Quantity,od.Discount,od.UnitPrice from orders o INNER JOIN orderdetails od ON o.OrderID=od.OrderID where o.OrderID="+orderNo+";");
            while (resultSet2.next()) {
                price+=resultSet2.getInt("Quantity")*resultSet2.getFloat("UnitPrice");
                System.out.printf("%s\t\t\t\t%d\n",
                        resultSet2.getString("ProductID"),
                        resultSet2.getInt("Quantity"));
            }
            System.out.printf("\nFinal price: %f",price);

        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());

        } finally {

            if (resultSet != null) {
                try { resultSet.close(); } catch (SQLException sqlEx) { }
                resultSet = null;
            }

            if (statement != null) {
                try { statement.close(); } catch (SQLException sqlEx) { } // ignore
                statement = null;
            }

            if (connection != null) {
                try { connection.close(); } catch (SQLException sqlEx) { } // ignore
                connection = null;
            }
        }
    }

}