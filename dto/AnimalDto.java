package dto;

import animals.Animal;
import data.AnimalTypeData;
import data.ColorData;

public class AnimalDto {

    private long id;
    private String name;
    private int age;
    private int weight;
    private ColorData color;
    private AnimalTypeData type;

    public AnimalDto(){}

    public AnimalDto(long id, String name, int age, int weight, String color, String type) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        setColor(color);
        setType(type);
    }

    public AnimalDto(Animal animal) {
        this.id = id;
        this.name = animal.getName();
        this.age = animal.getAge();
        this.weight = animal.getWeight();
        this.color = animal.getColor();
        this.type = animal.getType();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ColorData getColor() {
        return color;
    }

    public String getColorString() {
        if (color != null) {
            return color.toString();
        } else {
            return "";
        }
    }

    public void setColor(String color) {
        this.color = ColorData.fromString(color);
    }

    public AnimalTypeData getType() {
        return type;
    }

    public String getTypeString() {
        if (type != null) {
            return type.toString();
        } else {
            return "";
        }
    }

    public void setType(String type) {
        this.type = AnimalTypeData.fromString(type);
    }

    public String toString() {
        return String.format("Привет! Меня зовут %s, мне %d %s, я вешу - %d кг, мой цвет - %s", name, age, getYearCase(), weight, color.getName());
    }

    private String getYearCase() {
        if (age >= 11 && age <= 14) {
            return "лет";
        }

        int ostatok = age % 10;
        if (ostatok == 1) {
            return "год";
        }

        if (ostatok >= 2 && ostatok <= 4) {
            return "года";
        }

        return "лет";
    }
}


