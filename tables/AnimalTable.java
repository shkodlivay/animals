package tables;

import db.DatabaseConnection;
import dto.AnimalDto;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalTable extends AbsTable{
    public AnimalTable() {
        super("animals");
        columns.put("id", "bigint PRIMARY KEY AUTO_INCREMENT");
        columns.put("type", "varchar(15)");
        columns.put("name", "varchar(15)");
        columns.put("color", "varchar(15)");
        columns.put("weight", "int");
        columns.put("age", "int");
        createTable();
    }

    public List<AnimalDto> findAll() {
        List<AnimalDto> animals = new ArrayList<>();
        try {
            DatabaseConnection conn = DatabaseConnection.getInstance();
            try (ResultSet rs =  conn.executeQuery("SELECT * FROM " + tableName)) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    int weight = rs.getInt("weight");
                    String color = rs.getString("color");
                    String type = rs.getString("type");

                    AnimalDto animal = new AnimalDto(id, name, age, weight, color, type);
                    animals.add(animal);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return animals;
    }

    public AnimalDto findById(long searchId) {
        AnimalDto animal = new AnimalDto();
        try {
            DatabaseConnection conn = DatabaseConnection.getInstance();
            try (ResultSet rs = conn.executeQuery("SELECT * FROM " + tableName + " WHERE id=?",  searchId)) {
                if (!rs.isBeforeFirst()) {
                    return null;
                }
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    int weight = rs.getInt("weight");
                    String color = rs.getString("color");
                    String type = rs.getString("type");

                    animal = new AnimalDto(id, name, age, weight, color, type);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return animal;
    }

    public void insertAnimal(AnimalDto animalDto) {
        String sqlRequest = String.format( "INSERT INTO %s (name, age, weight, color, type) VALUES (?, ?, ?, ?, ?)",
                this.tableName);
        try {
            DatabaseConnection conn = DatabaseConnection.getInstance();
            conn.executeUpdate(sqlRequest, animalDto.getName(), animalDto.getAge(), animalDto.getWeight(), animalDto.getColor().toString(), animalDto.getType().toString());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateAnimal(AnimalDto animalDto) {
        String sqlRequest = String.format( "UPDATE %s SET name = ?, age = ?, weight = ?, color = ?, type = ? WHERE id = %d",
                this.tableName, animalDto.getId());
        try {
            DatabaseConnection conn = DatabaseConnection.getInstance();
            conn.executeUpdate(sqlRequest, animalDto.getName(), animalDto.getAge(), animalDto.getWeight(), animalDto.getColor().toString(), animalDto.getType().toString());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }
 }
