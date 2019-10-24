package pl.lyszczarz.mariusz.film_library.model.forms;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lyszczarz.mariusz.film_library.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryForm {
    private List<CategoryModel> listCategory;

    public CategoryForm() {
        this.listCategory = new ArrayList<>();
    }
}
