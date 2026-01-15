public class TrainCar {
    private String id;
    private String contents;
    private int weight;

    // Constructor to create a train car with ID, contents, and weight
    public TrainCar(String id, String contents, int weight) {
        this.id = id;
        this.contents = contents;
        this.weight = weight;
    }

    public String getID() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public int getWeight() {
        return weight;
    }

    // Format train car info for display
    @Override
    public String toString() {
        return id + " containing " + contents + " (" + weight + " lbs)";
    }
}
