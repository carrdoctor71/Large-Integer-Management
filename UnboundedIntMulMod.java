// Authors: Ken Carr and Duncan McKay
// The purpose of this class is to allow the manipulation of numbers that are unbounded in size.

//ADD IMPORT STATEMENTS

public class UnboundedInt implements Cloneable    // Start of UnboundedInt class
{
   // Invariant of the UnboundedInt class
   //    1. The Unbounded Integers are stored in a linked list.
   //    2. The head is a IntNode reference variable.
   //    3. The total number of Unbounded Integers in the list is in the instance variable nodes.
   //    4. The cursor location is in the instance variable index.
   //    5. The tail is a IntNode reference variable.
   
   private int nodes, index;
   private IntNode tail, head, cursor;
   /*
   * Initialize an empty linked list.
   * @param - none
   * @postcondition
   *   This list is empty.
   */
   public UnboundedInt()
   {//start of default constructor
      int nodes = 0;
      int index = 0; //when index is zero, there is no cursor
      IntNode tail, head, cursor = new IntNode(0,null);
   }//end of default constructor


   /*
   * Declare and initialize an UnboundedInt with the values parsed from a String input.
   * @param input
   *  the unbounded string of numbers
   * @postcondition
   *   a linked list of the periods has been created
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new IntNode.
   */
   public UnboundedInt(String input)   //start of constructor
   { 
      int nodes = 0;
      for(int x = input.length(); x >= 0; x=x-3)
      {
         head = new IntNode(Integer.parseInt(input.substring((x-3),x)), head.getLink());         
         nodes++;
      }
      cursor = tail;
   }//end of constructor

   /**
   * Add a new element to the list.
   * @param element
   *   the new element that is being added
   * @postcondition
   *   A new element has been added to the head of the list.
   * @exception IllegalStateException
   *   Indicates value sent in is greater then one period of a unbounded number
   **/

   public void addNode(int element)  {  // allows adding value to node, max value is 999
      if(element <= 999)   {
         head = new IntNode(element, head);
         index++;
      }
      else
         throw new IllegalStateException ("Value passed in must be 999 or less");
   }

   /*
   * Sums the calling UnboundedInt with the parameter.
   * @param UnboundedInt input
   *   the new number that is being added must be a UnboundedInt object
   * @postcondition
   *   A UnboundedInt object is returned, the calling and parameter UnboundedInt objects are not changed.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new IntNode.
   */
   public UnboundedInt add(UnboundedInt input)
   {
      UnboundedInt sum = new UnboundedInt();
      UnboundedInt longer;
      UnboundedInt shorter;
      
      if(IntNode.listLength(this.head) == Math.max(IntNode.listLength(this.head),IntNode.listLength(input.head))  ) {//if the calling UnbndInt is the longer one...
         longer = this;
         shorter = input;
      }
      else{//else when the input is the longer one
         shorter = this;
         longer = input;
      }
         
      // step through list and getData()
      // use add method above to add value to new 
      longer.start();//setting both cursors to the first cell (or head of the list)
      shorter.start();
      int overflow = 0;
      int num = 0;
      
      for(int x = 1; x <= IntNode.listLength(shorter.head); x++){//loooping until we run out of values in the shorter
         num = longer.getNodeValue() + shorter.getNodeValue() + overflow;
         overflow = 0;
         if(num >999){//Finds the amount to carry over to the next cell
            overflow = num%999;
            num = num-1000;//resets num to be only three digits
         }
         sum.cursor.addNodeAfter(num);         
         sum.nodes++;
         sum.advance();//moving all the cursors to the next cell/node
         shorter.advance();
         longer.advance();
      }//end of for loop
      
      //loop for the remaining longer-number UnbndInt
      for(int x = 1; x <= (IntNode.listLength(longer.head) - IntNode.listLength(shorter.head)); x++){//loooping until we run out of values in the shorter
         num = longer.getNodeValue() + overflow;
         overflow = 0;
         sum.cursor.addNodeAfter(num);         
         sum.nodes++;
         sum.advance();//moving all the cursors to the next cell/node
         longer.advance();
      }//end of for loop

      
      return sum;
   }
   
