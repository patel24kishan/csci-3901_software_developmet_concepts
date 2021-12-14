public class Cell 
{
    private int cellValue=0;
    private String category;

    
    public Cell(String cValue,int intvalue)
    {
        this.category=cValue;
        this.cellValue=intvalue;
    }
    public void setCellValue(int value)
    {
        this.cellValue=value;
    }

    public void setCategory(String value)
    {
        this.category=value;
    }

    public int getCellValue()
    {
        return cellValue;
    }
    public String getCategory()
    {
        return category;   
    }
    
}
