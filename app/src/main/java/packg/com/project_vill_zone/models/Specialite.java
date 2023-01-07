package packg.com.project_vill_zone.models;

public class Specialite {
    private int specialite_id;

    private String nom_specialite;

    public Specialite(int specialite_id, String nom_specialite) {
        this.specialite_id = specialite_id;
        this.nom_specialite = nom_specialite;
    }

    public int getSpecialite_id() {
        return specialite_id;
    }

    public void setSpecialite_id(int specialite_id) {
        this.specialite_id = specialite_id;
    }

    public String getNom_specialite() {
        return nom_specialite;
    }

    public void setNom_specialite(String nom_specialite) {
        this.nom_specialite = nom_specialite;
    }

    @Override
    public String toString() {
        return "Specialite{" +
                "specialite_id=" + specialite_id +
                ", nom_specialite='" + nom_specialite + '\'' +
                '}';
    }
}
