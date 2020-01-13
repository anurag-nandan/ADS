
public class wayneRBT {

	
	 private final int RED = 0;		//value to represent Red node
	 private final int BLACK = 1;	//value to represent black node
	 	  
	 public class RBT_Node {
		 
		    int buildingNum_RBT;			//variable to hold building number RBT will be sorted on this key
	        int color = BLACK;				//default color of node
	        RBT_Node left_child = null; 	//left child of node
	        RBT_Node right_child = null;	//right child of node
	        RBT_Node parent = null;			//parent node
	        Building corr_heap_node;		//corresponding node in MinHeap
	      
	    }
	 public RBT_Node root=null;
	 
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: find_RBNode
///Input Parameters		: (RBT_Noode *node to search*, RBT_Node *starting point*)
///Returns				: reference to found node or 'null' if node not found
///Description			: This Method finds the node passed as 'find_node' in the Red
/// 					  Black Tree by compairing the value of building number. 
///						  If a building no. matches the building num of find_node 
///						  it returns that node, otherwise it returns null.
//////////////////////////////////////////////////////////////////////////////////////////////
	 public RBT_Node find_RBNode(RBT_Node find_Node, RBT_Node node) 
	 {
	        if (root == null) {
	            return null;
	        }

	        if (find_Node.buildingNum_RBT < node.buildingNum_RBT) {			//go to left subtree if building no. to be searched is less than current node
	            if (node.left_child != null) {
	                return find_RBNode(find_Node, node.left_child);
	            }
	        } else if (find_Node.buildingNum_RBT > node.buildingNum_RBT) {	//go to right subtree if building no. to be searched is greater than current node
	            if (node.right_child != null) {
	                return find_RBNode(find_Node, node.right_child);
	            }
	        } else if (find_Node.buildingNum_RBT == node.buildingNum_RBT) {  //return the current node if it matches building no. to be searched
	            return node;
	        }
	        return null;
	 }
	 
	 
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: insert_RBT
///Input Parameters		: (RBT_Noode *node to be inserted*)
///Returns				:  -1 (if trying to insert duplicate key), otherwise 0 for insert done
///Description			: This Method inserts the node passed as 'node' in the Red
/// 					  Black Tree by traversing the correct subtree and finding 
///						  the location where this new node should be inserted 
///						  
//////////////////////////////////////////////////////////////////////////////////////////////	 
	 
