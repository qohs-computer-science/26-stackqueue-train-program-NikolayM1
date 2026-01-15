public class Train {
    private String product, origin, id, destination;
    private int weight, miles;

    private int weightLimit;
    private java.util.List<TrainCar> cars;
   // 2-7 Instance Variables; 5-6 are my additions 

    // Train Constructor - initializes train with route details and capacity limit
    public Train(String id, String destination, int weightLimit, String origin, String product, int miles) {
        this.id = id;
        this.destination = destination;
        this.weightLimit = weightLimit;
        this.weight = 0;
        this.cars = new java.util.ArrayList<>();
        this.origin = origin;
        this.product = product;
        this.miles = miles;
    }

    // Check if car can be added without exceeding weight limit
    public boolean canAddCar(TrainCar car) {
        if (weightLimit < 0) {
            return true;
        }
        return weight + car.getWeight() <= weightLimit;
    }

    // Add car to train and update total weight
    public void addCar(TrainCar car) {
        cars.add(car);
        weight += car.getWeight();
    }

    // Check if train has any cars
    public boolean isEmpty() {
        return cars.isEmpty();
    }

    // Display departure info and clear train for next load
    public void depart() {
        System.out.println(id + " leaving from " + origin + " for " + destination + " carrying " + product + " for " + miles + " miles with the following cars:");
        for (TrainCar car : cars) {
            System.out.println(car.getID() + " containing " + car.getContents());
        }
        cars.clear();
        weight = 0;
    }

    // Getters for train properties
    public String getID() { return id; }
    public String getDestination() { return destination; }
    public int getWeight() { return weight; }
    public int getWeightLimit() { return weightLimit; }
    public String getOrigin() { return origin; }
    public String getProduct() { return product; }
    public int getMiles() { return miles; }

}
 