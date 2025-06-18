import animals.Animal;
import data.CommandsData;
import data.AnimalTypeData;
import data.ColorData;
import dto.AnimalDto;
import factory.AnimalFactory;
import tables.AnimalTable;
import tools.NumberTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static data.AnimalTypeData.CAT;
import static data.ColorData.RED;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // choosing an animal type
    public static AnimalTypeData inputAnimalTypeData() {
        List<String> animalStr = new ArrayList<>();

        for (AnimalTypeData animalTypeData : AnimalTypeData.values()) {
            animalStr.add(animalTypeData.name().toLowerCase());
        }
        System.out.println(String.format("Выберите животное: %s", String.join("/", animalStr)));
        String animalTypeInput = scanner.nextLine().trim();
        if (!animalTypeInput.isEmpty() && animalTypeInput.charAt(0) == '\n') {
            scanner.nextLine();
        }
        String animalTypeDataUpperCase = animalTypeInput.toUpperCase();
        return AnimalTypeData.valueOf(animalTypeDataUpperCase);
    }

    // enter an animal name
    public static String inputAnimalName() {
        System.out.println(String.format("Введите имя животного:"));
        String animalName = scanner.nextLine().trim();
        if (animalName.isEmpty()) {
            System.out.println("Введено неверное имя животного");
        }
        return animalName;
    }

    // enter an animal age
    public static String inputAnimalAge() {
        System.out.println(String.format("Введите возраст животного:"));
        return scanner.nextLine().trim();
    }

    // enter an animal weight
    public static String inputAnimalWeight() {
        System.out.println(String.format("Введите вес животного:"));
        return scanner.nextLine().trim();
    }

    // choosing an animal color
    public static ColorData inputAnimalColorData() {
        System.out.println(String.format("Выберите цвет животного:"));
        String animalColorData = scanner.nextLine().trim();
        if (!animalColorData.isEmpty() && animalColorData.charAt(0) == '\n') {
            scanner.nextLine();
        }
        String animalColorDataUpperCase = animalColorData.toUpperCase();
        return ColorData.valueOf(animalColorDataUpperCase);
    }

    // enter an animal id
    public static String inputAnimalId() {
        System.out.println(String.format("Введите id животного:"));
        return scanner.nextLine().trim();
    }

    public static void main(String... args) {
        List<Animal> animals = new ArrayList<>();

        AnimalTable animalTable = new AnimalTable();
        while (true) {
            List<String> nameStr = new ArrayList<>();
            for (CommandsData commandsData : CommandsData.values()) {
                nameStr.add(commandsData.name().toLowerCase());
            }
            System.out.println(String.format("Введите команду: %s", String.join("/", nameStr)));
            String userCommand = scanner.nextLine().trim();
            String userCommandUpperCase = userCommand.toUpperCase();

            boolean isCommandExist = false;
            for (CommandsData commandsData : CommandsData.values()) {
                if (userCommandUpperCase.equals(commandsData.name())) {
                    isCommandExist = true;
                    break;
                }
            }

            if (!isCommandExist) {
                System.out.printf("Команда %s не поддерживается\n", userCommand);
                continue;
            }

            switch (CommandsData.valueOf(userCommandUpperCase)) {
                // add of animal
                case ADD: {
                    AnimalTypeData animalTypeData = validationAnimalType();
                    String animalName = "";
                    while (animalName.isEmpty()) {
                        animalName = inputAnimalName();
                    }
                    int animalAge = validationAnimalAge();
                    int animalWeight = validationAnimalWeight();
                    ColorData animalColor = validationAnimalColor();
                    AnimalFactory animalFactory = new AnimalFactory(animalName, animalAge, animalWeight, animalColor);
                    Animal newAnimal = animalFactory.create(animalTypeData);
                    AnimalDto animals_dto = new AnimalDto(newAnimal);
                    animalTable.insertAnimal(animals_dto);

                    animals.add(newAnimal);
                    newAnimal.say();
                    break;
                }

                case EDIT: {
                    long animalId = validationAnimalId();
                    AnimalTable animal = new AnimalTable();
                    AnimalDto animalDto = animal.findById(animalId);

                    if (animalDto == null) {
                        System.out.println("Введен несуществующий id");
                        break;
                    }

                    AnimalTypeData animalTypeData = validationAnimalType();
                    animalDto.setType(animalTypeData.toString());
                    String animalName = "";

                    while (animalName.isEmpty()) {
                        animalName = inputAnimalName();
                    }

                    animalDto.setName(animalName);
                    int animalAge = validationAnimalAge();
                    animalDto.setAge(animalAge);
                    int animalWeight = validationAnimalWeight();
                    animalDto.setWeight(animalWeight);
                    ColorData animalColor = validationAnimalColor();
                    animalDto.setColor(animalColor.toString());
                    animal.updateAnimal(animalDto);

                    break;
                }

                // view а list of animals
                case LIST: {
                    AnimalTable all_animals = new AnimalTable();
                    List<AnimalDto> animals_list = all_animals.findAll();

                    for (AnimalDto animalDto : animals_list) {
                        System.out.println(animalDto.toString());
                    }

                    break;
                }

                // filter animals
                case FILTER: {
                    AnimalTypeData animalType = validationAnimalType();
                    AnimalTable all_animals = new AnimalTable();
                    List<AnimalDto> animals_list = all_animals.findAll();

                    for (AnimalDto animalDto : animals_list) {
                        if (animalDto.getType() == animalType) {
                            System.out.println(animalDto.toString());
                        }
                    }

                    break;
                }

                // exit from program
                case EXIT: {
                    System.exit(0);

                }
            }
        }
    }

    // validation an animal type
    private static AnimalTypeData validationAnimalType() {
        boolean isAnimalExist = false;
        AnimalTypeData animalType = CAT;
        while (!isAnimalExist) {
            try {
                animalType = inputAnimalTypeData();
                isAnimalExist = true;
            } catch (Exception e) {
                System.out.println("Введен неверный тип животного");
            }
        }
        return animalType;
    }

    // validation an animal color
    private static ColorData validationAnimalColor() {
        boolean isAnimalColorExist = false;
        ColorData animalColor = RED;
        while (!isAnimalColorExist) {
            try {
                animalColor = inputAnimalColorData();
                isAnimalColorExist = true;
            } catch (Exception e) {
                System.out.println("Введен неверный цвет животного");
            }
        }
        return animalColor;
    }

    // validation an animal age
    private static int validationAnimalAge() {
        boolean isAnimalAgeExist = false;
        int animalAge = 0;
        while (!isAnimalAgeExist) {
            try {
                String animalAgeInput = inputAnimalAge();
                if (NumberTools.isNumber(animalAgeInput)) {
                    return Integer.parseInt(animalAgeInput);
                } else {
                    System.out.println("Введен неверный возраст животного");
                }
            } catch (Exception e) {
                System.out.println("Введен неверный возраст животного");
            }
        }
        return animalAge;
    }

    // validation an animal weight
    private static int validationAnimalWeight() {
        boolean isAnimalWeightExist = false;
        int animalWeight = 0;
        while (!isAnimalWeightExist) {
            try {
                String animalWeightInput = inputAnimalWeight();
                if (NumberTools.isNumber(animalWeightInput)) {
                    return Integer.parseInt(animalWeightInput);
                } else {
                    System.out.println("Введен неверный вес животного");
                }
            } catch (Exception e) {
                System.out.println("Введен неверный вес животного");
            }
        }
        return animalWeight;
    }

    // validation an animal id
    private static long validationAnimalId() {
        long animalId = 0;
        try {
            String animalIdInput= inputAnimalId();
            if (NumberTools.isNumber(animalIdInput)) {
                return Long.parseLong(animalIdInput);
            } else {
                System.out.println("Введен неверный id животного");
            }
        } catch (Exception e) {
            System.out.println("Введен неверный id животного");
        }

        return animalId;
    }
}
