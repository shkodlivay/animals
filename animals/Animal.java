package animals;

import data.AnimalTypeData;
import data.ColorData;

public abstract class Animal {
    private String name;
    private int age;
    private int weight;
    private ColorData color;
    protected AnimalTypeData type;

    public Animal(String name, int age, int weight, ColorData color) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.color = color;
        this.type = null;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getWeight() {
        return weight;
    }
    public ColorData getColor() {
        return color;
    }
    public AnimalTypeData getType() {
        return type;
    }

    public void say() {
        System.out.println("Я говорю");
    }

    public void go() {
        System.out.println("Я иду");
    }

    public void drink() {
        System.out.println("Я пью");
    }

    public void eat() {
        System.out.println("Я ем");
    }

    @Override
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
