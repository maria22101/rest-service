package com.lecture.restservice.controller;

import com.lecture.restservice.exception.ImpossibleToCreateTreeException;
import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Garden;
import com.lecture.restservice.model.Tree;
import com.lecture.restservice.repository.TreeDaoImpl;
import com.lecture.restservice.service.TreeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TreeControllerTest {
    private static int ID = 1;
    private static String FRUIT = "Apple";
    private static String GEO_LOCATION = "1.00000, 1.11111";
    private static String GEO_LOCATION_UPDATED = "2.00000, 2.22222";
    private static int GARDEN_ID = 1;

    @InjectMocks
    private TreeController controllerInstance;

    @Mock
    private TreeService treeService;

    @Test
    public void createTreeShouldReturnTreePassedAsArgument() {
        Tree treePassedAsArgument = new Tree(FRUIT, GEO_LOCATION, GARDEN_ID);
        Tree expectedResult = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);

        when(treeService.create(treePassedAsArgument)).thenReturn(expectedResult);
        Tree result = controllerInstance.createTree(treePassedAsArgument);

        assertEquals(expectedResult.getId(), result.getId());
        verify(treeService, times(1)).create(treePassedAsArgument);
    }

    @Test (expected = ImpossibleToCreateTreeException.class)
    public void createTreeShouldThrowImpossibleToCreateTreeExceptionIfNoGardenExists() {
        Tree treePassedAsArgument = new Tree(FRUIT, GEO_LOCATION, GARDEN_ID);

        when(treeService.create(treePassedAsArgument)).thenThrow(ImpossibleToCreateTreeException.class);
        controllerInstance.createTree(treePassedAsArgument);
    }

    @Test (expected = ImpossibleToCreateTreeException.class)
    public void createTreeShouldThrowImpossibleToCreateTreeExceptionIfNoGardenIdInArgument() {
        Tree treePassedAsArgument = new Tree();

        when(treeService.create(treePassedAsArgument)).thenThrow(ImpossibleToCreateTreeException.class);
        controllerInstance.createTree(treePassedAsArgument);
    }

    @Test
    public void getAllTreesShouldReturnTreesList() {
        Tree tree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);

        when(treeService.findAll()).thenReturn(Collections.singletonList(tree));
        List<Tree> result = controllerInstance.getAllTrees();

        assertTrue(result.contains(tree));
        verify(treeService, times(1)).findAll();
    }

    @Test
    public void getTreeByIdShouldReturnTreeIfExists() {
        Tree tree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);

        when(treeService.findById(ID)).thenReturn(tree);
        Tree result = controllerInstance.getTreeById(ID);

        assertEquals(tree, result);
        verify(treeService, times(1)).findById(ID);
    }

    @Test (expected = NoElementByThisIdException.class)
    public void getTreeByIdShouldThrowNoElementByThisIdExceptionIfIdNotPresent() {
        when(treeService.findById(ID)).thenThrow(NoElementByThisIdException.class);
        controllerInstance.getTreeById(ID);
    }

    @Test
    public void updateTreeShouldUpdateExistingTreeWithTreePassedAsArgument() {
        Tree treePassedAsArgument = new Tree(ID, FRUIT, GEO_LOCATION_UPDATED, GARDEN_ID);

        when(treeService.update(treePassedAsArgument)).thenReturn(treePassedAsArgument);
        Tree result = controllerInstance.updateTree(treePassedAsArgument);

        assertEquals(treePassedAsArgument.getId(), result.getId());
        assertEquals(treePassedAsArgument.getGeoLocation(), result.getGeoLocation());
        verify(treeService, times(1)).update(treePassedAsArgument);
    }

    @Test (expected = NoSuchElementForUpdateException.class)
    public void updateShouldThrowNoSuchElementForUpdateExceptionIfTreeNotPresent() {
        Tree treePassedAsArgument = new Tree(ID, FRUIT, GEO_LOCATION_UPDATED, GARDEN_ID);

        when(treeService.update(treePassedAsArgument)).thenThrow(NoSuchElementForUpdateException.class);
        controllerInstance.updateTree(treePassedAsArgument);
    }

    @Test
    public void deleteTreeByIdShouldDeleteExistingTree() {
        controllerInstance.deleteTreeById(ID);
        verify(treeService, times(1)).deleteById(ID);
    }

    @Test (expected = NoElementByThisIdException.class)
    public void deleteTreeByIdShouldThrowNoElementByThisIdExceptionIfIdNotPresent() {
        doThrow(NoElementByThisIdException.class).when(treeService).deleteById(ID);
        controllerInstance.deleteTreeById(ID);
    }
}