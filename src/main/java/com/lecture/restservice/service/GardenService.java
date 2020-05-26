package com.lecture.restservice.service;

import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Garden;
import com.lecture.restservice.model.Tree;
import com.lecture.restservice.repository.GardenDaoImpl;
import com.lecture.restservice.repository.TreeDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GardenService {
    private GardenDaoImpl gardenDaoImpl;
    private TreeDaoImpl treeDaoImpl;

    @Autowired
    public GardenService(GardenDaoImpl gardenDaoImpl, TreeDaoImpl treeDaoImpl) {
        this.gardenDaoImpl = gardenDaoImpl;
        this.treeDaoImpl = treeDaoImpl;
    }

    public Garden create(Garden garden) {
        return gardenDaoImpl.create(garden);
    }

    public List<Garden> findAll() {
        return gardenDaoImpl.findAll();
    }

    public Garden findById(int id) {
        return gardenDaoImpl.findAll()
                .stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(NoElementByThisIdException::new);
    }

    public Garden update(Garden garden) {
        try {
            findById(garden.getId());
        } catch (NoElementByThisIdException e) {
            throw new NoSuchElementForUpdateException();
        }
        updateTreesStorageWithNewTrees(garden);
        removeMissingTreesFromTreesStorage(garden);
        return gardenDaoImpl.update(garden);
    }

    public void deleteById(int id) throws NoElementByThisIdException {
        Garden gardenToDelete = findById(id);
        gardenToDelete.getTrees()
                .stream()
                .forEach(t -> treeDaoImpl.delete(t));

        gardenDaoImpl.delete(gardenToDelete);
    }

    public List<Tree> getGardenTrees(int id) {
        return findById(id).getTrees();
    }

    private void updateTreesStorageWithNewTrees(Garden updatingGarden) {
        List<Tree> newTreesOfUpdatingGarden = updatingGarden.getTrees();
        List<Tree> currentTreesOfUpdatingGarden = treeDaoImpl.findAll()
                .stream()
                .filter(t -> t.getGardenId() == updatingGarden.getId())
                .collect(Collectors.toList());

        newTreesOfUpdatingGarden.stream()
                .forEach(tree -> {
                    if (currentTreesOfUpdatingGarden.
                            stream()
                            .anyMatch(t -> t.getId() == tree.getId())) {
                        treeDaoImpl.update(tree);
                    } else {
                        treeDaoImpl.create(tree);
                    }
                });
    }

    private void removeMissingTreesFromTreesStorage(Garden updatingGarden) {
        List<Tree> newTreesOfUpdatingGarden = updatingGarden.getTrees();
        List<Tree> currentTreesOfUpdatingGarden = treeDaoImpl.findAll()
                .stream()
                .filter(t -> t.getGardenId() == updatingGarden.getId())
                .collect(Collectors.toList());

        currentTreesOfUpdatingGarden.stream()
                .forEach(tree -> {
                    Optional<Tree> checkForTreePresence = newTreesOfUpdatingGarden
                            .stream()
                            .filter(t -> t.getId() == tree.getId())
                            .findFirst();
                    if (!checkForTreePresence.isPresent()) {
                        treeDaoImpl.delete(tree);
                    }
                });
    }
}
