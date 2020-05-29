package com.lecture.restservice.repository;

import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Tree;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TreeDaoImpl implements TreeDao {
//    private List<Tree> treesStorage = new ArrayList<>();
    private List<Tree> treesStorage = new ArrayList<Tree>() {{
        add(new Tree(1, "Apple", "2.43456, 4.56486",1));
        add(new Tree(2, "Apple", "2.43567, 4.56490", 1));
        add(new Tree(3, "Pear", "3.45678, 4.34567", 2));
        add(new Tree(4, "Plum", "3.45688, 4.35555", 2));
    }};

    @Override
    public Tree create(Tree tree) {
        if (treesStorage.isEmpty()) {
            tree.setId(1);
        } else {
            int lastElementId = treesStorage.get(treesStorage.size() - 1).getId();
            tree.setId(lastElementId + 1);
        }

        treesStorage.add(tree);
        return tree;
    }

    @Override
    public List<Tree> findAll() {
        return treesStorage;
    }

    @Override
    public Tree update(Tree tree) {
        Optional<Tree> treeToUpdate = treesStorage
                .stream()
                .filter(t -> t.getId() == tree.getId())
                .findFirst();

        treeToUpdate.ifPresent(t -> treesStorage.set(treesStorage.indexOf(t), tree));
        return tree;
    }

    @Override
    public void delete(Tree tree) {
        treesStorage.remove(tree);
    }
}
