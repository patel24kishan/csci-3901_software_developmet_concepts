/*
	The proposed code has been developed with the help of reference taken 
	from a website called educative.io and 
	the link is provided below:
	https://www.educative.io/edpresso/how-to-code-an-iterative-binary-search-algorithm-in-java
*/

/*
	Following Assertions are introduced:
	
	1) assert input.length!=0 : "Input list is null";
	
	 ----> This assertion will check if null input array is passed; 
	 		if null input array is passed we throw an error
			specifying Input List is null.
		
			
	2) Loop precondition : assert first+last+1==input.length : "Not valid";
	
	 ----> This assertion will check if the calculate value of first and 
	 		last will be equal to the lenth of passed input array minus 1.
	
	
	3) Loop invariant : assert first<=(input.length-1) && last<=(input.length-1) && middle<=(input.length-1) && first<=middle && middle<=last : "Not valid";
	
	 -----> The first three conditions here checks that the value of first, 
	 		last and middle should be less than the length of input array;
	 	    the last two conditions specify that middleshould be between 
			first and last including first and last no 
			matter what happend inside the loop this conditions should always satisfy.
	
	4) Loop postcondition : assert (first>last) || (first<=middle && middle<=last) : "Something went wrong"; 
	
	----> Here, the first part signifies that we have not found element in
	 	  the array and the second part signifies that we have found the element;
	 	  there is an OR between them as we have
	 	  two possibilities that we will find an element 
		  or we will not find element after coming out of loop.
*/
package lab4;

import java.util.Arrays;
import java.util.Scanner;

public class Binarysearch {
    public static void binarySearch(int[] input, int number) {
        assert input.length!=0 : "Input list is null";
        int first = 0;
        int last = input.length - 1;
        int middle = (first + last) / 2;
        assert first+last+1==input.length : "Not valid";
        while (first <= last) {
            assert first<=(input.length-1) && last<=(input.length-1) && middle<=(input.length-1) && first<=middle && middle<=last : "Not valid";
            if (input[middle] < number) {
                first = middle + 1;
            }else if (input[middle] == number) {
                System.out.printf(number + " found at location %d %n", middle);
                break;
            } else {
            	
                last = middle - 1;
            }
            middle = (first + last) / 2;
        }
        assert (first>last) || (first<=middle && middle<=last) : "Something went wrong";
        if (first > last){
            System.out.println(number + " is not present in the list.\n");
        }
    }

    public static void main(String[] args) {
	// write your code here
        int[] list = new int[]{23, 43, 31, 12};
        int number = 12;

        Arrays.sort(list);
        System.out.printf("Binary Search %d in integer array %s %n", number, Arrays.toString(list));
        binarySearch(list, 13);
        System.out.printf("Binary Search %d in integer array %s %n", 43, Arrays.toString(list));
        binarySearch(list, 43);

        list = new int[]{123, 243, 331, 1298};
        number = 331;
        Arrays.sort(list);
        System.out.printf("Binary Search %d in integer array %s %n", number, Arrays.toString(list));
        binarySearch(list, 331);
        System.out.printf("Binary Search %d in integer array %s %n", 331, Arrays.toString(list));
        binarySearch(list, 90000);
  
    }
}
