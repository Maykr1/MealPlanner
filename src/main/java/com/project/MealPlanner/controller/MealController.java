package com.project.MealPlanner.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.MealPlanner.entity.Meal;
import com.project.MealPlanner.service.MealService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mealplanner")
public class MealController {
    private final MealService mealService;

    @GetMapping("")
    public ResponseEntity<List<Meal>> getAllMeals() {
        List<Meal> allMeals = this.mealService.getAllMeals();
        return new ResponseEntity<>(allMeals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable("id") Integer id) {
        Meal mealById = this.mealService.getMealById(id);
        return new ResponseEntity<>(mealById, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal createdMeal = this.mealService.createMeal(meal);
        return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable("id") Integer id, @RequestBody Meal meal) {
        Meal updatedMeal = this.mealService.updateMeal(id, meal);
        return new ResponseEntity<>(updatedMeal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable("id") Integer id) {
        this.mealService.deleteMeal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAllMeals() {
        this.mealService.deleteAllMeals();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}