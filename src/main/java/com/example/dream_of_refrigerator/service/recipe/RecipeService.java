package com.example.dream_of_refrigerator.service.recipe;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.domain.recipe.IngredientRecipe;
import com.example.dream_of_refrigerator.domain.recipe.Recipe;
import com.example.dream_of_refrigerator.domain.recipe.RecipeDetail;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import com.example.dream_of_refrigerator.dto.ingredient.response.IngredientFindResponseDto;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeDetailFindResponseDto;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeFindResponseDto;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeRecommendFindResponseDto;
import com.example.dream_of_refrigerator.global.util.JwtUtils;
import com.example.dream_of_refrigerator.repository.recipe.RecipeDetailRepository;
import com.example.dream_of_refrigerator.repository.recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    // 조회
    @Transactional(readOnly = true)
    public List<RecipeFindResponseDto> findAll(Integer page){
        Pageable pageable = PageRequest.of(page, 10);

        Page<Recipe> result = recipeRepository.findAll(pageable);

        return result.stream()
                .map(RecipeFindResponseDto::new)
                .collect(Collectors.toList());
    }

    // 디테일 조회
    @Transactional(readOnly = true)
    public List<RecipeDetailFindResponseDto> findRecipeDetail(Long recipeId){
        List<RecipeDetail> result = recipeDetailRepository.findByRecipeId(recipeId);

        return result.stream()
                .map(RecipeDetailFindResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<RecipeFindResponseDto> search(String keyword){
        List<Recipe> result = recipeRepository.search(keyword);

        return result.stream()
                .map(RecipeFindResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<RecipeRecommendFindResponseDto> findRecommendRecipe(){
        String email = JwtUtils.getEmail();
        List<RecipeRecommendFindResponseDto> recommendRecipes = new ArrayList<>();
        List<Recipe> result = recipeRepository.findRecommendRecipes(email);

        for (Recipe recipe : result) {
            List<IngredientFindResponseDto> ingredients = new ArrayList<>();
            Set<IngredientRecipe> ingredientRecipes = recipe.getIngredientRecipes();
            for (IngredientRecipe ingredientRecipe : ingredientRecipes) {
                // 여기에는 recipe에 해당된 ingredient만 있음
                Ingredient ingredient = ingredientRecipe.getIngredient();
                Set<UserIngredient> userIngredients = ingredient.getUserIngredients();
                for (UserIngredient userIngredient : userIngredients) {
                    log.info("{}", userIngredient.getId());
                    IngredientFindResponseDto build = IngredientFindResponseDto.builder()
                            .id(userIngredient.getIngredient().getId())
                            .category(userIngredient.getIngredient().getCategory())
                            .name(userIngredient.getIngredient().getName())
                            .quantity(userIngredient.getQuantity())
                            .expirationDate(userIngredient.getExpirationDate())
                            .purchaseDate(userIngredient.getPurchaseDate())
                            .build();

                    ingredients.add(build);
                }

            }
            ingredients = ingredients.stream().sorted(Comparator.comparing(IngredientFindResponseDto::getExpirationDate)).collect(Collectors.toList());
            RecipeRecommendFindResponseDto recommendRecipe = RecipeRecommendFindResponseDto.builder()
                    .id(recipe.getId())
                    .ingredients(ingredients)
                    .title(recipe.getTitle())
                    .category(recipe.getCategory())
                    .thumbnail(recipe.getThumbnail())
                    .build();
            recommendRecipes.add(recommendRecipe);
        }

        return recommendRecipes;
    }
}
