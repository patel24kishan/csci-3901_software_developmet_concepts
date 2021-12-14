import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.*;

import javax.print.attribute.SetOfIntegerSyntax;


public class CourseSelector {



	/*
	 * I have used ArrayList to store the course list from text file 
	 *  because it provides the dynamic size option which is a key 
	 *  requirement of this program as number of students may vary 
	 *  according to input file.
	 *  
	 *  Secondly, travesing through Arraylist is much easy because it
	 *  is to be utilized in every other function of the class which
	 *  requires complex traversals.
	 */
	public List<String[]> AllCourseList = new ArrayList<String[]>(); 

						/********************************FormattingStringToUppercase Method********************************/
	
	/*
	 1 This method splits the input data string  by space/spaces
	 2 Then, it modifies the individual course string to Uppercase
	 3 Return the modified string array
	 */
	private String[] FormattingStringToUppercase(String data) {

		String[] temp = data.split("\\s+");

		for (int k = 0; k < temp.length; k++) {
			temp[k] = "CSCI" + temp[k].substring(4, temp[k].length());
		}

		return temp;
	}


	public int read(String filename) {

		int linecount = 0; 		// counter for rows in the file

		AllCourseList.clear();	 //Clears previous objects of AllCourseList

		File txtfile = new File(filename);	 // read filename
		try
		{
			Scanner fileread = new Scanner(txtfile); 

			while (fileread.hasNextLine())      //Traverse through text file until EOF
			{
				fileread.hasNextLine();
				String dataString=fileread.nextLine();

				if(dataString.length()>0)	 //ignore blank line from the file
				{   
					linecount++;
					String[] temp = FormattingStringToUppercase(dataString);
					AllCourseList.add(temp);
					dataString="";
				}  

			}
		}
		catch (FileNotFoundException e) {            //If the filename string is null or empty string is inserted
			//System.out.println("file does not exist !!!");
		}

		return linecount;
	}

						/********************************Recommend Method********************************/

