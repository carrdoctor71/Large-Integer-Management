//Project 2 by Ken Carr and Duncan McKay
//Objective of this class is to allow handling and computations of integer numbers that are greater then
//what can normally be handeled as a primative integer, unbounded integers can be computed and stored.

public class UnboundedInt implements Cloneable    // Start of UnboundedInt class
{
   // Invariant of the UnboundedInt class
   //    1. The Unbounded Integers are stored in a linked list.
   //    2. The head is a IntNode reference variable.
   //    3. The total number of Unbounded Integers in the list is in the instance variable nodes.
   //    4. The cursor location is in the instance variable index.
   //    5. The tail is a IntNode reference variable.
   
   private int nodes;
   private IntNode tail, head, cursor;
   /*
   * Initialize an UnboundedInt with no values currently stored. Head, tail, and cursor are null.
   * signifiying that there are no other nodes in the list therefore the UnboundedInt is 0;
   * @param - none
   * @postcondition
   *   A UnboundedInt that is a empty linked list: head, tail, cursor are null.
   */
   public UnboundedInt()   // DEFAULT CONSTRUCTOR
   {
      int nodes = 1;
      IntNode tail = new IntNode(000,null);
      head = tail;
      cursor = tail;
   }  //******************END DEFAULT CONSTRUCTOR********************************************************************


   /*
   * Declare and initialize an UnboundedInt with the values parsed from a String input.
   * @param input
   *  a string of integers
   * @postcondition
   *   a UnboundedInt eith each node representing a period (multiple of 1000, max value=999)
   * @exception OutOfMemoryError
   *   Indicates insufficient memory
   */
   public UnboundedInt(String input)   // USER DEFINED CONSTRUCTOR 
   {
   
      tail = new IntNode(Integer.parseInt(input.substring((0),(input.length()%3))), null);
      head = new IntNode(Integer.parseInt(input.substring((input.length()-3),(input.length()))),tail);

      int endIndex = input.length() - 1;
      int numNodes = input.length() / 3;
      int lastNode = (input.length() % 3) - 1;

      if(input.length()%3!=0)
         numNodes = numNodes + 1;
         
      for(int index = numNodes; index>0; index--){
         if(index<3)
           head.addNodeAfter(Integer.parseInt(input.substring(0,index))); 
         else
           head.addNodeAfter(Integer.parseInt(input.substring((index-3),index)));           
      }

   
   
    
/*             if(input.length()>6){//if the number will fit in more than head and tail, 
            tail = new IntNode(Integer.parseInt(input.substring((0),(input.length()%3))), null);
            head = new IntNode(Integer.parseInt(input.substring((input.length()-3),(input.length()))),tail);
            nodes = 2;
            for(int x = 0; x < input.length()/3-1; x++){
               head.addNodeAfter(Integer.parseInt(input.substring((3*x)+input.length()%3 ,(3*(x+1))+input.length()%3 )));         
               nodes++;
            }
            cursor=head;
         }
         else if(input.length()<=6 && input.length()>3){//if the number only needs head and tail
            tail = new IntNode(Integer.parseInt(input.substring((0),(input.length()%3))), null);
            head = new IntNode(Integer.parseInt(input.substring((input.length()-3),(input.length()))),tail);
            cursor = head;
            nodes = 2;
         }
         else{//if the number only needs the tail node
            tail = new IntNode(Integer.parseInt(input.substring((0),(input.length()%3))), null);
            head = tail;
            cursor = head;
            nodes = 1;
         }
*/         
   }  //************************END USER DEFINED CONSTRUCTOR**************************************************************

   /**
   * addNode adds a node to the head of the UnboundedInt with a inputted value that must be less than 1000.
   * @param element
   *   integer that is less than 1000and passed into method
   * @postcondition
   *   A new node that is a period added to the UnboundedInt
   * @exception IllegalStateException
   *   Indicates value sent in is greater then one period of a unbounded number
   **/

   public void addNode(int element)  {  // ADDS A NODE TO HEAD
      if(element <= 999)   {
         head = new IntNode(element, head);
      }
      else
         throw new IllegalStateException ("Value passed in must be 999 or less");
   }  //**********************END addNode METHOD**************************************************************************

   /*
   * Sums the calling UnboundedInt with the parameter.
   * @param UnboundedInt input
   *   the new number that is being added must be a UnboundedInt object
   * @postcondition
   *   A UnboundedInt object is returned, the calling and parameter UnboundedInt objects are not changed.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new IntNode.
   */
   public UnboundedInt add(UnboundedInt input)  // add METHOD
   {  
      this.start();
      input.start();
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
      
   }  // **********************************END add METHOD********************************************************************
   
   /*
   * multiply a new UnboundedInt to this linked list.
   * @param element
   *   the unboundedInt is being multiplied by another UnboundedInt
   * @postcondition
   *   A UnboundedInt has been added to the head of the linked list
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new IntNode.
   */
  public UnboundedInt multiply(UnboundedInt input) // multiply METHOD
   {
      this.start();
      input.start();
      UnboundedInt sum = new UnboundedInt();
      UnboundedInt smaller = new UnboundedInt();
      UnboundedInt larger = new UnboundedInt();
      int remainder=0, topNum=0, product=0, bottomNum=0, addNum=0, placeHolder=0, storeNum=0, overflow=0;
      
      if( IntNode.listLength(this.head)>IntNode.listLength(input.head) )   {
         larger=this;
         smaller=input;
      }   
      else  {
         smaller=this;
         larger=input;
      }   
      
      for(smaller.cursor=smaller.head; smaller.cursor!=null; smaller.advance() ) {
         bottomNum = smaller.cursor.getData();
         larger.start();
         remainder=0;
         for(larger.cursor=larger.head; larger.cursor!=null; larger.advance() ) {
            topNum = larger.cursor.getData();
            product = bottomNum * topNum;
            addNum = product%1000;
            storeNum = overflow + remainder + addNum + sum.cursor.getData();
            overflow=0;
            remainder = product/1000;      
            
            if(storeNum>1000) { // when number is added to sum node and is greater then 3 values, carries 1 to next period of number
               storeNum=storeNum%1000;
               overflow=1;
            }   
            else
               sum.cursor.addNodeAfter(storeNum);  
               
         }  // end looping through larger number, top of multiplication
         sum.advance(); // move cursor
         sum.nodes++;
      }  // end looping through smaller number, bottom of multiplication
      
      if(remainder!=0)
         sum.cursor.addNodeAfter(remainder);
         
      return sum;          
   }  //******************************END multiply METHOD

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
   {  
//      this.start();
      String output="";
      IntNode checkCursor;
//      int listSize=IntNode.listLength(this.head);
//      for(int index=0; index<listSize; index++)
      for(this.head = this.cursor; this.cursor != null; this.advance())
      {
            output = this.getNodeValue() + "," + output;
//            output = output + "," + this.getNodeValue();
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
      for(this.head = this.cursor; this.cursor != null; this.advance())
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