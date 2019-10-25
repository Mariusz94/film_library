package pl.lyszczarz.mariusz.film_library.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.lyszczarz.mariusz.film_library.model.CategoryModel;

@Component
public class CategoryConverter implements Converter<String, CategoryModel> {

    private static Logger logCategoryConverter = LogManager.getLogger(CategoryConverter.class);

    @Autowired
    CategoryService categoryService = new CategoryService();

    @Override
    public CategoryModel convert(String id) {
        logCategoryConverter.info("Próba przekonwertowania id = " + id + " w CategoryModel");
        int idCategory = Integer.valueOf(id);
        int index = idCategory;
        CategoryModel categoryModel=null;
        for (CategoryModel model : categoryService.getCategories()) {
            if (model.getId() == index) {
                categoryModel = model;
                break;
            }
        }

        return categoryModel;
    }
}
