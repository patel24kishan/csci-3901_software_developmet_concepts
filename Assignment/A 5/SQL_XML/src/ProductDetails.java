// Creates object for each product containing all specified details
public class ProductDetails {

    private String category; //Store products's category
    private String productName; // Store product name
    private String supplierName; //Store supplier name
    private int units_Sold;// Store total uni of each product sold
    private float sale_Value; // Store total $ value of each product

    //Initializing Constructor with base parameter values
    public ProductDetails(String ct, String pN, String sN,int uS,float sV)
    {
        if(ct==null)
            this.category=" ";
        else
            this.category=ct;

        if(pN==null)
            this.productName =" ";
        else
            this.productName=pN;

         if(sN==null)   
            this.supplierName=" ";
         else
            this.supplierName =sN;
            
        this.units_Sold = uS;
        this.sale_Value =sV;
    }

    // Add each value of given variable enclosed with appropriate tags and proper intendation to Rslt string and return it
    public String printProductdetails()
    {
        String Rslt="";
        Rslt+="\n\t\t\t<product>\n"+
        "\t\t\t\t<product_name> "+productName+"</product_name>\n"+
        "\t\t\t\t<supplier_name> "+supplierName+"</supplier_name>\n"+
        "\t\t\t\t<unit_sold> "+units_Sold+"</unit_sold>\n"+
        "\t\t\t\t<sale_value> "+sale_Value+" </sale_value>\n"+
        "\t\t\t</product>";

        return Rslt;
    }
}
