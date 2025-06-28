package com.project.MealPlanner.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.project.MealPlanner.entity.Meal;
import com.project.MealPlanner.exception.MealNotFoundException;
import com.project.MealPlanner.repository.MealRepository;
import com.project.MealPlanner.service.impl.MealServiceImpl;

@ExtendWith(SpringExtension.class)
public class MealServiceTests {
    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealServiceImpl mealServiceImpl;

    List<Meal> allMeals = new ArrayList<>();
    Meal response;
    Meal mockedMeal1;
    Meal mockedMeal2;

    @BeforeEach
    public void setup() {
        mockedMeal1 = Meal.builder()
            .id(1)
            .name("Banana")
            .type("Lunch")
            .description("It's a banana obviously")
            .ingredients("Banana")
            .build();

        mockedMeal2 = Meal.builder()
            .id(2)
            .name("Apple")
            .type("Lunch")
            .description("It's a apple obviously")
            .ingredients("Apple")
            .build();

        allMeals.add(mockedMeal1);
        allMeals.add(mockedMeal2);
    }

    @Test
    public void getAllMeals() {
        when(mealRepository.findAll()).thenReturn(allMeals);

        List<Meal> response = mealServiceImpl.getAllMeals();

        assertEquals(allMeals, response);
        assertEquals(mockedMeal1.getName(), response.get(0).getName());
        assertEquals(mockedMeal2.getName(), response.get(1).getName());
        verify(mealRepository).findAll();
    }

    @Test
    public void getMealById() {
        when(mealRepository.findById(anyInt())).thenReturn(Optional.of(mockedMeal1));

        response = mealServiceImpl.getMealById(1);

        assertEquals(response, mockedMeal1);
        verify(mealRepository).findById(1);
    }

    @Test
    public void getMealByIdNotFound() {
        when(mealRepository.findById(anyInt())).thenReturn(Optional.empty());

        MealNotFoundException e = assertThrows(
            MealNotFoundException.class, 
            () -> response = mealServiceImpl.getMealById(1)
        );

        assertEquals(e.getMessage(), "Meal not found");
        verify(mealRepository).findById(anyInt());
    }

    @Test
    public void createMeal() {
        when(mealRepository.save(any(Meal.class))).thenReturn(mockedMeal1);

        response = mealServiceImpl.createMeal(mockedMeal1);

        assertEquals(response, mockedMeal1);
        verify(mealRepository).save(any(Meal.class));
    }

    @Test
    public void updateMeal() {
        when(mealRepository.findById(anyInt())).thenReturn(Optional.of(mockedMeal1));
        when(mealRepository.save(any(Meal.class))).thenReturn(mockedMeal2);

        response = mealServiceImpl.updateMeal(mockedMeal1.getId(), mockedMeal2);

        assertEquals(response.getName(), mockedMeal2.getName());
        verify(mealRepository).findById(anyInt());
        verify(mealRepository).save(any(Meal.class));
    }

    @Test
    public void updateMealNotFound() {
        when(mealRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(mealRepository.save(any(Meal.class))).thenReturn(mockedMeal2);

        MealNotFoundException e = assertThrows(
            MealNotFoundException.class, 
            () -> response = mealServiceImpl.getMealById(1)
        );

        assertEquals(e.getMessage(), "Meal not found");
        verify(mealRepository).findById(anyInt());
        verify(mealRepository, never()).save(any(Meal.class));
    }

    @Test
    public void deleteMeal() {
        when(mealRepository.findById(anyInt())).thenReturn(Optional.of(mockedMeal1));
        
        mealServiceImpl.deleteMeal(1);
        
        verify(mealRepository).findById(anyInt());
        verify(mealRepository).deleteById(anyInt());
    }

    @Test
    public void deleteMealNotFound() {
        when(mealRepository.findById(anyInt())).thenReturn(Optional.empty());

        MealNotFoundException e = assertThrows(
            MealNotFoundException.class, 
            () -> mealServiceImpl.deleteMeal(1)
        );
        
        assertNotNull(e);
        verify(mealRepository).findById(anyInt());
        verify(mealRepository, never()).deleteById(anyInt());
    }

    @Test
    public void deleteAllMeals() {
        mealServiceImpl.deleteAllMeals();

        verify(mealRepository).deleteAll();
    }
}
