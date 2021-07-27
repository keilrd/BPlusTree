import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    /**
     * Creates an empty BTree
     * @param t the minimum degree of the tree
     */
    BTree(int t) {
        this.root = null;
        this.t = t;
    }

    /**
     * Returns the root of the tree
     * @return the root node
     */
    public BTreeNode getRoot() {
        return this.root;
    }

    /**
     * Function to search the tree for a given key
     * @param studentId - The key to search for
     * @return the record ID for the given student id or -1
     *         if it is not found
     */
    long search(long studentId) {

        String errorMsg = "The given studentId has not been found in the table.";

        // if tree is empty display error message
        if (this.root == null) {
            System.out.println(errorMsg);
            return -1;
        } else {
            // if root exists
            BTreeNode current = root;

            // traverse the tree
            while (current.leaf == false) {
                // loop over keys in the node
                for (int i = 0; i < current.n; i++) {
                    // if current node key is larger than the key to insert we found where we need
                    // to insert the node
                    if (studentId < current.keys[i]) {
                        current = current.children[i];
                        break;
                    }
                    // if we reach the end start looking at children nodes
                    if (i == current.n - 1) {
                        current = current.children[i + 1];
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

    /**
     * Inserts a student into the tree and the Student.csv file
     * @param student the student to add to the tree
     * @return the updated BTree
     */
    BTree insert(Student student) {
        int maxNode = (2 * this.t);

        // throw an exception if student is null
        if (student == null) {
            throw new IllegalArgumentException("Argument 'student' is null");
        }

        // create new node if tree is empty
        if (this.root == null) {
            // create new root node
            this.root = new BTreeNode(this.t, true);

            // set leaf node key value pair
            this.root.keys[0] = student.studentId;
            this.root.values[0] = student.recordId;
            this.root.n++;

        } else {
            // if root already exists
            BTreeNode current = root;
            BTreeNode parent = current;

            // traverse tree and find the leaf node
            while (current.leaf == false) {
                parent = current;

                // loop over keys in the node
                for (int i = 0; i < current.n; i++) {
                    // if current node key is larger than the key to insert we found where we need
                    // to insert the node
                    if (student.studentId < current.keys[i]) {
                        current = current.children[i];
                        break;
                    }
                    // if we reach the end start looking at children nodes
                    if (i == current.n - 1) {
                        current = current.children[i + 1];
                        break;
                    }
                }
            }

            // after insert location has been found

            // if the node is not full
            if (current.n < maxNode) {
                int i = 0;

                // find the location where we need to insert the new key
                while (student.studentId > current.keys[i] && i < current.n) {
                    i++;
                }

                // make room for the new key and value
                for (int j = current.n; j > i; j--) {
                    current.keys[j] = current.keys[j - 1];
                    current.values[j] = current.values[j - 1];
                }

                // set new node and value
                current.keys[i] = student.studentId;
                current.values[i] = student.recordId;

                // increment the node size
                current.n++;

            } else {
                // node is already full we need to split
                BTreeNode newLeaf = new BTreeNode(this.t, true);

                // create temp data structures of max size + 1
                long[] tempKey = new long[maxNode + 1];
                long[] tempValue = new long[maxNode + 1];

                // copy nodes key and value to the new temp data structures
                for (int i = 0; i < maxNode; i++) {
                    tempKey[i] = current.keys[i];
                    tempValue[i] = current.values[i];
                }

                // find the location where we need to insert the new key
                int i = 0;
                while (student.studentId > tempKey[i] && i < maxNode) {
                    i++;
                }

                // make room for the new key and value
                for (int j = maxNode; j >= i; j--) {
                    tempKey[j] = tempKey[j - 1];
                    tempValue[j] = tempValue[j - 1];
                }

                // set new node and value
                tempKey[i] = student.studentId;
                tempValue[i] = student.recordId;

                // set the new size for the existing node and the new node
                // new size is (max size + 1)/2
                current.n = this.t;
                newLeaf.n = (maxNode + 1) - this.t;

                // set the next point in the existing node to the new node
                newLeaf.next = current.next;
                current.next = newLeaf;

                // set the first (max size + 1)/2 key and values in the existing node
                for (i = 0; i < current.n; i++) {
                    current.keys[i] = tempKey[i];
                    current.values[i] = tempValue[i];
                }

                // clear the remain keys and values from the existing node
                // these values now live in the new node
                for (i = current.n; i < maxNode; i++) {
                    current.keys[i] = 0;
                    current.values[i] = 0;
                }

                // set the second (max size + 1)/2 key and values in the new node
                int j;
                for (i = 0, j = current.n; i < newLeaf.n; i++, j++) {
                    newLeaf.keys[i] = tempKey[j];
                    newLeaf.values[i] = tempValue[j];
                }

                // if the node being split is the root node
                if (current == root) {
                    // create new non-leaf node and set existing/new nodes as child nodes
                    BTreeNode newRoot = new BTreeNode(this.t, false);

                    newRoot.keys[0] = newLeaf.keys[0];
                    newRoot.children[0] = current;
                    newRoot.children[1] = newLeaf;
                    newRoot.n = 1;

                    // set the new non-leaf node as the root
                    root = newRoot;
                } else {
                    // if the node is not the root node we need to recursively add to the internal
                    // non-leaf nodes
                    recursiveInsert(newLeaf.keys[0], parent, newLeaf);
                }
            }
        }

        // write line to file if it does not exist
        // create new student string
        String newStudent = student.studentId + "," + student.studentName + "," + student.major
            + "," + student.level + "," + student.age + "," + student.recordId;

        // open file and create scanner
        try (Scanner scanner = new Scanner(new File("src/Student.csv"));) {
            boolean found = false;
            // loop over lines in file to see if the student is already in file
            while (scanner.hasNextLine()) {
                // get current line from scanner
                String line = scanner.nextLine();

                // if found set found boolean and quit since we don't need to
                // insert it more than once
                if (newStudent.equals(line)) {
                    found = true;
                    break;
                }
            }

            // if we didn't find the line we need to add it
            if (!found) {
                FileWriter fileWriter = new FileWriter("src/Student.csv", true);
                PrintWriter writer = new PrintWriter(fileWriter);
                writer.println(newStudent);
                writer.flush();
                writer.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Insert: " + e);

        } catch (Exception e2) {
            System.out.println(e2);
        }


        return this;
    }

    /**
     * recursive helper function to insert a node into the internal (non-leaf) nodes
     * @param key the student ID to insert
     * @param current the current node to insert the student under
     * @param child the new child node to insert
     */
    void recursiveInsert(long key, BTreeNode current, BTreeNode child) {
        int maxNode = (2 * this.t);
        // if the nodes has available space for a new child
        if (current.n < maxNode) {
            int i = 0;

            // find the location where we need to insert
            while (key > current.keys[i] && i < current.n) {
                i++;
            }

            // make space for the new key
            for (int j = current.n; j > i; j--) {
                current.keys[j] = current.keys[j - 1];
            }

            // make space for the new children node
            for (int j = current.n + 1; j > i + 1; j--) {
                current.children[j] = current.children[j - 1];
            }

            // insert the new key and child node
            current.keys[i] = key;
            current.children[i + 1] = child;

            // increment the node size
            current.n++;

        } else {
            // if the node does not have space for a new child
            // we need to split the node
            BTreeNode newNode = new BTreeNode(this.t, false);

            // temp data structures for split
            long[] tempKey = new long[maxNode + 1];
            BTreeNode[] tempChildren = new BTreeNode[maxNode + 2];

            // copy over the current nodes key and children to the temp data structures
            for (int i = 0; i < maxNode; i++) {
                tempKey[i] = current.keys[i];
            }

            for (int i = 0; i < maxNode + 1; i++) {
                tempChildren[i] = current.children[i];
            }

            // find the location where we need to insert the new key
            int i = 0;
            while (key > tempKey[i] && i < maxNode) {
                i++;
            }

            // makes space for the new key
            for (int j = maxNode; j > i; j--) {
                tempKey[j] = tempKey[j - 1];
            }

            // add new key to the temp data structure
            tempKey[i] = key;

            // make space of the new children
            for (int j = maxNode + 1; j > i + 1; j--) {
                tempChildren[j] = tempChildren[j - 1];
            }

            // add new children to the temp data structure
            tempChildren[i + 1] = child;

            // set the new values for the existing and new nodes
            current.n = this.t;
            newNode.n = maxNode - this.t;

            // copy the values and children to the current node
            for (i = 0; i < current.n; i++) {
                current.keys[i] = tempKey[i];
            }

            for (i = 0; i < current.n + 1; i++) {
                current.children[i] = tempChildren[i];
            }

            // copy the values and children to the new node
            int j;
            for (i = 0, j = current.n + 1; i < newNode.n; i++, j++) {
                newNode.keys[i] = tempKey[j];
            }

            for (i = 0, j = current.n + 1; i < newNode.n + 1; i++, j++) {
                newNode.children[i] = tempChildren[j];
            }

            // clear the remain keys and children from the existing node
            // these values now live in the new node
            for (i = current.n; i < maxNode; i++) {
                current.keys[i] = 0;
            }

            for (i = current.n + 1; i < maxNode + 1; i++) {
                current.children[i] = null;
            }

            // if the existing node is the root node
            if (current == root) {
                // create a new non-leaf node and sent values
                BTreeNode newRoot = new BTreeNode(this.t, false);

                // newRoot.keys[0] = newNode.children[0].keys[0];
                newRoot.keys[0] = getLeftKey(newNode);
                newRoot.children[0] = current;
                newRoot.children[1] = newNode;
                newRoot.n = 1;

                // set the root to the new root node
                root = newRoot;
            } else {
                // if not root recursively insert in the parent nodes
                // recursiveInsert(newNode.children[newNode.n-1].keys[0],findParentNode(root,current),
                // newNode);
                BTreeNode parent = findParentNode(root, current);
                recursiveInsert(getLeftKey(newNode), parent, newNode);
            }
        }

    }

    /**
     * helper function to find the parent node of a node
     * @param 
     * @param
     * @return 
     */
    BTreeNode findParentNode(BTreeNode current, BTreeNode child) {
        BTreeNode parent = null;

        // if it reaches the end of the tree and does not find node
        if (current.leaf || current.children[0].leaf) {
            return null;
        }

        // traverse the tree
        for (int i = 0; i < current.n + 1; i++) {
            // if the node is a child or the current node return the current node
            if (current.children[i] == child) {
                parent = current;
                return parent;
            } else {
                // if not found recursively call on the child nodes
                parent = findParentNode(current.children[i], child);
                if (parent != null) {
                    return parent;
                }
            }
        }

        return parent;
    }

    Long getLeftKey(BTreeNode current) {

        while (current.leaf == false) {
            current = current.children[0];
        }

        return current.keys[0];
    }


    /**
     * Deletes the record ID for a given student ID from the tree
     * @param studentId - student ID to delete the record ID for
     * @return - true if the record is deleted successfully, otherwise false.
     */
    boolean delete(long studentId) {

        boolean success = false;

        success = delete(root, studentId);

        // if the root is now empty, the tree is empty so delete the root
        if (root != null && root.n == 0) {
            root = null;
        }

        // if found, delete from csv file

        if (success) {

            deleteFromFile(studentId);

        }


        return success;

    }

    /***********************************************************
     * Helper function to delete a node for the given key
     * @param current - the node to search for the key to delete
     * @param studentId - The key to search for
     * @return true if the student id is deleted, otherwise false
     **********************************************************/
    boolean delete(BTreeNode current, long studentId) {

        if (current == null) {
            return false; // key isn't in the tree if the tree is empty
        }

        if (current.leaf) {
            return deleteLeaf(current, studentId);
        }

        else {
            return deleteInternal(current, studentId);
        }
    }

    /***********************************************************
     * Helper function to search a leaf node for the given key
     * @param current - the leaf node to search
     * @param studentId - The key to search for
     * @return the record ID for the given student id or -1
     *         if it is not found
     **********************************************************/
    boolean deleteLeaf(BTreeNode current, long studentId) {

        boolean found = false;

        for (int i = 0; i < current.n; i++) {

            // stop searching if we've passed where they key should be
            if (!found && studentId < current.keys[i]) {
                return false;
            }

            // check if we found the key
            if (!found && studentId == current.keys[i]) {

                found = true;

            }

            if (found) {

                if (i == current.n - 1) {
                    current.keys[i] = 0;
                    current.values[i] = 0;
                }

                else {
                    current.keys[i] = current.keys[i + 1];
                    current.values[i] = current.values[i + 1];
                }

            }

        }

        if (found) {
            current.n--;
        }

        return found;

    }

    /***********************************************************
     * Helper function to continue searching the tree for a given
     * key in an internal node.
     * @param current - the node we're currently searching
     * @param studentId - the key we're searching for
     * @return the record ID for the student ID or -1 if it is
     *         not found
     **********************************************************/
    boolean deleteInternal(BTreeNode current, long studentId) {

        boolean found = false;
        BTreeNode child = null;
        int childIndex = 0;

        for (int i = 0; i < current.n && child == null; i++) {
            if (studentId < current.keys[i]) {
                child = current.children[i];
                childIndex = i;
                found = delete(child, studentId);
            }
        }

        // if the key wasn't less than any of the keys in the node, search the far right child
        if (child == null) {
            child = current.children[current.n];
            childIndex = current.n;
            found = delete(child, studentId);
        }

        if (!found) {
            return false;
        }


        // Rebalance Child
        if (child.n < t) {

            boolean done = false;

            BTreeNode rightSibling = null;
            BTreeNode leftSibling = null;


            // attempt to borrow from right sibling

            if (childIndex < current.n) { // if there is a right sibling

                rightSibling = current.children[childIndex + 1]; // check right sibling first if it
                // exists

                if (rightSibling.n > t) {

                    // borrow from sibling

                    if (!child.leaf) {

                        child.keys[child.n] = current.keys[childIndex];
                        child.children[child.n + 1] = rightSibling.children[0];
                        current.keys[childIndex] = rightSibling.keys[0];

                    }

                    else {
                        child.keys[child.n] = rightSibling.keys[0];
                        child.values[child.n] = rightSibling.values[0];
                    }

                    child.n++;

                    // shift key/value pairs in sibling
                    for (int i = 0; i < rightSibling.n - 1; i++) {
                        rightSibling.keys[i] = rightSibling.keys[i + 1];
                        rightSibling.values[i] = rightSibling.values[i + 1];
                        rightSibling.children[i] = rightSibling.children[i + 1];
                    }

                    // clear last key/value pair
                    rightSibling.keys[rightSibling.n - 1] = 0;
                    rightSibling.values[rightSibling.n - 1] = 0;
                    rightSibling.children[rightSibling.n - 1] =
                        rightSibling.children[rightSibling.n];
                    rightSibling.children[rightSibling.n] = null;
                    rightSibling.n--;

                    // update parent
                    if (child.leaf) {
                        current.keys[childIndex] = rightSibling.keys[0];
                    }

                    done = true;
                }

            }

            // if we couldn't borrow from the right, borrow from the left if possible

            if (!done) {

                if (childIndex > 0) { // if there is a left sibling

                    leftSibling = current.children[childIndex - 1]; // check right sibling first if
                                                                    // it
                    // exists

                    if (leftSibling.n > t) {

                        // borrow from sibling

                        if (!child.leaf) {
                            child.children[child.n + 1] = child.children[child.n];
                        }

                        // shift key/value pairs in child
                        for (int i = child.n; i > 0; i--) {
                            child.keys[i] = child.keys[i - 1];
                            child.values[i] = child.values[i - 1];
                            child.children[i] = child.children[i - 1];
                        }

                        if (child.leaf) {
                            child.keys[0] = leftSibling.keys[leftSibling.n - 1];
                            child.values[0] = leftSibling.values[leftSibling.n - 1];
                            child.n++;

                            // update parent value
                            current.keys[childIndex - 1] = child.keys[0];

                        }

                        else {
                            child.keys[0] = current.keys[childIndex - 1];
                            child.children[0] = leftSibling.children[leftSibling.n];
                            child.n++;

                            // update parent value
                            current.keys[childIndex - 1] = leftSibling.keys[leftSibling.n - 1];

                        }

                        // clear last key/value pair in sibling
                        leftSibling.keys[leftSibling.n - 1] = 0;
                        leftSibling.values[leftSibling.n - 1] = 0;
                        leftSibling.children[leftSibling.n] = null;
                        leftSibling.n--;



                        done = true;
                    }

                }

            }

            // if we weren't able to borrow from the left or the right, merge
            if (!done) {

                // merge right if there's a right sibling
                if (rightSibling != null) {

                    int offset = 0;
                    int addedKeys = 0; // keep track of how many keys are shifted to update n

                    // if we're merging internal nodes, shift down key from the parent
                    if (!child.leaf) {
                        offset = 1;
                        child.keys[child.n] = current.keys[childIndex];
                        child.children[child.n + 1] = rightSibling.children[0];
                        addedKeys++;
                    }

                    // add values from sibling
                    for (int i = 0; i < rightSibling.n; i++) {
                        child.keys[child.n + i + offset] = rightSibling.keys[i];
                        child.values[child.n + i + offset] = rightSibling.values[i];
                        child.children[child.n + i + 1 + offset] =
                            rightSibling.children[i + offset];
                        addedKeys++;
                    }

                    child.n += addedKeys;


                    for (int i = childIndex; i < current.n - 1; i++) {
                        current.keys[i] = current.keys[i + 1];
                        current.children[i + 1] = current.children[i + 2];

                    }

                    // clear last key/child pointer
                    current.keys[current.n - 1] = 0;
                    current.children[current.n] = null;
                    current.n--;

                    // update next pointer
                    child.next = rightSibling.next;

                    // if we emptied the root, make the child the new root
                    if (current == this.root && current.n == 0) {
                        this.root = child;
                    }

                }

                // otherwise merge left
                else {

                    int offset = 0;
                    int addedKeys = 0; // keep track of how many keys are shifted to update n

                    // if we're merging internal nodes, shift down key from the parent
                    if (!child.leaf) {
                        offset = 1;
                        leftSibling.keys[leftSibling.n] = current.keys[childIndex - 1];
                        leftSibling.children[leftSibling.n + 1] = child.children[0];
                        addedKeys++;
                    }

                    // add values from sibling
                    for (int i = 0; i < child.n; i++) {
                        leftSibling.keys[leftSibling.n + i + offset] = child.keys[i];
                        leftSibling.values[leftSibling.n + i + offset] = child.values[i];
                        leftSibling.children[leftSibling.n + i + 1] = child.children[i + 1];
                        addedKeys++;
                    }

                    leftSibling.n += addedKeys;


                    for (int i = childIndex - 1; i < current.n - 1; i++) {
                        current.keys[i] = current.keys[i + 1];
                        current.children[i + 1] = current.children[i + 2];

                    }

                    // clear last key/child pointer and decrement n
                    current.keys[current.n - 1] = 0;
                    current.children[current.n] = null;
                    current.n--;

                    // update next pointer
                    leftSibling.next = child.next;

                }

                // if we emptied the root, make the child the new root
                if (current == this.root && current.n == 0) {
                    this.root = leftSibling;
                }

            }

        }

        return true;
    }

    /**
     * Deletes the student ID from the csv file
     * @param studentId The student to delete
     */
    void deleteFromFile(long studentId) {

        try {

            File studentFile = new File("Student.csv");
            File tempFile = new File("tempStudent.tmp");

            tempFile.createNewFile();

            Scanner scanner = new Scanner(studentFile);

            FileWriter fileWriter = new FileWriter(tempFile);
            PrintWriter writer = new PrintWriter(fileWriter);

            // loop over lines in file and add students that should remain to array list
            while (scanner.hasNextLine()) {

                // get current line from scanner
                String line = scanner.nextLine();
                String[] student = line.split(",");

                if (studentId != Long.parseLong(student[0])) {
                    writer.println(line);
                    writer.flush();
                }
            }

            writer.close();
            scanner.close();

            tempFile.renameTo(studentFile);



        } catch (FileNotFoundException e) {
            System.out.println("Insert: " + e);

        } catch (Exception e2) {
            System.out.println(e2);
        }
    }

    /**
     * Returns an ArrayList of record IDs in order of student ID
     * @return ArrayList of record IDs
     */
    List<Long> print() {

        List<Long> listOfRecordID = new ArrayList<>();

        // null case
        if (this.root == null) {
            return listOfRecordID;
        }

        /**
         * TODO:
         * Implement this function to print the B+Tree.
         * Return a list of recordIDs from left to right of leaf nodes.
         *
         */

        BTreeNode current = this.root;
        while (current.leaf == false) {
            current = current.children[0];
        }

        while (current != null) {
            for (int i = 0; i < current.n; i++) {
                listOfRecordID.add(current.keys[i]);
            }
            current = current.next;
        }

        return listOfRecordID;
    }
}
