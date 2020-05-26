package com.lecture.restservice.service;

import com.lecture.restservice.exception.ImpossibleToCreateTreeException;
import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Garden;
import com.lecture.restservice.model.Tree;
import com.lecture.restservice.repository.GardenDaoImpl;
import com.lecture.restservice.repository.TreeDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeService {
    private TreeDaoImpl treeDaoImpl;
    private GardenDaoImpl gardenDaoImpl;

    @Autowired
    public TreeService(TreeDaoImpl treeDaoImpl, GardenDaoImpl gardenDaoImpl) {
        this.treeDaoImpl = treeDaoImpl;
        this.gardenDaoImpl = gardenDaoImpl;
    }

    public Tree create(Tree tree) {
        if (gardenDaoImpl.findAll().isEmpty()
                || tree.getGardenId() == 0
                || treeDaoImpl.findAll().contains(tree)) {
            throw new ImpossibleToCreateTreeException();
        }

        Garden gardenForTree = gardenDaoImpl.findAll()
                .stream()
                .filter(g -> g.getId() == tree.getGardenId())
                .findFirst()
                .orElseThrow(ImpossibleToCreateTreeException::new);

        Tree newTree = treeDaoImpl.create(tree);
        gardenForTree.getTrees().add(tree);
        gardenDaoImpl.update(gardenForTree);

        return newTree;
    }

    public List<Tree> findAll() {
        return treeDaoImpl.findAll();
    }

    public Tree findById(int id) {
        return treeDaoImpl.findAll()
                .stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(NoElementByThisIdException::new);
    }

    public Tree update(Tree tree) {

        try {
            findById(tree.getId());
        } catch (NoElementByThisIdException e) {
            throw new NoSuchElementForUpdateException();
        }

        Garden gardenWithTreeToUpdate = gardenDaoImpl.findAll()
                .stream()
                .filter(garden -> garden.getTrees()
                        .stream()
                        .anyMatch(t -> t.getId() == tree.getId()))
                .findFirst()
                .orElseThrow(NoSuchElementForUpdateException::new);

        Tree currentTree = findById(tree.getId());
        int currentTreeIndex = gardenWithTreeToUpdate.getTrees().indexOf(currentTree);

        Tree updatedTree = treeDaoImpl.update(tree);

        gardenWithTreeToUpdate.getTrees().set(currentTreeIndex, updatedTree);

        gardenDaoImpl.update(gardenWithTreeToUpdate);

        return updatedTree;
    }

    public void deleteById(int id) throws NoElementByThisIdException {

        Tree treeToDelete = findById(id);

        Garden gardenWithTreeToDelete = gardenDaoImpl.findAll()
                .stream()
                .filter(garden -> garden.getTrees().stream()
                        .anyMatch(t -> t.getId() == id))
                .findFirst()
                .orElseThrow(NoElementByThisIdException::new);

        treeDaoImpl.delete(treeToDelete);
        gardenWithTreeToDelete.getTrees().remove(treeToDelete);
        gardenDaoImpl.update(gardenWithTreeToDelete);
    }
}
