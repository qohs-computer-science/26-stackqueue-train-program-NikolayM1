/*
 * Nikolay Merenko
 * January 6th to January 12th 2026
 * Period 6
 * Program that sorts train cars into different tracks based on destination and contents using stacks and queues.
 */

import java.util.Scanner;
import java.io.File;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
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
	Map<String, Object> tracks = new HashMap<>();
	Map<String, Integer> trackWeight = new HashMap<>();
	Map<TrainCar, Integer> carMiles = new HashMap<>();
	Map<TrainCar, String> carDest = new HashMap<>();
	
	Train trackA = new Train("Track A", "Trenton", limitTrackA, "Freight Yard", "mixed cargo", 100);
	Train trackB = new Train("Track B", "Charlotte", limitTrackB, "Freight Yard", "mixed cargo", 100);
	Train trackC = new Train("Track C", "Baltimore", limitTrackC, "Freight Yard", "mixed cargo", 100);
	Train trackD = new Train("Track D", "Other destinations", -1, "Freight Yard", "mixed cargo", 0);
	
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
		String engineId = line; 
		Stack<TrainCar> traCar = tracks.remove(destinationCity);
		if (traCar == null || traCar.isEmpty()) {
			System.out.println("No cars on that track.");
		} else {
			
			System.out.println(engineId + " leaving for " + destinationCity + " with the following cars:");
			while (!traCar.isEmpty()) {
				TrainCar c = traCar.pop();
				mainLine.remove(c);
				System.out.println(c.getID() + " containing " + c.getContents());
			}
			trackWeight.put(destinationCity, 0);
		}
	} else {
		
	}
	}
	
	while (!mainLine.isEmpty()) {
		// dequeue from mainLine — process cars in arrival order 
		TrainCar car = mainLine.poll();
		int miles = carMiles.getOrDefault(car, 0);
		if (miles > 700) {
			// cars with more than 700 miles go to inspection 
			inspection.add(car);
		} else {
			String dest = carDest.getOrDefault(car, "Other destinations");
			sendCar(car, dest, tracks, trackWeight, trackA, trackB, trackC, trackD);
		}
	} 
	while (!inspection.isEmpty()) {
		// process inspection queue
		TrainCar car = inspection.poll();
		carMiles.put(car, 100); // reset miles after inspection
		String dest = carDest.getOrDefault(car, "Other destinations");
		sendCar(car, dest, tracks, trackWeight, trackA, trackB, trackC, trackD);
	} 

	x.close();
	
		Object o = tracks.get("Trenton");
		if (o instanceof java.util.Queue && !((java.util.Queue<?>)o).isEmpty()) {
			java.util.Queue<TrainCar> q = (java.util.Queue<TrainCar>)o;
			System.out.println("ENG00000 leaving for Trenton with the following cars:");
			while (!q.isEmpty()) {
				TrainCar c = q.poll();
				System.out.println(c.getID() + " containing " + c.getContents());
			}
			trackWeight.put("Trenton", 0);
		}
		o = tracks.get("Charlotte");
		if (o instanceof java.util.Queue && !((java.util.Queue<?>)o).isEmpty()) {
			java.util.Queue<TrainCar> q = (java.util.Queue<TrainCar>)o;
			System.out.println("ENG00000 leaving for Charlotte with the following cars:");
			while (!q.isEmpty()) {
				TrainCar c = q.poll();
				System.out.println(c.getID() + " containing " + c.getContents());
			}
			trackWeight.put("Charlotte", 0);
		}
		o = tracks.get("Baltimore");
		if (o instanceof java.util.Queue && !((java.util.Queue<?>)o).isEmpty()) {
			java.util.Queue<TrainCar> q = (java.util.Queue<TrainCar>)o;
			System.out.println("ENG00000 leaving for Baltimore with the following cars:");
			while (!q.isEmpty()) {
				TrainCar c = q.poll();
				System.out.println(c.getID() + " containing " + c.getContents());
			}
			trackWeight.put("Baltimore", 0);
		}  

		System.out.println("Station Status:");
		Object qdObj = tracks.get("Other destinations");
		if (qdObj == null || (qdObj instanceof java.util.Queue && ((java.util.Queue<?>)qdObj).isEmpty()) || (qdObj instanceof java.util.Stack && ((java.util.Stack<?>)qdObj).isEmpty())) {
			System.out.println("No cars waiting for other destinations");
		} else {
			// show what's waiting on Track D
			System.out.println("Cars waiting on Track D:");
			if (qdObj instanceof java.util.Stack) {
				java.util.Stack<TrainCar> st = (java.util.Stack<TrainCar>)qdObj;
				for (TrainCar c : st) System.out.println(c.getID() + " containing " + c.getContents());
			} else if (qdObj instanceof java.util.Queue) {
				java.util.Queue<TrainCar> q = (java.util.Queue<TrainCar>)qdObj;
				for (TrainCar c : q) System.out.println(c.getID() + " containing " + c.getContents());
			}
		}  
	}

	private static void sendCar(TrainCar car, String destination, Map<String, Object> tracks, Map<String, Integer> trackWeight, Train trackA, Train trackB, Train trackC, Train trackD) {
		Train target = null;
		String key = null;
		if ("Trenton".equals(destination)) {
			target = trackA; key = "Trenton";
		} else if ("Charlotte".equals(destination)) {
			target = trackB; key = "Charlotte";
		} else if ("Baltimore".equals(destination)) {
			target = trackC; key = "Baltimore";
		} else {
			target = trackD; key = "Other destinations";
		}

		int limit = target.getWeightLimit();
		int current = trackWeight.getOrDefault(key, 0);
		// try to add car to the track; some tracks are queues (FIFO) and some are stacks (LIFO)
		if (limit < 0 || current + car.getWeight() <= limit) {
			Object container = tracks.get(key);
			if (container == null) {
				// choose Stack for Other destinations (Track D), Queue for the main tracks
				if ("Other destinations".equals(key)) {
					java.util.Stack<TrainCar> st = new java.util.Stack<>();
					st.push(car);
					tracks.put(key, st);
				} else {
					java.util.Queue<TrainCar> q = new java.util.LinkedList<>();
					q.add(car);
					tracks.put(key, q);
				}
			} else {
				if (container instanceof java.util.Stack) {
					((java.util.Stack<TrainCar>)container).push(car);
				} else if (container instanceof java.util.Queue) {
					((java.util.Queue<TrainCar>)container).add(car);
				}
			}
			trackWeight.put(key, current + car.getWeight());
		} else {
			System.out.println("ENG00000 leaving for " + target.getDestination() + " with the following cars:");
			Object s = tracks.get(key);
			if (s != null) {
				if (s instanceof java.util.Stack) {
					java.util.Stack<TrainCar> st = (java.util.Stack<TrainCar>)s;
					while (!st.isEmpty()) {
						TrainCar c = st.pop();
						System.out.println(c.getID() + " containing " + c.getContents());
					}
				} else if (s instanceof java.util.Queue) {
					java.util.Queue<TrainCar> q = (java.util.Queue<TrainCar>)s;
					while (!q.isEmpty()) {
						TrainCar c = q.poll();
						System.out.println(c.getID() + " containing " + c.getContents());
					}
				}
			}
			trackWeight.put(key, 0);
			// add current car after departing
			Object container = tracks.get(key);
			if (container == null) {
				if ("Other destinations".equals(key)) {
					java.util.Stack<TrainCar> st = new java.util.Stack<>();
					st.push(car);
					tracks.put(key, st);
				} else {
					java.util.Queue<TrainCar> q = new java.util.LinkedList<>();
					q.add(car);
					tracks.put(key, q);
				}
			} else {
				if (container instanceof java.util.Stack) ((java.util.Stack<TrainCar>)container).push(car);
				else if (container instanceof java.util.Queue) ((java.util.Queue<TrainCar>)container).add(car);
			}
			trackWeight.put(key, car.getWeight());
		} 
	}
}