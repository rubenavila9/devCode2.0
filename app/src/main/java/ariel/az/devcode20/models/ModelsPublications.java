package ariel.az.devcode20.models;

import okhttp3.ResponseBody;

public class ModelsPublications {

    private ResponseBody responseBody;
    private String namePublication;
    private String descriptPublication;
    private Integer levelSubject;


    public ModelsPublications(String namePublication, String descriptPublication, Integer levelSubject) {
        this.namePublication = namePublication;
        this.descriptPublication = descriptPublication;
        this.levelSubject = levelSubject;
    }


}