	 public int insert_RBT(RBT_Node node)
	 {
		 RBT_Node temp_b = root;
		 if(root == null)	//if red black tree is empty make this the root
		 {
			 root = node;
			 node.color = BLACK;	//root of Red Black Tree will always be black
			 node.parent = null;
		 }
		 else
		 {
			 node.color = RED;		//new node is inserted as a red node in Red Black Tree
			 while(true)
			 {
				 if(node.buildingNum_RBT == temp_b.buildingNum_RBT)
				 {
					  return -1;			//return -1 if insert results in duplicate key
				 }
				 else if (node.buildingNum_RBT < temp_b.buildingNum_RBT) 	//if node to be inserted is less than current node insert in left subtree
				 {
	                    if (temp_b.left_child == null) 
	                    {
	                        temp_b.left_child = node;		//if left child is empty make this new node as left child
	                        node.parent = temp_b;			//reference to parent node
	                        break;
	                    } 
	                    else 
	                    {
	                        temp_b = temp_b.left_child;		//update current node to left child for next iteration
	                    }
	             }
				 else if(node.buildingNum_RBT > temp_b.buildingNum_RBT)		//if node to be inserted is greater than current node insert in right subtree
				 {
					 	if (temp_b.right_child == null) 
	                    {
	                        temp_b.right_child = node;		//if right child is empty make this new node as right child
	                        node.parent = temp_b;			//reference to parent node
	                        break;
	                    } 
	                    else 
	                    {
	                        temp_b = temp_b.right_child;		//update current node to right child for next iteration
	                    }
				 }			 
			 }
			 adjust_RBTtree(node);		//update nodes in tree after new node insertion	 
		 }
		 return 0;
	 }
	 
	 
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: adjust_RBTtree
///Input Parameters		: (RBT_Noode *newly inserted node*)
///Returns				: void
///Description			: This Method fixes he red black tree by performing the required
/// 					  rotations and color change for nodes. 
//////////////////////////////////////////////////////////////////////////////////////////////
	 private void adjust_RBTtree(RBT_Node node)
	 {
		 while((node!=null) && (node.parent!=null) && (node.parent.color == RED))
		 {
			 
			 RBT_Node p_sibling = null; 		//p_sibling is sibling of parent node of current node
			 if (node.parent == node.parent.parent.left_child) 			//checking if parent node is left child of grandparent node
			 {
				 p_sibling = node.parent.parent.right_child;		//p_sibling is right child of grand parent node
				 if ((p_sibling != null) && (p_sibling.color == RED))
				 {
					node.parent.color = BLACK;		//update color of parent node and sibling node as per 'XY'r rotation
					p_sibling.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;				 
				 }
				 if (node == node.parent.right_child)	//black node is the right child of parent so LR rotation will be followed
				 {
					 node = node.parent;
	                 rotate_Left(node); 					 //rotate current node left
				 }
				 node.parent.color = BLACK;				//update color of parent and grandparent
	             node.parent.parent.color = RED;
	             rotate_Right(node.parent.parent);		 //rotate grandparent node right
	             
			 }
			 else  //parent node is right child of grandparent node
			 {
				 p_sibling = node.parent.parent.left_child; 	//p_sibling is left child of grand parent
				 if (p_sibling != null && p_sibling.color == RED)
				 {
					node.parent.color = BLACK;				//update color of parent node and sibling node as per 'XY'r rotation
					p_sibling.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;				 
				 }				 
				 if (node == node.parent.left_child)		//black node is the left child of parent so LL rotation will be followed
				 {
					 node = node.parent;
	                 rotate_Right(node);	//rotate current node right					 
				 }
				 node.parent.color = BLACK;				//update color of parent and grandparent
	             node.parent.parent.color = RED;
	             rotate_Left(node.parent.parent);		//rotate grandparent node left
			 }
		 }
		 root.color = BLACK;		 
	 }
	 

//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: rotate_Left
///Input Parameters		: (RBT_Noode *node to be rotated*)
///Returns				: void
///Description			: This Method rotates to left, around the current node passed as input
/// 					  and restructure the subtrees as required.
///						  
//////////////////////////////////////////////////////////////////////////////////////////////
	 void rotate_Left(RBT_Node node)
	 {
		 if (node.parent != null)
		 {
			 if (node == node.parent.left_child) 
			 {
	                node.parent.left_child = node.right_child; //left child of parent is now the right child of current node
	         } 
			 else 
			 {
	                node.parent.right_child = node.right_child; //right child of parent is now the right child of current node
	         }
			 node.right_child.parent = node.parent;  //parent of current node is now parent of right child of current node
	         node.parent = node.right_child;		//right child of current node is now parent of current node
	         if (node.right_child.left_child != null) 
	         {
	                node.right_child.left_child.parent = node; //current node becomes parent of left child of right child of current node
	         }
	         node.right_child = node.right_child.left_child;
	         node.parent.left_child = node;		//node becomes the left child of, right child of current node
		 }
		 else
		 {
			 if(root.right_child!=null)	//root node needs to be rotated
			 {
				RBT_Node right = root.right_child;
				
				if(right.left_child != null)
	            {
					root.right_child = right.left_child;		//left child of right child of root becomes right child of root
	            	right.left_child.parent = root;				//parent of left child of right child updated
	            }
				else
				{
					root.right_child = null;
				}
	            root.parent = right;		//parent of previous root node updated
	            right.left_child = root;
	            right.parent = null;
	            root = right;			 //right child is the new root
			 }
		 }		 
	 }
	 
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: rotate_Right
///Input Parameters		: (RBT_Noode *node to be rotated*)
///Returns				: void
///Description			: This Method rotates to Right, around the current node passed as input
/// 					  and restructure the subtrees as required.
///						  
//////////////////////////////////////////////////////////////////////////////////////////////
	 
