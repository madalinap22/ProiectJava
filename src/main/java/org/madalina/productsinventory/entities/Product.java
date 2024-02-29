package org.madalina.productsinventory.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String categorie;
    private float pretAchizitie;
    private float pretVanzare;
    private LocalDate BBD;
    private int cantitateAchizitionata;
    private int cantitateVanduta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    @JsonBackReference
    private Supplier supplier;

    public Product()  // Constructor necesar pentru JPA
    {

    }

    public Product(int id, String name, String categorie, float pretAchizitie, float pretVanzare, LocalDate BBD,
                   int cantitateAchizitionata, int cantitateVanduta, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.categorie = categorie;
        this.pretAchizitie=pretAchizitie;
        this.pretVanzare = pretVanzare;
        this.BBD = BBD;
        this.cantitateAchizitionata=cantitateAchizitionata;
        this.cantitateVanduta=cantitateVanduta;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getCategorie() {
        return categorie;
    }

    public float getPretAchizitie() {
        return pretAchizitie;
    }

    public float getPretVanzare() {
        return pretVanzare;
    }

    public LocalDate getBBD() {
        return BBD;
    }

    public int getCantitateAchizitionata()
    {
        return cantitateAchizitionata;
    }
    public int getCantitateVanduta() {
        return cantitateVanduta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setPretAchizitie(float pretAchizitie) {
        this.pretAchizitie = pretAchizitie;
    }

    public void setPretVanzare(float pret) {
        this.pretVanzare= pret;
    }

    public void setBBD(LocalDate BBD) {
        this.BBD = BBD;
    }

    public void setCantitateAchizitionata(int cantitateAchizitionata) {
        this.cantitateAchizitionata = cantitateAchizitionata;
    }

    public void setCantitateVanduta(int cantitateVanduta1)
    {
        this.cantitateVanduta = cantitateVanduta1;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }



}


