package com.lecture.restservice.model;

import java.util.Set;

public class Garden {
    private int id;
    private String owner;
    private Set<Tree> trees;

    public Garden(String owner) {
        this.owner = owner;
    }

    public Garden() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<Tree> getTrees() {
        return trees;
    }

    public void setTrees(Set<Tree> trees) {
        this.trees = trees;
    }
}
