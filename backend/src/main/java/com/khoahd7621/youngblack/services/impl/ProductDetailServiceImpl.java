package com.khoahd7621.youngblack.services.impl;

import com.khoahd7621.youngblack.dtos.response.SuccessResponse;
import com.khoahd7621.youngblack.dtos.response.productdetail.ProductDetailResponse;
import com.khoahd7621.youngblack.entities.Product;
import com.khoahd7621.youngblack.exceptions.custom.CustomNotFoundException;
import com.khoahd7621.youngblack.mappers.ProductDetailMapper;
import com.khoahd7621.youngblack.repositories.ProductRepository;
import com.khoahd7621.youngblack.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDetailMapper productDetailMapper;


    @Override
    public SuccessResponse<ProductDetailResponse> getProductDetailBySlug(String slug) throws CustomNotFoundException {
        Optional<Product> productOptional = productRepository.findByIsDeletedFalseAndSlug(slug);
        if (productOptional.isEmpty()) {
            throw new CustomNotFoundException("Don't exist product with this slug.");
        }
        ProductDetailResponse productDetailResponse = productDetailMapper.toProductDetailResponse(productOptional.get());
        return new SuccessResponse<>(productDetailResponse, "Get product detail success.");
    }
}
