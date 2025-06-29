package com.project.MealPlanner.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.MealPlanner.dto.MealRequest;
import com.project.MealPlanner.dto.MealResponse;
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
    public List<MealResponse> getAllMeals() {
        List<Meal> allMeals = this.mealRepository.findAll();
        log.info("Retrieved all meals");
        
        return allMeals
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MealResponse getMealById(Integer id) {
        Meal mealById = this.mealRepository.findById(id)
            .orElseThrow(() -> new MealNotFoundException("Meal not found"));

        return mapToResponse(mealById);
    }

    @Override
    public MealResponse createMeal(MealRequest mealRequest) {
        Meal meal = Meal.builder()
            .name(mealRequest.getName())
            .build();


        Meal createdMeal = this.mealRepository.save(meal);
        
        return mapToResponse(createdMeal);
    }

    @Override
    public MealResponse updateMeal(Integer id, MealRequest mealRequest) {
        Meal mealById = this.mealRepository.findById(id)
            .orElseThrow(() -> new MealNotFoundException("Meal not found"));

        if (mealRequest.getName() != null) {
            mealById.setName(mealRequest.getName());
        }

        Meal updatedMeal = this.mealRepository.save(mealById);

        return mapToResponse(updatedMeal);
    }

    @Override
    public void deleteMeal(Integer id) {
        Meal mealById = this.mealRepository.findById(id)
            .orElseThrow(() -> new MealNotFoundException("Meal not found"));
        
        this.mealRepository.deleteById(mealById.getId());
    }

    @Override
    public void deleteAllMeals() {
        this.mealRepository.deleteAll();
    }

    public MealResponse mapToResponse(Meal meal) {
        MealResponse response = MealResponse.builder()
            .id(meal.getId())
            .name(meal.getName())
            .build();
        
        return response;
    }
}
