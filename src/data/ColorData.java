package data;

public enum ColorData {
    RED("Красный"),
    BLACK("Черный"),
    WHITE("Белый");

    private String name;

    ColorData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}