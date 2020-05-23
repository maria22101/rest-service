package com.lecture.restservice.repository;

import com.lecture.restservice.exception.NoElementByThisIndexException;
import com.lecture.restservice.model.Tree;

import java.util.ArrayList;
import java.util.List;

public class TreeDaoImpl implements TreeDao {
    private List<Tree> treesStorage = new ArrayList<>();

    @Override
    public Tree create(Tree tree) {
        int treeId;
        if (treesStorage.isEmpty()) {
            treeId = 1;
        } else {
            treeId = treesStorage.size() + 1;
        }
        tree.setId(treeId);
        treesStorage.add(tree);
        return tree;
    }

    @Override
    public List<Tree> findAll() {
        return treesStorage;
    }

    @Override
    public Tree findById(int id) {
        return treesStorage.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(NoElementByThisIndexException::new);
    }

    @Override
    public Tree update(Tree tree) {
        if(treesStorage.contains(tree)) {
            treesStorage.set(treesStorage.indexOf(tree), tree);
            return tree;
        }else {
            return create(tree);
        }
    }

    @Override
    public void deleteById(int id) {
        Tree treeToDelete = treesStorage.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(NoElementByThisIndexException::new);

        treesStorage.remove(treeToDelete);
    }
}
