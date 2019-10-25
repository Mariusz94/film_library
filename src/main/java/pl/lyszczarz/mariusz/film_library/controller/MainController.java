package pl.lyszczarz.mariusz.film_library.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.lyszczarz.mariusz.film_library.model.CategoryModel;
import pl.lyszczarz.mariusz.film_library.model.FilmModel;
import pl.lyszczarz.mariusz.film_library.model.forms.CategoryForm;
import pl.lyszczarz.mariusz.film_library.model.forms.InfoForm;
import pl.lyszczarz.mariusz.film_library.model.service.CategoryService;
import pl.lyszczarz.mariusz.film_library.model.service.FilmsService;
import pl.lyszczarz.mariusz.film_library.model.service.InfoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {

    private final
    FilmsService filmsService;

    private static Logger logMainController = LogManager.getLogger(MainController.class);

    @Autowired
    public MainController(FilmsService filmsService) {
        filmsService = new FilmsService();
        this.filmsService = filmsService;
    }

    @GetMapping("/")
    public String indexGet(Model model) {
        model.addAttribute("films",filmsService.getFilms());
        logMainController.info("Wyświetlenie strony głównej");
        return "dashboard";
    }

    @GetMapping("/film/{filmName}")
    public String filmGet(Model model, @PathVariable("filmName") String filmName) {
        filmName = filmName.replace("_"," ");
        model.addAttribute("filmModel",filmsService.findFilmModelByName(filmName));
        logMainController.info("Wyświetlenie strony z filmem : " + filmName);
        return "film";
    }

    @GetMapping("/film/{filmName}/edit")
    public String filmEditGet(Model model, @PathVariable("filmName") String filmName) {
        filmName = filmName.replace("_"," ");
        FilmModel filmModel = filmsService.findFilmModelByName(filmName);
        model.addAttribute("filmModel",filmModel);
        model.addAttribute("selectableCategory",filmsService.getCategoryService().getCategories());
        model.addAttribute("checkCategories",new CategoryForm());

        InfoForm infoForm = new InfoForm();
        infoForm.setName(filmModel.getInfo().getName());
        infoForm.setCategory(filmModel.getInfo().getCategory());
        infoForm.setDescription(filmModel.getInfo().getDescription());
        infoForm.setLanguage(filmModel.getInfo().getLanguage());
        infoForm.setRating(filmModel.getInfo().getRating());
        infoForm.setSubtitles(filmModel.getInfo().getSubtitles());
        infoForm.setYear(filmModel.getInfo().getYear());
        model.addAttribute("infoForm", infoForm);

        logMainController.info("Wyświetlenie strony do edycji opisu filmu : " + filmName);
        return "filmEdit";
    }

    @PostMapping("/film/{filmName}/edit")
    public String filmEditPost(Model model, @PathVariable("filmName") String filmName,
                               @ModelAttribute("infoForm") InfoForm infoForm,
                               @ModelAttribute("checkCategories") CategoryForm categoryForm) {
        StringBuilder category = new StringBuilder();
        for (CategoryModel categoryModel : categoryForm.getListCategory()) {
            category.append(categoryModel.getName());
            category.append(" / ");
        }
        if(category.length()>=1) {
            category.delete(category.length() - 3,category.length()-1);
            infoForm.setCategory(category.toString());
        }

        filmName = filmName.replace("_"," ");
        FilmModel filmModel = filmsService.findFilmModelByName(filmName);
        InfoService.changeInfoFile(infoForm, filmModel.getPathFilm() + "\\info.txt");

        filmsService.refreshFilms();

        model.addAttribute("filmModel",filmModel);

        logMainController.info("Prekierowanie do strony z filmem po edycji pliku info.txt");
        return "redirect:/film/" + filmName.replace(" ","_");
    }

    @GetMapping("/category")
    public String categoryGet(Model model) {
        model.addAttribute("categories", filmsService.getCategoryService().getCategories());
        return "category";
    }

    @GetMapping("/results/{name}")
    public String resultGet(Model model, @PathVariable("name") String name) {
        List<FilmModel> results = new ArrayList<>();

        for (FilmModel filmModel : filmsService.getFilms()) {
            if(filmModel.getInfo().getCategory().toLowerCase().contains(name)) results.add(filmModel);
        }

        model.addAttribute("results",results);

        return "results";
    }

}
