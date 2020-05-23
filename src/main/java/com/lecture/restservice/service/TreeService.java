package com.lecture.restservice.service;

import com.lecture.restservice.model.Tree;
import com.lecture.restservice.repository.TreeDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeService {
    private TreeDaoImpl treeDaoImpl;

    @Autowired
    public TreeService(TreeDaoImpl treeDaoImpl) {
        this.treeDaoImpl = treeDaoImpl;
    }

    public Tree create(Tree tree) {
        return treeDaoImpl.create(tree);
    }

    public List<Tree> findAll() {
        return treeDaoImpl.findAll();
    }

    public Tree findById(int id) {
        return treeDaoImpl.findById(id);
    }

    public Tree update(Tree tree) {
        return treeDaoImpl.update(tree);
    }

    public void deleteById(int id) {
        treeDaoImpl.deleteById(id);
    }
}
