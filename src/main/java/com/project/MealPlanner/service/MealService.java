package com.project.MealPlanner.service;

import java.util.List;

import com.project.MealPlanner.dto.MealRequest;
import com.project.MealPlanner.dto.MealResponse;

public interface MealService {
    public List<MealResponse> getAllMeals();
    public MealResponse getMealById(Integer id);
    public MealResponse createMeal(MealRequest mealRequest);
    public MealResponse updateMeal(Integer id, MealRequest mealRequest);
    public void deleteMeal(Integer id);
    public void deleteAllMeals();
}