   /*
   * multiply a new UnboundedInt to this linked list.
   * @param element
   *   the unboundedInt is being multiplied by another UnboundedInt
   * @postcondition
   *   A UnboundedInt has been added to the head of the linked list
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new IntNode.
   */
   public UnboundedInt multiply(UnboundedInt input)
   {
      UnboundedInt sum = new UnboundedInt();
      UnboundedInt smaller = new UnboundedInt();
      UnboundedInt larger = new UnboundedInt();
      int overflow=0, topNum=0, product=0, bottomNum=0, addNum=0, placeHolder=0, storeNum=0;
      
      if( IntNode.listLength(this.head)>IntNode.listLength(input.head) )   {
         larger=this;
         input=smaller;
      }   
      else  {
         smaller=this;
         input=larger;
      }   
      
      for(smaller.cursor=smaller.head; smaller.cursor!=null; smaller.advance() ) {
         bottomNum = smaller.cursor.getData();
         placeHolder++; // keeps count of position for adding to sum UnboundedInt, will have to move that distance from head   
         
         for(larger.cursor=larger.head; larger.cursor!=null; larger.advance() ) {
            topNum = larger.cursor.getData();
            product = bottomNum * topNum;
            addNum = product%1000;
            overflow = product/1000;
            storeNum = addNum+sum.cursor.getData();
            
            if(storeNum>1000) { // when number is added to sum node and is greater then 3 values, carries 1 to next period of number
               storeNum=storeNum%1000;
               overflow=overflow+1;
            }   
            
//            sum.cursor = IntNode.listPosition(sum.head, placeHolder);
//            for(int index=0; index<2; index++)  {  // loop to add values to sum of multiplication operations
//               cursor.setData = cursor.getData()+storeNum;
//               sum.cursor.advance();
//            }   
            
         }  // end looping through larger number, top of multiplication
         sum.advance(); // move cursor
      }  // end looping through smaller number, bottom of multiplication
      
      if(overflow!=0)
         sum.head.setData(overflow);
         
      return sum;          
      // nested loop, outer loop times inner loop
      
      //the max overflow from two 999 cells is 998 to the net cell with a remainder of 001 in the original 
      
      //make a method that goes through the top number and basically does multiplication by hand, 
      //recording overflows into a different UnbndInt and the product into another, 
      //then moves to the next cell and uses new UnbndInts to store overflow and the product, (remember to add 000s for placeholders). 
      //Eventually after looping through all the cells of the lower number, it adds up all the lines and returns the final product
      /*
      999,999,999
      x
      999,999,999
      9.99999998 E17
      999,999,998,000,000,000
      
      000,999,999,999
      x
      000,999,999,999
      =
      000,000,998,001
      +
      000,998,001,000
      +
      998,001,000,000
      =
      998,999,999,001 for the first cell of the bottom number. 
      this process or method would make (input.nodes)*(this.nodes) many new UnbndInts that need to be summed!  
      */
   }

   /*Method Javadocs
   * Add a new UnboundedInt to the end of the linked list.
   * @param element
   *   the new element that is being added must be a UnboundedInt
   * @postcondition
   *   A UnboundedInt has been added to the head of the linked list
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new IntNode.
   */
   public void addEnd(int input)
   {
      //Optional
      //nodes++;
   }
   
   /*
   * Generate a copy of this list.
   * @param - none
   * @return
   *   The return value is a copy of this list. Subsequent changes to the
   *   copy will not affect the original, nor vice versa. Note that the return
   *   value must be type cast to an UnboundedInt before it can be used.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   */
   public UnboundedInt clone()
   {
       UnboundedInt answer;
      
      try   {
         answer = (UnboundedInt) super.clone( );
      }
      
      catch (CloneNotSupportedException e)   {  
         // This exception should not occur. But if it does, it would probably
         // indicate a programming error that made super.clone unavailable.
         // The most common error would be forgetting the "Implements Cloneable"
         // clause at the start of this class.
         throw new RuntimeException
         ("This class does not implement Cloneable");
      }
      
    //  answer.head = head.clone( );    //might be wrong, maybe.... but like defintely not wrong
      return answer;

   }

   /*Method Javadocs
   *
   */
   public boolean equals(Object obj)
   {
      if(obj instanceof UnboundedInt)   {
         UnboundedInt sequence = (UnboundedInt) obj;
         
         if(IntNode.listLength(this.head) != IntNode.listLength(sequence.head))   {
            return false;
         }
         
         if((this.toString().equals(sequence.toString())))//we are comparing two string objects, this might not work...
         return true;
      }
      return false;

   }
   
   /*Method Javadocs
   *
   */
   public String toString()
   {  this.start();
      String output = ".";
      for(this.head = this.cursor; this.cursor != null; this.advance())
      {
         output = this.getNodeValue()+"," + output;
      }
      return output;
   }

   /*Method Javadocs
   *
   */
   public void start()
   {
      cursor = head;
   }
   
   /*Method Javadocs
   *
   */
   public void advance()
   {
      cursor = cursor.getLink();
   }
   
   /*Method Javadocs
   *
   */
   public int getNodeValue()
   {
      return cursor.getData();
   }
   
   /*Method Javadocs
   *
   */
   public String toStringNoCommas()
   {  this.start();
      String output = "";
      for(int x = this.nodes; x >= 0; x--)
      {
         output = this.getNodeValue() +"" + output;
      }
      return output;
   }
   
   /*Method Javadocs
   *
   */
//   public /*static , instance*/ methodName(parameters)
   {
      //Actual code
   }
                
   //for the multipl method, we run through the entire top number and multipl it b a single cell of the bottom number, 
   //then we loop that process for every cell in the bottom number
   
   //for the adding, we take the leftover from the previous cells' addition (zero for the first hundreds tens ones cell) 
   //and then set that to the base value of the cell before adding the top and bottom cells normall

}//end of UnboundedInt class