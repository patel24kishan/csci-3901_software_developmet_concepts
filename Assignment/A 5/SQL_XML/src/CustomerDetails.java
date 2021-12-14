// Creates object for each customer containing all specified details
public class CustomerDetails
{
      private  String customerName; //Store customer name
      private  String street; //Store street address
      private  String city; //Store city
      private  String region; //Store region name
      private  String postalCode; //Store postal code
      private  String country; // Store country
      private  int numOfOrders; //Store number Of Orders
      private  float orderValue; //Store total value of Orders

      //Initializing Constructor with base parameter values
      public CustomerDetails(String cN, String st, String ct, String rg,String pC,String cnty,int num,float val)
       {    
            if (cN==null) 
                this.customerName=" ";
            else
                this.customerName = cN;
           
           if (st==null) 
                this.street = " ";
           else
                this.street=st;

           if (ct==null)      
                this.city = " ";
            else
                this.city = ct;
           
           if (rg==null)
                this.region =" ";
           else
                this.region=rg;

           if (pC==null)    
                 this.postalCode = " ";
            else
                this.postalCode = pC;
          
           if (cnty==null)
                this.country = " ";
           else
                this.country =cnty;
           
           this.numOfOrders = num;
           
           this.orderValue = val;
       }

       // Add each value of given variable enclosed with appropriate tags and proper intendation to Rslt string and return it
       public String printCustomerDetails()
       {
           String Rslt="";
        Rslt+="\t\t\t<customer>\n"+
        "\t\t\t\t<customerName> "+customerName+" </customerName>\n"+
        "\t\t\t\t<address>\n"+
        "\t\t\t\t\t<street_address> "+street+" </street_address>\n"+
        "\t\t\t\t\t<city> "+city+" </city>\n"+
        "\t\t\t\t\t<region> "+region+" </region>\n"+
        "\t\t\t\t\t<postal_code> "+postalCode+" </postal_code>\n"+
        "\t\t\t\t\t<country> "+country+" </country>\n"+
        "\t\t\t\t</address>\n"+
        "\t\t\t\t<num_orders> "+numOfOrders+" </num_orders>\n"+
        "\t\t\t\t<order_value> "+orderValue+" </order_value>\n"+
        "\t\t\t</customer>\n";
       
        return Rslt;
       }
}
