package com.lecture.restservice.repository;

import com.lecture.restservice.model.Garden;

import java.util.List;

public interface GardenDao {
    Garden create(String owner);

    List<Garden> findAll();

    Garden findById(int id);

    Garden save(Garden garden);

    void deleteById(int id);
}
