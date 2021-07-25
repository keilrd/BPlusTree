import static org.junit.Assert.*;

import org.junit.Test;

public class InsertTest {

	@Test
	public void insertNull() {
		BTree testTree = new BTree(3);
		Student nullStudent = null;
		
		Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
			testTree.insert(nullStudent);
		});
		
		String expectedMessage = "Argument 'student' is null";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void insertMiddle() {
		BTree testTree = new BTree(3);
		testTree.insert(new Student(1, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(3, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(2, 10, "test", "CS", "level", 1));
		
		BTreeNode leaf = testTree.getRoot();
		
		assertTrue(leaf.keys[0] == 1);
		assertTrue(leaf.keys[1] == 2);
		assertTrue(leaf.keys[2] == 3);
		
	}
	
	@Test
	public void splitRoot() {
		BTree testTree = new BTree(3);
		testTree.insert(new Student(1, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(3, 10, "test", "CS", "level", 3));
		testTree.insert(new Student(2, 10, "test", "CS", "level", 2));
		testTree.insert(new Student(4, 10, "test", "CS", "level", 4));
		testTree.insert(new Student(5, 10, "test", "CS", "level", 5));
		testTree.insert(new Student(6, 10, "test", "CS", "level", 6));
		
		BTreeNode root = testTree.getRoot();
		BTreeNode leaf1 = root.children[0];
		BTreeNode leaf2 = root.children[1];
		
		assertFalse(root.leaf);
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == null);
		
		assertTrue(root.keys[0] == 4);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.keys[2] == 3);
		assertTrue(leaf2.keys[0] == 4);
		assertTrue(leaf2.keys[1] == 5);
		assertTrue(leaf2.keys[2] == 6);
		
	}
	
	@Test
	public void splitLeaf() {
		BTree testTree = new BTree(3);
		testTree.insert(new Student(1, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(3, 10, "test", "CS", "level", 3));
		testTree.insert(new Student(2, 10, "test", "CS", "level", 2));
		testTree.insert(new Student(4, 10, "test", "CS", "level", 4));
		testTree.insert(new Student(5, 10, "test", "CS", "level", 5));
		testTree.insert(new Student(6, 10, "test", "CS", "level", 6));
		testTree.insert(new Student(7, 10, "test", "CS", "level", 5));
		testTree.insert(new Student(8, 10, "test", "CS", "level", 8));
		testTree.insert(new Student(9, 10, "test", "CS", "level", 9));
		
		BTreeNode root = testTree.getRoot();
		BTreeNode leaf1 = root.children[0];
		BTreeNode leaf2 = root.children[1];
		BTreeNode leaf3 = root.children[2];
		
		assertFalse(root.leaf);
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == null);
		
		assertTrue(root.keys[0] == 4);
		assertTrue(root.keys[1] == 7);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.keys[2] == 3);
		assertTrue(leaf2.keys[0] == 4);
		assertTrue(leaf2.keys[1] == 5);
		assertTrue(leaf2.keys[2] == 6);
		assertTrue(leaf3.keys[0] == 7);
		assertTrue(leaf3.keys[1] == 8);
		assertTrue(leaf3.keys[2] == 9);
		
	}
	
	@Test
	public void splitLeafMiddle() {
		BTree testTree = new BTree(3);
		testTree.insert(new Student(1, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(2, 10, "test", "CS", "level", 2));
		testTree.insert(new Student(3, 10, "test", "CS", "level", 3));
		
		testTree.insert(new Student(10, 10, "test", "CS", "level", 10));
		testTree.insert(new Student(11, 10, "test", "CS", "level", 11));
		testTree.insert(new Student(12, 10, "test", "CS", "level", 12));
		
		testTree.insert(new Student(4, 10, "test", "CS", "level", 4));
		testTree.insert(new Student(5, 10, "test", "CS", "level", 5));
		testTree.insert(new Student(6, 10, "test", "CS", "level", 6));
		
		BTreeNode root = testTree.getRoot();
		BTreeNode leaf1 = root.children[0];
		BTreeNode leaf2 = root.children[1];
		BTreeNode leaf3 = root.children[2];
		
		assertFalse(root.leaf);
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == null);
		
		assertTrue(root.keys[0] == 4);
		assertTrue(root.keys[1] == 10);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.keys[2] == 3);
		assertTrue(leaf2.keys[0] == 4);
		assertTrue(leaf2.keys[1] == 5);
		assertTrue(leaf2.keys[2] == 6);
		assertTrue(leaf3.keys[0] == 10);
		assertTrue(leaf3.keys[1] == 11);
		assertTrue(leaf3.keys[2] == 12);
		
	}
	
	@Test
	public void splitLargeMiddle() {
		BTree testTree = new BTree(3);
		testTree.insert(new Student(1, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(2, 10, "test", "CS", "level", 2));
		testTree.insert(new Student(3, 10, "test", "CS", "level", 3));
		testTree.insert(new Student(30, 10, "test", "CS", "level", 30));
		testTree.insert(new Student(31, 10, "test", "CS", "level", 31));
		testTree.insert(new Student(32, 10, "test", "CS", "level", 32));
		testTree.insert(new Student(33, 10, "test", "CS", "level", 33));
		testTree.insert(new Student(34, 10, "test", "CS", "level", 34));
		testTree.insert(new Student(35, 10, "test", "CS", "level", 35));
		testTree.insert(new Student(36, 10, "test", "CS", "level", 36));
		testTree.insert(new Student(37, 10, "test", "CS", "level", 37));
		testTree.insert(new Student(38, 10, "test", "CS", "level", 38));
		testTree.insert(new Student(39, 10, "test", "CS", "level", 39));
		testTree.insert(new Student(40, 10, "test", "CS", "level", 40));
		testTree.insert(new Student(4, 10, "test", "CS", "level", 4));
		testTree.insert(new Student(5, 10, "test", "CS", "level", 5));
		testTree.insert(new Student(6, 10, "test", "CS", "level", 6));
		testTree.insert(new Student(7, 10, "test", "CS", "level", 7));
		testTree.insert(new Student(8, 10, "test", "CS", "level", 8));
		testTree.insert(new Student(9, 10, "test", "CS", "level", 9));
		testTree.insert(new Student(10, 10, "test", "CS", "level", 10));
		testTree.insert(new Student(11, 10, "test", "CS", "level", 11));
		testTree.insert(new Student(12, 10, "test", "CS", "level", 12));
		
		BTreeNode root = testTree.getRoot();
		
		BTreeNode int1 = root.children[0];
		BTreeNode int2 = root.children[1];
		
		BTreeNode leaf1 = int1.children[0];
		BTreeNode leaf2 = int1.children[1];
		BTreeNode leaf3 = int1.children[2];
		BTreeNode leaf4 = int1.children[3];
		BTreeNode leaf5 = int2.children[0];
		BTreeNode leaf6 = int2.children[1];
		BTreeNode leaf7 = int2.children[2];
		
		assertFalse(root.leaf);
		assertFalse(int1.leaf);
		assertFalse(int2.leaf);
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == leaf4);
		assertTrue(leaf4.leaf);
		assertTrue(leaf4.next == leaf5);
		assertTrue(leaf5.leaf);
		assertTrue(leaf5.next == leaf6);
		assertTrue(leaf6.leaf);
		assertTrue(leaf6.next == leaf7);
		assertTrue(leaf7.leaf);
		assertTrue(leaf7.next == null);
		
		assertTrue(root.keys[0] == 30);
		assertTrue(int1.keys[0] == 4);
		assertTrue(int1.keys[1] == 7);
		assertTrue(int1.keys[2] == 10);
		assertTrue(int2.keys[0] == 33);
		assertTrue(int2.keys[1] == 36);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.keys[2] == 3);
		assertTrue(leaf1.values[0] == 1);
		assertTrue(leaf1.values[1] == 2);
		assertTrue(leaf1.values[2] == 3);
		
		assertTrue(leaf2.keys[0] == 4);
		assertTrue(leaf2.keys[1] == 5);
		assertTrue(leaf2.keys[2] == 6);
		assertTrue(leaf2.values[0] == 4);
		assertTrue(leaf2.values[1] == 5);
		assertTrue(leaf2.values[2] == 6);
		
		assertTrue(leaf3.keys[0] == 7);
		assertTrue(leaf3.keys[1] == 8);
		assertTrue(leaf3.keys[2] == 9);
		assertTrue(leaf3.values[0] == 7);
		assertTrue(leaf3.values[1] == 8);
		assertTrue(leaf3.values[2] == 9);
		
		assertTrue(leaf4.keys[0] == 10);
		assertTrue(leaf4.keys[1] == 11);
		assertTrue(leaf4.keys[2] == 12);
		assertTrue(leaf4.values[0] == 10);
		assertTrue(leaf4.values[1] == 11);
		assertTrue(leaf4.values[2] == 12);
		
		assertTrue(leaf5.keys[0] == 30);
		assertTrue(leaf5.keys[1] == 31);
		assertTrue(leaf5.keys[2] == 32);
		assertTrue(leaf5.values[0] == 30);
		assertTrue(leaf5.values[1] == 31);
		assertTrue(leaf5.values[2] == 32);
		
		assertTrue(leaf6.keys[0] == 33);
		assertTrue(leaf6.keys[1] == 34);
		assertTrue(leaf6.keys[2] == 35);
		assertTrue(leaf6.values[0] == 33);
		assertTrue(leaf6.values[1] == 34);
		assertTrue(leaf6.values[2] == 35);
		
		assertTrue(leaf7.keys[0] == 36);
		assertTrue(leaf7.keys[1] == 37);
		assertTrue(leaf7.keys[2] == 38);
		assertTrue(leaf7.keys[3] == 39);
		assertTrue(leaf7.keys[4] == 40);
		assertTrue(leaf7.values[0] == 36);
		assertTrue(leaf7.values[1] == 37);
		assertTrue(leaf7.values[2] == 38);
		assertTrue(leaf7.values[3] == 39);
		assertTrue(leaf7.values[4] == 40);
		
	}
	
	@Test
	public void splitLarge() {
		BTree testTree = new BTree(3);
		testTree.insert(new Student(1, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(2, 10, "test", "CS", "level", 2));
		testTree.insert(new Student(3, 10, "test", "CS", "level", 3));
		testTree.insert(new Student(4, 10, "test", "CS", "level", 4));
		testTree.insert(new Student(5, 10, "test", "CS", "level", 5));
		testTree.insert(new Student(6, 10, "test", "CS", "level", 6));
		testTree.insert(new Student(7, 10, "test", "CS", "level", 7));
		testTree.insert(new Student(8, 10, "test", "CS", "level", 8));
		testTree.insert(new Student(9, 10, "test", "CS", "level", 9));
		testTree.insert(new Student(10, 10, "test", "CS", "level", 10));
		testTree.insert(new Student(11, 10, "test", "CS", "level", 11));
		testTree.insert(new Student(12, 10, "test", "CS", "level", 12));
		testTree.insert(new Student(13, 10, "test", "CS", "level", 13));
		testTree.insert(new Student(14, 10, "test", "CS", "level", 14));
		testTree.insert(new Student(15, 10, "test", "CS", "level", 15));
		testTree.insert(new Student(16, 10, "test", "CS", "level", 16));
		testTree.insert(new Student(17, 10, "test", "CS", "level", 17));
		testTree.insert(new Student(18, 10, "test", "CS", "level", 18));
		testTree.insert(new Student(19, 10, "test", "CS", "level", 19));
		testTree.insert(new Student(20, 10, "test", "CS", "level", 20));
		testTree.insert(new Student(21, 10, "test", "CS", "level", 21));
		
		BTreeNode root = testTree.getRoot();
		
		BTreeNode int1 = root.children[0];
		BTreeNode int2 = root.children[1];
		
		BTreeNode leaf1 = int1.children[0];
		BTreeNode leaf2 = int1.children[1];
		BTreeNode leaf3 = int1.children[2];
		BTreeNode leaf4 = int1.children[3];
		BTreeNode leaf5 = int2.children[0];
		BTreeNode leaf6 = int2.children[1];
		BTreeNode leaf7 = int2.children[2];
		
		assertFalse(root.leaf);
		assertFalse(int1.leaf);
		assertFalse(int2.leaf);
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == leaf4);
		assertTrue(leaf4.leaf);
		assertTrue(leaf4.next == leaf5);
		assertTrue(leaf5.leaf);
		assertTrue(leaf5.next == leaf6);
		assertTrue(leaf6.leaf);
		assertTrue(leaf6.next == leaf7);
		assertTrue(leaf7.leaf);
		assertTrue(leaf7.next == null);
		
		assertTrue(root.keys[0] == 13);
		assertTrue(int1.keys[0] == 4);
		assertTrue(int1.keys[1] == 7);
		assertTrue(int1.keys[2] == 10);
		assertTrue(int2.keys[0] == 16);
		assertTrue(int2.keys[1] == 19);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.keys[2] == 3);
		assertTrue(leaf1.values[0] == 1);
		assertTrue(leaf1.values[1] == 2);
		assertTrue(leaf1.values[2] == 3);
		
		assertTrue(leaf2.keys[0] == 4);
		assertTrue(leaf2.keys[1] == 5);
		assertTrue(leaf2.keys[2] == 6);
		assertTrue(leaf2.values[0] == 4);
		assertTrue(leaf2.values[1] == 5);
		assertTrue(leaf2.values[2] == 6);
		
		assertTrue(leaf3.keys[0] == 7);
		assertTrue(leaf3.keys[1] == 8);
		assertTrue(leaf3.keys[2] == 9);
		assertTrue(leaf3.values[0] == 7);
		assertTrue(leaf3.values[1] == 8);
		assertTrue(leaf3.values[2] == 9);
		
		assertTrue(leaf4.keys[0] == 10);
		assertTrue(leaf4.keys[1] == 11);
		assertTrue(leaf4.keys[2] == 12);
		assertTrue(leaf4.values[0] == 10);
		assertTrue(leaf4.values[1] == 11);
		assertTrue(leaf4.values[2] == 12);
		
		assertTrue(leaf5.keys[0] == 13);
		assertTrue(leaf5.keys[1] == 14);
		assertTrue(leaf5.keys[2] == 15);
		assertTrue(leaf5.values[0] == 13);
		assertTrue(leaf5.values[1] == 14);
		assertTrue(leaf5.values[2] == 15);
		
		assertTrue(leaf6.keys[0] == 16);
		assertTrue(leaf6.keys[1] == 17);
		assertTrue(leaf6.keys[2] == 18);
		assertTrue(leaf6.values[0] == 16);
		assertTrue(leaf6.values[1] == 17);
		assertTrue(leaf6.values[2] == 18);
		
		assertTrue(leaf7.keys[0] == 19);
		assertTrue(leaf7.keys[1] == 20);
		assertTrue(leaf7.keys[2] == 21);
		assertTrue(leaf7.values[0] == 19);
		assertTrue(leaf7.values[1] == 20);
		assertTrue(leaf7.values[2] == 21);
	}
	
	@Test
	public void splitXLarge() {
		BTree testTree = new BTree(2);
		testTree.insert(new Student(1, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(2, 10, "test", "CS", "level", 2));
		testTree.insert(new Student(3, 10, "test", "CS", "level", 3));
		testTree.insert(new Student(4, 10, "test", "CS", "level", 4));
		testTree.insert(new Student(5, 10, "test", "CS", "level", 5));
		testTree.insert(new Student(6, 10, "test", "CS", "level", 6));
		testTree.insert(new Student(7, 10, "test", "CS", "level", 7));
		testTree.insert(new Student(8, 10, "test", "CS", "level", 8));
		testTree.insert(new Student(9, 10, "test", "CS", "level", 9));
		
		BTreeNode root = testTree.getRoot();
		
		BTreeNode leaf1 = root.children[0];
		BTreeNode leaf2 = root.children[1];
		BTreeNode leaf3 = root.children[2];
		BTreeNode leaf4 = root.children[3];
		
		assertFalse(root.leaf);
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == leaf4);
		assertTrue(leaf4.leaf);
		assertTrue(leaf4.next == null);
		
		assertTrue(root.keys[0] == 3);
		assertTrue(root.keys[1] == 5);
		assertTrue(root.keys[2] == 7);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.values[0] == 1);
		assertTrue(leaf1.values[1] == 2);
		
		assertTrue(leaf2.keys[0] == 3);
		assertTrue(leaf2.keys[1] == 4);
		assertTrue(leaf2.values[0] == 3);
		assertTrue(leaf2.values[1] == 4);
		
		assertTrue(leaf3.keys[0] == 5);
		assertTrue(leaf3.keys[1] == 6);
		assertTrue(leaf3.values[0] == 5);
		assertTrue(leaf3.values[1] == 6);
		
		assertTrue(leaf4.keys[0] == 7);
		assertTrue(leaf4.keys[1] == 8);
		assertTrue(leaf4.keys[2] == 9);
		assertTrue(leaf4.values[0] == 7);
		assertTrue(leaf4.values[1] == 8);
		assertTrue(leaf4.values[2] == 9);
		
		
		testTree.insert(new Student(30, 10, "test", "CS", "level", 30));
		
		root = testTree.getRoot();
		
		BTreeNode int1 = root.children[0];
		BTreeNode int2 = root.children[1];
		
		leaf1 = int1.children[0];
		leaf2 = int1.children[1];
		leaf3 = int1.children[2];
		leaf4 = int2.children[0];
		BTreeNode leaf5 = int2.children[1];
		
		assertFalse(root.leaf);
		assertFalse(int1.leaf);
		assertFalse(int2.leaf);
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == leaf4);
		assertTrue(leaf4.leaf);
		assertTrue(leaf4.next == leaf5);
		assertTrue(leaf5.leaf);
		assertTrue(leaf5.next == null);
		
		assertTrue(root.keys[0] == 7);
		assertTrue(int1.keys[0] == 3);
		assertTrue(int1.keys[1] == 5);
		assertTrue(int2.keys[0] == 9);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.values[0] == 1);
		assertTrue(leaf1.values[1] == 2);
		
		assertTrue(leaf2.keys[0] == 3);
		assertTrue(leaf2.keys[1] == 4);
		assertTrue(leaf2.values[0] == 3);
		assertTrue(leaf2.values[1] == 4);
		
		assertTrue(leaf3.keys[0] == 5);
		assertTrue(leaf3.keys[1] == 6);
		assertTrue(leaf3.values[0] == 5);
		assertTrue(leaf3.values[1] == 6);
		
		assertTrue(leaf4.keys[0] == 7);
		assertTrue(leaf4.keys[1] == 8);
		assertTrue(leaf4.values[0] == 7);
		assertTrue(leaf4.values[1] == 8);
		
		assertTrue(leaf5.keys[0] == 9);
		assertTrue(leaf5.keys[1] == 30);
		assertTrue(leaf5.values[0] == 9);
		assertTrue(leaf5.values[1] == 30);
		
		
		testTree.insert(new Student(31, 10, "test", "CS", "level", 31));
		testTree.insert(new Student(32, 10, "test", "CS", "level", 32));
		testTree.insert(new Student(33, 10, "test", "CS", "level", 33));
		testTree.insert(new Student(34, 10, "test", "CS", "level", 34));
		testTree.insert(new Student(35, 10, "test", "CS", "level", 35));
		testTree.insert(new Student(36, 10, "test", "CS", "level", 36));
		
		root = testTree.getRoot();
		
		int1 = root.children[0];
		int2 = root.children[1];
		BTreeNode int3 = root.children[2];
		
		leaf1 = int1.children[0];
		leaf2 = int1.children[1];
		leaf3 = int1.children[2];
		leaf4 = int2.children[0];
		leaf5 = int2.children[1];
		BTreeNode leaf6 = int2.children[2];
		BTreeNode leaf7 = int3.children[0];
		BTreeNode leaf8 = int3.children[1];
		
		assertFalse(root.leaf);
		assertFalse(int1.leaf);
		assertFalse(int2.leaf);
		assertFalse(int3.leaf);
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == leaf4);
		assertTrue(leaf4.leaf);
		assertTrue(leaf4.next == leaf5);
		assertTrue(leaf5.leaf);
		assertTrue(leaf5.next == leaf6);
		assertTrue(leaf6.leaf);
		assertTrue(leaf6.next == leaf7);
		assertTrue(leaf7.leaf);
		assertTrue(leaf7.next == leaf8);
		assertTrue(leaf8.leaf);
		assertTrue(leaf8.next == null);
		
		assertTrue(root.keys[0] == 7);
		assertTrue(root.keys[1] == 33);
		assertTrue(int1.keys[0] == 3);
		assertTrue(int1.keys[1] == 5);
		assertTrue(int2.keys[0] == 9);
		assertTrue(int2.keys[1] == 31);
		assertTrue(int3.keys[0] == 35);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.values[0] == 1);
		assertTrue(leaf1.values[1] == 2);
		
		assertTrue(leaf2.keys[0] == 3);
		assertTrue(leaf2.keys[1] == 4);
		assertTrue(leaf2.values[0] == 3);
		assertTrue(leaf2.values[1] == 4);
		
		assertTrue(leaf3.keys[0] == 5);
		assertTrue(leaf3.keys[1] == 6);
		assertTrue(leaf3.values[0] == 5);
		assertTrue(leaf3.values[1] == 6);
		
		assertTrue(leaf4.keys[0] == 7);
		assertTrue(leaf4.keys[1] == 8);
		assertTrue(leaf4.values[0] == 7);
		assertTrue(leaf4.values[1] == 8);
		
		assertTrue(leaf5.keys[0] == 9);
		assertTrue(leaf5.keys[1] == 30);
		assertTrue(leaf5.values[0] == 9);
		assertTrue(leaf5.values[1] == 30);
		
		assertTrue(leaf6.keys[0] == 31);
		assertTrue(leaf6.keys[1] == 32);
		assertTrue(leaf6.values[0] == 31);
		assertTrue(leaf6.values[1] == 32);
		
		assertTrue(leaf7.keys[0] == 33);
		assertTrue(leaf7.keys[1] == 34);
		assertTrue(leaf7.values[0] == 33);
		assertTrue(leaf7.values[1] == 34);
		
		assertTrue(leaf8.keys[0] == 35);
		assertTrue(leaf8.keys[1] == 36);
		assertTrue(leaf8.values[0] == 35);
		assertTrue(leaf8.values[1] == 36);
		
		
		testTree.insert(new Student(37, 10, "test", "CS", "level", 37));
		testTree.insert(new Student(38, 10, "test", "CS", "level", 38));
		testTree.insert(new Student(39, 10, "test", "CS", "level", 39));
		
		root = testTree.getRoot();
		int1 = root.children[0];
		int2 = root.children[1];
		int3 = root.children[2];
		
		leaf1 = int1.children[0];
		leaf2 = int1.children[1];
		leaf3 = int1.children[2];
		leaf4 = int2.children[0];
		leaf5 = int2.children[1];
		leaf6 = int2.children[2];
		leaf7 = int3.children[0];
		leaf8 = int3.children[1];
		BTreeNode leaf9 = int3.children[2];
		
		assertFalse(root.leaf);
		assertFalse(int1.leaf);
		assertFalse(int2.leaf);
		assertFalse(int3.leaf);
		
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == leaf4);
		assertTrue(leaf4.leaf);
		assertTrue(leaf4.next == leaf5);
		assertTrue(leaf5.leaf);
		assertTrue(leaf5.next == leaf6);
		assertTrue(leaf6.leaf);
		assertTrue(leaf6.next == leaf7);
		assertTrue(leaf7.leaf);
		assertTrue(leaf7.next == leaf8);
		assertTrue(leaf8.leaf);
		assertTrue(leaf8.next == leaf9);
		
		testTree.insert(new Student(20, 10, "test", "CS", "level", 20));
		testTree.insert(new Student(21, 10, "test", "CS", "level", 21));
		testTree.insert(new Student(22, 10, "test", "CS", "level", 22));
		testTree.insert(new Student(23, 10, "test", "CS", "level", 23));
		testTree.insert(new Student(24, 10, "test", "CS", "level", 24));
		testTree.insert(new Student(25, 10, "test", "CS", "level", 25));
		testTree.insert(new Student(26, 10, "test", "CS", "level", 26));
		testTree.insert(new Student(27, 10, "test", "CS", "level", 27));
		testTree.insert(new Student(28, 10, "test", "CS", "level", 28));
		testTree.insert(new Student(29, 10, "test", "CS", "level", 29));
		
		root = testTree.getRoot();
		
		int1 = root.children[0];
		int2 = root.children[1];
		int3 = int1.children[0];
		BTreeNode int4 = int1.children[1];
		BTreeNode int5 = int1.children[2];
		BTreeNode int6 = int2.children[0];
		BTreeNode int7 = int2.children[1];
		
		leaf1 = int3.children[0];
		leaf2 = int3.children[1];
		leaf3 = int3.children[2];
		leaf4 = int4.children[0];
		leaf5 = int4.children[1];
		leaf6 = int4.children[2];
		leaf7 = int5.children[0];
		leaf8 = int5.children[1];
		leaf9 = int5.children[2];
		BTreeNode leaf10 = int6.children[0];
		BTreeNode leaf11 = int6.children[1];
		BTreeNode leaf12 = int7.children[0];
		BTreeNode leaf13 = int7.children[1];
		BTreeNode leaf14 = int7.children[2];
		
		assertFalse(root.leaf);
		assertFalse(int1.leaf);
		assertFalse(int2.leaf);
		assertFalse(int3.leaf);
		assertFalse(int4.leaf);
		assertFalse(int5.leaf);
		assertFalse(int6.leaf);
		assertFalse(int7.leaf);
		
		assertTrue(leaf1.leaf);
		assertTrue(leaf1.next == leaf2);
		assertTrue(leaf2.leaf);
		assertTrue(leaf2.next == leaf3);
		assertTrue(leaf3.leaf);
		assertTrue(leaf3.next == leaf4);
		assertTrue(leaf4.leaf);
		assertTrue(leaf4.next == leaf5);
		assertTrue(leaf5.leaf);
		assertTrue(leaf5.next == leaf6);
		assertTrue(leaf6.leaf);
		assertTrue(leaf6.next == leaf7);
		assertTrue(leaf7.leaf);
		assertTrue(leaf7.next == leaf8);
		assertTrue(leaf8.leaf);
		assertTrue(leaf8.next == leaf9);
		assertTrue(leaf9.leaf);
		assertTrue(leaf9.next == leaf10);
		assertTrue(leaf10.leaf);
		assertTrue(leaf10.next == leaf11);
		assertTrue(leaf11.leaf);
		assertTrue(leaf11.next == leaf12);
		assertTrue(leaf12.leaf);
		assertTrue(leaf12.next == leaf13);
		assertTrue(leaf13.leaf);
		assertTrue(leaf13.next == leaf14);
		assertTrue(leaf14.leaf);
		assertTrue(leaf14.next == null);
		
		assertTrue(root.keys[0] == 29);
		assertTrue(int1.keys[0] == 7);
		assertTrue(int1.keys[1] == 23);
		assertTrue(int2.keys[0] == 33);
		assertTrue(int3.keys[0] == 3);
		assertTrue(int3.keys[1] == 5);
		assertTrue(int4.keys[0] == 9);
		assertTrue(int4.keys[1] == 21);
		assertTrue(int5.keys[0] == 25);
		assertTrue(int5.keys[1] == 27);
		assertTrue(int6.keys[0] == 31);
		assertTrue(int7.keys[0] == 35);
		assertTrue(int7.keys[1] == 37);
		
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf1.values[0] == 1);
		assertTrue(leaf1.values[1] == 2);
		
		assertTrue(leaf2.keys[0] == 3);
		assertTrue(leaf2.keys[1] == 4);
		assertTrue(leaf2.values[0] == 3);
		assertTrue(leaf2.values[1] == 4);
		
		assertTrue(leaf3.keys[0] == 5);
		assertTrue(leaf3.keys[1] == 6);
		assertTrue(leaf3.values[0] == 5);
		assertTrue(leaf3.values[1] == 6);
		
		assertTrue(leaf4.keys[0] == 7);
		assertTrue(leaf4.keys[1] == 8);
		assertTrue(leaf4.values[0] == 7);
		assertTrue(leaf4.values[1] == 8);
		
		assertTrue(leaf5.keys[0] == 9);
		assertTrue(leaf5.keys[1] == 20);
		assertTrue(leaf5.values[0] == 9);
		assertTrue(leaf5.values[1] == 20);
		
		assertTrue(leaf6.keys[0] == 21);
		assertTrue(leaf6.keys[1] == 22);
		assertTrue(leaf6.values[0] == 21);
		assertTrue(leaf6.values[1] == 22);
		
		assertTrue(leaf7.keys[0] == 23);
		assertTrue(leaf7.keys[1] == 24);
		assertTrue(leaf7.values[0] == 23);
		assertTrue(leaf7.values[1] == 24);
		
		assertTrue(leaf8.keys[0] == 25);
		assertTrue(leaf8.keys[1] == 26);
		assertTrue(leaf8.values[0] == 25);
		assertTrue(leaf8.values[1] == 26);
		
		assertTrue(leaf9.keys[0] == 27);
		assertTrue(leaf9.keys[1] == 28);
		assertTrue(leaf9.values[0] == 27);
		assertTrue(leaf9.values[1] == 28);
		
		assertTrue(leaf10.keys[0] == 29);
		assertTrue(leaf10.keys[1] == 30);
		assertTrue(leaf10.values[0] == 29);
		assertTrue(leaf10.values[1] == 30);
		
		assertTrue(leaf11.keys[0] == 31);
		assertTrue(leaf11.keys[1] == 32);
		assertTrue(leaf11.values[0] == 31);
		assertTrue(leaf11.values[1] == 32);
		
		assertTrue(leaf12.keys[0] == 33);
		assertTrue(leaf12.keys[1] == 34);
		assertTrue(leaf12.values[0] == 33);
		assertTrue(leaf12.values[1] == 34);
		
		assertTrue(leaf13.keys[0] == 35);
		assertTrue(leaf13.keys[1] == 36);
		assertTrue(leaf13.values[0] == 35);
		assertTrue(leaf13.values[1] == 36);
		
		assertTrue(leaf14.keys[0] == 37);
		assertTrue(leaf14.keys[1] == 38);
		assertTrue(leaf14.keys[2] == 39);
		assertTrue(leaf14.values[0] == 37);
		assertTrue(leaf14.values[1] == 38);
		assertTrue(leaf14.values[2] == 39);
	}
		

}