	public ArrayList<String> recommend(String taken, int support, int numRec)
	{

		/*
		 *If user input is null value or empty string in the taken parameter,
		 *I have treated such cases as Invalid input case and return null
		 *because main function of recommend() is to recommend courses 
		 * based on some pair of courses. 
		 */
		if (taken==null || taken=="")
		{
			return null;
		}
		String[] takenCourse = FormattingStringToUppercase(taken); // User defined Course pair (taken parameter)

		List<String[]> tmpList = new ArrayList<String[]>(AllCourseList); // copy of all courses from .txt file

		List<String[]> similarCourse = new ArrayList<String[]>(); // array of courses without excluding user defined pair of courses

		ArrayList<String> recommendList=new ArrayList<String>(); // List which will store the recommended courses

		/*
		 * The following, loops through tmpList to figureout which student
		 * has taken the courses defined by user and then exclude them from that
		 * student's course list.
		 * That modified list is then added to similarCourse list
		 * 
		 */
		for (String[] data : tmpList) 
		{
			int hasCourse=0;
			for (int i = 0; i < data.length; i++) 
			{
				for (int j = 0; j < takenCourse.length; j++)
				{
					if (data[i].equals(takenCourse[j]))
					{
						hasCourse++;
						data[i]="";
						if (hasCourse==takenCourse.length)
						{
							similarCourse.add(data);
						}
					}	
				}
			}
		}

		/*

		 * Following code avoids one of boundary case that suggests 
		 * that taken parameter consists of all courses 
		 * which will result in an 
		 * almost no recommendation every time as it's rare
		 * for a student to take every course and error might
		 * occur on multiple occasions.
		 *
		 * Insert  every element of AllCourseList to get set 
		 * of unique courses without duplication.
		 * 
		 * Compare length of takenCourse and temporaryset
		 * If they match, then return null to avoid 
		 * as I can't seem to figure a way around this. 
		 */
		Set<String> temporary = new HashSet<String>(); 

		for (String[] data : AllCourseList)
		{
			for (int i = 0; i < data.length; i++) {
				temporary.addAll(Arrays.asList(data));
			}
		}

		if (takenCourse.length==temporary.size())
		{
			return null;	
		}


		/*
		 * Check if no. of students in SimilarCourse satisfies minimum support
		 * if it satisfies, then only recommend course.
		 * 
		 * I have assumed that if support=-1, then recommend null because such
		 * cases should be considered as invalid input.
		 * 
		 * For support = 0 , recommend numRec no. of courses based on their occurence frequency
		 * 
		 * For numRec as well, if it's given 0 ,return empty list
		 * For numRec= -1, then recommend null
		 */

		if (!(similarCourse.size()>=support) || support<0 ||numRec<=0 )
		{
			if (numRec==0)
			{
				return recommendList;
			}
			
			return null;
		}


		/*
		 * Insert  every element of similarCourse to get set 
		 * of unique courses without duplication
		 * 
		 * Convert set to array for easy traversal
		 */
		Set<String> tmpSet = new HashSet<String>(); 

		for (String[] data : similarCourse)
		{
			for (int i = 0; i < data.length; i++) {
				tmpSet.addAll(Arrays.asList(data));
			}
		}

		
		tmpSet.remove("");  // to remove null value from set before converting set to an array
		String[] similarCourseArray = tmpSet.toArray( new String[tmpSet.size()]);

		int[] frequencyCount=new int[similarCourseArray.length]; //array to store frequency count of Similar Courses

		
		/*
		 * Loop to count frequency of courses of 
		 * SimilarCourseArray from ArrayList of SimilarCourse
		 */

		for (String[] Course : similarCourse) {
			// selecting pairs such as no two pair occur twice
			for (int i = 0; i < Course.length; ++i) {
				//flags to see if course is taken by that student
				for (int index = 0; index < similarCourseArray.length; ++index)
				{
					if (similarCourseArray[index].equals(Course[i]))
					{

						frequencyCount[index]++;
					}
				}
			}
		}


		/*
		 * Following lopp sorts the frequency matrix of similar courses in
		 * descending order before making recommendation
		 */

		for (int i = 0; i < frequencyCount.length; i++)
		{
			for (int j = i+1; j < frequencyCount.length; j++)
			{
				if (frequencyCount[i]<frequencyCount[j])
				{
					int tmpcount=frequencyCount[i];
					String tmpString=similarCourseArray[i];

					frequencyCount[i]=frequencyCount[j];
					frequencyCount[j]=tmpcount;

					similarCourseArray[i]=similarCourseArray[j];
					similarCourseArray[j]=tmpString;

				}
			}	
		}

		/*
		 * Recommendation Logic

		 1. Tranvers through similarCourseArray for numRec Times
		 2. Check if course at i is already in recommendList, if not add it
		 3. Check if frequency of course at (i+1) is similar to that 
		 	of previous course. If it is same, add it to recommendList as well.

		 */

		for (int i = 0; i <numRec; i++)
		{
			if(!recommendList.contains(similarCourseArray[i]))
			{
				recommendList.add(similarCourseArray[i]);
			}
			if (!((i+1)==similarCourseArray.length)) 
			{
				if (frequencyCount[i]==frequencyCount[i+1])
				{
					recommendList.add(similarCourseArray[i+1]);

				}
			}
		}

		return recommendList;
	}

	/********************************showCommon Method********************************/

