package pl.lyszczarz.mariusz.film_library.model.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InfoForm {
    private String name;
    private String category;
    private int year;
    private String rating;
    private String language;
    private String subtitles;
    private String description;
}