	 void rotate_Right(RBT_Node node)
	 {
		 if (node.parent != null)
		 {
			if (node == node.parent.left_child) 
			{
	           node.parent.left_child = node.left_child;	//left child of parent is now the left child of current node
	        } 
			else 
			{
	           node.parent.right_child = node.left_child;	//right child of parent is now the left child of current node
	        }

            node.left_child.parent = node.parent;	//parent of current node is now parent of left child of current node
            node.parent = node.left_child;			//left child of current node is now parent of current node
            if (node.left_child.right_child != null) 
            {
                node.left_child.right_child.parent = node;	//current node becomes parent of right child of left child of current node
            }
            node.left_child = node.left_child.right_child;
            node.parent.right_child = node; 			 //node becomes the right child of, leftt child of current node
		 }
		 else
		 {
			 if(root.left_child!=null)	//root node needs to be rotated
			 {
				RBT_Node left = root.left_child;
	            
	            if(left.right_child != null)
	            {
	            	root.left_child = left.right_child;		//right child of left child of root becomes left child of root
	            	left.right_child.parent = root;			//parent of right child of left child updated
	            }
	            else
	            {
	            	root.left_child = null;
	            }
	            root.parent = left;		//parent of previous root node updated
	            left.right_child = root;
	            left.parent = null;
	            root = left;	//left child is the new root
			 }
		 }		 
	 }
	 
	 
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: paste_node
///Input Parameters		: (RBT_Noode *target*, RBT_Node *node to be pasted in place of target*)
///Returns				: void
///Description			: This Method will make the appropriate left or right subtree of the
/// 					  removed node as the new left or right child of parent of deleted node
///						  
//////////////////////////////////////////////////////////////////////////////////////////////
	 void paste_node(RBT_Node target, RBT_Node with)
	 { 
         if(target.parent == null)
         {
             root = with;	//if present root node needs to be replaced change root to with
         }					//'with' is child of current node which will become child of parent of current node
         else if(target == target.parent.left_child)
         {
             target.parent.left_child = with;  //if target is left child of its parent then make with the left_child of parent node
         }
         else
         {
             target.parent.right_child = with;	//if target is right child of its parent then make with the right_child of parent node
         }
         if(with!=null)
         {
        	 with.parent = target.parent;		//make grand parent node of 'with' its new parent node
         }
	 }
	 
	 
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: RBT_delete_node
///Input Parameters		: (RBT_Noode *node to be deleted*)
///Returns				: boolean (if deleted true otherwise false)
///Description			: This Method will deleted the node passed as input parameter
/// 					  and call another function to adjust the Tree after deletion,
///						  returns false if failed or node to be deleted not found, otherwise true
//////////////////////////////////////////////////////////////////////////////////////////////
	 boolean RBT_delete_node(RBT_Node n)
	 {
		 if((n = find_RBNode(n, root))==null)	//if node to be deleted is not present in tree return false
		 { return false; }
		 RBT_Node x=null;
		 RBT_Node y = n;
		 int y_original_color =  y.color;
		 if((n.left_child == null) && (n.right_child ==  null))			//check if both children are null
		 {
            if(n==root)
            {
            	root=null;            	//if root needs to be deleted and it has no child, just update root object to null
            }
            else
            {
            	if(n == n.parent.left_child)
            	{
                    n.parent.left_child = null;	//if left child is the one to be deleted, make left child of parent as null 
                }
            	else
            	{
            		n.parent.right_child = null; //if right child is the one to be deleted, make right child of parent as null
            	}
            }
	     }
		 else if(n.left_child == null)	//if left child of current node is null but it has right child
		 {
            x = n.right_child;  			//save reference of right child
            paste_node(n, n.right_child); 	//cut n from tree and paste right child of n in its place
	     }
		 else if(n.right_child == null)  //if right child of current node is null but it has left child
		 {
            x = n.left_child;				//save reference of left child
            paste_node(n, n.left_child); 	//cut n from tree and paste left child of n in its place
	     }
		 else							//if both child of current node are present
		 {
		 	y = Minimum_node(n.right_child);	//searching for minimum node in right subtree that will replace the current node
            y_original_color = y.color;			//save the original color of smallest node of the right subtree for updating node color later
            x = y.right_child;					//save reference to right child of the above smallest node
            if(y.parent == n)		//if minimum node is immediate child of current node and only one node between minimum node and parent of current node
            {   
            	if(x!=null)
            	{
            		x.parent = y;		//update parent pointer of minimum node found in right subtree
            	}
            }
            else		//if more than one nodes between minimum node and node to be deleted 
            {
            	paste_node(y, y.right_child);	//make right subtree of y as subtree of parent of y
                y.right_child = n.right_child;
                y.right_child.parent = y;
            }
            paste_node(n, y);		//paste minimum node greater than parent of current node to be deleted in place of current node
            y.left_child = n.left_child;
            y.left_child.parent = y;
            y.color = n.color;			 
		 }
		 if((y_original_color==BLACK) && (x!=null))
	     {  delete_FixRBT(x);  }  //function call to perform required rotation and color change if the delete node was black in color, deleting red node doesn't require fix
	     return true;
	 }
	 
	 
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: Minimum_node
///Input Parameters		: (RBT_Noode *node to be searched*)
///Returns				: RBT_Node (returns smallest node in subtree)
///Description			: This Method traverses the left subtree unless smallest node
/// 					  is found and then returns that smallest node.
///						  
//////////////////////////////////////////////////////////////////////////////////////////////
	 RBT_Node Minimum_node(RBT_Node small)
	 {
        while(small.left_child!=null)
        {
        	small = small.left_child; //recursively search the smallest node of subtree
        }
        return small;
	 }
	 
	 
//////////////////////////////////////////////////////////////////////////////////////////////
///Method				: delete_FixRBT
///Input Parameters		: (RBT_Noode *starting node to be fixed*)
///Returns				: void
///Description			: This Method performs the appropriate rotation and color change
/// 					  after particular node is deleted from the tree.
///						  
//////////////////////////////////////////////////////////////////////////////////////////////
	 
