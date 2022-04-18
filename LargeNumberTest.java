// Test program for project 2
// Authors: Ken Carr and Duncan Mckay
// This program has been made as a test bed for effectiveness and
// troubleshooting of UnboundedInt class and associated methods

import java.util.Scanner;  // Scanner class imported to allow user input
import java.util.InputMismatchException;  // allows handling of invalid inputs

class LargeNumbertest {

   public static void main(String [] args)   {  // start of main method
      
      Scanner keyboard = new Scanner(System.in);   // allows for user input
   
      int menuChoice;
      //UnboundedInt input1, input2;
      UnboundedInt input1 = new UnboundedInt("25225");
      UnboundedInt input2 = new UnboundedInt("25225");
      input1.start();
      input2.start();
   
      boolean run = true;
      boolean firstRun = true;
      //only prints these once
      System.out.println("Test Program For UnboundedInt Class\nBy: Ken and Duncan\n");
      System.out.println("Class allows processing of numbers greater then what is naturally allowed\n");
      
   
      while(run) {  // runs menu atleast once for user to decide what method to try or exit as desired
   
         System.out.print("---\tMain Menu\t---\n");

   
         // menu of DoubleArraySeq methods to test individually
         System.out.println("1  - Display both numbers (with commas)");
         System.out.println("2  - Input two new numbers (without commas)");
         System.out.println("3  - Check if numbers are equal");
         System.out.println("4  - The sum of the two numbers");
         System.out.println("5  - Multiply the two numbers");
         System.out.println("6  - clone of first number");
         System.out.println("7  - QUIT");
         
         try   {  // start of try block, checks all cases
      
            System.out.print("---Enter your choice---");
            if(firstRun){
               menuChoice=2;
               firstRun=false;            
            }
            else  
               menuChoice = keyboard.nextInt(); // user choice of method to test

            System.out.print("\n");
      
         switch(menuChoice)   {  //7 menu cases (6 and a default)
         
            case 1:  // display both inputted numbers with commas added appropriately
            
               System.out.println("The first number is:" + input1.toString());
               System.out.println("The second number is:" + input2.toString());
               break;
         
            case 2:  // user inputs two new numbers without commas
            
               System.out.println("Enter a Integer");
               String input1Case2 = keyboard.nextLine();
               input1 = new UnboundedInt(input1Case2);

               System.out.println("Enter a Integer");
               String input2Case2 = keyboard.nextLine();
               input2 = new UnboundedInt(input2Case2);           
               break;
         
            case 3:  // Checks to see if both numbers are equal
               if(input1.equals(input2))
                  System.out.print("The numbers are equal!\n");
               else
                  System.out.print("The numbers are not equal!\n");
               break;//probably Works
            
            case 4:  //Add
               System.out.print("The sum of the two UnboundedInt obects is: ");
               System.out.print(((input1.add(input2)).toString())+"\n");
               break;

            case 5: //Multiply
               System.out.print("The product of the two UnboundedInt obects is: "+((input1.multiply(input2)).toString())+"\n");
               break;
               
            case 6:
               System.out.print("Creating a clone of the first number\n");
               System.out.print("The clone is: "+(input1.clone()).toString() +"\n");
               break;
               
            case 7:
               System.out.print("\n---Program Ending---\n");
               run = false;
               break;
               
            default: // if invalid menu selection is inputted, exceptions used to handle non integer inputs
            
               System.out.println("\n---ENTER A VALID MENU OPTION---\n");
                     
               break;
      
         }  // end switch statement for menu options ************************************************************
      
      }  // end try block
      
      // allows invalid data to be handled and user to get message to help resolve issue
      catch(NullPointerException e)   {
         System.out.println(e.getMessage());
      }

      catch (IllegalStateException e)   {
         System.out.println(e.getMessage());
      }
      
      catch (IllegalArgumentException e)  {
         System.out.println(e.getMessage());
      }

      catch (OutOfMemoryError e) {
         System.out.println(e.getMessage());
      }
      
      catch (InputMismatchException e) {
         System.out.println("Invalid Input");
         keyboard.next();  // clears buffer to avoid infinite loop
      }
   
      catch (Exception e)  {
         System.out.println("Something Broke");
         keyboard.next();  // clears buffer to avoid infinite loop
      }

      System.out.println("\n");
   
      }  // end of run control loop, broken by case 16
            

  }   //end of main method
  
}  // end of test class

