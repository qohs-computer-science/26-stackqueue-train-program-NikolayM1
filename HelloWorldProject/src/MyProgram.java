/*
 * Nikolay Merenko
 * January 6th to January 12th 2026
 * Period 6
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
	Map<String, Integer> trackWeight = new HashMap<>();
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
			
			trackWeight.put(destinationCity, 0);
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
			sendCar(car, dest, tracks, trackWeight, trackA, trackB, trackC, trackD);
		}
	}
	while (!inspection.isEmpty()) {
		TrainCar car = inspection.poll();
		carMiles.put(car, 100);
		String dest = carDest.getOrDefault(car, "Other destinations");
		sendCar(car, dest, tracks, trackWeight, trackA, trackB, trackC, trackD);
	}

	x.close();
	
		if (tracks.containsKey("Trenton") && !tracks.get("Trenton").isEmpty()) {
			System.out.println("Track A departing:");
			Queue<TrainCar> q = tracks.get("Trenton");
			while (!q.isEmpty()) {
				System.out.println("Departing: " + q.poll().toString());
			}
			trackWeight.put("Trenton", 0);
		}
		if (tracks.containsKey("Charlotte") && !tracks.get("Charlotte").isEmpty()) {
			System.out.println("Track B departing:");
			Queue<TrainCar> q = tracks.get("Charlotte");
			while (!q.isEmpty()) {
				System.out.println("Departing: " + q.poll().toString());
			}
			trackWeight.put("Charlotte", 0);
		}
		if (tracks.containsKey("Baltimore") && !tracks.get("Baltimore").isEmpty()) {
			System.out.println("Track C departing:");
			Queue<TrainCar> q = tracks.get("Baltimore");
			while (!q.isEmpty()) {
				System.out.println("Departing: " + q.poll().toString());
			}
			trackWeight.put("Baltimore", 0);
		}

		System.out.println("Station Status:");
		Queue<TrainCar> qd = tracks.get("Other destinations");
		if (qd == null || qd.isEmpty()) {
			System.out.println("No cars waiting for other destinations");
		} else {
			System.out.println("Cars waiting on Track D:");
			for (TrainCar c : qd) {
				System.out.println(c.toString());
			}
		}
	}

	private static void sendCar(TrainCar car, String destination, Map<String, Queue<TrainCar>> tracks, Map<String, Integer> trackWeight, Train trackA, Train trackB, Train trackC, Train trackD) {
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
		if (limit < 0 || current + car.getWeight() <= limit) {
			tracks.computeIfAbsent(key, k -> new LinkedList<>()).add(car);
			trackWeight.put(key, current + car.getWeight());
		} else {
			System.out.println(target.getID() + " leaving from " + target.getOrigin() + " for " + target.getDestination() + " carrying " + target.getProduct() + " for " + target.getMiles() + " miles with the following cars:");
			Queue<TrainCar> q = tracks.get(key);
			if (q != null) {
				while (!q.isEmpty()) {
					TrainCar c = q.poll();
					System.out.println("Departing: " + c.toString());
				}
			}
			trackWeight.put(key, 0);
			
			tracks.computeIfAbsent(key, k -> new LinkedList<>()).add(car);
			trackWeight.put(key, car.getWeight());
		}
	}
}