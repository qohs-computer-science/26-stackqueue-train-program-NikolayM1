/*
 * TODO: Nikolay Merenko
 * TODO: January 6th to January 12th 2026
 * TODO: Period 6
 */

import java.util.Scanner;
import java.io.File;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class MyProgram {
	public static int val = 0;
	public static void main(String[] args) {

		int limitTrackA = 100000, limitTrackB = 100000, limitTrackC = 100000;
	
		Scanner x = new Scanner(System.in);
		try{
			File f = new File("HelloWorldProject/src/data.txt");
			x = new Scanner (f);
			String name = x.nextLine();
			System.out.println(name);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		Queue <String> mainLine = new LinkedList <String> ();
		Queue <String> inspection = new LinkedList <String> ();


		
		
	}
}
