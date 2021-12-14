/*
    XML file doesn't allow special characters like &, >, <,etc.
    But some company name or other column entries in the database contains '&' in their data. 
    Therefore, the XML file will generate error if opened in a web browser.

    Note that if no data is available for given period, then the output file 
    will be generated with details of period and empty lists of customer, product and supplier
    enclosed with respective open & close tag.

    You have you to enter your NetID and password first to estalish connection to database
*/

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySQL {
    
    private List<CustomerDetails> customerList=new ArrayList<CustomerDetails>(); //Stores customer related data for user specified period
    private List<SupplierDetails> supplierList=new ArrayList<SupplierDetails>(); //Stores supplier related data for user specified period
    private Map<String,List<ProductDetails>> categorizedProductList=new HashMap<String,List<ProductDetails>>(); //Stores product data  based on category for user specified period
    private String finalResult; // Stores every details of customers, products and suppliers for output XML file
    private String fileName=""; //Store file name given by user and if empty then default name is used to create a XML file 
    private Statement statement = null;
    private ResultSet productSet = null;
    private ResultSet customerSet = null;
    private ResultSet supplierSet = null;
    private Connection connection = null;
	
    public boolean XmlGeneration()
    {		
        //Initialize output String with xml version and encoding details
        finalResult="<?xml version="+"'1.0'"+" encoding="+"'UTF-8'"+" ?>";
    
        // Load the jdbc driver
        try {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
         } 
        
         //handle jdbc connection errors
        catch (Exception ex) {
            return false;
        }

        try
        {
            //Enter you NetID and password to get access to the csci3901 database from Dal server
            System.out.println(" Enter login credentials for accessing the database:");
            Scanner scanner=new Scanner(System.in);
            System.out.print(" Net ID:");
            String login_ID=scanner.nextLine();
            System.out.print(" Password:");
            String password=scanner.nextLine();

            // Establish connection to the csci3901 database using login credentials
            connection = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306/csci3901",login_ID,password);

            // Create a statement for query
            statement = connection.createStatement();

            /* 
               Take set of dates from user to get periodic summary
            
               Note that user can insert start_date & end_date only in (YYYY-MM-DD) format as SQL only takes dates in this format, 
               if either input is of other format it will be invalid and method will return false.    

               Also, date will be checked after each input and if false, method return false without taking another input
            */
            System.out.println("\n Enter Date (YYYY-MM-DD) for the Period:");
           // Scanner scanner=new Scanner(System.in);
            System.out.print(" Enter Start Date:");
            String start_Date=scanner.nextLine(); // Start date of the period
            if(!ValidateDate(start_Date))
                return false;
            
            System.out.print(" Enter End Date:");
            String end_Date=scanner.nextLine(); // End date of the period
            if(!ValidateDate(end_Date))
                return false;
            
            //fileName can be with .txt or.xml extension or can be just name as in that case, by default xml file will be created
            System.out.print("\n Enter name of output file with extension (.txt or .xml) along with file location:");
            String file=scanner.nextLine();
            scanner.close();

            /* validate user input for file name
               if file name doesn't have extension, xml file is created by default
           
               Note that user can choose to create text as well as xml file
            */ if (fileName != null)
            {
                if (file.contains(".xml") || file.contains(".txt")) 
                    fileName=file;
                else
                    fileName=file+".xml";  
            }

            // define start and end date along with their respective tags in output string
            finalResult+="\n"+"<period_summary>\n"+
            "\t<period>\n"+
            "\t\t<start_date> "+start_Date+" </start_date>\n"+
            "\t\t<end_date> "+end_Date+" </end_date>\n"+
            "\t</period>";
            
            // Query to fetch and create customer details table
            customerSet=statement.executeQuery(
                 "  SELECT c.CompanyName AS customer_name,c.Address AS street, c.City AS city"
                +" ,c.Region AS region,c.PostalCode AS postal_code,c.Country AS country,count(distinct o.OrderID) AS num_order,sum(od.Quantity*od.UnitPrice) AS order_value"
                +" FROM  orderdetails od INNER JOIN orders o ON o.OrderID=od.OrderID"
                +" INNER JOIN customers c ON o.CustomerID=c.CustomerID WHERE o.OrderDate between "+"'"+ start_Date+"'"+" AND "+"'"+end_Date+"'"
                +" group by c.CompanyName,c.CustomerID;");
            
            // create object for customer details for each customer entry in customer details table
            // and add that object to customerList
                while (customerSet.next()) {
                    CustomerDetails customerObj=new CustomerDetails(
                    customerSet.getString("customer_name"),
                    customerSet.getString("street"),
                    customerSet.getString("city"),
                    customerSet.getString("region"),
                    customerSet.getString("postal_code"),
                    customerSet.getString("country"),
                    customerSet.getInt("num_order"),
                    customerSet.getFloat("order_value"));

                customerList.add(customerObj);
            }

            /* Create list of customer section for XML file by adding detais of each customer
                from customerList and enclose between <customer_list> & </customer_list>
            */ 
            finalResult+="\n\t\t<customer_list>\n";
            for (int i = 0; i < customerList.size(); i++)
            {
                finalResult=finalResult+customerList.get(i).printCustomerDetails();
            }
            finalResult+="\t\t</customer_list>";

            // Query to fetch and create product details table
            productSet=statement.executeQuery(
                " SELECT  distinct ct.CategoryID, ct.CategoryName AS category,pd.ProductName AS productName,sup.CompanyName AS Supplier_Name,sum(od.Quantity) AS Unit_Sold,"+
            " sum(round(od.UnitPrice * od.Quantity * (1 - od.Discount), 2)) as TotalSales "+
            "  FROM orders o INNER JOIN orderdetails od ON o.OrderID=od.OrderID INNER JOIN products pd ON od.ProductID=pd.ProductID "+
                " INNER JOIN categories ct ON ct.CategoryID=pd.CategoryID INNER JOIN suppliers sup ON sup.SupplierID=pd.SupplierID "+
                " WHERE (o.OrderDate between" +"'"+start_Date+"'"+" AND "+"'"+end_Date+"')"+ 
                "group by ct.CategoryID,ct.CategoryName,pd.ProductName"+
                " order by ct.CategoryID,pd.ProductName,TotalSales desc;"
                );

                // Create object for product details for each product entry in product details table
                // and add that object to categorizedProductList based on its category
                while (productSet.next()) {
                    ProductDetails productObj=new ProductDetails(
                                    productSet.getString("category"),
                                    productSet.getString("productName"),
                                    productSet.getString("Supplier_Name"),
                                    productSet.getInt("Unit_Sold"),
                                    productSet.getFloat("TotalSales"));
                
                    if(categorizedProductList.containsKey(productSet.getString("category")))
                    {
                        categorizedProductList.get(productSet.getString("category")).add(productObj);
                    }
                    else
                    {
                        List<ProductDetails> tempList=new ArrayList<ProductDetails>();
                        tempList.add(productObj);
                        categorizedProductList.put(productSet.getString("category"),tempList);
                    }
                }

                /* Create list of product details section for XML file by adding detais of each product
                   from categorizedProductList and enclose between <product_list> & </product_list>
                */
                finalResult+="\n\t\t<product_list>";
                for (Map.Entry<String,List<ProductDetails>>  object : categorizedProductList.entrySet())
                {
                    finalResult+="\n\t\t\t<category>";
                    finalResult+="\n\t\t\t\t<category_name> "+object.getKey().toString()+"</category_name>";
                    for (int i = 0; i < object.getValue().size(); i++)
                    {
                        finalResult+=object.getValue().get(i).printProductdetails(); 
                    }    
                    finalResult+="\n\t\t\t</category>"; 
                } 
                finalResult+="\n\t\t</product_list>";
                
                // Query to fetch and create supplier details table
                supplierSet=statement.executeQuery(
                        "with product_sale_info as"+
                        " (SELECT  distinct ct.CategoryID, ct.CategoryName, pd.ProductName, sup.CompanyName AS Supplier_Name,"+
                        " sum(od.Quantity) AS NumOfProducts, sum(round(od.UnitPrice * od.Quantity * (1 - od.Discount), 2)) as Total_Sales"+
                        " FROM orders o INNER JOIN orderdetails od ON o.OrderID=od.OrderID INNER JOIN products pd ON od.ProductID=pd.ProductID"+
                        " INNER JOIN categories ct ON ct.CategoryID=pd.CategoryID INNER JOIN suppliers sup ON sup.SupplierID=pd.SupplierID"+
                        " WHERE (o.OrderDate between "+"'"+start_Date+"'"+" AND  "+"'"+end_Date+"') group by ct.CategoryID,ct.CategoryName,pd.ProductName"+
                        " order by ct.CategoryID,pd.ProductName,Total_Sales desc)"+
                        " select psi.Supplier_Name AS supplierName, s.Address AS street,s.City AS city, s.Region AS region,s.postalCode AS postalCode,s.Country AS  country,"+
                        " psi.NumOfProducts AS numOfProducts, sum(psi.Total_Sales) AS totalSales"+
                        " from product_sale_info psi join suppliers s on psi.Supplier_Name=s.CompanyName"+
                        " group by psi.Supplier_Name order by totalSales");
            
                // create object for supplier details for each supplier entry in supplier details table
                // and add that object to customerList
                while (supplierSet.next()) {
                        SupplierDetails supplierObj=new SupplierDetails(
                        supplierSet.getString("supplierName"),
                        supplierSet.getString("street"),
                        supplierSet.getString("city"),
                        supplierSet.getString("region"),
                        supplierSet.getString("postalCode"),
                        supplierSet.getString("country"),
                        supplierSet.getInt("numOfProducts"),
                        supplierSet.getFloat("totalSales"));    

                    supplierList.add(supplierObj);
                 }

                /* Create list of supplier details  section for XML file by adding detais of each supplier
                from supplierList and enclose between <supplier_list> & </supplier_list>
                */
                finalResult+="\n\t\t<supplier_list>\n";
                for (int i = 0; i < supplierList.size(); i++)
                {
                    finalResult=finalResult+supplierList.get(i).printSupplierDetails();
                }
                finalResult+="\t\t</supplier_list>";
                
                finalResult+="\n"+"</period_summary>"; //Closing the period summary
                
        }//End of Try block

        // handle any SQL errors
        catch (SQLException e) 
        {
            return false;
        }

        //This block is executed to close all ResultSet, Connection and Statement objects
        finally
        {
            if (customerSet != null) {
                try { customerSet.close(); } catch (SQLException sqlEx) {  return false; }
                customerSet = null;
            }

            if (productSet != null) {
                try { productSet.close(); } catch (SQLException sqlEx) {  return false; }
                productSet = null;
            }

            if (supplierSet != null) {
                try { supplierSet.close(); } catch (SQLException sqlEx) {  return false; }
                supplierSet = null;
            }

            if (statement != null) {
                try { statement.close(); } catch (SQLException sqlEx) { return false;} // ignore
                statement = null;
            }

            if (connection != null) {
                try { connection.close(); } catch (SQLException sqlEx) { return false; } // ignore
                connection = null;
            }
        }

        //Create and write to an XML file using FileWriter
        try {
            FileWriter WriteToFile = new FileWriter(fileName);
            WriteToFile.write(finalResult+"\n");
            
            //this message notifies user if file has been created successfully or not
            System.out.println("File Created!!!!"); 
            WriteToFile.close();
        }

        //if file is not created, then no message is displayed
        catch (FileNotFoundException e) {
            return false;
        }
        catch (IOException e){
            return false;
        }

        return true;        
    }

    //Below method validate the date format (YYYY-MM-DD) using regular expression pattern
   // the regex pattern is used from a problem on tutorialspoint.com 
    private boolean ValidateDate(String date)
    {
        String regex = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match=pattern.matcher((CharSequence) date);
        if (match.matches())
            return true;   
        else
            return false;
    }

}