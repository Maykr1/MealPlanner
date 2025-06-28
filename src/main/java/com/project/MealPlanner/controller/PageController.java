package com.project.MealPlanner.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.MealPlanner.entity.Meal;
import com.project.MealPlanner.service.MealService;


@Controller
public class PageController {
    @Autowired
    private MealService mealService;

    @GetMapping("/")
    public String home(Model model) {
        // List<Meal> meals = this.mealService.getAllMeals();
        List<String> days = List.of("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        List<String> mealTimes = List.of("Breakfast", "Lunch", "Dinner", "Snack");

        Map<String, Meal> mealMap = new HashMap<>();
        mealMap.put("Monday_Breakfast", new Meal(1, "Banana", "Breakfast", "Delicious", "no"));

        model.addAttribute("mealList", mealMap);
        model.addAttribute("daysList", days);
        model.addAttribute("mealTimesList", mealTimes);

        return "index";
    }
}
