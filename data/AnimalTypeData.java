package data;

public enum AnimalTypeData {
    CAT,
    DOG,
    DUCK;

    public static AnimalTypeData fromString(String value) {
        return AnimalTypeData.valueOf(value.toUpperCase());
    }
}
