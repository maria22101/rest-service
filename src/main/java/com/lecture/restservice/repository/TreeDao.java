package com.lecture.restservice.repository;

import com.lecture.restservice.model.Tree;

import java.util.List;

public interface TreeDao {
    Tree create(Tree tree);

    List<Tree> findAll();

    Tree update(Tree tree);

    void delete(Tree tree);
}
