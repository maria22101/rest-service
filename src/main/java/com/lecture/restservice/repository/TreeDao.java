package com.lecture.restservice.repository;

import com.lecture.restservice.model.Tree;

import java.util.List;

public interface TreeDao {
    Tree create(Tree tree);

    List<Tree> findAll();

    Tree findById(int id);

    Tree update(Tree tree);

    void deleteById(int id);
}
