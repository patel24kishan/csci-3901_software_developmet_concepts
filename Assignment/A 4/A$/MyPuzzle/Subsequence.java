import java.util.*;
public class Subsequence {
 
    // Function to find the subsequences
    // with given sum
    public static void subSequenceSum(
        ArrayList<ArrayList<Integer>> ans, 
        int a[], ArrayList<Integer> temp, 
                        int k, int start)
    {
        int size=3;
        // Here we have used ArrayList
        // of ArrayList in in Java for 
        // implementation of Jagged Array
 
        // if k < 0 then the sum of
        // the current subsequence
        // in temp is greater than K
        if(start > a.length || k < 0)
            return ;
 
        // if(k==0) means that the sum 
        // of this subsequence
        // is equal to K
        if (temp.size()==size)
        {    
        
        if(k == 0)
        {
            ans.add(
             new ArrayList<Integer>(temp)
             );
            return ;
        }
    }
        else {
            for (int i = start; 
                 i < a.length; i++) {
 
                // Adding a new 
                // element into temp
                temp.add(a[i]);
 
                // After selecting an
                // element from the
                // array we subtract K
                // by that value
                subSequenceSum(ans, a, 
                   temp, k - a[i],i+1);
 
                // Remove the lastly 
                // added element
                temp.remove(temp.size() - 1);
            }
        }
    }
 
    public static List<List<Integer>>  subSequenceMultiply(int puzzleSizeValue, int kOutput,int pairSize)
    {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= puzzleSizeValue; i++)
        {
            list.add(i);
        }
        List<List<Integer>> subsetList=getAllSubsets(list,pairSize);
        System.out.println("---------------------subsetList--------------------");
        System.out.println(subsetList);
        System.out.println("-----------------------------------------");
        List<List<Integer>> resultList=new ArrayList<List<Integer>>();
        System.out.println("---------------------ResultList--------------------");
       
        System.out.println("-----------------------------------------");
       
        for (List<Integer> subsetObj : subsetList)
        {
            int productValue=1;
            for (Integer objValue : subsetObj)
            {
                productValue*=objValue;
            }   
            
            System.out.println("ProductValue="+productValue);
            if (productValue==kOutput)
              {  resultList.add(subsetObj);}
        }

        return resultList;
    }

    public static List<List<Integer>> getAllSubsets(List<Integer> input,int pairSize) {
        int allMasks = 1 << input.size();
        List<List<Integer>> output = new ArrayList<List<Integer>>();
        for(int i=0;i<allMasks;i++) {
            List<Integer> sub = new ArrayList<Integer>();
            for(int j=0;j<input.size();j++) {
                if((i & (1 << j)) > 0) {
                    sub.add(input.get(j));
                }
            }
            if (sub.size()==pairSize) {
                output.add(sub);
            }
           
        }
    
        return output;
    }

     private static List<List<Integer>> subSequenceMultiply(int puzzleSizeValue, int output, int pairSize,
			List<Integer> tmp_List,List<List<Integer>> ResultList){
		
                List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= puzzleSizeValue; i++)
        {
            list.add(i);
        }
		
		if (tmp_List.size() == pairSize) {
			int product = 1;
			
            for (int value: tmp_List) {
				product *= value;
			}
		
			if (product == output) {
				ResultList.add(new ArrayList<Integer>(tmp_List));
			}	
            System.out.println("---------------------ResultList--------------------");
            System.out.println(" Result-list="+ResultList);	
             System.out.println("-----------------------------------------");	
           
			return ResultList;
		}

		for (Integer i: list) {
			
			// add a number to the solution
			tmp_List.add(i);
			System.out.println(" tmp-list="+tmp_List);
			subSequenceMultiply(puzzleSizeValue, output, pairSize, tmp_List, ResultList);
			
			// keep on changing the last element with all the possible combinations
			tmp_List.remove(tmp_List.size()-1);
		}
		return ResultList;
	}
    // Driver Code
    public static void main(String args[])
    {
        List<Integer> tmp_List= new ArrayList<Integer>();
        List<List<Integer>> ResultList= new ArrayList<List<Integer>>();
       System.out.println(subSequenceMultiply(5, 75,3,tmp_List,ResultList));
        
    }

}