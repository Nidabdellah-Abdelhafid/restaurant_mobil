package packg.com.project_vill_zone.models;

public class Serie {
    private int serie_id ;
    private String nom_serie ;

    public Serie(int serie_id, String nom_serie) {
        this.serie_id = serie_id;
        this.nom_serie = nom_serie;
    }

    public int getSerie_id() {
        return serie_id;
    }

    public void setSerie_id(int serie_id) {
        this.serie_id = serie_id;
    }

    public String getNom_serie() {
        return nom_serie;
    }

    public void setNom_serie(String nom_serie) {
        this.nom_serie = nom_serie;
    }
}
