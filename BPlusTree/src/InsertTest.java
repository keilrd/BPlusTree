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
		BTree testTree = new BTree(2);
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
		BTree testTree = new BTree(2);
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
		
		assertTrue(root.keys[0] == 3);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf2.keys[0] == 3);
		assertTrue(leaf2.keys[1] == 4);
		assertTrue(leaf2.keys[2] == 5);
		assertTrue(leaf2.keys[3] == 6);
		
		
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
		testTree.insert(new Student(10, 10, "test", "CS", "level", 10));
		
		
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
		assertTrue(leaf3.keys[3] == 10);
		
	}
	
	@Test
	public void splitLeafMiddle() {
		BTree testTree = new BTree(2);
		testTree.insert(new Student(1, 10, "test", "CS", "level", 1));
		testTree.insert(new Student(2, 10, "test", "CS", "level", 2));
		testTree.insert(new Student(3, 10, "test", "CS", "level", 3));
		
		testTree.insert(new Student(10, 10, "test", "CS", "level", 10));
		testTree.insert(new Student(11, 10, "test", "CS", "level", 11));
		testTree.insert(new Student(12, 10, "test", "CS", "level", 12));
		
		testTree.insert(new Student(4, 10, "test", "CS", "level", 4));
		testTree.insert(new Student(5, 10, "test", "CS", "level", 5));
		
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
		
		assertTrue(root.keys[0] == 3);
		assertTrue(root.keys[1] == 10);
		
		assertTrue(leaf1.keys[0] == 1);
		assertTrue(leaf1.keys[1] == 2);
		assertTrue(leaf2.keys[0] == 3);
		assertTrue(leaf2.keys[1] == 4);
		assertTrue(leaf2.keys[2] == 5);
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
		testTree.insert(new Student(13, 10, "test", "CS", "level", 13));
		testTree.insert(new Student(14, 10, "test", "CS", "level", 14));
		testTree.insert(new Student(15, 10, "test", "CS", "level", 15));
		testTree.insert(new Student(16, 10, "test", "CS", "level", 16));
		testTree.insert(new Student(17, 10, "test", "CS", "level", 17));
		
		
		
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
		BTreeNode leaf8 = int2.children[3];
		
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
		assertTrue(leaf7.next == leaf8);
		assertTrue(leaf8.leaf);
		assertTrue(leaf8.next == null);
		
		assertTrue(root.keys[0] == 13);
		assertTrue(int1.keys[0] == 4);
		assertTrue(int1.keys[1] == 7);
		assertTrue(int1.keys[2] == 10);
		assertTrue(int2.keys[0] == 30);
		assertTrue(int2.keys[1] == 33);
		assertTrue(int2.keys[2] == 36);
		
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
		assertTrue(leaf5.keys[3] == 16);
		assertTrue(leaf5.keys[4] == 17);
		assertTrue(leaf5.values[0] == 13);
		assertTrue(leaf5.values[1] == 14);
		assertTrue(leaf5.values[2] == 15);
		assertTrue(leaf5.values[3] == 16);
		assertTrue(leaf5.values[4] == 17);
		
		assertTrue(leaf6.keys[0] == 30);
		assertTrue(leaf6.keys[1] == 31);
		assertTrue(leaf6.keys[2] == 32);
		assertTrue(leaf6.values[0] == 30);
		assertTrue(leaf6.values[1] == 31);
		assertTrue(leaf6.values[2] == 32);
		
		assertTrue(leaf7.keys[0] == 33);
		assertTrue(leaf7.keys[1] == 34);
		assertTrue(leaf7.keys[2] == 35);
		assertTrue(leaf7.values[0] == 33);
		assertTrue(leaf7.values[1] == 34);
		assertTrue(leaf7.values[2] == 35);
		
		assertTrue(leaf8.keys[0] == 36);
		assertTrue(leaf8.keys[1] == 37);
		assertTrue(leaf8.keys[2] == 38);
		assertTrue(leaf8.keys[3] == 39);
		assertTrue(leaf8.keys[4] == 40);
		assertTrue(leaf8.values[0] == 36);
		assertTrue(leaf8.values[1] == 37);
		assertTrue(leaf8.values[2] == 38);
		assertTrue(leaf8.values[3] == 39);
		assertTrue(leaf8.values[4] == 40);
	}
	
	@Test
	public void splitLarge() {
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
		BTreeNode int3 = root.children[2];
		
		BTreeNode leaf1 = int1.children[0];
		BTreeNode leaf2 = int1.children[1];
		BTreeNode leaf3 = int1.children[2];
		BTreeNode leaf4 = int2.children[0];
		BTreeNode leaf5 = int2.children[1];
		BTreeNode leaf6 = int2.children[2];
		BTreeNode leaf7 = int3.children[0];
		BTreeNode leaf8 = int3.children[1];
		BTreeNode leaf9 = int3.children[2];
		BTreeNode leaf10 = int3.children[3];
		
		
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
		assertTrue(leaf9.leaf);
		assertTrue(leaf9.next == leaf10);
		assertTrue(leaf10.leaf);
		assertTrue(leaf10.next == null);
		
		assertTrue(root.keys[0] == 7);
		assertTrue(root.keys[1] == 13);
		
		assertTrue(int1.keys[0] == 3);
		assertTrue(int1.keys[1] == 5);
		
		assertTrue(int2.keys[0] == 9);
		assertTrue(int2.keys[1] == 11);
		
		assertTrue(int3.keys[0] == 15);
		assertTrue(int3.keys[1] == 17);
		assertTrue(int3.keys[2] == 19);
		
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
		assertTrue(leaf5.keys[1] == 10);
		assertTrue(leaf5.values[0] == 9);
		assertTrue(leaf5.values[1] == 10);
		
		assertTrue(leaf6.keys[0] == 11);
		assertTrue(leaf6.keys[1] == 12);
		assertTrue(leaf6.values[0] == 11);
		assertTrue(leaf6.values[1] == 12);
		
		assertTrue(leaf7.keys[0] == 13);
		assertTrue(leaf7.keys[1] == 14);
		assertTrue(leaf7.values[0] == 13);
		assertTrue(leaf7.values[1] == 14);
		
		assertTrue(leaf8.keys[0] == 15);
		assertTrue(leaf8.keys[1] == 16);
		assertTrue(leaf8.values[0] == 15);
		assertTrue(leaf8.values[1] == 16);
		
		assertTrue(leaf9.keys[0] == 17);
		assertTrue(leaf9.keys[1] == 18);
		assertTrue(leaf9.values[0] == 17);
		assertTrue(leaf9.values[1] == 18);
		
		assertTrue(leaf10.keys[0] == 19);
		assertTrue(leaf10.keys[1] == 20);
		assertTrue(leaf10.keys[2] == 21);
		assertTrue(leaf10.values[0] == 19);
		assertTrue(leaf10.values[1] == 20);
		assertTrue(leaf10.values[2] == 21);
		
	}
}
	