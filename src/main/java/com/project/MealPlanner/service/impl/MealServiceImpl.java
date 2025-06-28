package com.project.MealPlanner.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.MealPlanner.entity.Meal;
import com.project.MealPlanner.exception.MealNotFoundException;
import com.project.MealPlanner.repository.MealRepository;
import com.project.MealPlanner.service.MealService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;

    @Override
    public List<Meal> getAllMeals() {
        List<Meal> allMeals = this.mealRepository.findAll();
        log.info("Retrieved all meals");
        
        return allMeals;
    }

    @Override
    public Meal getMealById(Integer id) {
        Meal mealById = this.mealRepository.findById(id)
            .orElseThrow(() -> new MealNotFoundException("Meal not found"));

        return mealById;
    }

    @Override
    public Meal createMeal(Meal meal) {
        return this.mealRepository.save(meal);
    }

    @Override
    public Meal updateMeal(Integer id, Meal meal) {
        Meal mealById = this.mealRepository.findById(id)
            .orElseThrow(() -> new MealNotFoundException("Meal not found"));

        if (meal.getName() != null) {
            mealById.setName(meal.getName());
        }
        if (meal.getType() != null) {
            mealById.setType(meal.getType());
        }
        if (meal.getDescription() != null) {
            mealById.setDescription(meal.getDescription());
        }
        if (meal.getIngredients() != null) {
            mealById.setIngredients(meal.getIngredients());
        }

        return this.mealRepository.save(mealById);
    }

    @Override
    public void deleteMeal(Integer id) {
        Meal mealById = this.mealRepository.findById(id)
            .orElseThrow(() -> new MealNotFoundException("Meal not found"));
        
        this.mealRepository.delete(mealById);
    }

    @Override
    public void deleteAllMeals() {
        this.mealRepository.deleteAll();
    }
}
