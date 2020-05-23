package com.lecture.restservice.repository;

import com.lecture.restservice.exception.NoElementByThisIndexException;
import com.lecture.restservice.model.Garden;

import java.util.ArrayList;
import java.util.List;

public class GardenDaoImpl implements GardenDao{
    private List<Garden> gardensStorage = new ArrayList<>();

    @Override
    public Garden create(Garden garden) {
        int gardenId;
        if (gardensStorage.isEmpty()) {
            gardenId = 1;
        } else {
            gardenId = gardensStorage.size() + 1;
        }
        garden.setId(gardenId);
        gardensStorage.add(garden);
        return garden;
    }

    @Override
    public List<Garden> findAll() {
        return gardensStorage;
    }

    @Override
    public Garden findById(int id) {
        return gardensStorage.stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(NoElementByThisIndexException::new);
    }

    @Override
    public Garden update(Garden garden) {
        if(gardensStorage.contains(garden)) {
            gardensStorage.set(gardensStorage.indexOf(garden), garden);
            return garden;
        }else {
            return create(garden);
        }
    }

    @Override
    public void deleteById(int id) {
        Garden gardenToDelete = gardensStorage.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(NoElementByThisIndexException::new);

        gardensStorage.remove(gardenToDelete);
    }
}
