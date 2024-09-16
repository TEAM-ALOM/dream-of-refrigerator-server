package com.example.dream_of_refrigerator.service.favorite;

import com.example.dream_of_refrigerator.domain.recipe.Recipe;
import com.example.dream_of_refrigerator.domain.user.Favorite;
import com.example.dream_of_refrigerator.domain.user.User;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeFindResponseDto;
import com.example.dream_of_refrigerator.global.util.JwtUtils;
import com.example.dream_of_refrigerator.repository.recipe.FavoriteRepository;
import com.example.dream_of_refrigerator.repository.recipe.RecipeRepository;
import com.example.dream_of_refrigerator.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class FavoriteService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final FavoriteRepository favoriteRepository;

    public String save(Long recipeId){
        String email = JwtUtils.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("계정이 존재하지 않습니다."));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피가 존재하지 않습니다."));

        Favorite favorite = Favorite.builder()
                .user(user)
                .recipe(recipe)
                .build();

        favoriteRepository.save(favorite);

        return "레시피 즐겨찾기 완료";
    }
    public String delete(Long recipeId){
        String email = JwtUtils.getEmail();

        Favorite favorite = favoriteRepository.findByRecipeIdAndUserEmail(recipeId, email)
                .orElseThrow(() -> new RuntimeException("즐겨찾기가 존재하지 않습니다."));

        favoriteRepository.delete(favorite);

        return "레시피 즐겨찾기 삭제 완료";
    }
    @Transactional(readOnly = true)
    public List<RecipeFindResponseDto> findFavorite(){
        String email = JwtUtils.getEmail();
        List<Recipe> result = new ArrayList<>();
        List<Favorite> favorites = favoriteRepository.findByUserEmail(email);
        for (Favorite favorite : favorites) {
            Recipe recipe = favorite.getRecipe();
            result.add(recipe);
        }

        return result.stream()
                .map(RecipeFindResponseDto::new)
                .collect(Collectors.toList());
    }
}
