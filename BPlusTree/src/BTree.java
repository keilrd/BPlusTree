import java.util.ArrayList;
import java.util.List;

/**
 * B+Tree Structure
 * Key - StudentId
 * Leaf Node should contain [ key,recordId ]
 */
class BTree {

    /**
     * Pointer to the root node.
     */
    private BTreeNode root;
    /**
     * Number of key-value pairs allowed in the tree/the minimum degree of B+Tree
     **/
    private int t;

    BTree(int t) {
        this.root = null;
        this.t = t;
    }

    long search(long studentId) {
        /**
         * TODO:
         * Implement this function to search in the B+Tree.
         * Return recordID for the given StudentID.
         * Otherwise, print out a message that the given studentId has not been found in the table and return -1.
         */
    	String errorMsg = "The given studentId has not been found in the table.";
    	
    	//if tree is empty display error message
    	if (this.root == null) {
    		System.out.println(errorMsg);
    		return -1;
    	} else {
    		//if root exists
    		BTreeNode current = root;
    		
    		//traverse the tree
    		while (current.leaf == false) {
    			//loop over keys in the node
    			for (int i =0; i < current.n; i++) {
    				//if current node key is larger than the key to insert we found where we need to insert the node
    				if (studentId < current.keys[i]) {
    					current = current.children[i];
    					break;
    				}
    				//if we reach the end start looking at children nodes
    				if (i == current.n - 1) {
    					current = current.children[i+1];
    					break;
    				}
    			}
    		}
    		
    		//
    		for (int i = 0; i < current.n; i++) {
    			if (current.keys[i] == studentId) {
    				return current.values[i];
    			}
    		}
    	
    	}
    	
    	System.out.println(errorMsg);
        return -1;
    }

    BTree insert(Student student) {
        /**
         * TODO:
         * Implement this function to insert in the B+Tree.
         * Also, insert in student.csv after inserting in B+Tree.
         */
    	
    	//throw an exception if student is null
    	if (student == null) {
    		throw new IllegalArgumentException("Argument 'student' is null");
    	}
    	
    	//create new node if tree is empty
    	if (this.root == null) {
    		//create new root node
    		this.root = new BTreeNode(this.t, true);
    		
    		//set leaf node key value pair
    		this.root.keys[0] = student.studentId;
    		this.root.values[0] = student.recordId;
    		this.root.n++;
    		
    	} else {
    		//if root already exists
    		BTreeNode current = root;
    		BTreeNode parent = current;
    		
    		// traverse tree and find the leaf node
    		while (current.leaf == false) {
    			parent = current;
    			
    			//loop over keys in the node
    			for (int i =0; i < current.n; i++) {
    				//if current node key is larger than the key to insert we found where we need to insert the node
    				if (student.studentId < current.keys[i]) {
    					current = current.children[i];
    					break;
    				}
    				//if we reach the end start looking at children nodes
    				if (i == current.n - 1) {
    					current = current.children[i+1];
    					break;
    				}
    			}
    		}
    		
    		//after insert location has been found
    		
    		//if the node is not full
    		if (current.n < ((this.t * 2) - 1)) {
    			int i = 0;
    			
    			//find the location where we need to insert the new key
    			while(student.studentId > current.keys[i] && i < current.n) {
    				i++;
    			}
    			
    			//make room for the new key and value
    			for(int j = current.n; j > i; j--) {
    				current.keys[j] = current.keys[j-1];
    				current.values[j] = current.values[j-1];
    			}
    			
    			//set new node and value
    			current.keys[i] = student.studentId;
    			current.values[i] = student.recordId; 
    			
    			//increment the node size
    			current.n++;
    			
    		} else {
    			//node is already full we need to split 
    			BTreeNode newLeaf = new BTreeNode(this.t,true);
    			
    			//create temp data structures of max size + 1
    			long[] tempKey = new long[2 * this.t];
    			long[] tempValue = new long[2 * this.t];
    			
    			//copy nodes key and value to the new temp data structures
    			for(int i = 0; i < ((2 * this.t) - 1); i++) {
    				tempKey[i] = current.keys[i];
    				tempValue[i] = current.values[i];
    			}
    			
    			//find the location where we need to insert the new key
    			int i = 0;
    			while(student.studentId > tempKey[i] && i < (2 * this.t - 1)) {
    				i++;
    			}
    			
    			//make room for the new key and value
    			for(int j = 2 * t - 1; j >= i; j--) {
    				tempKey[j] = tempKey[j - 1];
    				tempValue[j] = tempValue[j - 1];
    			}
    			
    			//set new node and value
    			tempKey[i] = student.studentId;
    			tempValue[i] = student.recordId;
    			
    			//TODO switch to (max size + 1)/2 
    			//set the new size for the existing node and the new node
    			//new size is (max size + 1)/2 
    			current.n = this.t;
    			newLeaf.n = this.t;
    			
    			//set the next point in the existing node to the new node
    			current.next = newLeaf;
    			
    			//set the first (max size + 1)/2 key and values in the existing node
    			for (i = 0; i < current.n; i++) {
    				current.keys[i] = tempKey[i];
    				current.values[i] = tempValue[i];
    			}
    			
    			//clear the remain keys and values from the existing node
    			//these values now live in the new node
    			for(i = current.n; i < 2 * this.t - 1; i++) {
    				current.keys[i] = 0;
    				current.values[i] = 0;
    			}
    			
    			//set the second (max size + 1)/2 key and values in the new node
    			int j;
    			for (i = 0, j = current.n; i < newLeaf.n; i++,j++) {
    				newLeaf.keys[i] = tempKey[j];
    				newLeaf.values[i] = tempValue[j];
    			}
    			
    			//if the node being split is the root node
    			if (current == root) {
    				//create new non-leaf node and set existing/new nodes as child nodes
    				BTreeNode newRoot = new BTreeNode(this.t, false);
    				
    				newRoot.keys[0] = newLeaf.keys[0];
    				newRoot.children[0] = current;
    				newRoot.children[1] = newLeaf;
    				newRoot.n = 1;
    				
    				//set the new non-leaf node as the root
    				root = newRoot;
    			} else {
    				//if the node is not the root node we need to recursively add to the internal non-leaf nodes
    				recursiveInsert(newLeaf.keys[0], parent, newLeaf);
    			}
    		}
    	}
    	
        return this;
    }
    