	 void delete_FixRBT(RBT_Node n1)
	 {
		 while(n1!=root && n1.color == BLACK)		//keep looping until root isn't reached and all nodes that needs to be fixed are black.
		 {
			 if(n1 == n1.parent.left_child)	//if current node is left child of its parent
			 {
				 RBT_Node temp1 = n1.parent.right_child;	//reference to sibling of current node
				 if(temp1 != null)
				 {
					 if(temp1.color == RED)		//if sibling's color is red
					 {
						 temp1.color = BLACK;	//update color
						 n1.parent.color =  RED;
						 rotate_Left(n1.parent);		//rotate left around parent as deficiency of black nodes is in right subtree 
						 temp1 = n1.parent.right_child;		//fix the updated right subtree of current node
					 }
					 if((temp1 != null) && (temp1.left_child.color == BLACK) && (temp1.right_child.color == BLACK)) //if siblings color is black and so does the color of siblings children
					 {
						 temp1.color = RED;	//both child are black so, update color and move up in tree for next iteration
						 n1 = n1.parent;
						 continue;					 
					 }
					 else if((temp1 != null) && (temp1.right_child.color == BLACK))		//if right child of temp1 is black and left child is red
					 {
						 temp1.left_child.color = BLACK;		//update color of left child
						 temp1.color = RED;						//update color of temp1
						 rotate_Right(temp1);				//rotate right about temp1
						 temp1 = n1.parent.right_child;					 
					 }
					 if((temp1 != null) && (temp1.right_child.color == RED))	//if right child of temp1 is red
					 {
					 	temp1.color = n1.parent.color;	//update temp1 color
	                    n1.parent.color = BLACK;		//update parent color
	                    temp1.right_child.color = BLACK;
	                    rotate_Left(n1.parent);			//rotate left about parent
	                    n1 = root;					 	
					 }
				 }
			 }
			 else		//if current node is right child of its parent
			 {
				 RBT_Node temp2 = n1.parent.left_child;		//reference to sibling of current node
				 if(temp2 != null)
				 {
					 if(temp2.color == RED)
					 {
						temp2.color = BLACK;
	                    n1.parent.color = RED;
	                    rotate_Right(n1.parent);		//rotate right around parent as deficiency of black nodes is in left subtree
	                    temp2 = n1.parent.left_child;		//fix the updated left subtree of current node
					 }
					 if(temp2.right_child.color == BLACK && temp2.left_child.color == BLACK)  //if siblings color is black and so does the color of siblings children
					 {
	                    temp2.color = RED;	//both child are black so, update color and move up in tree for next iteration
	                    n1 = n1.parent;
	                    continue;
		             }
					 else if(temp2.left_child.color == BLACK)	//if left child of sibling is red
					 {
	                    temp2.right_child.color = BLACK;
	                    temp2.color = RED;
	                    rotate_Left(temp2);			//rotate left about temp2
	                    temp2 = n1.parent.left_child;
		             }
					 if(temp2.left_child.color == RED)
					 {
	                    temp2.color = n1.parent.color;	//update temp2 color
	                    n1.parent.color = BLACK;		//update parent color
	                    temp2.left_child.color = BLACK;
	                    rotate_Right(n1.parent);	//rotate right about parent
	                    n1 = root;
		             }
				 }
			 }			 
		 }
		 n1.color = BLACK;
	 }
	 
}
