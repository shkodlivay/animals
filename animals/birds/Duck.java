package animals.birds;

import animals.Animal;
import data.AnimalTypeData;
import data.ColorData;

public class Duck extends Animal implements IFlying {
    public Duck(String name, int age, int weight, ColorData color, AnimalTypeData type) {
        super(name, age, weight, color);
        this.type = type;
    }

    @Override
    public void fly() {
        System.out.println("Я лечу");
    }

    @Override
    public void say() {
        System.out.println("Кря");
    }
}