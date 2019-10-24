package pl.lyszczarz.mariusz.film_library.model;

import lombok.Data;

@Data
public class CategoryModel {
    private int id;
    private String name;

    public CategoryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
