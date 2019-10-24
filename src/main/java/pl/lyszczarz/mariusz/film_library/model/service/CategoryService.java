package pl.lyszczarz.mariusz.film_library.model.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.lyszczarz.mariusz.film_library.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class CategoryService {
    private List<CategoryModel> categories;

    public CategoryService() {
        this.categories = new ArrayList<>();
        createCategoryList();
    }

    private void createCategoryList(){
        categories.add(new CategoryModel(1,"Akcja"));
        categories.add(new CategoryModel(2,"Biograficzne"));
        categories.add(new CategoryModel(3,"Dramat"));
        categories.add(new CategoryModel(4,"Fantasty"));
        categories.add(new CategoryModel(5,"Horror"));
        categories.add(new CategoryModel(6,"Komedia"));
        categories.add(new CategoryModel(7,"Musical"));
        categories.add(new CategoryModel(8,"Polskie"));
        categories.add(new CategoryModel(9,"SciFi"));
        categories.add(new CategoryModel(10,"Sport"));
        categories.add(new CategoryModel(11,"Wojenne"));
        categories.add(new CategoryModel(12,"Animowane"));
        categories.add(new CategoryModel(13,"Dokumentalne"));
        categories.add(new CategoryModel(14,"Familijne"));
        categories.add(new CategoryModel(15,"Historyczne"));
        categories.add(new CategoryModel(16,"Katastroficzne"));
        categories.add(new CategoryModel(17,"Kostiumowe"));
        categories.add(new CategoryModel(18,"Obyczajowe"));
        categories.add(new CategoryModel(19,"Przygodowe"));
        categories.add(new CategoryModel(20,"Sensacyjne"));
        categories.add(new CategoryModel(21,"Thiller"));

    }
}
