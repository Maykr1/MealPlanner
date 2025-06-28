package com.project.MealPlanner.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.MealPlanner.entity.Meal;
import com.project.MealPlanner.exception.MealNotFoundException;
import com.project.MealPlanner.service.MealService;

@WebMvcTest(MealController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MealControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MealService mealService;

    List<Meal> allMeals = new ArrayList<>();
    ResponseEntity<Meal> response;
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
    public void getAllMeals() throws Exception {
        when(mealService.getAllMeals()).thenReturn(allMeals);

        mockMvc.perform(get("/mealplanner"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(allMeals)));
    }

    @Test
    public void getMealById() throws Exception {
        when(mealService.getMealById(anyInt())).thenReturn(mockedMeal1);

        mockMvc.perform(get("/mealplanner/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(mockedMeal1)));
    }
    
    @Test
    public void getMealByIdNotFound() throws Exception {
        when(mealService.getMealById(anyInt()))
            .thenThrow(new MealNotFoundException("Meal not found"));

        mockMvc.perform(get("/mealplanner/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void createMeal() throws Exception{
        when(mealService.createMeal(any(Meal.class))).thenReturn(mockedMeal1);

        mockMvc.perform(post("/mealplanner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedMeal1)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(mockedMeal1)));
    }

    @Test
    public void updateMeal() throws Exception {
        when(mealService.updateMeal(anyInt(), any(Meal.class))).thenReturn(mockedMeal2);

        mockMvc.perform(put("/mealplanner/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedMeal2)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(mockedMeal2)));
    }

    @Test
    public void updateMealNotFound() throws Exception {
        when(mealService.updateMeal(anyInt(), any(Meal.class)))
            .thenThrow(new MealNotFoundException("Meal not found"));
        
        mockMvc.perform(put("/mealplanner/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(mockedMeal2)))
        .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMeal() throws Exception {
        mockMvc.perform(delete("/mealplanner/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void deleteMealNotFound() throws Exception {
        doThrow(new MealNotFoundException("Meal not found"))
            .when(mealService).deleteMeal(anyInt());

        mockMvc.perform(delete("/mealplanner/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAllMeals() throws Exception {
        mockMvc.perform(delete("/mealplanner"))
            .andExpect(status().isNoContent());
    }
}
