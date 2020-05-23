package com.lecture.restservice.repository;

import com.lecture.restservice.model.Garden;

import java.util.List;

public interface GardenDao {
    Garden create(Garden garden);

    List<Garden> findAll();

    Garden findById(int id);

    Garden update(Garden garden);

    void deleteById(int id);
}
