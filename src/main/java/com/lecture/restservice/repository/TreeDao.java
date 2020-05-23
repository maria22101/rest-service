package com.lecture.restservice.repository;

import com.lecture.restservice.model.Tree;

import java.util.List;

public interface TreeDao {
    Tree create(String type, String sort);

    List<Tree> findAll();

    Tree findById(int id);

    Tree save(Tree tree);

    void deleteById(int id);
}
