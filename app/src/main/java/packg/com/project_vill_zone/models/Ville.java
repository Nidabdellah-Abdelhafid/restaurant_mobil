package packg.com.project_vill_zone.models;

import com.google.gson.annotations.SerializedName;

public class Ville {

    private int ville_id;

    private String nom_ville;


    public Ville(int ville_id, String nom_ville) {
        this.ville_id = ville_id;
        this.nom_ville = nom_ville;
    }

    public Ville(String nom_ville) {
        this.nom_ville = nom_ville;
    }

    public int getVille_id() {
        return ville_id;
    }

    public void setVille_id(int ville_id) {
        this.ville_id = ville_id;
    }

    public String getNom_ville() {
        return nom_ville;
    }

    public void setNom_ville(String nom_ville) {
        this.nom_ville = nom_ville;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "ville_id=" + ville_id +
                ", nom_ville='" + nom_ville + '\'' +
                '}';
    }

}
