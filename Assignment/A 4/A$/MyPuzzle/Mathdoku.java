import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
 
 
 
public class Mathdoku
 {
 
    public List<List<Cell>> Matrix=new ArrayList<List<Cell>>(); //Stores Puzzle Matrix
   
    public List<String> bufferList=new ArrayList<String>(); // temporary stores string fromm bufferStream
    public Map<String,myOperations> operationsList=new HashMap<String,myOperations>(); //store operation
    public Map<String,List<List<Integer>>> possibleValuesSetByGroup=new HashMap<String,List<List<Integer>>>(); //store operation
 
    public int puzzleSizeValue=0;
    public boolean ReadPuzzleFileFlag=false,ValidateFlag=false;
 
    public int[][] TempSolutionMatrix;
    public int traversalChoices=0; //Number of choices before solution was found or not found
    // Initialize Constructor
    public Mathdoku() {
 
    }
/**************************************** ReadPuzzleFile method *****************************************/
    private boolean ReadPuzzleFile( BufferedReader br)
     {
        String Line="";
             
           // System.out.println(br.toString());
             try {
               while ((Line = br.readLine()) != null) {
                   if (!Line.isBlank()) {
                       bufferList.add(Line);
                   } else // Consider blank line as invalid input
                   {
                       System.out.println("Invalid Input !!!");
                       return false;
                   }
               }
               br.close();
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
       
           ReadPuzzleFileFlag=true;
       return true;
    }
 
/**************************************** Validate method *****************************************/
    public boolean validate()
   {
       boolean intFlag=true;
        
       if (ReadPuzzleFileFlag) // Checks if puzzle is read successfully
        {
           
       try 
       {    
           if(intFlag) // checking valid if first line is Integer value
           {
               puzzleSizeValue= Integer.parseInt(bufferList.get(0)); 
               TempSolutionMatrix=new int[puzzleSizeValue][puzzleSizeValue]; //Initialize temporary matrix for back tracking
                System.out.println("Valid n Value !!!\n puzzleValue= "+puzzleSizeValue);
                intFlag=false;
            }
            else
            {
                return false;
            }
       }  
       catch (NumberFormatException e) 
       { 
           System.out.println("Invalid n value !!!"); 
       } 
 
       // Checks puzzle matrix and assign the matrix
       for (int i = 1; i <= puzzleSizeValue; i++)
        {
           List<Cell> tmp=new ArrayList<Cell>();
           String TmpString=bufferList.get(i).replaceAll("\\s+",""); //Remove white spaces between characters
           if (TmpString.length()==puzzleSizeValue)
           {
           for (int j = 0; j < TmpString.length(); j++)
           {
               
               String C=Character.toString(TmpString.charAt(j));
               Cell tmpCell=new Cell(C, 0);
               tmp.add(tmpCell); 
           }
           Matrix.add(tmp);
        }
        else
        {
            System.out.println("Invalid number of cells!!");
            return false;
        }
       }
 
        // Checks Operations are valid
        for (int i = puzzleSizeValue+1; i <bufferList.size(); i++)
        {
            String[] tmpString=bufferList.get(i).split("\\s+");
            String operationKey="";
            myOperations myObject=new myOperations();
 
                if (tmpString[0].length()==1) {
                    if (1<tmpString.length && tmpString[1].matches("-?\\d+")) {
                        
                        operationKey=tmpString[0];
                        myObject.setValue(Integer.parseInt(tmpString[1]));
                        if (2<tmpString.length) {
                            myObject.setOperations(tmpString[2]);
                        }
                        else
                        {
                            System.out.println("NO Operation found");
                            return false;
                        }
                    }
                    else{
                        System.out.println("Invalid Integer value !!!");
                        return false;
                    }
                 }
                 else{
                     System.out.println("Invalid Operation String !!!");
                     return false;
                 }
            operationsList.put(operationKey, myObject);
        }
    }
    else //returns false if file not read successfully
    {
        return false;
    }
       //Display Matrix
        displayMatrix();
 
    for (Map.Entry<String,myOperations> obj1: operationsList.entrySet())
    {
        List<CellLocation> locations=new ArrayList<CellLocation>();
        String category=obj1.getKey();
        for (int i = 0; i < Matrix.size(); i++)
        {
           for (int j = 0; j < Matrix.get(i).size(); j++)
           {
               String matrixCategory=Matrix.get(i).get(j).getCategory();
               if(matrixCategory.equals(category))
               {
                CellLocation loc=new CellLocation(i, j);
                locations.add(loc);
               }
            }
           
        }
        obj1.getValue().setLocation(locations);
    }
    
       System.out.println("-----------------------------------------");
       for (Map.Entry<String,myOperations> obj: operationsList.entrySet()){
    System.out.println(obj.getKey()+" "+obj.getValue().getValue()+" "+obj.getValue().getOperation() +" locations(i,j) "+ obj.getValue().printLocation());
       }
       System.out.println("-----------------------------------------");
     
       ValidateFlag=true;   //Checkvalidateflag for successfull validation
     ///Check if number of cells are valid according to the type of operations
       return    checkNumberOfCells(Matrix);
   }
 
   /**************************************** CheckNumberOfCells method *****************************************/
   private boolean checkNumberOfCells (List<List<Cell>> list)
   {
    List<String> CellList=new ArrayList<String>();
      for (int i = 0; i < list.size(); i++)
      {
          for (int j = 0; j < list.get(i).size(); j++) {
           CellList.add(list.get(i).get(j).getCategory());
      }
    }
      // hashmap to store the frequency of element 
      Map<String, Integer> CellCount = new HashMap<String, Integer>(); 
  
      //Calculating occurences of each cell
      for (String i : CellList) { 
          Integer j = CellCount.get(i); 
          CellCount.put(i, (j == null) ? 1 : j + 1); 
      } 
 
      // displaying the occurrence of elements in the arraylist 
    //   for (Map.Entry<String, Integer> val : CellCount.entrySet()) { 
    //       System.out.println( val.getKey() + " " + ": " + val.getValue()); 
    //   } 
     
     
      for (Map.Entry<String,myOperations> obj: operationsList.entrySet()) {
           String cellKey=obj.getKey();
           String operator=obj.getValue().getOperation();
           int cellCount=CellCount.get(cellKey);
 
           if( operator.equals("=") && cellCount==1)
           {
          //   System.out.println(cellKey+" "+"Valid = for count="+ cellCount);
           }
           else if(((operator.equals("-") || operator.equals("/")) && cellCount==2))
           {
        ///   System.out.println(cellKey+" "+"Valid - or / for count="+ cellCount);
           }
           else if(((operator.equals("+") || operator.equals("*")) && cellCount>=2))
           {
         //   System.out.println(cellKey+" "+"Valid + or *  for count="+ cellCount);
           }
           else
            {
                System.out.println(cellKey+" Invalid");
                return false;
            }           
        
     }
     System.out.println("End of CheckOperation!!!!");
       return true;
   }
   
    /**************************************** Solve method *****************************************/
  
    public boolean solve()
    {
        Map<String,myOperations>  TempOperationList=operationsList;
        Set<String> removeSet=new HashSet<String>();
 
        //For "="
        for (Map.Entry<String,myOperations> listObj : TempOperationList.entrySet())
        {
            if(listObj.getValue().getOperation().equals("="))
            {
                int x=listObj.getValue().getLocation().get(0).getX();
                int y=listObj.getValue().getLocation().get(0).getY();
                int value=listObj.getValue().getValue();
                Matrix.get(x).get(y).setCellValue(value);
                TempSolutionMatrix[x][y]=value; //Set value in temporary solution list
                removeSet.add(listObj.getKey());
            }   
        }
            //TempOperationList.keySet().removeAll(removeSet); //From StackOverFlow
            
        //For "+,-,/,*"
 
        for (Map.Entry<String,myOperations> listObj : TempOperationList.entrySet())
        {
            String operator=listObj.getValue().getOperation();
            int output=listObj.getValue().getValue();
            int locationsSize=listObj.getValue().getLocation().size();
            List<List<Integer>> ABCList=new ArrayList<List<Integer>>(createPossibleSets(operator,output,locationsSize));
            System.out.println("Possible Value List for "+output+" "+operator+" = "+ABCList );
            possibleValuesSetByGroup.put(listObj.getKey(),ABCList);
        }
 
        for(int i=0; i<puzzleSizeValue; i++){
            for(int j=0; j< puzzleSizeValue; j++){
                System.out.print(TempSolutionMatrix[i][j]);
            }
            System.out.println();
        }
 
        System.out.println(generateMatrix());
 
        for(int i=0; i<puzzleSizeValue; i++){
            for(int j=0; j< puzzleSizeValue; j++){
                System.out.print(TempSolutionMatrix[i][j]);
            }
            System.out.println();
        }
        
        return true;
    }
 
    public boolean generateMatrix(){
        List<Integer> possibleValuList = new ArrayList<Integer>();
        String group;
        for(int row=0; row < puzzleSizeValue; row++){
            for(int col=0; col < puzzleSizeValue; col++){
                if (TempSolutionMatrix[row][col] == 0) {
                    // System.out.println(TempSolutionMatrix[row][col]);
                    group = findGroupByLocation(row,col);
                    possibleValuList = getPossibleNumber(group);
                    // System.out.println(possibleValuList);
                    for (int number = 0; number < possibleValuList.size(); number++) {
                        // System.out.println(row+" "+col+" "+possibleValuList.size());
                        if (isOk(row, col, possibleValuList.get(number))) {
                            TempSolutionMatrix[row][col] = possibleValuList.get(number);
                            // System.out.println("["+row+"]["+col+"] == "+possibleValuList.get(number));
                            if (generateMatrix()) { 
                                return true;
                            } else {
                                TempSolutionMatrix[row][col] = 0;
                            }
                        }
                    }
                return false;
                }
            }
        }
        return true;
    }
 
     public boolean isOk(int row, int col, int number) {
        return (!isInRow(row, number)  &&  !isInCol(col, number));
    }
 
    public boolean isInRow(int row, int number) {
        for (int i = 0; i < puzzleSizeValue; i++)
            if (TempSolutionMatrix[row][i] == number)
                return true;
        return false;
    }
 
    public boolean isInCol(int col, int number) {
        for (int i = 0; i < puzzleSizeValue; i++)
            if (TempSolutionMatrix[i][col] == number)
                return true;
        return false;
    }
 
    public List<Integer> getPossibleNumber(String group) {
        List<Integer> list = new ArrayList<Integer>();
        List<List<Integer>> tmp_list = possibleValuesSetByGroup.get(group);
        for(int i = 0; i < tmp_list.size(); i++){
            for(int j = 0; j<tmp_list.get(i).size(); j++){
                if(!list.contains(tmp_list.get(i).get(j))){
                    list.add(tmp_list.get(i).get(j));
                }
            }
        }
        return list;
    }
 
    public String findGroupByLocation(int i, int j) {
        String group = "";
        List<CellLocation> location;
        for (Map.Entry<String,myOperations> listObj : operationsList.entrySet())
        {
            location = listObj.getValue().getLocation();
            for(int k = 0; k < location.size(); k++){
                if(location.get(k).getX()==i && location.get(k).getY()==j){
                    group = listObj.getKey();
                }
            }
        }
        return group;
    }
    /**************************************** createPossibleSets method *****************************************/
 
     private List<List<Integer>> createPossibleSets(String operator,int value,int size )
     {
         int[] possibleValues=new int[puzzleSizeValue];
         for(int i = 0; i < puzzleSizeValue; i++)
         {
            possibleValues[i]=i+1;
         }
        List<List<Integer>> list = new ArrayList<List<Integer>>();
 
        switch(operator)
        {
            case "+":
            List<Integer> tmp_list1 = new ArrayList<Integer>();
            list=subSequenceSum(list,puzzleSizeValue,tmp_list1,value,size);
            break;
 
            case "-":
                for (int i = 1; i <= puzzleSizeValue; i++)
                {
                    for (int j = 1; j <= puzzleSizeValue; j++)
                    {
                        List<Integer> tmp_list = new ArrayList<Integer>();
                        if (i-j==value)
                        {
                            tmp_list.add(i);
                            tmp_list.add(j);
                            list.add(tmp_list);
                        }
                    }  
                }  
                break;
 
                case "*":
                        List<Integer> tmp_List= new ArrayList<Integer>();
                        List<List<Integer>> ResultList=new ArrayList<List<Integer>>();
                         list= subSequenceMultiply(5, value,size,  tmp_List,ResultList);
                break;
 
                case "/":
                 for(Integer i=1; i<=puzzleSizeValue; i++){
                    List<Integer> tmp_list = new ArrayList<Integer>();
                    for(Integer j=1; j<=puzzleSizeValue; j++){
                        if(Math.abs(i/j) == value && (i%j) == 0){
                            tmp_list.add(i);
                            tmp_list.add(j);
                            list.add(tmp_list);
                        }
                    }
                }
                break;
        }
        return list;
     }
 
       /**************************************** subSequenceMultiply method *****************************************/
 
     private static List<List<Integer>> subSequenceMultiply(int puzzleSizeValue, int output, int pairSize,
     List<Integer> tmp_List,List<List<Integer>> ResultList)
     {
 
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
                
            // System.out.println("---------------------ResultList--------------------");
            // System.out.println(" Result-list="+ResultList);  
            // System.out.println("-----------------------------------------"); 
        }  
            return ResultList;
        }
 
        for (Integer i: list) {
            
            // add a number to the solution
            tmp_List.add(i);
            //System.out.println(" tmp-list="+tmp_List);
            subSequenceMultiply(puzzleSizeValue, output, pairSize, tmp_List, ResultList);
            
            // keep on changing the last element with all the possible combinations
            tmp_List.remove(tmp_List.size()-1);
        }
        return ResultList;
    }
 
  /**************************************** subSequenceSum method *****************************************/
 
    //  private List<List<Integer>> subSequenceSum(List<List<Integer>> ans,int a[],  List<Integer> temp,int k,int pairSize, int start)
    // {
    //     //System.out.println(k+"  "+pairSize);
    //     int size=pairSize;
    //     if(start > a.length || k < 0)
    //         return null;
 
    //     if (temp.size()==size)
    //     {    
        
    //     if(k == 0)
    //     {
    //         ans.add(new ArrayList<Integer>(temp));
    //         return ans;
    //     }
    // }
    //     else {
    //         for (int i = start; 
    //              i < a.length; i++) {
 
    //             // Adding a new 
    //             // element into temp
    //             temp.add(a[i]);
 
    //             // After selecting an
    //             // element from the
    //             // array we subtract K
    //             // by that value
    //             subSequenceSum(ans, a, 
    //                temp, k - a[i],size,i+1);
 
    //             // Remove the lastly 
    //             // added element
    //             temp.remove(temp.size() - 1);
    //         }
    //     }
    //     return ans;
    // }
 
    private List<List<Integer>> subSequenceSum(List<List<Integer>> ans,int puzzleSizeValue,  List<Integer> temp,int output,int pairSize)
    {
        int a[] = new int[puzzleSizeValue];
        for (int i = 0; i < a.length; i++)
        {
            a[i] =i+1;
        }
 
       // int size=pairSize;
        if (temp.size()==pairSize)
        {    
            int sum=0;
            for (int value: temp) {
                sum += value;
            }
        
            if (sum == output) {
                ans.add(new ArrayList<Integer>(temp));
        }
    }
        else {
            for (int i = 0;i < a.length; i++) {
 
                
                temp.add(a[i]);
 
                
                subSequenceSum(ans, puzzleSizeValue,temp,output,pairSize);
 
               
                temp.remove(temp.size() - 1);
            }
        }
        return ans;
    }
 
 
 
    private void displayMatrix()
    {
        System.out.println("-----------------------------------------");
        for (int i = 0; i < Matrix.size(); i++)
        {
        for (int j = 0; j < Matrix.get(i).size(); j++)
        {
            System.out.print(Matrix.get(i).get(j).getCategory()+"-"+Matrix.get(i).get(j).getCellValue()+"  ");   
        }
        System.out.println();
       }
       System.out.println("-----------------------------------------");
    }
   public static void main(String[] arg)
    {
        Mathdoku mathdoku=new Mathdoku();
        
        try {
            mathdoku.ReadPuzzleFile(new BufferedReader(new FileReader("SamplePuzzle")));
        } catch (FileNotFoundException e) {
                System.out.println("File not Found!!!");
        }
        mathdoku.validate();
       mathdoku.solve();
 
    }
 
}
 

