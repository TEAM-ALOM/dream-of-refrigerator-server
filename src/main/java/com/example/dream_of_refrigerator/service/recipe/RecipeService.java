package com.example.dream_of_refrigerator.service.recipe;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.domain.recipe.IngredientRecipe;
import com.example.dream_of_refrigerator.domain.recipe.Recipe;
import com.example.dream_of_refrigerator.domain.recipe.RecipeDetail;
import com.example.dream_of_refrigerator.domain.user.Favorite;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import com.example.dream_of_refrigerator.dto.ingredient.response.IngredientFindResponseDto;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeDetailFindResponseDto;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeFindResponseDto;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeRecommendFindResponseDto;
import com.example.dream_of_refrigerator.global.util.JwtUtils;
import com.example.dream_of_refrigerator.repository.ingredient.IngredientRepository;
import com.example.dream_of_refrigerator.repository.ingredient.UserIngredientRepository;

import com.example.dream_of_refrigerator.repository.recipe.RecipeDetailRepository;
import com.example.dream_of_refrigerator.repository.recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final UserIngredientRepository userIngredientRepository;

    // 조회

    public List<RecipeFindResponseDto> findAll(Integer page){
        String email = JwtUtils.getEmail();
        Pageable pageable = PageRequest.of(page, 10);
        List<UserIngredient> userIngredients = userIngredientRepository.findByUserEmail(email);
        Page<Recipe> result = recipeRepository.findAll(pageable);

        return getRecipeFindResponseDto(result.stream().collect(Collectors.toList()), userIngredients);
    }

    // 디테일 조회

    public List<RecipeDetailFindResponseDto> findRecipeDetail(Long recipeId){
        List<RecipeDetail> result = recipeDetailRepository.findByRecipeId(recipeId);

        return result.stream()
                .map(RecipeDetailFindResponseDto::new)
                .collect(Collectors.toList());
    }


    public List<RecipeFindResponseDto> search(String keyword){
        if(keyword == null){
            throw new RuntimeException("키워드가 존재하지 않습니다.");
        }
        String email = JwtUtils.getEmail();
        List<Recipe> result = recipeRepository.search(keyword);
        List<UserIngredient> userIngredients = userIngredientRepository.findByUserEmail(email);

        return getRecipeFindResponseDto(result, userIngredients);
    }

    public List<RecipeFindResponseDto> findRecommendRecipe(){
        String email = JwtUtils.getEmail();

        List<Recipe> result = recipeRepository.findAll();
        List<UserIngredient> userIngredients = userIngredientRepository.findByUserEmail(email);

        List<RecipeFindResponseDto> recipeFindResponseDto = getRecipeFindResponseDto(result, userIngredients);
        recipeFindResponseDto.removeIf(x -> x.getIngredients().stream().noneMatch(IngredientFindResponseDto::getIsContained));

        return recipeFindResponseDto;
    }

    public List<RecipeFindResponseDto> findByCategory(String category, Integer page){
        String email = JwtUtils.getEmail();
        Pageable pageable = PageRequest.of(page, 10);
        Page<Recipe> result = recipeRepository.findByCategory(pageable, category);
        List<UserIngredient> userIngredients = userIngredientRepository.findByUserEmail(email);

        return getRecipeFindResponseDto(result.stream().collect(Collectors.toList()), userIngredients);
    }

    public List<RecipeFindResponseDto> findFavorite(Integer page){
        String email = JwtUtils.getEmail();
        Pageable pageable = PageRequest.of(page, 10);
        Page<Recipe> recipes = recipeRepository.findFavorite(pageable, email);
        List<UserIngredient> userIngredients = userIngredientRepository.findByUserEmail(email);

        return getRecipeFindResponseDto(recipes.stream().collect(Collectors.toList()), userIngredients);
    }

//    public List<RecipeFindResponseDto> findRandomRecipe(){
//        Random random = new Random();
//        random.nextLong(1, 537)
//    }
    private List<RecipeFindResponseDto> getRecipeFindResponseDto(List<Recipe> result, List<UserIngredient> userIngredients){
        List<RecipeFindResponseDto> recipes = new ArrayList<>();
        for (Recipe recipe : result) {
            Map<Long, IngredientFindResponseDto> ingredientMap = new HashMap<>();
            Set<IngredientRecipe> ingredientRecipes = recipe.getIngredientRecipes();
            for (IngredientRecipe ingredientRecipe : ingredientRecipes) {
                // 여기에는 recipe에 해당된 ingredient만 있음
                Ingredient ingredient = ingredientRecipe.getIngredient();
                IngredientFindResponseDto ingredientFindResponseDto = IngredientFindResponseDto.builder()
                        .id(ingredient.getId())
                        .name(ingredient.getName())
                        .category(ingredient.getCategory())
                        .isContained(false)
                        .build();
                ingredientMap.put(ingredient.getId(), ingredientFindResponseDto);
            }
            for (UserIngredient userIngredient : userIngredients) {
                Long id = userIngredient.getIngredient().getId();
                if (ingredientMap.containsKey(id)){
                    IngredientFindResponseDto ingredientFindResponseDto = ingredientMap.get(id);
                    ingredientFindResponseDto.setExpirationDate(userIngredient.getExpirationDate());
                    ingredientFindResponseDto.setIsContained(true);
                    ingredientFindResponseDto.setPurchaseDate(userIngredient.getPurchaseDate());
                    ingredientFindResponseDto.setQuantity(userIngredient.getQuantity());
                }
            }

            List<IngredientFindResponseDto> ingredients = new ArrayList<>(ingredientMap.values());
            ingredients = ingredients.stream()
                    .sorted(Comparator.comparing(IngredientFindResponseDto::getIsContained).reversed()
                            .thenComparing(IngredientFindResponseDto::getExpirationDate, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
            RecipeFindResponseDto recommendRecipe = RecipeFindResponseDto.builder()
                    .id(recipe.getId())
                    .ingredients(ingredients)
                    .title(recipe.getTitle())
                    .category(recipe.getCategory())
                    .thumbnail(recipe.getThumbnail())
                    .build();
            recipes.add(recommendRecipe);
        }


        return recipes;
    }


}
