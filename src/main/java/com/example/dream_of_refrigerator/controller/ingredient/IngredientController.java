package com.example.dream_of_refrigerator.controller.ingredient;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import com.example.dream_of_refrigerator.dto.ingredient.response.BasicIngredientDto;
import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.IngredientFindAllDto;
import com.example.dream_of_refrigerator.service.ingredient.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController  //REST API 용 컨트롤러! 데이터(JSON)를 반환
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@Tag(name = "재료 API", description = "재료 조회 및 냉장고로의 재료 추가 관련 기능을 위한 API입니다.")
public class IngredientController {
    private final IngredientService ingredientService;

    //모든 재료 조회(Ingredient table에 저장된 모든 것 전체 조회)
    @Operation(summary = "전체 재료 조회", description = "등록되어있는 모든 재료들을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "전체 재료 조회 성공")
    @GetMapping//재료 id,name,categoryName
    public ResponseEntity<List<IngredientFindAllDto>> getAllIngredientsBasic() {
        List<IngredientFindAllDto> ingredients = ingredientService.findAllBasic();
        return ResponseEntity.ok(ingredients);
    }

    //카테고리별 재료 조회
    @Operation(summary = "카테고리별 재료 조회", description = "카테고리별로 등록되어있는 재료들을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "카테고리별 재료 조회 성공")
    @GetMapping("/category/{category}")//재료 name만 반환 (BasicIngredientDto)
    public ResponseEntity<List<BasicIngredientDto>> getIngredientsByCategory(@PathVariable String category) {
        List<BasicIngredientDto> ingredients = ingredientService.findByCategory(category);
        return ResponseEntity.ok(ingredients);
    }

    //재료 검색
    @Operation(summary = "재료 검색", description = "/api/ingredients/search?keyword=계 꼴로 요청. 한 글자 이상 같은 게 있으면 모두 조회됩니다.")
    @ApiResponse(responseCode = "200", description = "재료 검색 성공")
    @GetMapping("/search")
    public ResponseEntity<List<BasicIngredientDto>> searchIngredients(@RequestParam(required = false) String keyword) {
        List<BasicIngredientDto> ingredients = ingredientService.searchIngredients(keyword);
        return ResponseEntity.ok(ingredients);
    }

    //재료 등록(재료 검색 페이지에서 선택완료 클릭할 경우 실행)
    @Operation(summary = "재료 정보 등록", description = "추가하려는 재료를 선택한 후 해당 재료의 상세 정보를 사용자가 입력하여 등록합니다.")
    @ApiResponse(responseCode = "200", description = "재료 정보 등록 성공")
    @PostMapping("/send")   //선택 완료버튼 눌러서 보내기
    public ResponseEntity<List<UserIngredient>> registerIngredients(
            @RequestBody List<UserIngredientRequestDto> userIngredientRequestDtos){
        List<UserIngredient> userIngredients=ingredientService.register(userIngredientRequestDtos);
        return  ResponseEntity.ok(userIngredients);
    }
}
