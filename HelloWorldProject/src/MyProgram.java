/*
 * TODO: Nikolay Merenko
 * TODO: January 6th to January 12th 2026
 * TODO: Period 6
 */

// NOTE TO SELF: Add comments before submitting at the end once you are done

import java.util.Scanner;
import java.io.File;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;


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
	
	Queue<TrainCar> mainLine = new LinkedList<>();
	Queue<TrainCar> inspection = new LinkedList<>();
	Map<String, Queue<TrainCar>> tracks = new HashMap<>();
	Map<TrainCar, Integer> carMiles = new HashMap<>();
	Map<TrainCar, String> carDest = new HashMap<>();
	

	Train trackA = new Train("trackA", "Trenton", limitTrackA);
	Train trackB = new Train("trackB", "Charlotte", limitTrackB);
	Train trackC = new Train("trackC", "Baltimore", limitTrackC);
	Train trackD = new Train("trackD", "Other destinations", -1);
	
	while (x.hasNextLine()) { 
	String line = x.nextLine().trim(); 
	if (line.equals("END")) break;

	if (line.startsWith("CAR")) { 

		if (!x.hasNextLine()) break;
		String contents = x.nextLine().trim();
		if (!x.hasNextLine()) break;
		String origin = x.nextLine().trim();
		if (!x.hasNextLine()) break;
		String destination = x.nextLine().trim();
		if (!x.hasNextLine()) break;
		String weightStr = x.nextLine().trim();
		if (!x.hasNextLine()) break;
		String milesStr = x.nextLine().trim();

			int weight = 0;
			try { weight = Integer.parseInt(weightStr); } catch (NumberFormatException e) {}
			int miles = 0;
			try { miles = Integer.parseInt(milesStr); } catch (NumberFormatException e) {}

			TrainCar car = new TrainCar(origin, contents, weight);
			mainLine.add(car);
			carMiles.put(car, miles);
			carDest.put(car, destination);

	} else if (line.startsWith("ENG")) {
	
		if (!x.hasNextLine()) break;
		String destinationCity = x.nextLine().trim();
		System.out.println("Engine for " + destinationCity + " departing.");
		Queue<TrainCar> traCar = tracks.remove(destinationCity);
		if (traCar == null || traCar.isEmpty()) {
			System.out.println("No cars on that track.");
		} else {
				while (!traCar.isEmpty()) {
				TrainCar c = traCar.poll();
			
				mainLine.remove(c);
					System.out.println("Departing: " + c.toString());
			}
		}
	} else {
		
	}
	}

	while (!mainLine.isEmpty()) {
		TrainCar car = mainLine.poll();
		int miles = carMiles.getOrDefault(car, 0);
		if (miles > 700) {
			inspection.add(car);
		} else {
			String dest = carDest.getOrDefault(car, "Other destinations");
			tracks.computeIfAbsent(dest, k -> new LinkedList<>()).add(car);
		}
	}
	while (!inspection.isEmpty()) {
		TrainCar car = inspection.poll();
		carMiles.put(car, 100);
		String dest = carDest.getOrDefault(car, "Other destinations");
		tracks.computeIfAbsent(dest, k -> new LinkedList<>()).add(car);
	}

	x.close();

	}
}