package pl.lyszczarz.mariusz.film_library.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.lyszczarz.mariusz.film_library.model.InfoModel;
import pl.lyszczarz.mariusz.film_library.model.forms.InfoForm;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

@Service
public class InfoService {
    private static Logger logInfoService = LogManager.getLogger(InfoService.class);

    public InfoModel readInfoFile(Path infoPath){
        List<String> lines = Collections.emptyList();

        try {
            lines = Files.readAllLines(infoPath,StandardCharsets.UTF_8);
            logInfoService.info("Odczytano dane z pliku info.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createInfoModel(lines);
    }

    private InfoModel createInfoModel(List<String> lines){
        InfoModel infoModel = new InfoModel();

        String [] name = lines.get(0).split("=");
        infoModel.setName(name[1].trim());

        String [] category = lines.get(1).split("=");
        infoModel.setCategory(category[1].trim());

        String [] year = lines.get(2).split("=");
        infoModel.setYear(Integer.valueOf(year[1].trim()));

        String [] rating = lines.get(3).split("=");
        infoModel.setRating(rating[1].trim());

        String [] language = lines.get(4).split("=");
        infoModel.setLanguage(language[1].trim());

        String [] subtitles = lines.get(5).split("=");
        infoModel.setSubtitles(subtitles[1].trim());

        String [] descriptions = lines.get(6).split("=");
        infoModel.setDescription(descriptions[1].trim());
        logInfoService.info("Utworzono infoModel : " + infoModel.getName());
        return infoModel;
    }

    public static void changeInfoFile(InfoForm infoForm, String pathInfo){
        File file = new File(pathInfo);
        String text = "name = " + infoForm.getName() +"\n" +
                "category = " + infoForm.getCategory()+"\n" +
                "year = " + infoForm.getYear() +"\n" +
                "rating = " + infoForm.getRating() +"\n" +
                "language = " + infoForm.getLanguage() +"\n" +
                "subtitles = " + infoForm.getSubtitles() +"\n" +
                "descriptions = " + infoForm.getDescription() +"\n" +
                "\n";
        try {
            Files.write(file.toPath(),text.getBytes() , StandardOpenOption.TRUNCATE_EXISTING);
            logInfoService.info("Zamieniono dane w info.txt w filmie : " + infoForm.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
