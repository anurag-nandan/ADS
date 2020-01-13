
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class risingCity {
	
	public static int global_time;	//counter to act as incrementing days
    public static int build_done;	//Status flag for completion of city
    public static int found_node = 0; 	//flag to indicate node found for printing
    public static int builds_remain = 0;	//flag to separate triplets while printing
	
    public static void main(String[] args) throws IOException {
		
    	
    	risingCity.build_done = 0;	//initialize status flag
    	risingCity.global_time = -1;	//initialize global time
    	String str_in;
    	int input_time = -1;		//time extracted from input text file for each operation
    	int read_next = 1;	//flag to read next line from text file
    	int choice = 0;
    	int param1 = -1, param2 = -1;
    	int loc_in_heap = -1;
    	int local_time = 0;		//local time counter to calculate consecutive five days
    	int remove_from_heap = 0;	//status flag for removing node from heap
    	int no_of_builds = 0;		//count of buildings under construction
    	int end_of_file = 0;		//flag to indicate end of input text file
    	
    	wayneMinHeap Heap_City = new wayneMinHeap(2000);	//object for min heap class created
    	
    	wayneRBT RB_City = new wayneRBT();	//instance for Red Black tree class created
    
        Building temp_mh = new Building();	//temporary Building node for intermediate functions
    	
		File file_input = new File(args[0]); //take name of text file from command line argument
		
		BufferedReader read_in = null;
		try {
		read_in = new BufferedReader(new FileReader(file_input));
		} catch (FileNotFoundException e) {
		
		e.printStackTrace();
		}
		
		PrintStream write2txt=new PrintStream("Output.txt");	//file to output results
		System.setOut(write2txt);
        	
		for(risingCity.global_time = 0; risingCity.build_done !=1 ; risingCity.global_time++ )	//incrementing days till city is build
		{
			
			if(wayneMinHeap.current_size > 0)
			{	
				
				if(risingCity.global_time > 0)
				{
					
					
					if(local_time == 5)	//updating heap after consecutive 5 days are done on a building
					{
						if(Heap_City.bldg[1].executed_time < Heap_City.bldg[1].total_time)
						{
							local_time = 0;
							for(int i = wayneMinHeap.current_size; i > (wayneMinHeap.current_size/2); i--)
							{
								Heap_City.bubble_Up(i); //bubble up from leaf nodes to take minimum to root
							}							
						}	
					}
					local_time++;	
					Heap_City.bldg[1].executed_time = Heap_City.bldg[1].executed_time + 1; //incrementing the no.of days worked on a building
					if((Heap_City.bldg[1].executed_time) == (Heap_City.bldg[1].total_time))
					{
						remove_from_heap = 1;	//set flag to remove bilding if execution time reached total time
						local_time = 0;
					}
					
				}
			}
			if(read_next == 1)
			{
				if((str_in = read_in.readLine()) != null) //read input line from text file
				{ 
					if(str_in.length() < 4)			
					{
						while(((str_in = read_in.readLine()) != null) && (str_in.length() < 4));						
					}
					
				    String[] input_line = str_in.split(":|\\(|,|\\)", 0); //tokenize input line into various parameters
									    		   
				    input_time = str_2_Int(input_line[0]); //extracting time of performing an operation in input file
				    if(((input_line[1].contains("insert")) == true) | ((input_line[1].contains("Insert")) == true))
				    {
				    	choice = 1;  //set if insert command
				    }
				    else if(((input_line[1].contains("PrintBuilding")) == true) | ((input_line[1].contains("Print")) == true))
				    {
				    	choice = 2;		//set if Print command    	
				    }
				    else
				    {
				    	choice = 0;
				    }
				    	param1 = str_2_Int(input_line[2]); //extract first input parameter
				    if(input_line.length == 4)
				    {
				    	param2 = str_2_Int(input_line[3]);	//extract second input parameter if it exists
				    }
				    else
				    {
				    	param2 = -1;
				    }
				}
				else if(str_in == null)
				{
					end_of_file = 1;	//set status flag to indicate end of file
				}
			}
			read_next=0;
		    if(risingCity.global_time == input_time)	//perform the given operation day of arrival of input matches the day
		    {
		    	read_next = 1;
		    	wayneRBT.RBT_Node RBTn = RB_City.new RBT_Node();
		    	wayneRBT.RBT_Node temp_n = RB_City.new RBT_Node();
	    		
		    	RBTn.buildingNum_RBT = param1;
		    	if(choice == 1)	//if command was an insert
		    	{
		    		if(wayneMinHeap.current_size < (wayneMinHeap.HpCapacity + 1))
		    		{
		    					    		
			    		if((RB_City.insert_RBT(RBTn)) == -1)	//insert in red black tree
			    		{
			    			System.out.println("(Error: Duplicate entry)"); //if duplicate building number arrives
			    		}
			    		else
			    		{
			    			if((loc_in_heap = Heap_City.insert(param1, param2))!=-1)	//insert building in MinHeap
			    			{
				    			no_of_builds++;
					    		Heap_City.bldg[loc_in_heap].corrTr = RBTn;			//link corresponding node from MinHeap to RB tree
					    		RBTn.corr_heap_node = Heap_City.bldg[loc_in_heap];	//link corresponding node from RB tree to MinHeap
			    			}
			    	
			    		}
		    		}
		    		
		    	}		    	
		    	else if(choice == 2)	//if command was a print
		    	{
		    		if(param2 != -1)
		    		{		    			
		    			int found = -1;
		    			found_node = 0;
		    			builds_remain = 0;
		    			found = PrintBuilding(RB_City.root, param1, param2);  //function call to print Buildings within range [param1 to param2] inclusive. search will start from root
		    			if(found == -1)
		    			{
		    				System.out.println("(0,0,0)"); //if no building found in the given range
		    			}
		    			else
		    			{
		    				System.out.println(""); //insert new line
		    			}
		    		}
		    		else if(param2 == -1)	//if single building needs to be printed
		    		{
		    			if((temp_n = RB_City.find_RBNode(RBTn, RB_City.root)) != null)
		    			{
			    			temp_mh = temp_n.corr_heap_node;
			    			System.out.println("(" + temp_mh.buildingNum + "," + temp_mh.executed_time + "," + temp_mh.total_time + ")");	//print triplets
			    		}
		    			else
		    			{
		    				System.out.println("(0,0,0)");	//if building not found
		    			}
		    		}
		    	}
		    }		    
		
		    if(remove_from_heap == 1)
		    {
		    	remove_from_heap = 0;
		    	wayneRBT.RBT_Node temp_rm = RB_City.new RBT_Node(); //temporary variable to remove the corresponding node in RB tree
		    	temp_rm = Heap_City.bldg[1].corrTr;
		    	System.out.println("(" + Heap_City.bldg[1].buildingNum + "," + risingCity.global_time + ")");	//output building num and day of completion before deleting from data structure
		    	Heap_City.extractMin();	//remove building from heap
		    	no_of_builds--;
		    	for(int i = wayneMinHeap.current_size; i > (wayneMinHeap.current_size/2); i--)
				{
					Heap_City.bubble_Up(i);	//heapify after removing node from heap
				}
		    	RB_City.RBT_delete_node(temp_rm);	//remove building from red black tree
		    	
		    }
		    if((no_of_builds == 0) && (end_of_file==1))
	    	{
	    		risingCity.build_done = 1; //if construction on all building from input file is done
	    	}
		}			
		
	read_in.close();	//close input file
	}
    
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: PrintBuilding
///Input Parameters		: (wayneRBT.RBT_Node  , int 'range start', int 'range end')
///Returns				: int (flag to indicate if fond or not)
///Description			: This Method will print all buildings that exists and under construction
/// 					  between range 'range start' and 'range end', returns -1 if no node 
///						  found between range otherwise returns 1
//////////////////////////////////////////////////////////////////////////////////////////////
    
    public static int PrintBuilding(wayneRBT.RBT_Node RB_node, int b1, int b2)	//inorder traversal
    {
    	Building temp_node = new Building();	//temporary node of type Building
    	
    	if(RB_node == null)
    	{
    		return -1;
    	}
    	if(b1 < RB_node.buildingNum_RBT)	//go to left subtree if current node is grater than b1
    	{
    		PrintBuilding(RB_node.left_child, b1, b2);    		
    	}
    	if((b1 <= RB_node.buildingNum_RBT) && (RB_node.buildingNum_RBT <= b2))
    	{
    		temp_node = RB_node.corr_heap_node;
    		if(builds_remain > 0)	//flag to check whether already a printed building exist in front of present building
    		{
    			System.out.print(",");
    			builds_remain = 0;
    		}
    		System.out.print("(" + temp_node.buildingNum + "," + temp_node.executed_time + "," + temp_node.total_time + ")");	//print triplets which are in range
    		builds_remain++;
    		found_node = 1;		//set flag to indicate building found in range
    	}
    	if(b2 > RB_node.buildingNum_RBT)
    	{
    		PrintBuilding(RB_node.right_child, b1, b2);		//go to right subtree if current node is less than b2
    	}
    	
    	if(found_node == 1)
    	{
    		return 1;	//success, building found in given range
    	}
    	else
    	{
    		return -1;	//fail, no building in range
    	}
    		
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: str_2_Int
///Input Parameters		: String 'str'
///Returns				: int (converted string in integer)
///Description			: This Method will convert the characters to their equivalent
/// 					  integer representation and returns this converted integer
///						  
//////////////////////////////////////////////////////////////////////////////////////////////
    
    public static int str_2_Int( String str )
    {
    	int i = 0;
    	int extract_num = 0;
    	while( i < str.length()) {
    		extract_num *= 10;
    		extract_num += str.charAt(i++) - '0';	//subtracting char by ascii value of 0 will give the integer equivalent of numeric character
        }
    	return extract_num;		//return the integer obtained
    }

}
