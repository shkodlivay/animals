package animals.pets;

import animals.Animal;
import data.AnimalTypeData;
import data.ColorData;

public class Cat extends Animal {
    public Cat(String name, int age, int weight, ColorData color, AnimalTypeData type) {
        super(name, age, weight, color);
        this.type = type;
    }

    @Override
    public void say() {
        System.out.println("Мяу");
    }
}
