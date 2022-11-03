package com.khoahd7621.youngblack.dtos.request.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class CreateNewCategoryRequest {
    @NotBlank(message = "Name of category is required")
    private String name;
}
