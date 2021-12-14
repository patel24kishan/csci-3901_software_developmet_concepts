import java.util.List;



public class myOperations
{
    private int value=0;
    private String operation=null;
    private List<CellLocation> locationList;
    
    public void setValue(int val)
    {
      this.value= val;
    }

    public void setOperations(String Op)
    {
        this.operation=Op;   
    }

    public void setLocation(List<CellLocation> loc)
    {
        this.locationList=loc;   
    }
    public int getValue()
    {
        return this.value;
    }

    public String getOperation()
    {
        return this.operation;
    }

    public List<CellLocation> getLocation()
    {
        return this.locationList;   
    }
    
    public String printLocation()
    {   String rslt="";
        
        for (int i = 0; i < locationList.size(); i++) {
            rslt+="("+locationList.get(i).getX()+","+locationList.get(i).getY()+"),";
        }
        return rslt;
    }
    
}