    //recursive helper function to insert a node into the internal (non-leaf) nodes
    void recursiveInsert(long key, BTreeNode current, BTreeNode child) {
    	//if the nodes has available space for a new child
    	if(current.n < 2 * this.t -1) {
    		int i = 0;
    		
    		//find the location where we need to insert
    		while(key > current.keys[i] && i < current.n) {
    			i++;
    		}
    		
    		//make space for the new key
    		for(int j = current.n; j > i; j--) {
    			current.keys[j] = current.keys[j-1];
    		}
    		
    		//make space for the new children node
    		for(int j = current.n + 1; j > i+ 1; j--) {
    			current.children[j] = current.children[j - 1];
    		}
    		
    		//insert the new key and child node
    		current.keys[i] = key;
    		current.children[i+1] = child;
    		
    		//increment the node size
    		current.n++;
    		
    	} else {
    		//if the node does not have space for a new child
    		//we need to split the node
    		BTreeNode newNode = new BTreeNode(this.t, false);
    		
    		//temp data structures for split
    		long[] tempKey= new long[2 * current.t];
    		BTreeNode[] tempChildren = new BTreeNode[2 * current.t + 1];
    		
    		//copy over the current nodes key and children to the temp data structures
    		for (int i = 0; i < 2 * this.t - 1; i++) {
    			tempKey[i] = current.keys[i];
    		}
    		
    		for (int i = 0; i < 2 * this.t; i++) {
    			tempChildren[i] = current.children[i];
    		}
    		
    		//find the location where we need to insert the new key
    		int i = 0;
    		while(key > tempKey[i] && i < 2 * current.t - 1) {
    			i++;
    		}
    		
    		//makes space for the new key
    		for(int j = 2 * this.t - 1; j > i; j--) {
    			tempKey[j] = tempKey[j - 1];
    		}
    		
    		//add new key to the temp data structure
    		tempKey[i] = key;
    		
    		//make space of the new children
    		for(int j = 2 * this.t; j > i + 1; j++) {
    			tempChildren[j] = tempChildren[j-1];
    		}
    		
    		//add new children to the temp data structure
    		tempChildren[i+1] = child;
    		
    		//set the new values for the existing and new nodes
    		current.n = this.t;
    		newNode.n = (2 * this.t - 1) - this.t;
    		
    		//copy the values and children to the new node
    		int j;
    		for (i = 0, j = current.n + 1; i < newNode.n; i++, j++) {
    			newNode.keys[i] = tempKey[j];
    			System.out.println(j);
    		}
    		
    		for (i = 0,j = current.n + 1; i < newNode.n + 1; i++, j++) {
    			newNode.children[i] = tempChildren[j];
    		}
    		
    		//TODO
			//clear the remain keys and children from the existing node
			//these values now live in the new node
			for(i = current.n ; i < 2 * this.t - 1; i++) {
				current.keys[i] = 0;
			}
			
			for(i = current.n + 1; i < 2 * this.t; i++) {
				current.children[i] = null;
			}
    		
    		//if the existing node is the root node
    		if (current == root) {
    			//create a new non-leaf node and sent values
    			BTreeNode newRoot = new BTreeNode(this.t, false);
    			
    			newRoot.keys[0] = newNode.children[0].keys[0];
    			newRoot.children[0] = current;
                newRoot.children[1] = newNode;
                newRoot.n = 1;
                
                //set the root to the new root node
                root = newRoot;
    		} else {
    			//if not root recursively insert in the parent nodes
    			recursiveInsert(current.keys[current.n],findParentNode(root,current), newNode);
    		}
    	}
    }
    
    //helper function to find the parent node of a node
    BTreeNode findParentNode(BTreeNode current, BTreeNode child) {
    	BTreeNode parent= null;
    	
    	//if it reaches the end of the tree and does not find node
    	if (current.leaf || current.children[0].leaf) {
    		return null;
    	}
    	
    	//traverse the tree
    	for (int i = 0; i < current.n + 1; i++) {
    		//if the node is a child or the current node return the current node
    		if (current.children[i] == child) {
    			parent = current;
    			return parent;
    		}else {
    			//if not found recursively call on the child nodes
    			parent = findParentNode(current.children[i],child);
    			if(parent != null) {
    				return parent;
    			}
    		}
    	}
    	
    	return parent;
    }
    

    boolean delete(long studentId) {
        /**
         * TODO:
         * Implement this function to delete in the B+Tree.
         * Also, delete in student.csv after deleting in B+Tree, if it exists.
         * Return true if the student is deleted successfully otherwise, return false.
         */
        return true;
    }

    List<Long> print() {

        List<Long> listOfRecordID = new ArrayList<>();

        /**
         * TODO:
         * Implement this function to print the B+Tree.
         * Return a list of recordIDs from left to right of leaf nodes.
         *
         */
        return listOfRecordID;
    }
}
