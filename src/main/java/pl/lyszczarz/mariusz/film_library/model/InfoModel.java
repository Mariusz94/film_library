package pl.lyszczarz.mariusz.film_library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InfoModel {
    private String name;
    private String category;
    private int year;
    private String rating;
    private String language;
    private String subtitles;
    private String description;
}
