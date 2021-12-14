import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;


public class Government
{
    //remove
    public Map<String,MobileDevice> deviceList=new HashMap<String,MobileDevice>(); // stores device_Hash of each contacting MobleDevice
    private String database=""; //stores database address from config file
    private String userName=""; //stores user name from config file
    private String password=""; //stores password from config file
    public  boolean allLoginInfo=false;
    public boolean assignFlag=true;
    public Statement statement = null; 
    public PreparedStatement preparedStatement=null;
    public Connection connection = null;
    private int index=0; //for storing default_id in testDatabase
    
    
    /*************************************** Government() for .properties file ***************************************/
    
    //Main Constructor
    //This method doesn't handle empty file, blank lines, extra/white space 
    public Government(String configFile)
    {
        File txtfile = new File(configFile);// read filename
		try
		{
			FileReader reader = new FileReader(txtfile);
            Properties props = new Properties();
            props.load(reader);
            this.database=props.getProperty("database");
            this.userName=props.getProperty("user");
            this.password=props.getProperty("password");	

             //Checks if object has every info needed for accessing dB
             if(database!=null && userName!=null && password!=null) 
                allLoginInfo=true;
        }
        // in case file not found or error occurs while reading the file
        catch (FileNotFoundException e) 
        {            
            assignFlag=false;
            System.out.println("File not found!!!");
		} catch (Exception e) {
           System.out.println("Error occurred while reading Gov. config file");
        }
    }

    /*************************************** mobileContact() ***************************************/
  
