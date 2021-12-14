/* This class acts as a data object which helps to stores every contact Information 
   between 2 unique Mobiledevices 
*/
public class MobileDeviceData 
{
    private String Mobile_ID=""; 
    private String Contact_ID="";  
    private int NumOfDays=0; 
    private int DurationOfContact=0; 
    private String Test_ID=""; 
    private int DaysOfTest=0;  
    private boolean Result =false;

    public MobileDeviceData()
    {
    }

    //Main Constructor
    public MobileDeviceData(String mID,String cID,int days,int time)
    {
        this.Mobile_ID=mID;
        this.Contact_ID=cID;
        this.NumOfDays=days;
        this.DurationOfContact=time;
    }

    //set Mobile_ID
    public void setMobileId(String Mid)
    {
        this.Mobile_ID=Mid;
    }

    //set Test_ID
    public void setTestId(String Tid)
    {
        this.Test_ID=Tid;
    }

    //set Result
    public void setResult(boolean rslt)
    {
        this.Result=rslt;   
    }

    // Append each available contact information to a single string, which is then passed to Governement's MobileContact()
    public String contactInfo()
    {
        String Rslt="Mobile_ID="+Mobile_ID+"\n"+
                    "Contact_ID="+Contact_ID+"\n"+
                    "NumOfDays="+NumOfDays+"\n"+
                    "DurationOfContact="+DurationOfContact+"\n"+
                    "Test_ID="+Test_ID+"\n"+
                    "DaysOfTest="+DaysOfTest+"\n"+
                    "Result="+Result;

        return Rslt;
    }
}
