package com.project.MealPlanner.service;

import java.util.List;

import com.project.MealPlanner.entity.Meal;

public interface MealService {
    public List<Meal> getAllMeals();
    public Meal getMealById(Integer id);
    public Meal createMeal(Meal meal);
    public Meal updateMeal(Integer id, Meal meal);
    public void deleteMeal(Integer id);
    public void deleteAllMeals();
}
