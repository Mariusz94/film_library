package pl.lyszczarz.mariusz.film_library.model;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.Data;

import java.util.Collections;

@Data
public class CategoryModel implements Comparable<CategoryModel> {
    private int id;
    private String name;

    public CategoryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public int compareTo(CategoryModel o) {
        return name.compareTo(o.getName());
    }
}
