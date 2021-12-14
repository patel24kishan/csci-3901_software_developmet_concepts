// Creates object for each supplier containing all specified details
public class SupplierDetails {

    private String supplier_name; //Store supplier name
    private String street_address; //Store street address
    private String city; //Store city name
    private String region; //Store region name
    private String postal_code; //Store postal code
    private String country; // Store country
    private int num_products; //Store number Of total products
    private float product_value; //Store total $ value of products

    //Initializing Constructor with base parameter values
    public SupplierDetails(String sN, String st,String ct,String reg,String postC,String cnty,int num,float value)
    {
        if(sN==null)
            this.supplier_name=" ";
        else
            this.supplier_name = sN; 
        
        if(st==null)
            this.street_address=" ";
        else
            this.street_address = st; 

        if(ct==null)
            this.city=" ";
        else
            this.city = ct; 

        if(reg==null)
            this.region=" ";
        else
            this.region = reg;
        
        if(postC==null)
            this.postal_code=" ";
        else
            this.postal_code = postC;

        if(cnty==null)
            this.country=" ";
        else
            this.country =cnty;
        
        this.num_products =num;
        this.product_value = value;
    }
    
    // Add each value of given variable enclosed with appropriate tags and proper intendation to Rslt string and return it
    public String printSupplierDetails()
    {
        String Rslt="";

        Rslt+="\t\t\t<supplier>\n"+
        "\t\t\t\t<supplier_name> "+supplier_name+" </supplier_name>\n"+
        "\t\t\t\t<address>\n"+
        "\t\t\t\t\t<street_address> "+street_address+" </street_address>\n"+
        "\t\t\t\t\t<city> "+city+" </city>\n"+
        "\t\t\t\t\t<region> "+region+" </region>\n"+
        "\t\t\t\t\t<postal_code> "+postal_code+" </postal_code>\n"+
        "\t\t\t\t\t<country> "+country+" </country>\n"+
        "\t\t\t\t</address>\n"+
        "\t\t\t\t<num_products> "+num_products+" </num_products>\n"+
        "\t\t\t\t<product_value> "+product_value+" </product_value>\n"+
        "\t\t\t</supplier>\n";

        return Rslt;
    }

}
