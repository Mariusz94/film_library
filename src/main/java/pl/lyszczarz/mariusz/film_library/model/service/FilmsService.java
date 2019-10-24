package pl.lyszczarz.mariusz.film_library.model.service;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lyszczarz.mariusz.film_library.model.FilmModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class FilmsService {

    private final
    InfoService infoService ;

    private
    CategoryService categoryService;

    private static Logger logFilmService = LogManager.getLogger(FilmsService.class);

    private static final String FILMS_PATH = "D:\\Biblioteka Filmowa\\Filmy";
    private File[] filmsFile;
    private List<FilmModel> films;

    @Autowired
    public FilmsService() {
        this.infoService = new InfoService();
        this.categoryService = new CategoryService();

        filmsFile = new File(FILMS_PATH).listFiles();
        logFilmService.info("Pobrano liste " + filmsFile.length + " Filmów z katalogu");
        films = new ArrayList<>();
        isInfoFileExist();
        createFilmsModelsLibrary();
        try {
            copyImageToResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isInfoFileExist(){
        for (File file : filmsFile) {
            String pathInfo = file.getAbsolutePath() + "\\info.txt";
            try {
                if(new File(pathInfo).createNewFile()){
                    logFilmService.info("Brak pliku info.txt dla filmu : " + file.getName());
                    writeToNewFile(pathInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logFilmService.info("Zakończone sprawdzanie posiadania pików info przez filmy");
    }

    private void writeToNewFile(String pathInfo){
        File file = new File(pathInfo);
        String text = "name = none\n" +
                "category = none\n" +
                "year = 0\n" +
                "rating = none\n" +
                "language = none\n" +
                "subtitles = none\n" +
                "descriptions = none\n" +
                "\n";
        try {
            Files.write(file.toPath(),text.getBytes() , StandardOpenOption.TRUNCATE_EXISTING);
            logFilmService.info("Utworzono plik info.txt dla filmu : " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createFilmsModelsLibrary(){
        films.clear();
        logFilmService.info("Wyczyszczono kolekcje films z filmModelami");
        for (File file : filmsFile) {
            FilmModel filmModel = new FilmModel(file);
            filmModel.setInfo(infoService.readInfoFile(Paths.get(file.getAbsolutePath()+"\\info.txt")));
            films.add(filmModel);
            logFilmService.info("Dodno \"" + file.getName() + "\" do kolekcji");
        }
    }

    private void copyImageToResource() throws IOException {

        for (FilmModel film : films) {
            String pathImage = film.getPathFilm() + "\\image.jpg";

            if(!Files.isExecutable(Paths.get(pathImage)) || Files.isExecutable(Paths.get("D:\\Biblioteka Filmowa\\film_library\\src\\main\\resources\\static\\jpg\\" + film.getName() + ".jpg"))) {
                continue;
            }else{
                FileInputStream in = new FileInputStream(pathImage);
                FileOutputStream ou = new FileOutputStream("D:\\Biblioteka Filmowa\\film_library\\src\\main\\resources\\static\\jpg\\" + film.getName() + ".jpg");
                BufferedInputStream bin = new BufferedInputStream(in);
                BufferedOutputStream bou = new BufferedOutputStream(ou);
                int b = 0;
                while (b != -1) {
                    b=bin.read();
                    bou.write(b);
                }
                bin.close();
                bou.close();
            }
        }
        logFilmService.info("Wykonano kopie wszystkich obrazów do projektu");
    }

    public FilmModel findFilmModelByName(String name){
        FilmModel filmModel = new FilmModel();
        for (FilmModel film : films) {
            if(film.getName().equals(name)){
                filmModel = film;
                break;
            }
        }
        logFilmService.info("Znaleziono filmModel po nazwie : " + name);
        return filmModel;
    }

    public void refreshFilms(){
        logFilmService.info("Rozpoczęcie odświerzania danych");
        filmsFile = new File(FILMS_PATH).listFiles();
        isInfoFileExist();
        createFilmsModelsLibrary();
        try {
            copyImageToResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logFilmService.info("Odświerzanie danych zakończone");
    }
}
