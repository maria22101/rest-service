package com.lecture.restservice.repository;

import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Garden;
import com.lecture.restservice.model.Tree;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GardenDaoImpl implements GardenDao {
    private List<Garden> gardensStorage = new ArrayList<>();

    @Override
    public Garden create(Garden garden) {
        if (gardensStorage.isEmpty()) {
            garden.setId(1);
        } else {
            int lastElementId = gardensStorage.get(gardensStorage.size() - 1).getId();
            garden.setId(lastElementId + 1);
        }

        gardensStorage.add(garden);
        return garden;
    }

    @Override
    public List<Garden> findAll() {
        return gardensStorage;
    }

    @Override
    public Garden update(Garden garden) {
        Optional<Garden> gardenToUpdate = gardensStorage
                .stream()
                .filter(g -> g.getId() == garden.getId())
                .findFirst();

        gardenToUpdate.ifPresent(g -> gardensStorage.set(gardensStorage.indexOf(g), garden));
        return garden;
    }

    @Override
    public void delete(Garden garden) {
        gardensStorage.remove(garden);
    }
}
