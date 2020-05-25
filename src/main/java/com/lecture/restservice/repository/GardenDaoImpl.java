package com.lecture.restservice.repository;

import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Garden;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GardenDaoImpl implements GardenDao{
    private List<Garden> gardensStorage = new ArrayList<>();
//    private List<Garden> gardensStorage = new ArrayList<Garden>(){{
//        add(new Garden(1,"Ivan", new ArrayList<Tree>(){{
//            add(new Tree(1, "Apple", "2.43456, 4.56486",1));
//            add(new Tree(2, "Apple", "2.43567, 4.56490", 1));
//        }}));
//        add(new Garden(2, "Mykola", new ArrayList<Tree>(){{
//            add(new Tree(3, "Pear", "3.45678, 4.34567", 2));
//            add(new Tree(4, "Plum", "3.45688, 4.35555", 2));
//        }}));
//    }};

    @Override
    public Garden create(Garden garden) {
        if (gardensStorage.isEmpty()) {
            garden.setId(1);
        }else {
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
    public Garden findById(int id) {
        return gardensStorage.stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(NoElementByThisIdException::new);
    }

    @Override
    public Garden update(Garden garden) {
        if(gardensStorage.contains(garden)) {
            gardensStorage.set(gardensStorage.indexOf(garden), garden);
            return garden;
        }else {
            throw new NoSuchElementForUpdateException();
        }
    }

    @Override
    public void deleteById(int id) {
        Garden gardenToDelete = gardensStorage.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(NoElementByThisIdException::new);

        gardensStorage.remove(gardenToDelete);
    }
}
