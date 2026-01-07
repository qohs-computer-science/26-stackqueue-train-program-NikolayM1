public class Train {
    private String name, product, origin, destination;
    private int weight, miles;

   private int weightLimit;
   private java.util.List<TrainCar> cars;
   // 2-7 Instance Variables ~ 5-6 are my additions 

    public Train(String name, String destination, int weightLimit) {
        this.name = name;
        this.destination = destination;
        this.weightLimit = weightLimit;
        this.currentWeight = 0;
        this.cars = new java.util.ArrayList<>();
    }
    //9-16 Train Constructor

    public boolean canAddCar(TrainCar car) {
        if (weightLimit < 0) {
            return true;
        }
        return currentWeight + car.getWeight() <= weightLimit;
    }
    //18-23 method checks if a car can be added

    public void addCar(TrainCar car) {
        cars.add(car);
        currentWeight += car.getWeight();
    }
    //26-29 method to add a car

    public boolean isEmpty() {
        return cars.isEmpty();
    }
    //32-34 method for when car is empty

    public void depart() {
        System.out.println(name + " leaving for " + destination + " with the following cars:");
        for (TrainCar car : cars) {
            System.out.println(car.getName() + " containing " + car.getContents());
        }
        cars.clear();
        currentWeight = 0;
    }
    //37-44 method prints out info when train is leaving

    public String getName() { return name; }
    public String getDestination() { return destination; }
    public int getCurrentWeight() { return currentWeight; }
    public int getWeightLimit() { return weightLimit; }
    //47-50 returns instance variables

}
 