    public boolean mobileContact(String initiator,String contactInfo)
    {
       String[] tempData=contactInfo.split("\n"); //Stackoverlfow
       String Mobile_ID=""; 
       String Contact_ID="";  
       int NumOfDays=0; 
       int DurationOfContact=0; 
       String Test_ID=""; 
       int DaysOfTest=0;  
       boolean Result=false;
       for (String stringObject : tempData) 
       { 
            if(stringObject.length()>0)	 //ignore blank line from the file
            { 
                //read Mobile_ID
                if(stringObject.contains("Mobile_ID") ) 
                        Mobile_ID=stringObject.substring(stringObject.lastIndexOf("=")+1).replaceAll(" ", "");
                
                //read Contact_ID
                else if(stringObject.contains("Contact_ID") ) 
                        Contact_ID=stringObject.substring(stringObject.lastIndexOf("=")+1).replaceAll(" ", "");
                
                //read NumOfDays
                else if(stringObject.contains("NumOfDays") ) 
                        NumOfDays=Integer.parseInt(stringObject.substring(stringObject.lastIndexOf("=")+1).replaceAll(" ", ""));
                
                else if(stringObject.contains("DurationOfContact") ) 
                        DurationOfContact=Integer.parseInt(stringObject.substring(stringObject.lastIndexOf("=")+1).replaceAll(" ", ""));
                
                //read Test_ID
                else if(stringObject.contains("Test_ID") ) 
                        Test_ID=stringObject.substring(stringObject.lastIndexOf("=")+1).replaceAll(" ", "");
                
                //read DaysOfTest
                else if(stringObject.contains("DaysOfTest") ) 
                        DaysOfTest=Integer.parseInt(stringObject.substring(stringObject.lastIndexOf("=")+1).replaceAll(" ", ""));
                
                //read Resulr
                else if(stringObject.contains("Result") ) 
                        Result=Boolean.parseBoolean(stringObject.substring(stringObject.lastIndexOf("=")+1).replaceAll(" ", "").toString());

                else
                {
                    //Ignore
                }          
            }          
        }

            try 
            {
                 ResultSet rslt=null;
                 String query="";
                //Insert data from MobileDevice to the database 
               if(!Mobile_ID.isEmpty() && !Contact_ID.isEmpty())
                {
                     //this query helps to insert data of 2 same devices who made contact on different days
                    query="select * from contactInformation ct where ct.Mobile_ID='"+Mobile_ID.toString()+"'AND ct.Contact_ID='"+Contact_ID.toString()+"' AND ct.NumOfDays="+NumOfDays+";";
                    rslt=statement.executeQuery(query);
                }

                // checks if data already exists to avoid duplication
                if(rslt!=null && !rslt.next()) 
                {
                    //Update contactInformation
                    int id=0;
                    preparedStatement=connection.prepareStatement("INSERT INTO contactInformation VALUES (?,?,?,?,?,?,?,?)");
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, Mobile_ID);
                    preparedStatement.setString(3, Contact_ID);
                    preparedStatement.setInt(4, NumOfDays);
                    preparedStatement.setInt(5, DurationOfContact);
                    preparedStatement.setString(6, Test_ID);
                    preparedStatement.setInt(7, DaysOfTest);
                    preparedStatement.setBoolean(8, Result);
                    int i=preparedStatement.executeUpdate();
                   
                    //Insert into  contactInformation if testDatabase contains Mobile_ID 
                    // Insert to testDatabase if current testhash!=null & Test_ID has positive result associated with it
                    String tmpString="select * FROM testDatabase WHERE Mobile_ID='"+Mobile_ID+"';";
                    rslt=statement.executeQuery(tmpString);
                    if(rslt.next())
                    {
                        if (Test_ID!=null && Result==true) {
                             tmpString ="UPDATE testDatabase SET TestHash='"+Test_ID+"', TestResult="+Result+" WHERE Mobile_ID='"+Mobile_ID+"';";
                            statement.executeUpdate(tmpString);
                        }   
                    }
                }
               else
                {
                    //Update testDatabase with new data for same entry from contactList
                    //Update teshash for a device if positive is called after first syncdata()
                    if (!Test_ID.isEmpty())
                    {
                       String tmpString="select * FROM testDatabase WHERE Mobile_ID='"+Mobile_ID+"';";
                       rslt=statement.executeQuery(tmpString);
                    if(rslt.next())
                    {
                       query="UPDATE testDatabase SET TestHash='"+Test_ID+"', TestResult="+Result+" WHERE Mobile_ID='"+Mobile_ID+"';";
                       statement.executeUpdate(query);
                       rslt.close();
                    }
                    else
                    {
                        preparedStatement=connection.prepareStatement("INSERT IGNORE INTO testDatabase VALUES (?,?,?,?)");
                        preparedStatement.setString(1, Mobile_ID);
                        preparedStatement.setString(2, Test_ID);
                        preparedStatement.setBoolean(3, Result);
                        preparedStatement.setInt(4, NumOfDays);
                        preparedStatement.executeUpdate();   
                    }
                   }
                   else
                   {
                       //IGNORE
                   }
                }
               
                //Update testDatabase with new contactInfo
                if(!Mobile_ID.isEmpty() && !Contact_ID.isEmpty() && !Test_ID.isEmpty())
                {
                    query="insert ignore into testDatabase (Mobile_ID,TestHash,TestResult) values"+
                    " ('"+Mobile_ID+"','"+Test_ID+"',"+Result+")"+
                    ", ('"+Contact_ID+"',"+null+","+false+");";
                    statement.executeUpdate(query);
                }
                else if(Mobile_ID.isEmpty() && !Contact_ID.isEmpty() && Test_ID==null)
                {
                    query="insert ignore into testDatabase (Mobile_ID,TestHash,TestResult) values"+
                    " ('"+Mobile_ID+"','"+Test_ID+"',"+null+")"+
                    ", ('"+Contact_ID+"',"+null+","+null+");";
                    statement.executeUpdate(query);
                }
             } 
             // If any error occurs while updating data in either table
            catch (SQLException e)
            {
                System.out.println("Not able to synch data with the Database");
            } 
            catch (NullPointerException e)
            {
                System.out.println("Not able to synch data with the Database");
            } 

                //Update testDatabase after recordeTestResult() is called by matching testhash from testDatabase 
               //update testhash, test day, result if they are null in the table
               //Update Mobile_id in testDatabase from recenlty updated testDatabase 
             
