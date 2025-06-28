package com.project.MealPlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.MealPlanner.entity.Meal;

public interface MealRepository extends JpaRepository<Meal, Integer> {
    
}
