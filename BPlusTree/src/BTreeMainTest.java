import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BTreeMainTest {

	private BTree bTree;

	public void setUp(String csvFileName, String input) throws Exception {
        Scanner scan = null;
        try {
            scan = new Scanner(new File(input));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        /** Read the minimum degree of B+Tree first */

        int degree = scan.nextInt();

        bTree = new BTree(degree);

        /** Reading the database student.csv into B+Tree Node*/
        List<Student> studentsDB = getStudents(csvFileName);

        for (Student s : studentsDB) {
            bTree.insert(s);
        }
        scan.close();
        //System.out.println(bTree.print());
        return;
	}

    private static List<Student> getStudents(String csvFileName) {

        List<Student> studentList = new ArrayList<>();
        
        //read file line by line
        try (Scanner scanner = new Scanner(new File(csvFileName));) {
        	while (scanner.hasNextLine()) {
        		//get current line from scanner
        		String line = scanner.nextLine();
        		
        		//split line by commas
        		String[] student = line.split(",");
        		
        		//convert strings to long/int
        		long studentId = Long.parseLong(student[0]); 
        		int age = Integer.parseInt(student[4]);
        		String studentName = student[1]; 
        		String major = student[2];
        		String level = student[3];
        		long recordId = Long.parseLong(student[5]);
        		
        		//create new student and add to array list
        		studentList.add(new Student(studentId, age, studentName, major, level, recordId));
        	}
        	scanner.close();
        	
        } catch(FileNotFoundException e) {
        	System.out.println(e);
        } catch(Exception e2) {
        	System.out.println(e2);
        }
  
        return studentList;
    }

	@Test
	public void testPrint() throws Exception {
		setUp("Student.csv","input.txt");
		assert(bTree.print().contains((long)13));
	}

	@Test
	public void testSearch() throws Exception {
		setUp("Student.csv","input.txt");
		assert(bTree.search((long)13) == (long)12);
		assert(bTree.search((long)500) == (long)-1);
	}
	
	@Test
	public void testInsert() throws Exception {
		setUp("Student.csv","input.txt");
		bTree.insert(new Student(25, 10, "test", "CS", "level", 1));
		//System.out.println("Root 0: " + bTree.getRoot().keys[0]);
		assert(bTree.getRoot().keys[0] == 13);
		assert(bTree.getRoot().keys[1] == 0);
		assert(bTree.getRoot().keys[2] == 0);
		for (long i = 26; i<38 ; i++) {
			bTree.insert(new Student(i, (int)i, "test", "CS", "level", 1));
		}
		System.out.println("Root 0: " + bTree.getRoot().keys[0]);
		System.out.println("Root 1: " + bTree.getRoot().keys[1]);
		assert(bTree.getRoot().keys[0] == 13);
		assert(bTree.getRoot().keys[1] == 25);
		assert(bTree.getRoot().keys[1] == 0);
		
	}
	
	
	@Test
	public void testDoubleInsert() throws Exception {
		setUp("Student.csv","input.txt");
		bTree.insert(new Student(25, 10, "test", "CS", "level", 1));
		bTree.insert(new Student(25, 10, "test", "CS", "level", 1));
		assert(bTree.print().size() == 25);
	}
	
	
}
