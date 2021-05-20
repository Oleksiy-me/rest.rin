package com.example.rin.demo.database.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Rent_Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    int price;
    String location;
    String description;

    int category;
    int oblasty;
    int view;
    int active;

    public Product() {
    }

    public Product(String name, int price, String location, String description, int category, int oblasty, int active) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.description = description;
        this.category = category;
        this.oblasty = oblasty;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getOblasty() {
        return oblasty;
    }

    public void setOblasty(int oblasty) {
        this.oblasty = oblasty;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
