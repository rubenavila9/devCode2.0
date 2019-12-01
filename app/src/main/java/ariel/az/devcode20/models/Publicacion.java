package ariel.az.devcode20.models;

public class Publicacion {
   private String name_publication;
   private String name_user;
   private Integer image_user;

    public Publicacion(String name_publication, String name_user, Integer image_user) {
        this.name_publication = name_publication;
        this.name_user = name_user;
        this.image_user = image_user;
    }

    public String getName_publication() {
        return name_publication;
    }

    public void setName_publication(String name_publication) {
        this.name_publication = name_publication;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public Integer getImage_user() {
        return image_user;
    }

    public void setImage_user(Integer image_user) {
        this.image_user = image_user;
    }
}
