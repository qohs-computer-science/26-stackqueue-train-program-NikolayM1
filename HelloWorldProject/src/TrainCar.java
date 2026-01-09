public class TrainCar {
    private String name;
    private String contents;
    private int weight;

    public TrainCar(String name, String contents, int weight) {
        this.name = name;
        this.contents = contents;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getContents() {
        return contents;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name + " containing " + contents + " (" + weight + " lbs)";
    }
}
