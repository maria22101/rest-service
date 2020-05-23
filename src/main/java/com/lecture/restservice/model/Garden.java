package com.lecture.restservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Garden {
    private int id;
    private String owner;
    private List<Tree> trees = new ArrayList<>();

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

    public List<Tree> getTrees() {
        return trees;
    }

    public void setTrees(List<Tree> trees) {
        this.trees = trees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Garden garden = (Garden) o;
        return id == garden.id &&
                owner.equals(garden.owner) &&
                trees.equals(garden.trees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, trees);
    }
}
