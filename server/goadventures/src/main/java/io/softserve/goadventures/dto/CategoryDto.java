package io.softserve.goadventures.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private int id;

    private String categoryName;

    public CategoryDto(String categoryName){
        this.categoryName = categoryName;
    }
}
