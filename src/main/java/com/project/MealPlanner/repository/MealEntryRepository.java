package com.project.MealPlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.MealPlanner.entity.MealEntry;

public interface MealEntryRepository extends JpaRepository<MealEntry, Integer> {
    
}
