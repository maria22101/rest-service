package com.lecture.restservice.service;

import com.lecture.restservice.model.Garden;
import com.lecture.restservice.repository.GardenDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GardenService {
    private GardenDaoImpl gardenDaoImpl;

    @Autowired
    public GardenService(GardenDaoImpl gardenDaoImpl) {
        this.gardenDaoImpl = gardenDaoImpl;
    }

    public Garden create(Garden garden) {
        return gardenDaoImpl.create(garden);
    }

    public List<Garden> findAll() {
        return gardenDaoImpl.findAll();
    }

    public Garden findById(int id) {
        return gardenDaoImpl.findById(id);
    }

    public Garden update(Garden garden) {
        return gardenDaoImpl.update(garden);
    }

    public void deleteById(int id) {
        gardenDaoImpl.deleteById(id);
    }
}
