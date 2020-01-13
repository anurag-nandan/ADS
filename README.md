# ADS
The Project is build in JAVA using the following structure:-
Project consists of the following java files:
 risingCity.java
 Building.java
 wayneMinHeap.java
 wayneRBT.java
Classes used in Project
1. Class for node Building
public class Building {
public int buildingNum;
public int executed_time;
public int total_time;
wayneRBT.RBT_Node corrTr;
}
Description: This Class is the basic node structure of the Building with the required parameters i.e. Building Number (which will be unique), executed time (executed time of construction) and total time (time required to construct this building). The object of this class are used throughout the program to be inserted in the Heap/Tree or as reference object to store nodes temporarily for intermediate operations.
2. Class for Red black tree node
public class RBT_Node {
int buildingNum_RBT;
int color = BLACK;
RBT_Node left_child = null;
RBT_Node right_child = null;
RBT_Node parent = null
Building corr_heap_node;
}
Description: This is the basic structure of node of a Red Black Tree, it has the appropriate parameters of a Red-Black tree node i.e. color, right child, left child, parent and a reference node to corresponding heap node of same building. It also has the building number which is the key based on which this red black tree will be implemented.
3. Java Class files
The Java Class file wayneMinHeap.java and wayneRBT.java holds the code for performing the required operations on MinHeap and Red Black Tree respectively.
The Java Class file risingCity.java is the main java class file which is the entry point of the program and holds the Main() function.
Methods implemented in project
The following methods are contained in the program to perform all the required operations on buildings. Listed below are their prototypes.
In file MinHeap.java
1. wayneMinHeap(int );
Description: constructor for class wayneMinHeap which will create array of objects of class building with the given capacity passed as argument to constructor
2. int insert(int , int );
Description: This Method insert the building in MinHeap ordered by executed time, by taking in building number and total days required for construction as arguments, and returns the location of inserted node in Min Heap.
3. void bubble_Up(int );
Description: This Method will heapify the MinHeap by going from bottom to up i.e. from leaves to root, and performs the necessary swaps based on executed time and if executed time is same, then based on building number
4. void swap(int , int );
Description: This Method will swap the two nodes of heap passed as input argument to this function.
5. Building extractMin();
Description: This Method will remove the smallest element from MinHeap, which is the root node and update the MinHeap by replacing last node with root and then performing heapify operation, it returns the removed node.
6. void sinkdown(int );
Description: This Method will start from root and heapify the MinHeap, swapping the node with it's child if child is small, as it goes down till leaf nodes or till the correct position of node is found.
In file wayneRBT.java
1. RBT_Node find_RBNode(RBT_Node , RBT_Node );
Description: This Method finds the node passed as 'find_node' in the Red Black Tree by compairing the value of building number. If a building is found it returns that node, otherwise it returns null. The first argument is node to be searched and second argument is the starting point for search in tree.
2. int insert_RBT(RBT_Node );
Description: This Method inserts the node passed as 'node' in the Red Black Tree by traversing the correct subtree and finding the location where this new node should be inserted. Returns – if there is duplicate insertion.
3. void adjust_RBTtree(RBT_Node );
Description: This Method fixes the red black tree by performing the required color change for nodes and calling the required functions to rotate node either left or right.
4. void rotate_Left(RBT_Node );
Description: This Method rotates to left, around the current node passed as input and restructure the subtrees as required. The node to be rotated is passed as argument.
5. void rotate_Right(RBT_Node );
Description: This Method rotates to Right, around the current node passed as input and restructure the subtrees as required. The node to be rotated is passed as argument.
6. void paste_node(RBT_Node , RBT_Node );
Description: This Method will make the appropriate left or right subtree of the removed node as the new left or right child of parent of deleted node. The first argument is the node to be deleted and the second argument is child of deleted node which will be pasted in its position.
7. boolean RBT_delete_node(RBT_Node );
Description: This Method will delete the node passed as input parameter and call another function to adjust the Tree after deletion. Returns false if node to be deleted not found, otherwise true.
8. RBT_Node Minimum_node(RBT_Node )
Description: This Method traverses the left subtree unless smallest node is found and then returns that smallest node. Input argument is node from where the downward traversal will start.
9. void delete_FixRBT(RBT_Node );
Description: This Method performs the appropriate rotation and color change after particular node is deleted from the tree.
In file risingCity.java
1. void main(String[] args);
Description: This is the entry point for the complete program all the fundamental codes to increment the counter for days, reading the input text file and calling other functions to perform insert and print operations is contained in Main function.
2. int PrintBuilding(wayneRBT.RBT_Node , int , int );
Description: This Method will print all buildings that exists and are under construction between range passed as the second and third argument to function. Returns -1 if no node found between range otherwise returns 1.
3. int str_2_Int( String );
Description: This Method will convert the numeric string to its equivalent integer value and returns this converted integer.
Program Execution
The program will keep incrementing count for days in a for loop, subsequent operations takes place based on this count.
With each incrementing day program will increase the execution time of the selected building and if the executed time matches the total time required to construct that building it will set the status flag to remove building from data structure.
After consecutive 5 days next building from heap is selected by heapifying based on minimum execution time, if two nodes with same minimum execution times exist then the one with smaller building number will be selected.
The program will read input from given input text file and if the day in input command matches the current day count then corresponding operation will be executed. If day in input command doesn’t match the current day count then the readline function will wait before reading the next line.
Line read from input file is tokenized to extract various parameters which are time of arrival of command, type of command and the third and fourth parameter will depend on the type of command.
If it’s an insert command than parameters will be building number and total time
If it’s a print command than parameter will be either a single building number or min. and max. range of building number.
The variable ‘choice’ is updated based on the type of input command.
When day of arrival of command(variable: input_time) matches the day(variable: risingCity.global_time) following steps are performed:-
 if command is an insert, program first checks the red black tree for any duplicate entries by calling insert_RBT() (this search is done in O(log(n)) time).If building to be
