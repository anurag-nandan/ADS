
public class wayneMinHeap {
	
	public static int HpCapacity; //maximum size of Heap
	public static int current_size = 0;
	public Building [] bldg;

//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: wayneMinHeap
///Input Parameters		: int (size of array of objects)
///Returns				: void
///Description			: constructor for class wayneMinHeap which will create array of objects of
/// 					  class building with the given capacity
///						  
//////////////////////////////////////////////////////////////////////////////////////////////
	public wayneMinHeap(int capacity)
	{
		HpCapacity = capacity;   //constructor to initialize MinHeap Size
		current_size = 0;
		bldg = new Building [HpCapacity+1];  //HPcapacity+1
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: insert
///Input Parameters		: (int building num, int total_time)
///Returns				: int
///Description			: This Method insert the building in MinHeap ordered by executed time
/// 					  and returns the location of inserted node in Min Heap.
///						  
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public int insert(int buildingNum, int total_time)
	{
		int pos;
		//Insert(buildingNum ,total_time) should produce no output unless 
		//buildingNum is a duplicate in which case you should output an error and stop.
		if(current_size == (HpCapacity + 1))
		{
			//it's an error do not produce any output as per project statement above; skip this
            return -1;
		}
		
		current_size++;
		pos = current_size;
		bldg[pos]=new Building();
		bldg[pos].buildingNum = buildingNum;  //update parameters of building to the node of MinHeap
		bldg[pos].total_time = total_time;
		bldg[pos].executed_time = 0;
		return pos;
		
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: bubble_Up
///Input Parameters		: (int pos)
///Returns				: void
///Description			: This Method will heapify the MinHeap by going from bottom to up
/// 					  and performing the necessary swaps based on executed time
///						  and if executed time is same, then based on building id
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void bubble_Up(int pos)
	{
		int parent_node = pos/2;
		int current_node = pos;
		while((current_node > 1) && (bldg[parent_node].executed_time >= bldg[current_node].executed_time))
		{
			
			//if to break ties
			int swap_true = 1;
			if(bldg[parent_node].executed_time == bldg[current_node].executed_time)
			{
				if(bldg[parent_node].buildingNum > bldg[current_node].buildingNum)
				{
					swap_true = 1;
				}
				else
				{///duplicate kkeys
					swap_true = 0;
				}
				
			}
			if(swap_true == 1)
			{
				swap(current_node, parent_node);
			}
			current_node = parent_node;
			parent_node = parent_node/2;
			
		}
		
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: swap
///Input Parameters		: (int *target 1*, in *target 2*)
///Returns				: void
///Description			: This Method will swap the two nodes of heap passed as input to this
///						 function.
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public void swap(int x1, int x2)
	{
		Building temp_b;
		temp_b = new Building();
		
		temp_b = bldg[x1];
		bldg[x1] = bldg[x2];
		bldg[x2] = temp_b;
			
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: extractMin
///Input Parameters		: none
///Returns				: object of class Building
///Description			: This Method will remove the smallest elemnt from MinHeap, which is the
///						  root node and update the MinHeap by replacing last node with root and
///						  then performing heapify operation, it reurns the removed node.
//////////////////////////////////////////////////////////////////////////////////////////////
	
	public Building extractMin()
	{
		Building b_temp = bldg[1];
		bldg[1]=bldg[current_size];
		bldg[current_size] = null;
		if(current_size > 1)
		{
			sinkdown(1);
		}
		current_size--;
		return b_temp;
		
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: sinkdown
///Input Parameters		: int (starting index)
///Returns				: void
///Description			: This Method will start from root and heapify the MinHeap,
///						  swapping the node with it's child if child is small, as it goes
///						  down till leaf nodes or till the correct position of node is found
//////////////////////////////////////////////////////////////////////////////////////////////
	public void sinkdown(int loc)
	{
		int loc_small = loc;
		int left_Child_Id = 2 * loc;
        int right_Child_Id = (2 * loc)+1;
        
        
        if ((left_Child_Id < current_size) && (bldg[left_Child_Id]!=null) && (bldg[loc_small].executed_time >= bldg[left_Child_Id].executed_time)) {
           if(bldg[loc_small].executed_time == bldg[left_Child_Id].executed_time)
           {
        	   if(bldg[left_Child_Id].buildingNum < bldg[loc_small].buildingNum)
        	   {
        		   loc_small = left_Child_Id;
        	   }     
           }
           else
           {
        	loc_small = left_Child_Id;
           }
        }
        if ((right_Child_Id < current_size) && (bldg[right_Child_Id]!=null) &&(bldg[loc_small].executed_time >= bldg[right_Child_Id].executed_time)) {
        	if(bldg[loc_small].executed_time == bldg[left_Child_Id].executed_time)
            {
         	   if(bldg[right_Child_Id].buildingNum < bldg[loc_small].buildingNum)
         	   {
         		   loc_small = right_Child_Id;
         	   }     
            }
            else
            {
            	loc_small = right_Child_Id;
            }
        }
        if(loc_small != loc)
        {
        	swap(loc, loc_small);
        	sinkdown(loc_small);
        }
	}
	
	
	
}
