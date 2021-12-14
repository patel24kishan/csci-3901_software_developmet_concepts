/* While compiling and running the code, please ensure that the code is stored in a package named "com.company" which you should create before hand. */
package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/*
This is an implementation similar to Map Interface in java
*/
class Map<T, U> {
    List<T> key_data = new ArrayList<T>();
    List<U> value_data = new ArrayList<U>();

    Map() {
    }

    /* Insert values in Map */
    public void Put(T key, U value) {
        key_data.add(key);
        value_data.add(value);
    }

    /* Check if the passed key already exists */
    public boolean validateKey(T key) {
        return key_data.contains(key);
    }

    /* Print the map */
    public void print() {
        Iterator keyIterator = key_data.iterator();
        Iterator valueIterator = value_data.iterator();
        System.out.println("------------------------------");
        System.out.println("KEY\t\t\t\tVALUE");
        System.out.println("-------------------------------");
        while (keyIterator.hasNext() && valueIterator.hasNext()) {
            System.out.printf("%-20s %s\n", keyIterator.next(),valueIterator.next());
        }
    }

    /* Fetch the value of a requested key from our Map */
    public U get(T key) {
        if (key_data.indexOf(key) != -1) {
            return value_data.get(key_data.indexOf(key));
        } else {
            return null;
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String key_value;
        int value_value;
        Map<String, Integer> data = new Map<String, Integer>();
        char a='N',c;

        /* Do while loop for inserting uer defined values to our Map */
        do {
            System.out.println("\nPlease enter key");
            key_value = input.nextLine();

            if (data.validateKey(key_value)) {
                System.out.println("\nKey already exist. Please enter different key.\n");
            } else {
                System.out.println("\nPlease enter value for the entered key");
                value_value = input.nextInt();
                data.Put(key_value, value_value);

                if (data.get(key_value) != null) {
                    System.out.println("Your input as Key:Value is " + key_value + ":" + data.get(key_value));
                } else {
                    System.out.println("The input key is invalid or the value is null\n");
                }

                System.out.println("Do you wish to enter more values(Y/N)");
                a = input.next().charAt(0);
                input.nextLine();
            }
        } while (a == 'Y' || a == 'y');

        /* Print the map */
        System.out.println("Your Map is as follows\n");
        data.print();

        /* Fetch the value for the given key*/
        while(true) {
            System.out.println("Do you wish to search the value of your key (Y/N)");
            c = input.next().charAt(0);
            input.nextLine();

            if(c == 'Y' || c == 'y'){
                System.out.println("Enter the key you want the value for:");
                String find = input.nextLine();
                if (data.get(find) != null) {
                    System.out.println("The value of the the requested key is " + data.get(find));
                } else {
                    System.out.println("The input key is invalid");
                }
            }
            else
            {
                break;
            }
        }
        System.out.println("********GOOD BYE*******");
    }
}
