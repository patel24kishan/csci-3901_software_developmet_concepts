/*
	The proposed code has been developed with the help of reference taken 
	from a website called educative.io and 
	the link is provided below:
	https://www.educative.io/edpresso/how-to-code-an-iterative-binary-search-algorithm-in-java
*/


package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
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

       /* list = new int[]{};
        number = 331;
        Arrays.sort(list);
        System.out.printf("Binary Search %d in integer array %s %n", number, Arrays.toString(list));
        binarySearch(list, 331);*/
  
    }
}
