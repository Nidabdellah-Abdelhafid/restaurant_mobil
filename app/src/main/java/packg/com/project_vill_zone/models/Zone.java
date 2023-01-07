package packg.com.project_vill_zone.models;

import com.google.gson.annotations.SerializedName;

public class Zone {
    private int zone_id;
    private String nom_zone;
    private int ville_id;
    private Ville ville;



    public Zone(int zone_id, String nom_zone) {
        this.zone_id = zone_id;
        this.nom_zone = nom_zone;
    }

    public Zone(int zone_id, String nom_zone, int ville_id) {
        this.zone_id = zone_id;
        this.nom_zone = nom_zone;
        this.ville_id = ville_id;
    }

    public Zone(String nom_zone) {
        this.nom_zone = nom_zone;
    }

    public Zone() {
    }

    public Zone(String nom_zone, int ville_id) {
        this.nom_zone = nom_zone;
        this.ville_id = ville_id;
    }

    public int getVille_id() {
        return ville_id;
    }

    public void setVille_id(int ville_id) {
        this.ville_id = ville_id;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }

    public String getNom_zone() {
        return nom_zone;
    }

    public void setNom_zone(String nom_zone) {
        this.nom_zone = nom_zone;
    }


    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }
    @Override
    public String toString() {
        return "Zone{" +
                "zone_id=" + zone_id +
                ", nom_zone='" + nom_zone + '\'' +
                ", ville_id=" + ville_id +
                ", ville=" + ville +
                '}';
    }
}
