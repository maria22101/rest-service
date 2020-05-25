package com.lecture.restservice.controller;

import com.lecture.restservice.model.Tree;
import com.lecture.restservice.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trees")
public class TreeController {
    private TreeService treeService;

    @Autowired
    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @PostMapping
    public Tree createTree(@RequestBody Tree tree) {
        return treeService.create(tree);
    }

    @GetMapping
    public List<Tree> getAllTrees() {
        return treeService.findAll();
    }

    @GetMapping("/{id}")
    public Tree getTreeById(@PathVariable int id) {
        return treeService.findById(id);
    }

    @PutMapping
    public Tree updateTree(@RequestBody Tree tree) {
        return treeService.update(tree);
    }

    @DeleteMapping("/{id}")
    public void deleteTreeById(@PathVariable int id) {
        treeService.deleteById(id);
    }
}
