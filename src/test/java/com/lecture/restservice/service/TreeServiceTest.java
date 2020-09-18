package com.lecture.restservice.service;

import com.lecture.restservice.exception.ImpossibleToCreateTreeException;
import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Garden;
import com.lecture.restservice.model.Tree;
import com.lecture.restservice.repository.GardenDaoImpl;
import com.lecture.restservice.repository.TreeDaoImpl;
import org.junit.Before;
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
public class TreeServiceTest {
    private static int ID = 1;
    private static String FRUIT = "Apple";
    private static String NEW_FRUIT = "Apricot";
    private static String GEO_LOCATION = "1.00000, 1.11111";
    private static String GEO_LOCATION_UPDATED = "2.00000, 2.22222";
    private static int GARDEN_ID = 1;
    private static String GARDEN_OWNER = "Ivan";

    @InjectMocks
    private TreeService testingServiceInstance;

    @Mock
    private TreeDaoImpl treeRepository;
    @Mock
    GardenDaoImpl gardenRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void createShouldReturnTreePassedAsArgument() {
        Tree treePassedAsArgument = new Tree(FRUIT, GEO_LOCATION, GARDEN_ID);
        Garden garden = new Garden(ID, GARDEN_OWNER);
        Tree expectedResult = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        when(treeRepository.create(treePassedAsArgument)).thenReturn(expectedResult);
        Tree result = testingServiceInstance.create(treePassedAsArgument);

        assertEquals(expectedResult, result);
        verify(gardenRepository, times(1)).update(garden);
    }

    @Test (expected = ImpossibleToCreateTreeException.class)
    public void createShouldThrowImpossibleToCreateTreeExceptionIfNoGardenExists() {
        Tree treePassedAsArgument = new Tree(FRUIT, GEO_LOCATION, GARDEN_ID);
        testingServiceInstance.create(treePassedAsArgument);
    }

    @Test (expected = ImpossibleToCreateTreeException.class)
    public void createShouldThrowImpossibleToCreateTreeExceptionIfNoGardenIdInArgument() {
        Tree treePassedAsArgument = new Tree();
        testingServiceInstance.create(treePassedAsArgument);
    }

    @Test (expected = ImpossibleToCreateTreeException.class)
    public void createShouldThrowImpossibleToCreateTreeExceptionIfGeoLocationIsBusy() {
        Tree existingTree = new Tree(FRUIT, GEO_LOCATION, GARDEN_ID);
        Garden garden = new Garden(ID, GARDEN_OWNER);
        garden.setTrees(new ArrayList<Tree>() {{add(existingTree);}});

        Tree treePassedAsArgument = new Tree(NEW_FRUIT, GEO_LOCATION, GARDEN_ID);

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        when(treeRepository.findAll()).thenReturn(Collections.singletonList(existingTree));
        testingServiceInstance.create(treePassedAsArgument);
    }

    @Test
    public void findAllShouldReturnTreesList() {
        Tree tree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);

        when(treeRepository.findAll()).thenReturn(Collections.singletonList(tree));
        List<Tree> result = testingServiceInstance.findAll();

        assertTrue(result.contains(tree));
    }

    @Test
    public void findByIdShouldReturnTreeIfExists() {
        Tree tree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);

        when(treeRepository.findAll()).thenReturn(Collections.singletonList(tree));
        Tree result = testingServiceInstance.findById(ID);

        assertEquals(tree, result);
    }

    @Test (expected = NoElementByThisIdException.class)
    public void findByIdShouldThrowNoElementByThisIdExceptionIfIdNotPresent() {
        Tree tree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);

        when(treeRepository.findAll()).thenReturn(Collections.singletonList(tree));
        Tree result = testingServiceInstance.findById(ID + 1);
    }

    @Test
    public void updateShouldUpdateExistingTreeWithTreePassedAsArgument() {
        Tree existingTree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);
        Garden existingGarden = new Garden(ID, GARDEN_OWNER);
        existingGarden.setTrees(new ArrayList<Tree>() {{add(existingTree);}});

        Tree treePassedAsArgument = new Tree(ID, FRUIT, GEO_LOCATION_UPDATED, GARDEN_ID);

        when(treeRepository.findAll()).thenReturn(Collections.singletonList(existingTree));
        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(existingGarden));
        when(treeRepository.update(treePassedAsArgument)).thenReturn(treePassedAsArgument);
        Tree result = testingServiceInstance.update(treePassedAsArgument);

        assertEquals(treePassedAsArgument.getId(), result.getId());
        assertEquals(treePassedAsArgument.getGeoLocation(), result.getGeoLocation());
        verify(gardenRepository, times(1)).update(any(Garden.class));
    }

    @Test (expected = NoSuchElementForUpdateException.class)
    public void updateShouldThrowNoSuchElementForUpdateExceptionIfTreeNotPresent() {
        Tree existingTree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);
        Tree treePassedAsArgument = new Tree(ID + 1, FRUIT, GEO_LOCATION_UPDATED, GARDEN_ID);

        when(treeRepository.findAll()).thenReturn(Collections.singletonList(existingTree));
        testingServiceInstance.update(treePassedAsArgument);
    }

    @Test
    public void deleteByIdShouldDeleteExistingTree() {
        Tree tree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);
        Garden garden = new Garden(ID, GARDEN_OWNER);
        garden.setTrees(new ArrayList<Tree>() {{add(tree);}});

        when(treeRepository.findAll()).thenReturn(Collections.singletonList(tree));
        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        testingServiceInstance.deleteById(ID);

        verify(treeRepository, times(1)).delete(any(Tree.class));
        verify(gardenRepository, times(1)).update(any(Garden.class));
    }

    @Test (expected = NoElementByThisIdException.class)
    public void deleteShouldThrowNoElementByThisIdExceptionIfIdNotPresent() {
        Tree tree = new Tree(ID, FRUIT, GEO_LOCATION, GARDEN_ID);

        when(treeRepository.findAll()).thenReturn(Collections.singletonList(tree));
        testingServiceInstance.deleteById(ID + 1);
    }
}