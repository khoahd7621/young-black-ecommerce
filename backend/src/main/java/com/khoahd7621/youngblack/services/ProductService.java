package com.khoahd7621.youngblack.services;

import com.khoahd7621.youngblack.dtos.response.SuccessResponse;
import com.khoahd7621.youngblack.dtos.response.product.ListProductWithPaginateResponse;

public interface ProductService {

    public SuccessResponse<ListProductWithPaginateResponse> getAllProductsWithPaginate(Integer offset, Integer limit);

}