	public boolean showCommon(String courses) {

		// check if user has enter any course
		if (courses==null ||courses=="")
		{
			return false;	
		}
		String[] userCourse = FormattingStringToUppercase(courses); // Stores user specified courses (courses parameter)

		int[][] courseCount = new int[userCourse.length][userCourse.length]; // 2D Array that stores Frequency count of
																			// pairs of User defined courses

		
		//  Intialise each entry of courseCount array with "0" value
		 
		for (int i = 0; i < courseCount.length; i++) {
			for (int j = 0; j < courseCount.length; j++) {
				courseCount[i][j] = 0;
			}
		}

		
		/*
		 *  looping through all the students (AllCourseList is the main list we get from
		 	.txt file)
		 * Below is the Loop which calculates the occurence/conflict of each course pair in
		 * the provided student data
		 */

		for (String[] student : AllCourseList) {

			for (int i = 0; i < student.length; ++i) {

				for (int j = i + 1; j < student.length; ++j) {

					int iIndex = -1, jIndex = -1; //flags to see if both courses of the pair are taken

					for (int index = 0; index < userCourse.length; ++index)
					{
						if (userCourse[index].equals(student[i]))
							iIndex = index;
						if (userCourse[index].equals(student[j]))
							jIndex = index;
					}

					if(iIndex != -1 && jIndex != -1) // if both flags are set, increment at (iIndex, jIndex)
													//	and (jIndexn,iIndex) of courseCount
					{
						courseCount[iIndex][jIndex]++;
						courseCount[jIndex][iIndex]++;
					}
				}
			}
		}

		
		/*
		 * Display courseCount matrix along with all possible
		 * pair of user defined courses as rows.
		 * */
		System.out.print("\n");
		for (int i = 0; i < courseCount.length; i++) {
			System.out.print(userCourse[i]+" ");
			for (int j = 0; j < courseCount.length; j++) {
				System.out.print(courseCount[i][j] + " ");
			}
			System.out.println();
		}

		return true;
	}

	/********************************showCommonAll Method********************************/

	public boolean showCommonAll(String filename) 
	{ 
		/* 

		 * I chose set to store every courses from AllCourseList to 
		 * create an list of all unique courses.
		 * 
		 * Main reason behind choosing set is it's build-in
		 * charactersistics of not allowing duplicates values
		 * which makes it easy for eliminating repeating courses
		 * per student in one single step.
		 * 

		 */
		Set<String> allCourseSet = new HashSet<String>(); 
		for (String[] student : AllCourseList)
		{
			for (int i = 0; i < student.length; i++) {
				allCourseSet.addAll(Arrays.asList(student));
			}

		}

		String[] userCourse = allCourseSet.toArray( new String[allCourseSet.size()]);// Convert set to an array for easy traversal

		
		/*
		 * Below given function sorts all courses in
		 * useCourse array based on numeric substring of the 
		 * alphanumeric course string using Comparator<>..
		 */
		Arrays.sort(userCourse, new Comparator<String>() {
			public int compare(String str1, String str2) {
				String substr1 = str1.substring(4);
				String substr2 = str2.substring(4);

				return Integer.valueOf(substr1).compareTo(Integer.valueOf(substr2));
			}
		});


		/*
		 * 2D Array that storesFrequency count of
		 *  pair of courses from AllCourseList.
		 */

		int[][] courseCount = new int[userCourse.length][userCourse.length]; 


		/*

		 * First Loop traverse through all the students (AllCourseList is the main list )
		 *
		 *Nested Loops calculates the occurence/conflict of each course pair in
		 *the provided student data

		 */
		for (String[] student : AllCourseList) {
			// selecting pairs such as no two pair occur twice
			for (int i = 0; i < student.length; ++i) {
				for (int j = i + 1; j < student.length; ++j) {

					int iIndex = -1, jIndex = -1; //flags to see if both courses of the pair are taken
					for (int index = 0; index < userCourse.length; ++index)
					{
						if (userCourse[index].equals(student[i]))
							iIndex = index;
						if (userCourse[index].equals(student[j]))
							jIndex = index;
					}

					if(iIndex != -1 && jIndex != -1)	// if both flags are set, increment at (iIndex, jIndex)
														//	and (jIndexn iIndex) of courseCount
					{
						courseCount[iIndex][jIndex]++;
						courseCount[jIndex][iIndex]++;
					}
				}
			}
		}

		/*

		 * Following section writes the FrequencyCount matrix 
		 * for every course pair to an output file of name 
		 * provided by user ("filename")

		 */
		try {
			FileWriter WriteToFile = new FileWriter(filename);

			WriteToFile.write("\n\n");
			for (int i = 0; i < courseCount.length; i++) {
				WriteToFile.write(userCourse[i]+"   ");
				for (int j = 0; j < courseCount.length; j++) {
					WriteToFile.write(" "+courseCount[i][j] + "\t");
				}
				WriteToFile.write("\n");
			}
			WriteToFile.close();

		}

		catch (FileNotFoundException e) {
			return false;
		}
		catch (IOException e)
		{
			return false;
		}


		return true;
	}

}
