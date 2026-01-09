public class Train {
    private String product, origin, id, destination;
    private int weight, miles;

    private int weightLimit;
    private java.util.List<TrainCar> cars;
   // 2-7 Instance Variables; 5-6 are my additions 

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
    //9-18 Train Constructor

    public boolean canAddCar(TrainCar car) {
        if (weightLimit < 0) {
            return true;
        }
        return weight + car.getWeight() <= weightLimit;
    }
    //21-26 method checks if a car can be added

    public void addCar(TrainCar car) {
        cars.add(car);
        weight += car.getWeight();
    }
    //29-32 method to add a car

    public boolean isEmpty() {
        return cars.isEmpty();
    }
    //35-37 method for when car is empty

    public void depart() {
        System.out.println(id + " leaving from " + origin + " for " + destination + " carrying " + product + " for " + miles + " miles with the following cars:");
        for (TrainCar car : cars) {
            System.out.println(car.getID() + " containing " + car.getContents());
        }
        cars.clear();
        weight = 0;
    }
    //40-47 method prints out info when train is leaving

    public String getID() { return id; }
    public String getDestination() { return destination; }
    public int getWeight() { return weight; }
    public int getWeightLimit() { return weightLimit; }
    public String getOrigin() { return origin; }
    public String getProduct() { return product; }
    public int getMiles() { return miles; }
    //50-56 returns instance variables

}
 