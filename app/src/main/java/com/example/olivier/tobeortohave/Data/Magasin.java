package com.example.olivier.tobeortohave.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Olivier on 26/04/2017.
 */

public class Magasin implements Parcelable, ClusterItem {

    private int id;
    private String nom;
    private String adresse;
    private String ville;
    private String postalCode;
    private long latitude;
    private long longitude;
    private String telephone;
    private String mail;
    private String pageWeb;
    private int selected;

    public Magasin(int id, String nom, String adresse, String ville, String postalCode, long latitude, long longitude, String telephone, String mail, String pageWeb, int selected) {

        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.telephone = telephone;
        this.mail = mail;
        this.pageWeb = pageWeb;
        this.selected = selected;

    }

    protected Magasin(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        adresse = in.readString();
        ville = in.readString();
        postalCode = in.readString();
        latitude = in.readLong();
        longitude = in.readLong();
        telephone = in.readString();
        mail = in.readString();
        pageWeb = in.readString();
        selected = in.readInt();
    }

    public static final Creator<Magasin> CREATOR = new Creator<Magasin>() {
        @Override
        public Magasin createFromParcel(Parcel in) {
            return new Magasin(in);
        }

        @Override
        public Magasin[] newArray(int size) {
            return new Magasin[size];
        }
    };

    public int getId() {
        return id;
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

    public String getVille() {
        return ville;
    }

    public boolean isSelected() {

        return selected > 0;

    }

    public void setSelected(boolean sel){

        if (sel){

            selected = 1;

        }else {

            selected = 0;

        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(adresse);
        dest.writeString(ville);
        dest.writeString(postalCode);
        dest.writeLong(latitude);
        dest.writeLong(longitude);
        dest.writeString(telephone);
        dest.writeString(mail);
        dest.writeString(pageWeb);
        dest.writeInt(selected);
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }
}
