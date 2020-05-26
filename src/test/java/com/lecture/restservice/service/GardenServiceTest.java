package com.lecture.restservice.service;

import com.lecture.restservice.exception.NoElementByThisIdException;
import com.lecture.restservice.exception.NoSuchElementForUpdateException;
import com.lecture.restservice.model.Garden;
import com.lecture.restservice.model.Tree;
import com.lecture.restservice.repository.GardenDaoImpl;
import com.lecture.restservice.repository.TreeDaoImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GardenServiceTest {
    private static int ID = 1;
    private static String GARDEN_OWNER = "Ivan";
    private static String GARDEN_NEW_OWNER = "Mari";

    @InjectMocks
    GardenService testingServiceInstance;

    @Mock
    GardenDaoImpl gardenRepository;
    @Mock
    TreeDaoImpl treeRepository;

    @Test
    public void createShouldReturnGardenPassedAsArgument() {
        Garden gardenPassedAsArgument = new Garden(GARDEN_OWNER);
        Garden expectedResult = new Garden(ID, GARDEN_OWNER);

        when(gardenRepository.create(gardenPassedAsArgument)).thenReturn(expectedResult);
        Garden result = testingServiceInstance.create(gardenPassedAsArgument);

        assertEquals(expectedResult.getId(), result.getId());
        assertEquals(expectedResult.getOwner(), result.getOwner());
    }

    @Test
    public void findAllShouldReturnGardensList() {
        Garden garden = new Garden(ID, GARDEN_OWNER);

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        List<Garden> result = testingServiceInstance.findAll();

        assertTrue(result.contains(garden));
    }

    @Test
    public void findByIdShouldReturnGardenIfExists() {
        Garden garden = new Garden(ID, GARDEN_OWNER);

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        Garden result = testingServiceInstance.findById(ID);

        assertEquals(garden, result);
    }

    @Test (expected = NoElementByThisIdException.class)
    public void findByIdShouldThrowNoElementByThisIdExceptionIfIdNotPresent() {
        Garden garden = new Garden(ID, GARDEN_OWNER);

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        Garden result = testingServiceInstance.findById(ID + 1);
    }

    @Test
    public void updateShouldUpdateExistingGardenWithGardenPassedAsArgument() {
        Garden existingGarden = new Garden(ID, GARDEN_OWNER);
        Garden gardenPassedAsArgument = new Garden(ID, GARDEN_NEW_OWNER);

        List<Tree> trees = new ArrayList<>();
        trees.add(new Tree(1, "Apple", "2.43456, 4.56486",1));

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(existingGarden));
        when(treeRepository.findAll()).thenReturn(trees);
        when(gardenRepository.update(gardenPassedAsArgument)).thenReturn(gardenPassedAsArgument);
        Garden result = testingServiceInstance.update(gardenPassedAsArgument);

        assertEquals(gardenPassedAsArgument.getId(), result.getId());
        assertEquals(gardenPassedAsArgument.getOwner(), result.getOwner());
    }

    @Test (expected = NoSuchElementForUpdateException.class)
    public void updateShouldThrowNoSuchElementForUpdateExceptionIfGardenNotPresent() {
        Garden existingGarden = new Garden(ID, GARDEN_OWNER);
        Garden gardenPassedAsArgument = new Garden(ID + 1, GARDEN_NEW_OWNER);

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(existingGarden));
        testingServiceInstance.update(gardenPassedAsArgument);
    }

    @Test
    public void deleteByIdShouldDeleteExistingGarden() {
        Garden garden = new Garden(ID, GARDEN_OWNER);
        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        testingServiceInstance.deleteById(ID);

        verify(gardenRepository, times(1)).delete(garden);
    }

    @Test (expected = NoElementByThisIdException.class)
    public void deleteShouldThrowNoElementByThisIdExceptionIfIdNotPresent() {
        Garden garden = new Garden(ID, GARDEN_OWNER);

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        testingServiceInstance.deleteById(ID + 1);
    }

    @Test
    public void getGardenTreesShouldReturnGardenTrees() {
        Garden garden = new Garden(ID, GARDEN_OWNER);
        List<Tree> trees = new ArrayList<>();
        trees.add(new Tree(1, "Apple", "2.43456, 4.56486",1));
        trees.add(new Tree(2, "Apricot", "2.43567, 4.56490", 1));
        garden.setTrees(trees);

        when(gardenRepository.findAll()).thenReturn(Collections.singletonList(garden));
        List<Tree> result = testingServiceInstance.getGardenTrees(ID);

        assertTrue(result.equals(trees));
    }
}