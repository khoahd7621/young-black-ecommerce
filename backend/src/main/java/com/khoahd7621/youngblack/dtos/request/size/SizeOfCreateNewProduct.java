package com.khoahd7621.youngblack.dtos.request.size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SizeOfCreateNewProduct {
    private Integer id;
    private String size;
}
