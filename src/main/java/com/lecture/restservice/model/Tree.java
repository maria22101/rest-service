package com.lecture.restservice.model;

public class Tree {
    private int id;
    private String type;
    private String sort;
    private Garden garden;

    public Tree(String type, String sort) {
        this.type = type;
        this.sort = sort;
    }

    public Tree() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }
}
