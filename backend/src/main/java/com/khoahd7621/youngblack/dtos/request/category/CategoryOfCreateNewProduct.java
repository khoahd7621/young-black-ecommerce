package com.khoahd7621.youngblack.dtos.request.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryOfCreateNewProduct {
    private Integer id;
    private String name;
}
