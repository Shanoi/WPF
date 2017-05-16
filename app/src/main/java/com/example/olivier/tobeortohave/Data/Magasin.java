package com.example.olivier.tobeortohave.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Olivier on 26/04/2017.
 */

public class Magasin implements Parcelable {

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

    public Magasin(int id, String nom, String adresse, String ville, String postalCode, long latitude, long longitude, String telephone, String mail, String pageWeb) {

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
    }

    protected Magasin(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        adresse = in.readString();
        postalCode = in.readString();
        latitude = in.readLong();
        longitude = in.readLong();
        telephone = in.readString();
        mail = in.readString();
        pageWeb = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(adresse);
        dest.writeString(postalCode);
        dest.writeLong(latitude);
        dest.writeLong(longitude);
        dest.writeString(telephone);
        dest.writeString(mail);
        dest.writeString(pageWeb);
    }
}