insered is unique it checks for the MinHeap size and inserts the building to MinHeap by calling Heap_City.insert() if current size is less than the capacity of Heap.
 If command is print, with single argument, that particular node is searched by calling function find_RBNode() and if node is found, its building number, execution time and total time are printed within square brackets and separated by comma in this same order to an output text file otherwise (0,0,0) is printed to the output file.  If command is print with two arguments, then PrintBuilding() is called which searches for all the available building that exists in data structure between the range passed as argument to function PrintBuilding(). If no building is found in range it prints (0,0,0) to output file.
After each print operation new line is inserted to the output file.
Whenever a new insert command comes and if a particular building under construction hasn’t reached its limit of 5 consecutive days of construction, then it is inserted as the last node in the MinHeap without performing heapify after this insert. Heapify will be performed when the building currently under construction reaches the limit of 5 days under construction after the insert command came. If insert arrives on the same day as a building completes 5 days of construction or it completes its total time then in that case heapify will be performed.
With each insert to MinHeap corresponding node in Red black tree is also inserted.
With each insert into MinHeap a variable no_of_builds is incremented and when a building is removed from MinHeap no_of_builds is decremented. This variable is used to know when program needs to be terminated along with end_of_file variable which is set to ‘1’ when no more lines remain to read from input file.
When a building completes construction i.e. ‘execution time’ matches ‘total time’, remove_from_heap variable is set to ‘1’ and its building number and day of completion is printed to the output file in the format of (building number, day of completion) given in program as: (buildingNum, risingCity.global_time)
With each removal of node from MinHeap corresponding node from Red Black tree is also removed.
When all the input is read from input file and all the buildings have been constructed the program’s execution is terminated by setting the flag risingCity.build_done = 1 which is the terminating condition in the for loop: for(risingCity.global_time = 0;risingCity.build_done !=1;risingCity.global_time++)
The last value to the output file will be the building number and day of execution of the final building which was completed.
The operation of insertion and deletion in MinHeap is different from the fundamental heap operation as we are postponing the heapify operation if some building is already under construction at the root of data structure.
Operation on red black tree are same as he usual insertion and deletion from Red Black Tree.

Compiling the program
Please execute the following command on terminal from the directory where project zip file is extracted.
1. make clean
2. make
Executing the program
Following command is for running the java program
1. java risingCity “input text file.txt”
Result
The final output file will be created in the same directory where program will be executed and the name of file will be ‘Output.txt’.
