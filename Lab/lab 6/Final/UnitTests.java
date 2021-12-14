import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class UnitTests
 {
	  HfxDonairExpress hfx = new HfxDonairExpress();
	// Order small Donair
        @Test
        public void orderSmallDonair() throws Exception {
        	
              assertEquals(5, hfx.order(0, 0, null, null)); 
              assertEquals(7, hfx.order(0, 2, null, null)); 
        }
        
     // Order large Donair
        @Test
        public void orderBigDonair() throws Exception 
        {
        	DecimalFormat df2 = new DecimalFormat("#.##");
        	Double resultDouble=Double.parseDouble(df2.format(hfx.order(0, 2, null, "YOLO5")));
            assertEquals(6.65,resultDouble);     
        }
        
     // Order medium pizza with pepperoni & mushroom
        @Test
        public void orderMediumPizza() throws Exception 
        {
        	ArrayList<String> toppings=new ArrayList<String>(Arrays.asList("pepperoni","mushroom"));
        	DecimalFormat df2 = new DecimalFormat("#.##");
        	Double resultDouble=Double.parseDouble(df2.format(hfx.order(1, 1, toppings, null)));
            assertEquals(10.75,resultDouble);     
        }

        
     // call makeOrder
        @Test
        public void makeOrderTest() throws Exception 
        {
        	
            assertEquals(0, hfx.makeOrder(0, 0, null));     
        }  
        
     // call multiple makeorder
        @Test
        public void multipleMakeOrderTest() throws Exception 
        {
        	ArrayList<String> toppings=new ArrayList<String>(Arrays.asList("pepperoni","mushroom"));
        	 assertEquals(0, hfx.makeOrder(0, 0, null));     
        	 assertEquals(1,hfx.makeOrder(1, 1, toppings));  
        	 assertEquals(2, hfx.makeOrder(0, 2, null));   
        }
}