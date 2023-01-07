package packg.com.project_vill_zone.models;

public class Restaurant {
    private Integer restaurant_id;
    private String nom_restaurant;
    private String addresse;
    private Double lat;
    private Double log;
    private String heure_open;
    private int etat;
    private String heure_close;
    private Boolean week;
    private int serie_id ;
    private int specialite_id ;
    private int zone_id;

    public Restaurant(String nom_restaurant, String addresse, Double lat, Double log, String heure_open, String heure_close, Boolean week, int serie_id, int specialite_id, int zone_id) {
        this.nom_restaurant = nom_restaurant;
        this.addresse = addresse;
        this.lat = lat;
        this.log = log;
        this.heure_open = heure_open;
        this.heure_close = heure_close;
        this.week = week;
        this.serie_id = serie_id;
        this.specialite_id = specialite_id;
        this.zone_id = zone_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getNom_restaurant() {
        return nom_restaurant;
    }

    public void setNom_restaurant(String nom_restaurant) {
        this.nom_restaurant = nom_restaurant;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLog() {
        return log;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    public String getHeure_open() {
        return heure_open;
    }

    public void setHeure_open(String heure_open) {
        this.heure_open = heure_open;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getHeure_close() {
        return heure_close;
    }

    public void setHeure_close(String heure_close) {
        this.heure_close = heure_close;
    }

    public Boolean getWeek() {
        return week;
    }

    public void setWeek(Boolean week) {
        this.week = week;
    }

    public int getSerie_id() {
        return serie_id;
    }

    public void setSerie_id(int serie_id) {
        this.serie_id = serie_id;
    }

    public int getSpecialite_id() {
        return specialite_id;
    }

    public void setSpecialite_id(int specialite_id) {
        this.specialite_id = specialite_id;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }
}
