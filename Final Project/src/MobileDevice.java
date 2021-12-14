import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

public class MobileDevice 
{
    private String deviceInfo=""; //stores combined string of networkAddress and deviceName
    public String device_Hash_ID; //indicates unique hash value for each device
    private Map<Integer,MobileDeviceData> contactList=new HashMap<Integer,MobileDeviceData>(); //acts as local database to store contact information
    public Government contactTracer;// instance to connect to database
    private int contactIndex=0;
    private String testPositiveID=""; // stores positive test hash which is unique for each device
    private boolean contactTracerFlag=false; // indicates if goverment object is successfully created
    private boolean deviceFlag=false; // indicates if MobileDevice object is successfully created
    
     /*************************************** MobileDevice() ***************************************/
   
     //Constructor
     public MobileDevice(String configFile, Government contactTracer)
    {
        //checks if goverment objec was created
        if(contactTracer.assignFlag)
        {   
             this.contactTracer=contactTracer;
             contactTracerFlag=true;
         } 
         
        File txtfile = new File(configFile);	 // read filename
		try
		{
            String networkAddress=""; //stores network address from config file
            String deviceName=""; //stores device name from config file
			FileReader reader = new FileReader(txtfile);
            Properties props = new Properties(); 
            props.load(reader); //read .properties file 
            
			networkAddress=props.getProperty("address"); //
            deviceName=props.getProperty("deviceName");
            deviceInfo=networkAddress+" | "+ deviceName; // append networkAddress with deviceName
            deviceFlag=true; // set to indicate that MobileDevice object is created successfully
        }
        // Called if file is not found
        catch (FileNotFoundException e) {            
            System.out.println("File not found!!!");
		}
        // Called if error occured while reading the file
        catch (IOException e) {
            System.out.println("Error while reading file");
        }

        // encrypted SHA-256 hash code of MobileDevice is created from deviceInfo and assigned to device_Hash_ID
        try
        {
        MessageDigest md= MessageDigest.getInstance("SHA-256");
        byte[] digest=md.digest(deviceInfo.getBytes(StandardCharsets.UTF_8));
        device_Hash_ID=toHexString(digest);
        contactTracer.deviceList.put(device_Hash_ID, this); // storing MobileDevice's reference in goverment object to keep data of which object have access the database  
        }
        //if specified algoritm is not found
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("Import class for SHA-256");
        }

    }

     /*************************************** recordContact() ***************************************/
    
    public boolean recordContact(String individual,int date, int duration)
    {
        // Validates individual, date, duration and also checks if govt. & MobileDevice is successfully created or not
        if (!deviceFlag || !contactTracerFlag || individual.length()!=64 || date<=0 || duration <=0) {
           System.out.println("Invalid contact hash, date or duration of contact.");
            return false;
        }
        // Instantiate MobileDeviceData to store contact information
        MobileDeviceData dataObj1=new MobileDeviceData(device_Hash_ID,individual, date, duration);
       
        // Assign positive test to contact information
        if(!testPositiveID.isEmpty()) 
        {
            dataObj1.setResult(true);
            dataObj1.setTestId(testPositiveID);
        }
        contactList.put(++contactIndex, dataObj1); // stores contact entry of device with individual in contactList
    return true;
    }

     /*************************************** recordContact() ***************************************/

    //report positive test hash and result to the local contact list
    public boolean positiveTest(String testHash)
    {
        // Validates if test hash is of right length 
        if (testHash.length()!=6)
        {
            System.out.println("Invalid TestHash");
            return false;   
        }

        //Checks if govt. & MobileDevice is successfully created or not
        if (!deviceFlag || !contactTracerFlag) {
            return false;
        }
            if(testPositiveID.isEmpty())
            {
                testPositiveID=testHash; //attach testhash to MobileDevice
               
                // link testHash to existing contacts information in the contactList
                if(contactList.size()!=0)
                {  
                    for (Entry<Integer,MobileDeviceData> obj : contactList.entrySet())
                    {
                        obj.getValue().setTestId(testHash);
                        obj.getValue().setResult(true);
                    }
                }
            }
            // returns this message if device is already link to another testhash
            else
            {
                System.out.println("Device is associated with prexisting TeshHash");
                return false;
            }
        return true;
   }

    /*************************************** synchronizeData() ***************************************/
   
    public boolean synchronizeData()
    {
        //Checks if govt. & MobileDevice is successfully created or not
        if (!deviceFlag || !contactTracerFlag) {
            return false;
        }

        //Establishing connection to Database per call fo better efficiency
        if (contactTracer.allLoginInfo) 
        {  
            contactTracer.establishConnectionToDatabase(); 
        } 

        // If any error occurs
        else
        {
            System.out.println("Problem Connecting to Database");
            return false;
        }
         boolean covidIndication=false;
       
         // Sync  new or old contact information from contactList to database
         try
        {
            if (contactList.size()>0)
            {
                for (Entry<Integer,MobileDeviceData> contactObject :contactList.entrySet()) 
                {
                    String contactInfo=contactObject.getValue().contactInfo();
                    covidIndication=contactTracer.mobileContact(device_Hash_ID, contactInfo);
                }
            }
            else if (contactList.size()==0 && !testPositiveID.isEmpty())
            {
                MobileDeviceData tempdData=new MobileDeviceData();
                tempdData.setMobileId(device_Hash_ID);
                tempdData.setTestId(testPositiveID);
                tempdData.setResult(true);
                covidIndication=contactTracer.mobileContact(device_Hash_ID, tempdData.contactInfo());  
            }

            // if the contact list is empty
            else
            {
                return false;
            }
        }
         
        //Close the connection to ensure security
        finally
            {  
                if (contactTracer.statement != null) {
                    try {contactTracer.statement.close(); } catch (SQLException sqlEx) {  } // ignore
                    contactTracer.statement = null;
                }
                if (contactTracer.preparedStatement != null) {
                    try {contactTracer.preparedStatement.close(); } catch (SQLException sqlEx) {  } // ignore
                    contactTracer.preparedStatement = null;
                }
                if (contactTracer.connection != null) {
                    try {contactTracer. connection.close(); } catch (SQLException sqlEx) { } // ignore
                    contactTracer.connection = null;
                }
            } 
        return covidIndication;
    }

    /*************************************** toHexString() ***************************************/
    
     //Store hashed string value to deviceID
    private String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation 
        BigInteger number = new BigInteger(1, hash); 
  
        // Convert message digest into hex value 
        StringBuilder hexString = new StringBuilder(number.toString(16)); 
  
        // Pad with leading zeros
        while (hexString.length() < 32) 
        { 
            hexString.insert(0, '0'); 
        } 
  
        return hexString.toString(); 
    }
}
