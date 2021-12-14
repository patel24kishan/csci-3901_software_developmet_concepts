import java.io.File;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import jdk.internal.joptsimple.internal.Strings;


public class CourseSelector {
	
	private static final String CourseSubsetmapr = null;

	int linecount=0;  //counter for rows in the file

	public List<String[]> AllCourseList = new ArrayList<String[]>();  //ArrayList to store the course list from text text file
    
	public int read(String filename)
    {    
		
		File txtfile=new File(filename);  //read filename
    	try {
			Scanner fileread=new Scanner(txtfile); //Scanner File
			
			while (fileread.hasNextLine())
			{
				/* read single line from the file
				 * then split the string by " "
				 * create an list of array of courses 
				   taken by each students
				 */
				
				String dataString= fileread.nextLine();
				linecount++;
				
				String[] temp=FormattingStringToUppercase(dataString);
				AllCourseList.add(temp);
				
				
			}
			
		} 
    	
    	catch (FileNotFoundException e) 
    	{
			System.out.println("file not Found !!!");
		}
    	
    	System.out.println("Total no. of rows in the file:"+linecount);
    	
    	for (String[] strings : AllCourseList)
    	{
    		
    			for (int i=0;i<strings.length;i++) {
					
    				System.out.print(strings[i].toString()+" ");
				}
    			System.out.println();
    			
			
		}
    	
    	return linecount;
    	
    }

	

   /*public ArrayList<String> recommend(String taken, int support, int numRec) 
   {   /* IMPLEMENT }*/ 
	

	
	
   public boolean showCommon(String courses)
	{
		String[] UserCourse=FormattingStringToUppercase(courses);
		
		
		HashMap<Integer, List<String[]>> CourseSubsetmap = new HashMap<Integer, List<String[]>>();
		HashMap<Integer, List<String[]>> UserSpecifiedCourseMap = new HashMap<Integer, List<String[]>>();
			
		int[][] CourseCount=new int[UserCourse.length][UserCourse.length];    //2D Arrat that storesFrequency count of pairs of User defined courses  
		
		/*
		 * Loop to Intialise each entry of CourseCount array with "0" value 
		 */
		
		for (int i = 0; i < CourseCount.length; i++) {
			for (int j = 0; j < CourseCount.length; j++) {
				CourseCount[i][j]=0;
			}
			
		}
															/*  Loop to create create course pair from the Main CourseList    */
			
		for (int k = 0; k < AllCourseList.size(); k++) 
		{ 
			String[] strings=AllCourseList.get(k); 
			
			List<String[]> tmpList = new ArrayList<String[]>();
				
			
				for (int i = 0; i < strings.length; i++)
				{
					
					if (strings.length==1)
					{
						String[] tmp1={strings[i]};
						tmpList.add(0,tmp1);
						//System.out.println("inside if");
						break;
					}
					
					
					for (int j = i+1; j < strings.length; j++)
					{
						String[] tmp1={strings[i],strings[j]};
						tmpList.add(0,tmp1);
						
				}
					
				}
				CourseSubsetmap.put(k, tmpList);
		}
				//Below loop is to display the content of the CourseSubsetmap 
		
		 for (int i = 0; i < CourseSubsetmap.size(); i++) {
			
			System.out.print("Value at "+i+"\n");
			
		for (String[] string : CourseSubsetmap.get(i)) {
			System.out.println(Arrays.toString(string)+"\n");
		}
		}
		
		
		
		/* Below is the Loop which calculates the occurence/confict of each course in the provided student data*/
		
			
					
					
						for (int S1 = 0; S1 < UserCourse.length; S1++)
						{
							for (int S2 = S1+1; S2 < UserCourse.length; S2++)
							{
								for (int mapIndx = 0; mapIndx < CourseSubsetmap.size(); mapIndx++) 
								{
									
						for (int key = 0; key < CourseSubsetmap.get(mapIndx).size(); key++) 
						{
							
							  
							 if (CourseSubsetmap.get(key).size()==1) { break; }
							 
							  
							 
							else
							{
						
						  for (String[] Pair : CourseSubsetmap.get(key)) {
						  
						  System.out.println("MI: "+mapIndx+"\tK: "+key);
						  System.out.println("P1 :"+Pair[0] +" P2: "+Pair[1]);
						  System.err.println("S1 :"+UserCourse[S1] +" S2: "+UserCourse[S2]);
						  System.out.println();
						  
						  
						  if (((Pair[0]==UserCourse[S1]) && (Pair[1]==UserCourse[S1])) ||
						  ((Pair[1]==UserCourse[S1]) && (Pair[0]==UserCourse[S1]))) {
						  System.out.println("inside else if"); CourseCount[S1][S2]++;
						  CourseCount[S2][S1]++; }
						  
						  
						  } }
						 
						}
					}
				}
				
			}
			
			for (int i = 0; i < CourseCount.length; i++) 
			{
				for (int j = 0; j < CourseCount.length; j++) {
					System.out.print(CourseCount[i][j]+" ");
				}
				System.out.println();
				
			}
		return true;
	}

   
   

   
   /*
    * 
    * Split the data string at " "
    * convert the intital four character of that splited element to UpperCase
    * 
    * */
   private String[] FormattingStringToUppercase(String data)
   {
	  
		String[] temp=data.split(" ");
		
		
		for (int k= 0; k < temp.length; k++)
		{
			
				temp[k]="CSCI"+temp[k].substring(4,temp[k].length());
		}
		
		return temp;
	
   }
   
   // public boolean showCommonAll(String filename) { /* IMPLEMENT */ } 
   
   
  
   
   
}

