package com.example.dream_of_refrigerator.repository.recipe;

import com.example.dream_of_refrigerator.domain.user.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByRecipeIdAndUserEmail(@Param("recipeId") Long recipeId, @Param("userEmail") String email);

    @Query("select f from favorite f left join fetch f.recipe r left join fetch f.user u where u.email=:userEmail")
    List<Favorite> findByUserEmail(@Param("userEmail") String email);
}