               //check for positive contact
               boolean pos=synchDatabase(Mobile_ID);
               if (pos) 
               {
                return false;
               }     
        return true;
    }

    /*************************************** recordTestResult() ***************************************/
    
    public boolean recordTestResult(String testHash,int date,boolean result)
    {
        //Validates if testHaish is of correct size
        if (testHash.length()!=6 || date<=0)
        {
            System.out.println("Check your TestHash or Date");
            return false;   
        }
        //establish connection to the database
         if (allLoginInfo)
        {  
            establishConnectionToDatabase(); 
        } 
        // Error message
        else
        {
            System.out.println("********TROUBLE CONNECTING TO DATABASE********");
            return false;
        }
        try
        {
            // checks if data already exists and if not insert new as new data
            String query="Select * from testDatabase WHERE TestHash='"+testHash+"'";
            ResultSet rslt=statement.executeQuery(query);
            if(rslt!=null && !rslt.next()) 
            {
                String tmpstr="default_device_"+(index++);
               query="insert ignore into testDatabase (Mobile_ID,TestHash,TestResult,TestDate) values"+
            " ('"+tmpstr+"','"+testHash+"',"+result+","+date+");";
            statement.executeUpdate(query);
            }
            // update existing data with same testHash
            else
            {
                query="update testDatabase SET TestDate="+date+",TestResult="+result+" where TestHash='"+testHash+"';";
                statement.executeUpdate(query);
            }
        }
        //Error message if any error occured
        catch(SQLException e)
        {
            System.out.println("Error in recording the test result");
        }
        catch(NullPointerException e)
        {
             System.out.println("Error in recording the test result");
        }

        //Close connection to database 
        finally
            {  
                if (this.statement != null) {
                    try {this.statement.close(); } catch (SQLException sqlEx) {  } // ignore
                    this.statement = null;
                }
                if (this.connection != null) {
                    try {this. connection.close(); } catch (SQLException sqlEx) { } // ignore
                    this.connection = null;
                }
            } 
        return true;
    }

    /*************************************** establishConnectionToDatabase() ***************************************/
   
    // Creates connection and statement instances 
    public void establishConnectionToDatabase()
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } 
            
        //handle jdbc connection errors
        catch (Exception ex) {
    
            System.out.println("Error in jdbc Driver");
        }
    
        // Establish connection to the kahodariya database using login credentials
        try
        {
            connection = DriverManager.getConnection(database,userName,password);
            statement=connection.createStatement();
             }
         // handle any SQL errors
        catch (SQLException e) 
        {
            System.out.println("Error establishing connect to the database");
        }
    }

    /*************************************** synchDatabase() ***************************************/
    
    private boolean synchDatabase( String deviceID)
    {
        
        try {
            //Update testDatabase within itself
            this.statement.addBatch("UPDATE ignore testDatabase parent"+
                                    " join (select * from testDatabase where Mobile_ID regexp \"^default\") as child"+
                                    " ON  parent.TestHash=child.TestHash"+
                                    "  SET parent.TestDate=child.TestDate;");

           
            //Update contactInformation using testDatabase
            this.statement.addBatch("UPDATE ignore contactInformation CI"+
                                     " JOIN testDatabase TD "+
                                     " ON  TD.Mobile_ID=CI.Mobile_ID"+
                                     " SET    CI.Test_ID = TD.TestHash;");

            this.statement.addBatch("UPDATE ignore contactInformation CI"+
                                     " JOIN testDatabase TD "+
                                     " ON CI.Test_ID = TD.TestHash"+
                                     " SET    CI.TestDate=TD.TestDate;");
        
            //Update testDatabase using contactInformation  
            this.statement.addBatch("UPDATE ignore testDatabase TD"+
                                    " JOIN contactInformation CI"+
                                    " ON CI.Test_ID = TD.TestHash"+
                                    " SET  TD.Mobile_ID=CI.Mobile_ID;");
            
            statement.executeBatch();

            //Check for positive contact
            String tmpQuery="WITH info as (SELECT * from contactInformation"+
                " where Mobile_ID='"+deviceID+"')"+
                " Select pi.* from info pi"+
                " inner join testDatabase td on td.Mobile_ID=pi.Contact_ID"+
                " where td.TestDate>=0 AND td.TestResult=true and abs(td.TestDate-pi.NumOfDays) <=14;";
            ResultSet resultSet=statement.executeQuery(tmpQuery);

            if (resultSet.next()) 
                return false;

        } catch (SQLException e) {
            System.out.println("Problem while synching DataBase");
        }
        return true;
    }

    /*************************************** findGatherings() ***************************************/
   
    public int findGatherings(int date,int minSize,int minTime,float density)
    {
        Map<String,List<Pair>> gatheringList= new  HashMap<String,List<Pair>>(); //stores all possible contact pairs for specified date
        Map<String,List<Pair>> finalGatheringList= new  HashMap<String,List<Pair>>(); // store list of pairs which may form a large gathering
        Integer numberOfLargeGathering=0;
        
        // Validates date, minSize, minTime and density ans ensure it they are non zero and non-negative
        if(date<=0 || minSize<0 || minTime<=0 || density<0)
        {
            System.out.println("Invalid input. Check your date, minSize, mintime or density");
            return numberOfLargeGathering;
        }

        //establish connection to the Database
        if (allLoginInfo)
        {  
            establishConnectionToDatabase(); 
        } 

        //get list of contact pairs from contactInformation table for date and who have met of atleast minTIme
        try 
        {
            List<Pair> pairList= new ArrayList<Pair>();
            Set<String> tmpSet=new HashSet<String>();
          ResultSet result=statement.executeQuery("select * from contactInformation WHERE NumOfDays="+date+
          "  AND Contact_Duration>="+minTime+";");
          while(result.next())
          { 
           Pair tmp1=new Pair(result.getString("Mobile_ID"),result.getString("Contact_ID"));
           Pair tmp2=new Pair(result.getString("Contact_ID"),result.getString("Mobile_ID"));
              
              if (!pairList.contains(tmp1) && !pairList.contains(tmp2))
                    pairList.add(tmp1);
              
              tmpSet.add(result.getString("Mobile_ID")) ; 
              tmpSet.add(result.getString("Contact_ID")) ;     
          }

          List<String> individualList = new ArrayList<String>(tmpSet); //convert tmpSet to a indexed list
            
          //generate set of pairs S who have interacted with set of both A an B from individualList
          for (int i = 0; i < individualList.size(); i++)
          {
              for (int j = i+1; j < individualList.size(); j++)
              {
                    String A="",B="";
                    A=individualList.get(i); B=individualList.get(j);   
                    List<Pair> tmp= new ArrayList<Pair>();
                    
                    for (int pairIndex = 0; pairIndex < pairList.size(); pairIndex++) 
                    { 
                        if (pairList.get(pairIndex).has(A) || pairList.get(pairIndex).has(B))
                        {
                            tmp.add(pairList.get(pairIndex));
                        }   
                    }
                    gatheringList.put(A+"_"+B, tmp);
              }
          } 

          //Calculate intersection of individuals and generate number of pairs c
          for (Entry<String,List<Pair>> obj: gatheringList.entrySet())
          {
                List<Pair> tmpFinalList= findPairs(obj.getValue());
                if(tmpFinalList.size()!=0)
                        finalGatheringList.put(obj.getKey(),tmpFinalList);
          }

          //Calculate fraction c/m and report any large gathering if faction value is less then density
          for (Entry<String,List<Pair>> obj: finalGatheringList.entrySet())
           {
               int c=obj.getValue().size();
               float m=0;
               Set <String> indSet=new HashSet<String>();
               
               //Add each device hash from paie list to a set, to get set of unique Device_Hash_ID
                 for (int i = 0; i < obj.getValue().size(); i++)
                  {
                  indSet.add(obj.getValue().get(i).getA());
                  indSet.add(obj.getValue().get(i).getB());
                 }
                int n=indSet.size(); //calculate n (size of Set)
                 
                if(n>=minSize)
                {
                    m=(n*(n-1))/2; //calculate m
                    float fractionValue=c/m;
                
                    //if c/m is less than density, then increment the counter
                    if (fractionValue>density)
                        numberOfLargeGathering++;  
                }          
            }
        } 

        //Handles any type of error while accesing Database
        catch (Exception e) {
            System.out.println("Error while fetching data for gatherings from Database");
            e.printStackTrace();
        }

        //Close connection the Database
        finally
            {  
                if (this.statement != null) {
                    try {this.statement.close(); } catch (SQLException sqlEx) {  } // ignore
                    this.statement = null;
                }
                if (this.connection != null) {
                    try {this. connection.close(); } catch (SQLException sqlEx) { } // ignore
                    this.connection = null;
                }
            } 

        return numberOfLargeGathering;
    }

    /*************************************** findPairs() ***************************************/
   
    //Internally used by findGatherings() to generate set S by comparing each pair for each set A and B generated from individualList  
    private List<Pair> findPairs(List<Pair> pairs)
    {
        String a,b,c,d;
        List<Pair> final_list = new ArrayList<>();
        for (int i = 0; i < pairs.size(); i++) {
            a = pairs.get(i).getA();
            b = pairs.get(i).getB();

            for(int j = 0; j < pairs.size(); j++){
                if(i != j){
                    c = pairs.get(j).getA();
                    d = pairs.get(j).getB();
                    
                    if(a.equals(c)){
                        if(findPairWith(pairs, new Pair(b,d))){
                            if(!findPairWith(final_list, new Pair(a,b))){
                                final_list.add(new Pair(a,b));
                            }
                            if(!findPairWith(final_list, new Pair(c,d))){
                                final_list.add(new Pair(c,d));
                            }
                            if(!findPairWith(final_list, new Pair(b,d))){
                                final_list.add(new Pair(b,d));
                            }
                        }
                    }
                    else if(a.equals(d)){
                        if(findPairWith(pairs, new Pair(b,c))){
                            if(!findPairWith(final_list, new Pair(a,b))){
                                final_list.add(new Pair(a,b));
                            }
                            if(!findPairWith(final_list, new Pair(c,d))){
                                final_list.add(new Pair(c,d));
                            }
                            if(!findPairWith(final_list, new Pair(b,c))){
                                final_list.add(new Pair(b,c));
                            }
                        }
                    }
                    else if(b.equals(c)){
                        if(findPairWith(pairs, new Pair(a,d))){
                            if(!findPairWith(final_list, new Pair(a,b))){
                                final_list.add(new Pair(a,b));
                            }
                            if(!findPairWith(final_list, new Pair(c,d))){
                                final_list.add(new Pair(c,d));
                            }
                            if(!findPairWith(final_list, new Pair(a,d))){
                                final_list.add(new Pair(a,d));
                            }
                        }
                    }
                    else if(b.equals(d)){
                        if(findPairWith(pairs, new Pair(a,c))){
                            if(!findPairWith(final_list, new Pair(a,b))){
                                final_list.add(new Pair(a,b));
                            }
                            if(!findPairWith(final_list, new Pair(c,d))){
                                final_list.add(new Pair(c,d));
                            }
                            if(!findPairWith(final_list, new Pair(a,c))){
                                final_list.add(new Pair(a,c));
                            }
                        }
                    }
                }
            }
        }
        return final_list;
    }
    
    /*************************************** findPairWith() ***************************************/
  
    // Used within findPairs()
    private  boolean findPairWith(List<Pair> pairs, Pair pair) {
        for (Pair p : pairs) {
            if (p.equals(pair)) {
                return true;
            }
        }
        return false;
    }

     /*************************************** clearDataBase() ***************************************/

     //Used within Testing framework only to clear the database before testing any test case 
    public boolean clearDataBase()
    {
        establishConnectionToDatabase();
        try {
            statement.executeUpdate("truncate table contactInformation;");
            statement.executeUpdate("truncate table testDatabase;");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        catch(NullPointerException e)
        {
            return false;
        }
        return true;
    }
}
