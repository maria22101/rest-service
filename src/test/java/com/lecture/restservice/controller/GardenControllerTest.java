package com.lecture.restservice.controller;

import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Garden;
import com.lecture.restservice.model.Tree;
import com.lecture.restservice.service.GardenService;
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
public class GardenControllerTest {
    private static int GARDEN_ID = 1;
    private static int TREE_ID = 1;
    private static String GARDEN_OWNER = "Ivan";
    private static String GARDEN_NEW_OWNER = "Mari";
    private static String FRUIT = "Apple";
    private static String GEO_LOCATION = "2.43456, 4.56486";

    @InjectMocks
    GardenController controllerInstance;

    @Mock
    GardenService gardenService;

    @Test
    public void createGardenShouldReturnGardenPassedAsArgument() {
        Garden gardenPassedAsArgument = new Garden(GARDEN_OWNER);
        Garden expectedResult = new Garden(GARDEN_ID, GARDEN_OWNER);

        when(gardenService.create(gardenPassedAsArgument)).thenReturn(expectedResult);
        Garden result = controllerInstance.createGarden(gardenPassedAsArgument);

        assertEquals(expectedResult.getId(), result.getId());
        assertEquals(expectedResult.getOwner(), result.getOwner());
        verify(gardenService, times(1)).create(gardenPassedAsArgument);
    }

    @Test
    public void getAllGardensShouldReturnGardensList() {
        Garden garden = new Garden(GARDEN_ID, GARDEN_OWNER);

        when(gardenService.findAll()).thenReturn(Collections.singletonList(garden));
        List<Garden> result = controllerInstance.getAllGardens();

        assertTrue(result.contains(garden));
        verify(gardenService, times(1)).findAll();
    }

    @Test
    public void getGardenByIdShouldReturnGardenIfExists() {
        Garden garden = new Garden(GARDEN_ID, GARDEN_OWNER);

        when(gardenService.findById(GARDEN_ID)).thenReturn(garden);
        Garden result = controllerInstance.getGardenById(GARDEN_ID);

        assertEquals(garden, result);
        verify(gardenService, times(1)).findById(GARDEN_ID);
    }

    @Test (expected = NoElementByThisIdException.class)
    public void getGardenByIdShouldThrowNoElementByThisIdExceptionIfIdNotPresent() {
        when(gardenService.findById(GARDEN_ID)).thenThrow(NoElementByThisIdException.class);
        controllerInstance.getGardenById(GARDEN_ID);
    }

    @Test
    public void updateGardenShouldUpdateExistingGardenWithGardenPassedAsArgument() {
        Garden gardenPassedAsArgument = new Garden(GARDEN_ID, GARDEN_NEW_OWNER);

        when(gardenService.update(gardenPassedAsArgument)).thenReturn(gardenPassedAsArgument);
        Garden result = controllerInstance.updateGarden(gardenPassedAsArgument);

        assertEquals(gardenPassedAsArgument.getId(), result.getId());
        assertEquals(gardenPassedAsArgument.getOwner(), result.getOwner());
        verify(gardenService, times(1)).update(gardenPassedAsArgument);
    }

    @Test (expected = NoSuchElementForUpdateException.class)
    public void updateGardenShouldThrowNoSuchElementForUpdateExceptionIfGardenNotPresent() {
        Garden gardenPassedAsArgument = new Garden(GARDEN_ID, GARDEN_NEW_OWNER);

        when(gardenService.update(gardenPassedAsArgument)).thenThrow(NoSuchElementForUpdateException.class);
        controllerInstance.updateGarden(gardenPassedAsArgument);
    }

    @Test
    public void deleteGardenByIdShouldDeleteExistingGarden() {
        controllerInstance.deleteGardenById(GARDEN_ID);
        verify(gardenService, times(1)).deleteById(GARDEN_ID);
    }

    @Test (expected = NoElementByThisIdException.class)
    public void deleteGardenByIdShouldThrowNoElementByThisIdExceptionIfIdNotPresent() {
        doThrow(NoElementByThisIdException.class).when(gardenService).deleteById(GARDEN_ID);
        controllerInstance.deleteGardenById(GARDEN_ID);
    }

    @Test
    public void getGardenTreesShouldReturnGardenTrees() {
        Garden garden = new Garden(GARDEN_ID, GARDEN_OWNER);
        List<Tree> trees = new ArrayList<>();
        trees.add(new Tree(TREE_ID, FRUIT, GEO_LOCATION, GARDEN_ID));
        garden.setTrees(trees);

        when(gardenService.getGardenTrees(GARDEN_ID)).thenReturn(trees);
        List<Tree> result = controllerInstance.getGardenTrees(GARDEN_ID);

        assertTrue(result.equals(trees));
        verify(gardenService, times(1)).getGardenTrees(GARDEN_ID);
    }
}