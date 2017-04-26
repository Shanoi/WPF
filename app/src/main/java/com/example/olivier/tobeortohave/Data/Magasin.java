package com.example.olivier.tobeortohave.Data;

/**
 * Created by Olivier on 26/04/2017.
 */

public class Magasin {

    private String nom;
    private String adresse;
    private String postalCode;
    private long latitude;
    private long longitude;
    private String telephone;
    private String mail;
    private String pageWeb;

    public Magasin(String nom, String adresse, String postalCode, long latitude, long longitude, String telephone, String mail, String pageWeb) {
        this.nom = nom;
        this.adresse = adresse;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.telephone = telephone;
        this.mail = mail;
        this.pageWeb = pageWeb;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMail() {
        return mail;
    }

    public String getPageWeb() {
        return pageWeb;
    }
}
