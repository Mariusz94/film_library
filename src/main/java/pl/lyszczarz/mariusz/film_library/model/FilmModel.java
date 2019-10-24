package pl.lyszczarz.mariusz.film_library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
public class FilmModel {
    private String name;
    private String pathFilm;
    private InfoModel info;

    public FilmModel(File file) {
        this.name = file.getName();
        this.pathFilm = file.getAbsolutePath();
    }
}
