package com.lecture.restservice.model;

import java.util.Objects;

public class Tree {
    private int id;
    private String fruit;
    private String geoLocation;
    private int gardenId;

    public Tree(String fruit, String geoLocation, int gardenId) {
        this.fruit = fruit;
        this.geoLocation = geoLocation;
        this.gardenId = gardenId;
    }

    public Tree(int id, String fruit, String geoLocation, int gardenId) {
        this.id = id;
        this.fruit = fruit;
        this.geoLocation = geoLocation;
        this.gardenId = gardenId;
    }

    public Tree() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return geoLocation.equals(tree.geoLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geoLocation);
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id=" + id +
                ", fruit='" + fruit + '\'' +
                ", geoLocation='" + geoLocation + '\'' +
                ", gardenId=" + gardenId +
                '}';
    }
}
