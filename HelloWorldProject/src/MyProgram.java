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
	public static void main(String[] args) {

	final int TRENTON_LIMIT = 100000;
	final int CHARLOTTE_LIMIT = 100000;
	final int BALTIMORE_LIMIT = 100000;

	// Read train data from file
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
	
	// Queues for different destination tracks and inspection station
	Queue<TrainCar> mainLine = new LinkedList<>();
	Queue<TrainCar> inspection = new LinkedList<>();
	Queue<TrainCar> trentonTrack = new LinkedList<>();
	Queue<TrainCar> charlotteTrack = new LinkedList<>();
	Queue<TrainCar> baltimoreTrack = new LinkedList<>();
	Stack<TrainCar> otherTrack = new Stack<>();
	int trentonWeight = 0;
	int charlotteWeight = 0;
	int baltimoreWeight = 0;
	int[] weights = {trentonWeight, charlotteWeight, baltimoreWeight};
	int[] limits = {TRENTON_LIMIT, CHARLOTTE_LIMIT, BALTIMORE_LIMIT};
	Map<TrainCar, Integer> carMiles = new HashMap<>();
	Map<TrainCar, String> carDest = new HashMap<>();
	
	// Process each line of input data
	while (x.hasNextLine()) { 
		String line = x.nextLine().trim(); 
		if (line.equals("END")) break;

		// Parse train car data: ID, contents, destination, weight, and miles
		if (line.startsWith("CAR")) { 

		if (!x.hasNextLine()) break;
		String contents = x.nextLine().trim();
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

		// Create car and store metadata in hash maps for quick lookup
		TrainCar car = new TrainCar(line, contents, weight);
		mainLine.add(car);
		carMiles.put(car, miles);
		carDest.put(car, destination);

	} else if (line.startsWith("ENG")) {
	
	if (!x.hasNextLine()) break;
		String destinationCity = x.nextLine().trim();
		String engineId = line;
		if ("Trenton".equals(destinationCity)) {
			if (trentonTrack.isEmpty()) {
				System.out.println("No cars on that track.");
			} else {
				System.out.println(engineId + " leaving for " + destinationCity + " with the following cars:");
				while (!trentonTrack.isEmpty()) {
					TrainCar c = trentonTrack.poll();
					mainLine.remove(c);
					System.out.println(c.getID() + " containing " + c.getContents());
				}
				trentonWeight = 0;
			}
		} else if ("Charlotte".equals(destinationCity)) {
			if (charlotteTrack.isEmpty()) {
				System.out.println("No cars on that track.");
			} else {
				System.out.println(engineId + " leaving for " + destinationCity + " with the following cars:");
				while (!charlotteTrack.isEmpty()) {
					TrainCar c = charlotteTrack.poll();
					mainLine.remove(c);
					System.out.println(c.getID() + " containing " + c.getContents());
				}
				charlotteWeight = 0;
			}
		} else if ("Baltimore".equals(destinationCity)) {
			if (baltimoreTrack.isEmpty()) {
				System.out.println("No cars on that track.");
			} else {
				System.out.println(engineId + " leaving for " + destinationCity + " with the following cars:");
				while (!baltimoreTrack.isEmpty()) {
					TrainCar c = baltimoreTrack.poll();
					mainLine.remove(c);
					System.out.println(c.getID() + " containing " + c.getContents());
				}
				baltimoreWeight = 0;
			}
		} else if ("Other destinations".equals(destinationCity)) {
			if (otherTrack.isEmpty()) {
				System.out.println("No cars on that track.");
			} else {
				System.out.println(engineId + " leaving for " + destinationCity + " with the following cars:");
				while (!otherTrack.isEmpty()) {
					TrainCar c = otherTrack.pop();
					mainLine.remove(c);
					System.out.println(c.getID() + " containing " + c.getContents());
				}
			}
		} else {
			System.out.println("No cars on that track.");
		}
	} else {
		
	}
	}
	
	// Process main line: route cars or send to inspection for long distances
	while (!mainLine.isEmpty()) {
		TrainCar car = mainLine.poll();
		int miles = carMiles.getOrDefault(car, 0);
		if (miles > 700) {
			// Cars traveling >700 miles go to inspection for maintenance
			inspection.add(car);
		} else {
			String dest = carDest.getOrDefault(car, "Other destinations");
			sendCar(car, dest, trentonTrack, charlotteTrack, baltimoreTrack, otherTrack, weights, limits);
		}
	} 
	// Process inspection queue and route inspected cars
	while (!inspection.isEmpty()) {
		TrainCar car = inspection.poll();
		carMiles.put(car, 100); // reset miles after inspection
		String dest = carDest.getOrDefault(car, "Other destinations");
		sendCar(car, dest, trentonTrack, charlotteTrack, baltimoreTrack, otherTrack, weights, limits);
	} 

	x.close();
	
		if (!trentonTrack.isEmpty()) {
			System.out.println("ENG00000 leaving for Trenton with the following cars:");
			while (!trentonTrack.isEmpty()) {
				TrainCar c = trentonTrack.poll();
				System.out.println(c.getID() + " containing " + c.getContents());
			}
			weights[0] = 0;
		}
		if (!charlotteTrack.isEmpty()) {
			System.out.println("ENG00000 leaving for Charlotte with the following cars:");
			while (!charlotteTrack.isEmpty()) {
				TrainCar c = charlotteTrack.poll();
				System.out.println(c.getID() + " containing " + c.getContents());
			}
			weights[1] = 0;
		}
		if (!baltimoreTrack.isEmpty()) {
			System.out.println("ENG00000 leaving for Baltimore with the following cars:");
			while (!baltimoreTrack.isEmpty()) {
				TrainCar c = baltimoreTrack.poll();
				System.out.println(c.getID() + " containing " + c.getContents());
			}
			weights[2] = 0;
		}  

		System.out.println("Station Status:");
		if (otherTrack.isEmpty()) {
			System.out.println("No cars waiting for other destinations");
		} else {
			System.out.println("Cars waiting on Track D:");
			for (TrainCar c : otherTrack) System.out.println(c.getID() + " containing " + c.getContents());
		}  
	}

	// Route car to appropriate track based on destination, respecting weight limits
	private static void sendCar(TrainCar car, String destination, Queue<TrainCar> trentonTrack, Queue<TrainCar> charlotteTrack, Queue<TrainCar> baltimoreTrack, Stack<TrainCar> otherTrack, int[] weights, int[] limits) {
		if ("Trenton".equals(destination)) {
			int i = 0;
			int limit = limits[i];
			int current = weights[i];
			// If adding car doesn't exceed weight limit, add it; otherwise dispatch and start new train
			if (limit < 0 || current + car.getWeight() <= limit) {
				trentonTrack.add(car);
				weights[i] += car.getWeight();
			} else {
				System.out.println("ENG00000 leaving for Trenton with the following cars:");
				while (!trentonTrack.isEmpty()) {
					TrainCar c = trentonTrack.poll();
					System.out.println(c.getID() + " containing " + c.getContents());
				}
				weights[i] = 0;
				trentonTrack.add(car);
				weights[i] = car.getWeight();
			}
		} else if ("Charlotte".equals(destination)) {
			int i = 1;
			int limit = limits[i];
			int current = weights[i];
			if (limit < 0 || current + car.getWeight() <= limit) {
				charlotteTrack.add(car);
				weights[i] += car.getWeight();
			} else {
				System.out.println("ENG00000 leaving for Charlotte with the following cars:");
				while (!charlotteTrack.isEmpty()) {
					TrainCar c = charlotteTrack.poll();
					System.out.println(c.getID() + " containing " + c.getContents());
				}
				weights[i] = 0;
				charlotteTrack.add(car);
				weights[i] = car.getWeight();
			}
		} else if ("Baltimore".equals(destination)) {
			int i = 2;
			int limit = limits[i];
			int current = weights[i];
			if (limit < 0 || current + car.getWeight() <= limit) {
				baltimoreTrack.add(car);
				weights[i] += car.getWeight();
			} else {
				System.out.println("ENG00000 leaving for Baltimore with the following cars:");
				while (!baltimoreTrack.isEmpty()) {
					TrainCar c = baltimoreTrack.poll();
					System.out.println(c.getID() + " containing " + c.getContents());
				}
				weights[i] = 0;
				baltimoreTrack.add(car);
				weights[i] = car.getWeight();
			}
		} else {
			otherTrack.push(car);
		}
	}
}