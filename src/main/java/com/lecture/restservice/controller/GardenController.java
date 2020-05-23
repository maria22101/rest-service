package com.lecture.restservice.controller;

import com.lecture.restservice.model.Garden;
import com.lecture.restservice.model.Tree;
import com.lecture.restservice.service.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gardens")
public class GardenController {
    private GardenService gardenService;

    @Autowired
    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }

    @PostMapping
    public Garden createGarden(@RequestBody Garden garden) {
        return gardenService.create(garden);
    }

    @GetMapping
    public List<Garden> getAllGardens() {
        return gardenService.findAll();
    }

    @GetMapping("/{id}")
    public Garden getGardenById(@PathVariable int id) {
        return gardenService.findById(id);
    }

    @PutMapping
    public Garden updateGardenById(@RequestBody Garden garden) {
        return gardenService.update(garden);
    }

    @DeleteMapping("/{id}")
    public void deleteGardenById(@PathVariable int id) {
        gardenService.deleteById(id);
    }
}
