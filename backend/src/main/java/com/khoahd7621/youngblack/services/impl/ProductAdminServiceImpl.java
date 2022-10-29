package com.khoahd7621.youngblack.services.impl;

import com.khoahd7621.youngblack.dtos.request.product.CreateNewProductRequest;
import com.khoahd7621.youngblack.dtos.request.productvariant.ProductVariantOfCreateNewProduct;
import com.khoahd7621.youngblack.dtos.response.NoData;
import com.khoahd7621.youngblack.dtos.response.SuccessResponse;
import com.khoahd7621.youngblack.entities.Image;
import com.khoahd7621.youngblack.entities.Product;
import com.khoahd7621.youngblack.entities.ProductVariant;
import com.khoahd7621.youngblack.entities.VariantSize;
import com.khoahd7621.youngblack.exceptions.custom.CustomBadRequestException;
import com.khoahd7621.youngblack.mappers.ImageMapper;
import com.khoahd7621.youngblack.mappers.ProductMapper;
import com.khoahd7621.youngblack.mappers.ProductVariantMapper;
import com.khoahd7621.youngblack.mappers.VariantSizeMapper;
import com.khoahd7621.youngblack.repositories.ImageRepository;
import com.khoahd7621.youngblack.repositories.ProductRepository;
import com.khoahd7621.youngblack.repositories.ProductVariantRepository;
import com.khoahd7621.youngblack.repositories.VariantSizeRepository;
import com.khoahd7621.youngblack.services.ProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductAdminServiceImpl implements ProductAdminService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private VariantSizeRepository variantSizeRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductVariantMapper productVariantMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private VariantSizeMapper variantSizeMapper;

    @Override
    public SuccessResponse<NoData> createNewProduct(CreateNewProductRequest createNewProductRequest) throws CustomBadRequestException {
        Optional<Product> productDBOptFindByName = productRepository.findByName(createNewProductRequest.getName().toUpperCase());
        if (productDBOptFindByName.isPresent()) {
            throw new CustomBadRequestException("Product name already exist");
        }
        List<VariantSize> listVariantSizeDBOptFindBySku = variantSizeRepository.findBySkuStartsWith(createNewProductRequest.getColors().get(0).getSku());
        if (listVariantSizeDBOptFindBySku.size() > 0) {
            throw new CustomBadRequestException("Sku already exist");
        }

        Product product = productMapper.toProduct(createNewProductRequest);
        Product productDB = productRepository.save(product);

        for (ProductVariantOfCreateNewProduct productVariantOfCreateNewProduct : createNewProductRequest.getColors()) {
            ProductVariant productVariant = productVariantMapper.
                    toProductVariant(productVariantOfCreateNewProduct.getColor(), productDB);
            ProductVariant productVariantDB = productVariantRepository.save(productVariant);

            List<Image> imageList = imageMapper.
                    toListImagesWithProductVariant(productVariantOfCreateNewProduct.getImages(), productVariantDB);
            List<VariantSize> variantSizeList = variantSizeMapper.
                    toListVariantSizeWithProductVariant(
                            productVariantOfCreateNewProduct.getSizes(),
                            productVariantDB,
                            productVariantOfCreateNewProduct.getSku(),
                            productVariantOfCreateNewProduct.getColor().getName());
            imageRepository.saveAll(imageList);
            variantSizeRepository.saveAll(variantSizeList);
        }
        return new SuccessResponse<>(NoData.builder().build(), "Create new product successfully.");
    }
}
